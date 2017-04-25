/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Stores information pertaining to QuestGiver type NPC
 * @author Lauren
 */
public class QuestGiver extends NPC{
    boolean gotItem = false; 
    boolean hasItem = false; 
    Item itemToLookFor = null;
    Item reward = null;
    private String rewardMsg = "";

    /**
     * Creates a new QuestGiver from a dungeon file.
     * @param s the scanner reading the zork file.
     * @param d the Dungeon the AutoKiller is in.
     */
    QuestGiver(Scanner s, Dungeon d){
        String input = s.nextLine();
        name = input;

        input = s.nextLine();
        desc = input;

       input = s.nextLine();
        input = input.substring(input.indexOf(":") + 2); //chops off data to the left of colon.
        

        //while the death description has not ended,
        while(!input.startsWith("Current room:")){
            rewardMsg += input + "\n";
            input = s.nextLine();
        }
        
       input = input.substring(input.indexOf(":") + 2); //chops off data to the left of colon.
        currentRoom = d.getRoom(input);
        currentRoom.add(this);
        currentDungeon = d; 
        input = s.nextLine();
       
     
        input = input.substring(input.indexOf(":") + 2); 

         try{ //the inventory might contain invalid items.
            reward = currentDungeon.getItem(input);
                }
                catch(NoItemException e){
                    
                }
         this.addToInventory(reward);
         input = s.nextLine();
        input = input.substring(input.indexOf(":") + 2); 
        try {
            itemToLookFor = currentDungeon.getItem(input);
        } catch (NoItemException e) {
           
        }            
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
     * Stores the state of the QuestGiver to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
         w.println("QuestGiver:");
         w.println(name);
         w.println("GotItem: " + gotItem);
         w.println("---");
        
    }
    
    /**
     * Restores the state of a QuestGiver from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the QuestGiver description contains invalid contents.
     */
    void restoreState(Scanner s, Dungeon d)throws IllegalSaveFormatException{
         String line = s.nextLine();
        
                    if(line.contains("GotItem: ")){
                            if(line.contains("true")){
                                    gotItem = true;
                                    changeTalkMessage();
                            }
                            else{
                                    gotItem = false;
                            }
                    }else {throw new IllegalSaveFormatException("Corrupted save file: ");
                            }
                    line = s.nextLine();
    }
    
    
    /**
     * This method adds the QuestGiver's reward to the player's inventory, while
     * removing the QuestGiver's desired item from the player's inventory.
     */
    String giveReward(){
        player.addToInventory(reward);
        return rewardMsg;
    }


    /**
     * Accepts a gift from the player and reciprocates if the given item
     * is the item the QuestGiver needs.
     * @param item the item from the player.
     * @return the QuestGiver's response to the gift.
     */
    String give(Item item){
      if(!gotItem){
        if(item.getPrimaryName().equals(itemToLookFor.getPrimaryName())){
            addToInventory(item);
            changeTalkMessage();
            gotItem = true;
            return giveReward();
        }
        return super.give(item);}
      else{
          changeTalkMessage();
          addToInventory(itemToLookFor);
          return super.give(item);
      }
    }


    /**
     * Changes the QuestGiver's saved Quest message when the
     * player talks to the QuestGiver to a default message.
     */
    void changeTalkMessage(){
        String newMessage = "Thank you for giving me the " + itemToLookFor.getPrimaryName() + "!";
        messages.put("talk to", newMessage);
    }
    
    
}
