/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

/**
 *Contains info pertaining to verbose command 
 * @author Lauren
 */
public class VerboseCommand extends Command{
   Room room = state.getAdventurersCurrentRoom();
    /**
     * creates VerboseComand 
     */
    VerboseCommand(){
    }
    
    
    /**
     * Executes the command to set boolean isVerbose in Room to true 
     * @return The result of the verboseCommand 
     */
    String execute(){
        return "";
    }
}
