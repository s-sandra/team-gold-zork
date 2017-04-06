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
        GameState state = GameState.instance();
		Room currentRoom = state.getAdventurersCurrentRoom();
		Room newRoom = currentRoom.leaveBy(dir);

		if(newRoom != null){
			state.setAdventurersCurrentRoom(newRoom);
		}
		else{
			return "You can't go " + dir + ".\n";
		}

		return state.getAdventurersCurrentRoom().describe();
    }
}
