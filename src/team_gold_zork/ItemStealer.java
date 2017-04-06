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
    private boolean hasItem = false;
    private Item itemToLookFor = null;
    
    /**
     * Creates a new ItemStealer from scratch.
     * @param itemToLookFor The item the ItemStealer wants.
     */
    ItemStealer(Item itemToLookFor){
    }
    
    /**
     * Stores the state of the ItemStealer to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    }

     
    /**
     * Restores the state of a ItemStealer from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the ItemStealer description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
    }

}
