

package team_gold_zork;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Stores common information pertaining to all character types.
 * @author Lauren
 */
abstract class Character {
    Room currentRoom; //stores the character's current room.
    Dungeon currentDungeon; //stores the character's current dungeon.
    ArrayList<Item> inventory = new ArrayList<>(); //stores a Character's inventory.


    /**
     * Stores the state of the character to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    abstract void storeState(PrintWriter w);
    
    
    /**
     * Restores the state of a character from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the character description contains invalid contents.
     */
    abstract void restoreState(Scanner s, Dungeon d) throws IllegalSaveFormatException;
    
    
    /**
     * Returns the character's current dungeon.
     * @return currentDungeon the character's current dungeon.
     */
    Dungeon getCurrentDungeon(){
	return currentDungeon;
    }


    /**
     * Returns the character's current room.
     * @return currentRoom the character's current room.
     */
    Room getCurrentRoom(){
        return currentRoom;
    }
    
    /**
    * Changes the character's current Dungeon.
    * @param dungeon the character's current Dungeon.
    */
    void setCurrentDungeon(Dungeon dungeon){
	currentDungeon = dungeon;
    }


    /**
     * Changes the character's current room.
     * @param room the character's current room.
     */
    void setCurrentRoom(Room room){
        currentRoom = room;
    }
        
        
    /**
    * Returns the character's current dungeon.
    * @return currentDungeon the character's current dungeon.
    */
    Dungeon getDungeon(){
	return currentDungeon;
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
     * Determines if the character's inventory is empty.
     * @return if the inventory is empty.
     */
    boolean isEmptyInventory(){
        return inventory.isEmpty();
    }

}
