package team_gold_zork;

/**
 * Deals with the command allowing the user to fall asleep. 
 * @author Sandra Shtabnaya
 */
class SleepCommand extends Command{
    
    /**
     * Constructs a SleepCommand object.
     */
    SleepCommand(){
        
    }
    
    /**
     * Executes the command to fall asleep.
     * @return The result of the sleep command. 
     */
    String execute(){
        return state.getAdventurer().sleep() + "\n";
    }
}
