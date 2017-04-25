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
public class ItemStealer extends NPC{
    private boolean hasItem = false;
    private Item itemToLookFor = null;
    private String stealMsg = "";
    
    /**
     * Creates a new ItemStealer from scratch.
     */
    ItemStealer(Scanner s, Dungeon d){
        String input = s.nextLine();
        name = input;

        input = s.nextLine();
        desc = input;

       input = s.nextLine();
        input = input.substring(input.indexOf(":") + 2); //chops off data to the left of colon.
        

        //while the death description has not ended,
        while(!input.startsWith("Current room:")){
            stealMsg += input;
            input = s.nextLine();
        }
        
       input = input.substring(input.indexOf(":") + 2); //chops off data to the left of colon.
        currentRoom = d.getRoom(input);
        d.getRoom(input).add(this);
        currentDungeon = d; 
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
         w.println("ItemStealer:");
         w.println(name);
         w.println("HasItem: " + hasItem);
         w.println("---");
        
    }
    
    /**
     * Restores the state of a QuestGiver from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the QuestGiver description contains invalid contents.
     */
    void restoreState(Scanner s, Dungeon d)throws IllegalSaveFormatException{
        String line = s.nextLine(); 
        if(line.contains("HasItem: ")){
                            if(line.contains("true")){
                                    hasItem = true;
                            }
                            else{
                                    hasItem = false;
                            }
                    }else {throw new IllegalSaveFormatException("Corrupted save file: ");
                            }
                    line = s.nextLine();
    }

    
    boolean checkItem(){
        for(Item item : player.inventory){
            if (item.getPrimaryName().equals(itemToLookFor.getPrimaryName())){
                hasItem = true;
                return hasItem;
                 }
           }
        hasItem = false; 
        return hasItem;
    }
    
    String steal() {
       if(isEmptyInventory()){
        if(checkItem()){
            player.removeFromInventory(itemToLookFor);
            addToInventory(itemToLookFor);
            return "The " + name + " has stolen your " + itemToLookFor.getSecondaryName() + "! '" + stealMsg + "' says the " + name + ".\n";
        }  else{
            return "'Drats! You don't have what I want!' says the "+ name+".\n";
        }
       }else return "'Hehe' says " + name;
    }
     /**
     * Used by NPCs to react to a player entering the room.
      * @return a message detailing the result of the NPC's action.
     */
    String greetPlayer(){
    return steal();}

}
