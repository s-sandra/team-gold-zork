/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Stores information pertaining to ItemStealer type NPC
 * @author Lauren
 */
public class ItemStealer extends Character{
    boolean hasItem = false; 
    Item lookingfor = null;
    
       /**
     * Creates a new ItemStealer from scratch.
     */
    ItemStealer(){
    }
    
    /**
     * Stores the state of the ItemStealer to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    }
    /**
     * Setter for ItemStealer's item they take
     * @param lookingFor 
     */
    void setItemSought(Item lookingFor)
    {
        this.lookingFor = lookingfor; 
    }
     
    /**
     * Restores the state of a ItemStealer from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the player description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
    }

}
