package team_gold_zork;

import java.util.ArrayList;

/**
 * Deals with commands that pick up an object.
 * @author team_gold
 * @version 4
 */
class TakeCommand extends Command{
    private String itemName; //stores the name of the Item the player wishes to take.


    /**
     * Constructs a TakeCommand object.
     * @param itemName  the name of the Item the player wishes to pick up.
     */
    TakeCommand(String itemName){
        this.itemName = itemName;
    }


    /**
     * Executes a command to pick up an Item object.
     * @return  the result of the command.
     * @throws NoItemException if the item is not in the room.
     */
    String execute() throws NoItemException{

        ArrayList<String> inventory = state.getInventoryNames(false);
        if(inventory.contains(itemName)){
            return "You already have the " + itemName + ".\n";
        }

        Item takenItem = state.getItemInVicinityNamed(itemName);
        state.addToInventory(takenItem);
        state.getAdventurersCurrentRoom().remove(takenItem);
        return "Taken.\n";
    }
}
