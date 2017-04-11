package team_gold_zork;

/**
 * Deals with a user's request to print out the player's health.
 * @author Sandra Shtabnaya
 */
class HealthCommand extends Command{
    
    /**
     * Constructs a HealthCommand object.
     */
    HealthCommand(){
        
    }
    
    /**
     * Executes the command to print the player's health.
     * @return A message describing the state of the player's health. 
     */
    String execute(){
        Player player = state.getAdventurer();
        String playerHealth = player.getHealthWarning();
        return playerHealth +"\n";
    }
}
