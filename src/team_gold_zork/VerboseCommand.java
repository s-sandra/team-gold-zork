/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

/**
 * Deals with the command to change the game to verbose mode.
 * @author Lauren
 */
public class VerboseCommand extends Command{
    /**
     * Creates a VerboseCommand.
     */
    VerboseCommand(boolean state){
    }
    
    
    /**
     * Executes the command to modify the boolean isVerbose in Room.
     * @return The result of the verboseCommand 
     */
    String execute(){
        Room playersCurrentRoom = state.getAdventurer().getCurrentRoom();
        return "";
    }
}
