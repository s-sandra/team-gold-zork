package team_gold_zork;

import java.util.ArrayList;

/**
 * Deals with commands to view a player's inventory.
 * @author team_gold
 * @version 4
 */
class InventoryCommand extends Command{

    /**
     * Constructs an InventoryCommand object.
     */
    InventoryCommand(){}


    /**
     * Executes a command to view a player's inventory.
     * @return  the message detailing the inventory contents.
     */
    String execute(){
        ArrayList<String> items = state.getAdventurer().getInventoryNames(false);
        String inventory = "You are carrying:";
        if(items.isEmpty()){
            return "You are empty handed.\n";
        }

        for(String item : items){
           inventory += "\n" + "A " + item + ".";
        }
        return inventory + "\n";
    }
}
