package team_gold_zork;

import java.util.ArrayList;

/**
 *
 * @author Sandra Shtabnaya
 */
public class Player extends Character{
    private Room currentRoom; //stores the player's current room.
    private Dungeon currentDungeon; //stores the player's current dungeon.
    private ArrayList<Item> inventory = new ArrayList<>(); //stores an adventurer's inventory.
    
    /**
	 * Returns the player's current room.
	 * @return currentRoom	the player's current room.
	 */
	Room getAdventurersCurrentRoom(){
		return currentRoom;
	}
        
        
        /**
	 * Changes the player's current room.
	 * @param room	the player's current room.
	 */
	void setAdventurersCurrentRoom(Room room){
		currentRoom = room;
	}
        
        
        /**
	 * Returns the player's current dungeon.
	 * @return currentDungeon the player's current dungeon.
	 */
	Dungeon getDungeon(){
		return currentDungeon;
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
		return getAdventurersCurrentRoom().getItemNamed(name);
	}


	/**
	 * Looks for and returns a desired item in the inventory.
	 * @param name	the name of the desired item in the inventory.
	 * @return	the item corresponding to the name, if found.
	 * @throws NoItemException	if the item is not found in the inventory.
	 */
	Item getItemFromInventoryNamed(String name) throws NoItemException {
		for(Item item : inventory){
			if(item.goesBy(name)){
				return item;
			}
		}
		throw new NoItemException("There's no " + name + " here.");
	}
}
