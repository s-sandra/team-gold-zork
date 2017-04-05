/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;


/**
 * This class deals with events that are triggered by commands, which are related to specific objects or characters.
 * Then executes the appropriate methods to reflect those consequences
 * @author MargauxTucker
 */
class Event {
    
    /**
     * Constructs an Event object related to ItemSpecificCommands
     * @param event describes the result/consequence of a command related to an Item.
     * @param item the Item which was involved in triggering the event.
     */
    Event(String event, Item item){
        
    }
    
    /**
     * This method will take in a string of Events obtained from the constructor,
     * parses them for meaning, and performs the appropriate methods.
     * @param events the string of events obtained form the constructor 
     */
    void execute(String events){
        
    }
    /**
     * Modifies the players score depending on the point value of Score event 
     * @param pointValue the amount of points to add to the player's score.
     */
    void score(int pointValue){
        
    }
    /**
     * This method modifies the players health depending on the point value of the wound event/action
     * @param pointValue the amount of points to deduct from the player's health.
     */
    void wound(int pointValue){
        
    }
    /**
     * The method modifies the players health to the point of the player not being able to function
     * and essentially kills the player
     */
    void die(){
    
    }
    /**
     *This method will modify the player state (probably by changing some boolean variable 
     * if all the requirements for winning the game are met)
     * for winning the game have been met
     */
    void win(){
        
    }
    /**
     * This method allows an item to disappear and be replaced with an item of the given name
     * @param newItemName the name of the new existing item that replaced the old item
     */
    void transform(String newItemName){
        
    }
    /**
     *This method will actually change the adventurer's current room
     */
    void teleport(){
        
    }
    /**
     * This method will turn a light on or off in a room
     * @param status determines whether or not the light should be turned on, or off
     * @author KatieMelhuish
     */
    void light(String status){
        
    }
    
}

