package team_gold_zork;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Stores methods pertaining to non-playable characters.
 */
public class NPC extends Character{
    String name; //stores the name of the NPC.
    String desc; //stores a description of the NPC.

    /**
     * This constructs NPCs given a scanner reading a zork file.
     * @param s the scanner reading the zork file.
     * @param d the dungeon to add the character to.
     * @throws NoCharacterException if the character section has ended.
     */
    static NPC loadNPCs(Scanner s, Dungeon d) throws NoCharacterException{
        String input = s.nextLine();

        //if the end of the rooms section has been reached,
        if(input.equals("===")){
            throw new NoCharacterException();
        }

        if(input.equals("AutoKiller")){
            return new AutoKiller(s, d);
        }
//        if(input.equals("QuestGiver")){
//            return new QuestGiver(s, d);
//        }
//        if(input.equals("ItemStealer")){
//            return new ItemStealer(s, d);
//        }
        return null;
    }

    /**
     * Stores the state of the NPC to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){}

    /**
     * Restores the state of an NPC from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the NPC description contains invalid contents.
     */
    void restoreState(Scanner s) throws IllegalSaveFormatException {}

    /**
     * Used by NPCs to react to a player entering the room.
     */
    void greetPlayer(){}

    /**
     * This method will return a description of the character.
     */
    String describe(){
        return desc;
    }


    /**
     * Returns the name of the character.
     * @return the name of the character.
     */
    String getName(){
        return name;
    }
}

/** Thrown when there are no more characters to read from
 * a file.
 */
class NoCharacterException extends Exception{}
