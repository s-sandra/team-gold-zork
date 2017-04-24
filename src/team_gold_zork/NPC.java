package team_gold_zork;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;

/**
 * Stores methods pertaining to non-playable characters.
 */
public class NPC extends Character{
    String name; //stores the name of the NPC.
    String desc; //stores a description of the NPC.
    GameState state = GameState.instance(); //stores the state of the game.
    Player player = state.getAdventurer();
    Hashtable<String, String> messages = new Hashtable<>(); //stores the message corresponding with the verb key.
    Hashtable<String, String> events = new Hashtable<>(); //stores the events corresponding with the verb key.

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
          if(input.equals("QuestGiver")){
            return new QuestGiver(s, d);
         }
     if(input.equals("ItemStealer")){
           return new ItemStealer(s, d);
   }
        return null;
    }


    /**
     * Restores the state of all NPCs in the dungeon.
     * @param s the Scanner reading the .sav file.
     * @param d the Dungeon the NPCs are in.
     * @throws IllegalSaveFormatException if the Character description contains invalid contents.
     */
    static void reloadNPCs(Scanner s, Dungeon d) throws IllegalSaveFormatException{
        String name = s.nextLine();
        NPC npc = d.getNPCNamed(name);
        npc.restoreState(s, d);
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
    void restoreState(Scanner s, Dungeon d) throws IllegalSaveFormatException {}

    /**
     * Used by NPCs to react to a player entering the room.
     * @return a message detailing the NPC's action.
     */
    String greetPlayer(){return "";}

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


    /**
     * Accepts a gift from the player and adds it
     * to the NPC's inventory.
     * @param item the item from the player.
     * @return the NPC's reaction to the gift.
     */
    String give(Item item){
        addToInventory(item);
        String message = messages.get("give");
        if(message != null){
            return message;
        }
        return "The " + name + " has accepted your " + item.getSecondaryName() + ".";
    }


    /**
     * Gets the message associated with a verb.
     * @param verb  the action to be done on the item.
     * @return  the message detailing the result of the action.
     */
    public String getMessageForVerb(String verb){
        return messages.get(verb);
    }


    /**
     * Gets the event(s) associated with a verb.
     * @param verb  the action to be done on the item.
     * @return  the message detailing the event(s) of the action.
     */
    public String getEventForVerb(String verb){
        return events.get(verb);
    }
}

/** Thrown when there are no more characters to read from
 * a file.
 */
class NoCharacterException extends Exception{}
