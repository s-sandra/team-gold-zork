package team_gold_zork;

import java.util.Scanner;
/**
 * This class stores all the exits in a Room. 
 * @author team_gold
 * @version 4
 */
public class Exit {
	private String dir; //stores the direction.
	private Room currentRoom; //stores the room the exit is located in.
	private Room destination; //stores where the exit leads. 
	
	/**
	 * Constructs an exit by reading a bork file.
	 * @param s	The name of the scanner reading the file.
	 * @param d	The name of the dungeon containing the rooms.
	 */
	public Exit(Scanner s, Dungeon d) throws NoExitException {
		String input = s.nextLine();
		
		if(input.equals("===")){
			throw new NoExitException();
		}
		
		//while the exit description has not ended.
		while(!input.equals("---")){
			
			//gets the room we want to add an exit to from the dungeon. 
			Room room = d.getRoom(input);
			
			String dir = s.nextLine();
			Room dest = d.getRoom(s.nextLine());
			room.addExit(new Exit(dir, room, dest));
			input = s.nextLine();
		}
	
	}
	
	/**
	 * Creates an exit given its direction and destination.
	 * @param dir the direction of the exit.
	 * @param src the room containing the exit.
	 * @param dest the room the exit leads to.
	 */
	Exit(String dir, Room src, Room dest){
		this.dir = dir;
		currentRoom = src;
		destination = dest;
	}
	
	
	/**
	 * Returns a description of where the exit leads.
	 * @return The description of the direction and destination.
	 */
	String describe(){
		return "You can go " + dir + " to " + destination.getTitle();
	}
	
	
	/**
	 * Returns the direction of the exit.
	 * @return dir The direction of the exit.
	 */
	public String getDir(){
		return dir;
	}
	
	
	/**
	 * Returns the room in which the exit is located.
	 * @return currentRoom The room the exit belongs to.
	 */
	public Room getSrc(){
		return currentRoom;
	}
	
	
	/**
	 * Returns where the exit leads.
	 * @return destination Where the exit leads.
	 */
	public Room getDest(){
		return destination;
	}

}

/**
 * This Exception is thrown when there are no more exits to 
 * read from a file.
 */
class NoExitException extends Exception{}
