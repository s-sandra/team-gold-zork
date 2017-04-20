package team_gold_zork;

/**
 * Deals with the user's attempt to open a locked door.
 * @author Sandra Shtabnaya
 */
class UnlockCommand extends Command {
    private String key;
    private String door;
    
    /**
     * Constructs an UnlockCommand object.
     * @param door the name of the locked door.
     * @param key the name of the Item to open the door with. 
     */
    UnlockCommand(String door, String key){
        this.key = key;
        this.door = door;
    }
    
    
    /**
     * Executes the command to unlock a closed door.
     * @return The result of the unlock command. 
     */
    String execute(){
        if(key.isEmpty()){
            return "Open the " + door + " with what?\n";
        }
        else if(door.isEmpty()){
            return "Open what with the " + key + "?\n";
        }

        Player adventurer = state.getAdventurer();
        Exit lockedDoor;
        try{
            lockedDoor = adventurer.getCurrentRoom().getExitNamed(door);
        }
        catch(NoExitException e){
            return "There's no exit named " + door + " here.\n";
        }

        adventurer.passTime();
        String healthWarning = state.getAdventurer().checkHealth();

        if(!healthWarning.isEmpty()){
            healthWarning += "\n";
        }

        try{
            adventurer.getItemFromInventoryNamed(key);
        }
        catch(NoItemException e){
            try{
                adventurer.getItemInVicinityNamed(key);
            }
            catch(NoItemException w){
                return "You don't have the " + key + ".\n";
            }
        }

        if(lockedDoor.keyFits(key)){
            lockedDoor.unlock();
            return "Unlocked.\n" + healthWarning;
        }

        return "You can't open the " + door + " with the " + key + ".\n" + healthWarning;
    }
}
