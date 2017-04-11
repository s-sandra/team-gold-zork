/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

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
    private Character character;
    GameState state = GameState.instance(); //stores the state of the game.
    
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
     * @param character the Character involved in triggering the event.
     */
    Event(String event, Character character){
        this.character = character;
    }
    
    /**
     * This method will take in a string of events obtained from the constructor,
     * parses it for meaning, and performs the appropriate methods.
     * @param events the string of events obtained form the constructor 
     */
    void execute(String events){
        String command = events.toLowerCase();
        if (command.startsWith("win")){
            win();
        }
        if (command.startsWith("die")){
            die();
        }
        if (command.startsWith("teleport")){
            teleport();
        }
        if(command.startsWith("disappear")){
           String line = events.substring(events.indexOf("(")+1);
           line = events.substring(0,line.indexOf(")"));
           String itemName = line;
           disappear(itemName);
        }
        
        if(command.startsWith("score")){
           String line = events.substring(events.indexOf("(")+1);
           line = events.substring(0,line.indexOf(")"));
           int points = Integer.parseInt(line);
           score(points);
        }
        if(command.startsWith("wound")){
           String line = events.substring(events.indexOf("(")+1);
           line = events.substring(0,line.indexOf(")"));
           int damage = Integer.parseInt(line);
           wound(damage);
        }
        
        if(command.startsWith("transform")){
            
        }
    }

    /**
     * Modifies the player's score depending on the point value of the Score event.
     * Once a verb first triggers a score event, it is removed from the Item's
     * Hashtable of verb-events.
     * @param pointValue the amount of points to add to the player's score.
     */
    void score(int pointValue){
        Player player = state.getAdventurer();
        player.addScore(pointValue);
    }
    /**
     * This method modifies the player's damage depending on the given point value.
     * @param pointValue the amount of points to add to the player's damage.
     */
    void wound(int pointValue){
        Player player = state.getAdventurer();
        player.addDamage(pointValue);
    }
    /**
     * This method increases the player's damage to the maximum threshold, thus
     * killing the player.
     */
    void die(){
        Player player = state.getAdventurer();
        player.kill();
    }
    /**
     *This method will change hasWon to true in the Player class.
     */
    void win(){
        Player player  = state.getAdventurer();
        player.setHasWon();
        
    }
    /**
     * This method will remove and item from the room, dungeon, and inventory
     * @param itemName the name of the item being removed
     */
    void disappear(String itemName){
        
    }
    /**
     * This method allows the item that triggered the event to disappear and be
     * replaced with an item of the given name.
     * @param newItemName the name of the existing item that replaces the old item.
     */
    void transform(String newItemName){
        
    }
    /**
     *This method will change the adventurer's current room to a randomly generated room,
     *other than the current room.
     */
    void teleport(){
       Room playersCurrentRoom = state.getAdventurer().getCurrentRoom();
       Collection<Room> temp = state.getAdventurer().getCurrentDungeon().getRooms();
       Room[] rooms = (Room[]) temp.toArray();
       Random rand = new Random();
       int n = rand.nextInt(rooms.length); 
    }
    /**
     * This method turns a light on or off in a room
     * @param status if the light should be turned on, or off
     * @author KatieMelhuish
     */
    void light(String status){
        
    }
}

