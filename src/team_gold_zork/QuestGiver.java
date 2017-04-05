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
 * Stores information pertaining to QuestGiver type NPC
 * @author Lauren
 */
public class QuestGiver extends Character{
    boolean hasItem = false; 
    Item lookingfor = null;
    String quest = null; 
    boolean accepted = false; 
    Item reward; 
    
       /**
     * Creates a new QuestGiver from scratch.
     */
    QuestGiver(){
    }
    
    /**
     * Stores the state of the QuestGiver to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    }
    
        /**
     * Restores the state of a ItemStealer from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the player description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
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
     * Gives the quest to the user 
     * @return String Quest
     */
     String giveQuest(){
         return (quest);
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
     * this looks at hasItem and if hasItem = true and player offered lookingFor to QuestGiver 
     * then takes the item from player and adds it to its own inventory 
     * @return notice that Item has been accepted
     */
    String accept(){
        accepted = true;
        return ("This character accapted" + lookingFor );
    }
    /**
     * if accepted = true then the QuestGiver gives Player Item Reward 
     * 
     */
    void giveReward()
    {
        
    }
}