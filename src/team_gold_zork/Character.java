
package team_gold_zork;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 *
 * @author Lauren
 */
abstract public class Character {
    private Room currentRoom; //stores the character's current room.
    private Dungeon currentDungeon; //stores the character's current dungeon.
    
    
    abstract void storeState(PrintWriter w);
    
    abstract void restoreState(Scanner s);
    
    /**
     * Returns the character's current room.
     * @return currentRoom the character's current room.
     */
    Room getCurrentRoom(){
	return currentRoom;
    }
    
    
    /**
    * Changes the character's current room.
    * @param room the character's current room.
    */
    void setCurrentRoom(Room room){
	currentRoom = room;
    }
        
        
    /**
    * Returns the character's current dungeon.
    * @return currentDungeon the character's current dungeon.
    */
    Dungeon getDungeon(){
	return currentDungeon;
    }
}
