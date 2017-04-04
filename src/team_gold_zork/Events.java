/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;


/**
 *This class deals with commands that can only be performed on certain objects.
 * Each ItemSpecificCommand instantiates an Event object, which parses the Item’s events--
 * including the class' Hashtable of verb keys and event String values
 * Then executes the appropriate methods to reflect those consequences
 * @author MargauxTucker
 */
public class Events {
    
    /**
     * Constructs an Event object.
     * @param event the desired action/event to be performed on an Item.
     * @param items the name of the Item to manipulate.
     */
    public Events(String event, Item items){
        
    }
    /**
     * Modifies the players score depending on the point value of the event/action
     * @param pointValue the amount of points to add to the player's score.
     */
    public void score(int pointValue){
        
    }
    /**
     * This method modifies the players health depending on the point value of the wound event/action
     * @param pointValue the amount of points to deduct from the player's health.
     */
    public void wound(int pointValue){
        
    }
    /**
     * The method modifies the players health to the point of the player not being able to function
     * and essentially kills the player
     */
    public void die(){
    
    }
    /**
     *This method will inform the player they have won if all the requirements 
     * for winning the game have been met
     */
    public void win(){
        
    }
    /**
     * This method allows an item to disappear and be replaced with an item of the given name
     * @param newItemName the name of the new existing item that replaced the old item
     */
    public void transform(String newItemName){
        
    }
    /**
     * This method will automatically take the player to a different room the-- room will be chosen at random
     */
    public void teleport(){
        
    }
    
   
    
}

