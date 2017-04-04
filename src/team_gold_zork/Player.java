package team_gold_zork;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 *
 * @author Sandra Shtabnaya
 */
class Player extends Character{
    private ArrayList<Item> inventory = new ArrayList<>(); //stores an adventurer's inventory.
 
    /**
     * Creates a new player from scratch.
     */
    Player(){
    }
    
    /**
     * Stores the state of the player to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    }
    
    /**
     * Restores the state of a player from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the player description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
    }
    
    /**
     * Gets the names of all the items in a player's inventory.
     * @param isPrimary whether the names should be in camelCase.
     * @return the names of all the items in the inventory.
     */
    ArrayList<String> getInventoryNames(boolean isPrimary){
	ArrayList<String> inventoryNames = new ArrayList<>();
	for(Item item : inventory){
		if(isPrimary){
			inventoryNames.add(item.getPrimaryName());
		}
		else{
			inventoryNames.add(item.getSecondaryName());
		}
	}
	return inventoryNames;
    }


    /**
     * Adds an item to a player's inventory.
     * @param item the item to add.
     */
    void addToInventory(Item item){
            inventory.add(item);
    }


    /**
     * Removes an item from a player's inventory.
     * @param item the item to remove.
     */
    void removeFromInventory(Item item){
            inventory.remove(item);
    }


    /**
     * Looks for and returns a desired item in the current room.
     * @param name	the name of the desired item in the room.
     * @return	the item corresponding to the name, if found.
     * @throws NoItemException if the item is not found in the room.
     */
    Item getItemInVicinityNamed(String name) throws NoItemException{
            return getCurrentRoom().getItemNamed(name);
    }


    /**
     * Looks for and returns a desired item in the inventory.
     * @param name the name of the desired item in the inventory.
     * @return the item corresponding to the name, if found.
     * @throws NoItemException if the item is not found in the inventory.
     */
    Item getItemFromInventoryNamed(String name) throws NoItemException {
            for(Item item : inventory){
                    if(item.goesBy(name)){
                            return item;
                    }
            }
            throw new NoItemException("There's no " + name + " here.");
    }    
    
    
    /**
     * Modifies the player's hunger.
     * @param n The number to add to the player's hunger. This number 
     * is negative if the hunger should diminish.
     */
    void addHunger(int n){
        
    }
    
    
    /**
     * Modifies the player's fatigue.
     * @param n The number to add to the player's fatigue. This number 
     * is negative if the fatigue should diminish.
     */
    void addFatigue(int n){
        
    }
    
    /**
     * Modifies the player's damage.
     * @param n The number to add to the player's damage. This number 
     * is negative if the player is wounded and positive if the player
     * is healed.
     */
    void addDamage(int n){
        
    }
    
    /**
     * Prints out the state of the player's health, if a 
     * threshold for damage, fatigue or hunger has been reached. 
     * @return the warning message associated with the player's health.
     * If a threshold has not been reached, then it returns an empty string.
     */
    String getHealthWarning(){
        return "";
    }
        
}
