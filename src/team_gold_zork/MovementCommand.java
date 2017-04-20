package team_gold_zork;

 /** Deals with commands that move a player to a new room.
 * @author team_gold
 * @version 4
 */
class MovementCommand extends Command{
    private String dir; //stores the direction the player wishes to go.


    /**
     * Constructs a MovementCommand object.
     * @param dir  the direction the player wishes to go.
     */
    MovementCommand(String dir){
        this.dir = dir;
    }


    /**
     * Executes a command to move to a new room.
     * @return The result of the movement command.
     */
    String execute(){
        Player adventurer = state.getAdventurer();
        GameState state = GameState.instance();
		Room currentRoom = adventurer.getCurrentRoom();
		Room newRoom;

		try{ //the exit may be locked.
            newRoom = currentRoom.leaveBy(dir);
        }
		catch(LockedExitException e){
		    return e.getMessage();
        }

		if(newRoom != null){
            adventurer.setCurrentRoom(newRoom);
		}
		else{
			return "You can't go " + dir + ".\n";
		}

        state.getAdventurer().passTime();
		String healthWarning = state.getAdventurer().checkHealth();

        if(!healthWarning.isEmpty()){
            healthWarning += "\n";
        }

		return adventurer.getCurrentRoom().describe() + healthWarning;
    }
}
