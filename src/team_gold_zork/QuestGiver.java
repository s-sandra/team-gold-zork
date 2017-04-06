/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

import java.io.PrintWriter;
import java.util.Scanner;
/**
 * Stores information pertaining to QuestGiver type NPC
 * @author Lauren
 */
public class QuestGiver extends Character{
    boolean hasItem = false; 
    Item itemToLookFor = null;
    Item reward = null;
    
    /**
     * Creates a new QuestGiver from scratch.
     * @param itemToLookFor the Item the QuestGiver wants.
     * @param reward the Item the QuestGiver exchanges for the Item it wants.
     */
    QuestGiver(Item itemToLookFor, Item reward){
    }
    
    /**
     * Stores the state of the QuestGiver to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    }
    
    /**
     * Restores the state of a QuestGiver from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the QuestGiver description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
    }
     
    /**
     * looks in Player's inventory and checks if player is holding Item lookingFor
     * if player is it returns true 
     * 
     * @return boolean hasItem
     */
    boolean hasItem (){
        return hasItem; 
    }
    
    /**
     * This method adds the QuestGiver's reward to the player's inventory, while
     * removing the QuestGiver's desired item from the player's inventory.
     */
    void giveReward(){
    }
}