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
        
    }
   
    /**
     * Checks if the AutoKiller's current room is a dead end.
     * @return boolean hasExit If the room has another exit.
     */
    boolean hasExit()
    {
      return true;
    } 
    
    
    /**
     * Triggers a die event if the room is a dead end. 
     */
   void kill() 
   {
       
   }
    
    
}