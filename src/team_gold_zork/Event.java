/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;


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
        
    }

    /**
     * Modifies the player's score depending on the point value of the Score event.
     * Once a verb first triggers a score event, it is removed from the Item's
     * Hashtable of verb-events.
     * @param pointValue the amount of points to add to the player's score.
     */
    void score(int pointValue){
        
    }
    /**
     * This method modifies the player's damage depending on the given point value.
     * @param pointValue the amount of points to add to the player's damage.
     */
    void wound(int pointValue){
        
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
        
    }
    /**
     * This method turns a light on or off in a room
     * @param status if the light should be turned on, or off
     * @author KatieMelhuish
     */
    void light(String status){
        
    }
}

