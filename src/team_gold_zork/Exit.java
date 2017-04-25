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
    
    private boolean isLocked;
    private String keyName;
    private String doorName; //the name of the exit. 

    /**
     * Constructs an exit by reading a bork file.
     * @param s	The name of the scanner reading the file.
     * @param d	The name of the dungeon containing the rooms.
     * @throws NoExitException If there are no more exits in the bork file. 
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

                    Exit door = new Exit(dir, room, dest);
                    room.addExit(door);
                    input = s.nextLine();

                    if(input.startsWith("isLocked")){
                        door.isLocked = true;
                        input = s.nextLine();

                        door.doorName = input.substring(input.indexOf(":") + 1); //chops off data to the left of semi-colon.
                        input = s.nextLine();

                        door.keyName = input.substring(input.indexOf(":") + 1); //chops off data to the left of semi-colon.
                        input = s.nextLine();
                    }
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
        if(isLocked) {
            return "There is a closed " + doorName + " to the " + dir;
        }
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
    
    public boolean isLocked(){
        return isLocked; 
    }


    /**
     * Returns where the exit leads.
     * @return destination Where the exit leads.
     */
    public Room getDest(){
            return destination;
    }
    
    /**
     * Unlocks a locked exit.
     */
    void unlock(){
        isLocked = false;
    }


    /**
     * Locks an unlocked exit.
     */
    void lock(){
        isLocked = true;
    }
    
    
    /**
     * Determines if the provided key fits into the door.
     * @param keyName the name of the Item to attempt to open the door with.
     * @return whether the key can open the door.
     */
    boolean keyFits(String keyName){
        return keyName.toLowerCase().equals(this.keyName);
    }
    
    
    /**
     * Determines if the Exit goes by the given name.
     * @param name the name in question. 
     * @return if the Exit goes by the given name.
     */
    boolean isNamed(String name){
        return name.equals(this.doorName);
    }

}


/**
 * This Exception is thrown when there are no more exits to 
 * read from a file.
 */
class NoExitException extends Exception{}
