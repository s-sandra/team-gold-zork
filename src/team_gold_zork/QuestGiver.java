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
public class QuestGiver extends NPC{
    boolean hasItem = false; 
    Item itemToLookFor = null;
    Item reward = null;

    /**
     * Creates a new QuestGiver from a dungeon file.
     * @param s the scanner reading the zork file.
     * @param d the Dungeon the AutoKiller is in.
     */
    QuestGiver(Scanner s, Dungeon d){
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
         String line = s.nextLine();
         
         //if the "Current room:" title is not found.
        if(!line.startsWith("Character Name:")){
            throw new IllegalSaveFormatException();
        }

        //reads in the player's current room.
        line = line.substring(line.indexOf(":") + 2);
        name = line;
         
        line = s.nextLine();
        
        //if the "Current room:" title is not found.
        if(!line.startsWith("Current room:")){
            throw new IllegalSaveFormatException();
        }

        //reads in the player's current room.
        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        currentRoom = currentDungeon.getRoom(line);
        

        line = s.nextLine();

        //if the player had items in their inventory at save time (the "Inventory:" title would not appear otherwise).
        if(line.startsWith("Inventory: ")){
            line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.

            //chops off the commas from the inventory list.
            String[] items = line.split(",");
            for(String item: items){
                try{ //the inventory might contain invalid items.
                    this.inventory.add(currentDungeon.getItem(item));
                }
                catch(NoItemException e){
                    throw new IllegalSaveFormatException(e.getMessage());
                }
            }
            line = s.nextLine();
    }
      
    }
    
    /**
     * This method adds the QuestGiver's reward to the player's inventory, while
     * removing the QuestGiver's desired item from the player's inventory.
     */
    String giveReward(){
        return "";
    }


    /**
     * Accepts a gift from the player and reciprocates if the given item
     * is the item the QuestGiver needs.
     * @param item the item from the player.
     * @return the QuestGiver's response to the gift.
     */
    String give(Item item){
        if(item.getPrimaryName().equals(itemToLookFor)){
            addToInventory(item);
            return giveReward();
        }
        return super.give(item);
    }
}