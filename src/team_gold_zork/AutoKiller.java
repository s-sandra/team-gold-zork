/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class stores information pertaining to the AutoKiller type NPC
 * @author Lauren
 */
class AutoKiller extends NPC{

    private String deathMsg = "";

    /**
     * Creates a new AutoKiller from a dungeon file.
     * @param s the scanner reading the zork file.
     * @param d the Dungeon the AutoKiller is in.
     */
    AutoKiller(Scanner s, Dungeon d){
        String input = s.nextLine();
        name = input;

        input = s.nextLine();
        desc = input;

        input = s.nextLine();
        input = input.substring(input.indexOf(":") + 2); //chops off data to the left of colon.
        

        //while the death description has not ended,
        while(!input.startsWith("Current room:")){
            deathMsg += input + "\n";
            input = s.nextLine();
        }

        input = input.substring(input.indexOf(":") + 2); //chops off data to the left of colon.
        currentRoom = d.getRoom(input);
        d.getRoom(input).add(this);

        input = s.nextLine();
        //while the verb description has not ended.
        while(!input.equals("---")){
            String verbLine = input;
            String verb = "";
            String message = "";


            //checks to see if the verb triggers events.
            if(verbLine.contains("[")){
                verb = verbLine.substring(0, verbLine.indexOf("[")); //chops off data to the right of bracket.
                String event = verbLine.substring(verbLine.indexOf("[") + 1, verbLine.indexOf("]")); //gets all events between brackets.
                events.put(verb, event);
            }
            else{
                verb = verbLine.substring(0, verbLine.indexOf(":")); //chops off data to the right of colon.
            }

            message = verbLine.substring(verbLine.indexOf(":") + 1); //chops off data to the left of colon.
            input = s.nextLine();

            //while the message description has not ended.
            while(!input.contains(":") && !input.equals("---")){
                message += "\n" + input;
                input = s.nextLine();
            }
            messages.put(verb, message);
            d.addCharacterVerb(verb);
        }


    }
    
    /**
     * Stores the state of the AutoKiller to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
    }


    /**
     * Restores the state of a AutoKiller from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @param d the dungeon the AutoKiller is in.
     * @throws IllegalSaveFormatException If the AutoKiller description contains invalid contents.
     */
    void restoreState(Scanner s, Dungeon d)throws IllegalSaveFormatException{
         String line = s.nextLine();
         
    }
   
    /**
     * Checks if the AutoKiller's current room is a dead end.
     * @return boolean hasExit If the room has another exit.
     */
    private boolean hasExit()
    {
      if (getCurrentRoom().getNumExit() < 2){
          return false;
          
      }
      else{
          return true;
      }
  
    }


    /**
     * Triggers a die event if the room is a dead end.
     */
    void kill()
    {
        player.kill(deathMsg);
    }


    /**
     * Reacts to a player entering the current room
     * of the AutoKiller. It kills the player if they have
     * no avenue of escape.
     * @return a message detailing the action of the AutoKiller.
     */
    String greetPlayer(){
        if(!hasExit() && currentRoom.equals(player.currentRoom)){
            kill();
        }
        return "";
    }
    
}
