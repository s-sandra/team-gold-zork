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
        state.getAdventurer().passTime();
        return "";
    }
}
