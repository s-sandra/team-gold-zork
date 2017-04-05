/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;
public class AutoKiller extends Character{
    boolean hasExit = false; 
    
       /**
     * Creates a new AutoKiler from scratch.
     */
    AutoKiller(){
    }
    
    /**
     * Stores the state of the Auto to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    } 
    /**
     * Restores the state of a AutoKiller from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the player description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
    }
   
    /**
     * Checks if the room the AutoKiller is in has exits
     * @return boolean hasExit
     */
    boolean hasExit()
    {
      return hasExit; 
    } 
    /**
     * checks if hasExit and if it = false then triggers die event
     */
   void kill() 
   {
       
    }
    
    
}