/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;


/**
 * This class deals with events that are triggered by commands related to 
 * specific objects or characters.
 * @author MargauxTucker
 */
class Event {
    private String events;
    private Item item;
    GameState state = GameState.instance(); //stores the state of the game.
    Player player = state.getAdventurer();
    
    /**
     * Constructs an Event object related to ItemSpecificCommands
     * @param event describes the consequence of a command related to an Item.
     * @param item the Item involved in triggering the event.
     */
    Event(String event, Item item){
        this.events = event;
        this.item = item;
    }
    
    /**
     * Constructs an Event object related to CharacterSpecificCommands
     * @param event describes the consequence of a command related to a Character.
     */
    Event(String event){
        events = event;
    }
    
    /**
     * This method will take in a string of events obtained from the constructor,
     * parses it for meaning, and performs the appropriate methods.
     * @return a message detailing the result of the execution.
     * @throws NoItemException if the transform event had an invalid item name parameter.
     */
    String execute() throws NoItemException{
        String message = "";
        String[] result = events.split(",");

        //executes all events
        for(String event: result){
            if (event.startsWith("Win")){
                win();
            }
            else if (event.startsWith("Die")){
                die();
            }
            else if (event.startsWith("Teleport")){
                message += teleport();
            }
            else if(event.startsWith("Disappear")){
                disappear();
            }
            else if(event.startsWith("Score")){
                String line = event.substring(event.indexOf("(")+1);
                line = line.substring(0,line.indexOf(")"));
                int points = Integer.parseInt(line);
                score(points);
            }
            else if(event.startsWith("Wound")){
                String line = event.substring(event.indexOf("(")+1);
                line = line.substring(0,line.indexOf(")"));
                int value = Integer.parseInt(line);

                try{
                    wound(value);
                    message += player.getDamageWarning() + "\n";
                }
                catch(HealthStateException e){
                    message += e.getMessage();
                }
            }
            else if(event.startsWith("Hunger")){
                String line = event.substring(event.indexOf("(")+1);
                line = line.substring(0,line.indexOf(")"));
                int value = Integer.parseInt(line);

                try{
                    hunger(value);
                    message += "After your snack, " + player.getHungerWarning().toLowerCase() + "\n";
                }
                catch(HealthStateException e){
                    message += e.getMessage();
                }
            }
            else if(event.startsWith("Transform")){
                String transformedItemName = event.substring(event.indexOf("(")+1);
                transformedItemName = transformedItemName.substring(0,transformedItemName.indexOf(")"));

                try{ //the transformed item may not exist in the zork file.
                    transform(transformedItemName);
                }
                catch(NoItemException e){
                    throw new NoItemException("The transform event parameter for the item "
                            + item.getPrimaryName() + " does not exist in the dungeon.");
                }
            }
             else if(event.startsWith("Light")){
                String status = event.substring(event.indexOf("(")+1);
                status = status.substring(0,status.indexOf(")"));
                light(status);
                message += player.getCurrentRoom().describe();
            }

        }

        return message;
    }

    /**
     * Modifies the player's score depending on the point value of the Score event.
     * Once a verb first triggers a score event, it is removed from the Item's
     * Hashtable of verb-events.
     * @param pointValue the amount of points to add to the player's score.
     */
    private void score(int pointValue){
        player.addScore(pointValue);
    }


    /**
     * This method modifies the player's damage depending on the given point value.
     * @param pointValue the amount of points to add to the player's damage.
     * @throws HealthStateException if pointValue is negative and the player is already healed.
     */
    private void wound(int pointValue) throws HealthStateException{
        Player player = state.getAdventurer();
        player.addDamage(pointValue);
    }

    /**
     * This method modifies the player's hunger depending on the given point value.
     * @param pointValue the amount of points to add to the player's hunger.
     * @throws HealthStateException if pointValue is negative and the player is not hungry.
     */
    private void hunger(int pointValue) throws HealthStateException{
        Player player = state.getAdventurer();
        player.addHunger(pointValue);
    }


    /**
     * This method increases the player's damage to the maximum threshold, thus
     * killing the player.
     */
    private void die(){
        Player player = state.getAdventurer();
        player.kill();
    }
    /**
     *This method will change hasWon to true in the Player class.
     */
    private void win(){
        Player player  = state.getAdventurer();
        player.setHasWon();
        
    }

    /**
     * This method will remove the item which triggered the event
     * from the room, dungeon, and inventory.
     */
    private void disappear(){
        item.disappear();
    }

    /**
     * This method allows the item that triggered the event to disappear and be
     * replaced with an item of the given name.
     * @param newItemName the name of the existing item that replaces the old item.
     * @throws NoItemException if the transformed item is not found in the dungeon.
     */
    private void transform(String newItemName) throws NoItemException{
        Item transformedItem = player.getCurrentDungeon().getItem(newItemName);
        try{
            player.getItemFromInventoryNamed(item.getPrimaryName());
            player.addToInventory(transformedItem);
        }
        catch(NoItemException e){}
        disappear();
        player.getCurrentRoom().add(transformedItem);
    }

    /**
     *This method will change the adventurer's current room to a randomly generated room,
     *other than the current room.
     * @return a message detailing the result of the teleport event.
     */
    private String teleport(){
       Room playersCurrentRoom = player.getCurrentRoom();
       Collection<Room> temp = player.getCurrentDungeon().getRooms();
       ArrayList<Room> rooms = new ArrayList<>(temp);

       Random rand = new Random();
       int n = rand.nextInt(rooms.size());
       Room randomRoom = rooms.get(n);

       //generates a new random room if the last one was the same as the player's current room.
       while(playersCurrentRoom.equals(randomRoom)){
           n = rand.nextInt(rooms.size());
           randomRoom = rooms.get(n);
       }

       player.setCurrentRoom(randomRoom);
       return "The " + item.getSecondaryName() + " has teleported you to " + randomRoom.describe();
    }
    /**
     * This method turns a light on or off in a room
     * @param status if the light should be turned on, or off
     * @author KatieMelhuish
     */
    private void light(String status){
        if(status.equals("on")){
            item.setIsOn(true);
        }
        else if(status.equals("off")){
            item.setIsOn(false);
        }
    }
}

