package team_gold_zork;

/**
 * Deals with commands that drop an object.
 * @author team_gold
 * @version 4
 */
class DropCommand extends Command{
    private String itemName; //stores the name of the Item the player wishes to drop.


    /**
     * Constructs a DropCommand object.
     * @param itemName  the name of the Item the player wishes to drop.
     */
    DropCommand(String itemName){
        this.itemName = itemName;
    }


    /**
     * Executes a command to drop an Item object.
     * @return  the result of the command.
     */
    String execute(){
        try{
            Item droppedItem = state.getAdventurer().getItemFromInventoryNamed(itemName);
            state.getAdventurer().removeFromInventory(droppedItem);
            state.getAdventurer().getCurrentRoom().add(droppedItem);
        }
        catch(NoItemException e){
            return "You don't have a " + itemName + ".\n";
        }

        state.getAdventurer().passTime();
        String healthWarning = state.getAdventurer().checkHealth();

        if(!healthWarning.isEmpty()){
            healthWarning += "\n";
        }

        return "Dropped.\n" + healthWarning;
    }
}
