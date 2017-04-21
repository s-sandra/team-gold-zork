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
class AutoKiller extends Character{
    
    /**
     * Creates a new AutoKiller from scratch.
     */
    AutoKiller(){
        name = " ";
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
     * @throws IllegalSaveFormatException If the AutoKiller description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
         String line = s.nextLine();
         
         //if the "Current room:" title is not found.
        if(!line.startsWith("Character Name:")){
            throw new IllegalSaveFormatException();
        }

        //reads in the player's current room.
        line = line.substring(line.indexOf(":") + 2);
        this.setName(line);
         
        line = s.nextLine();
        
        //if the "Current room:" title is not found.
        if(!line.startsWith("Current room:")){
            throw new IllegalSaveFormatException();
        }

        //reads in the player's current room.
        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        currentRoom = currentDungeon.getRoom(line);
        currentRoom.addNPC(this);
        line = s.nextLine();
    
    }
   
    /**
     * Checks if the AutoKiller's current room is a dead end.
     * @return boolean hasExit If the room has another exit.
     */
    boolean hasExit()
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
   String kill() 
   { String kill = null; 
       if (hasExit()){
           kill = name + " is sleeping pass through carefully";}
       else {
           kill = name + " attacked you as soon as you entered the room! \n"; 
          player.kill();
          kill = kill + "You died! Meowch";
       }
       return kill;
       
   }
    
   /**
    * describes what the npc is doing 
    * @return desc
    */
   String describe(){
      desc = desc +  name + " looms in the darkness\n"; 
      desc = desc + kill();
      return desc; 
   }
   
  
   /**
    * sets name
    * @param name 
    */
   void setName(String name){
       name = this.name;
   }
    
}
