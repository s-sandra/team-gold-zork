/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

/**
 * Deals with commands to view a player's score.
 * @author MargauxTucker
 */
class ScoreCommand extends Command {
    
    /**
     * Constructs a ScoreCommand object.
     */
    ScoreCommand(){
        
    }
    /**
     * Executes a command to give the player their score .
     * @return  the player's score and rank 
     */
    String execute(){
        Player player = state.getAdventurer();
        int playerScore= player.getScore();
        String playerRank = player.getRank();
        return "You have " +playerScore+ " points. You are now "+playerRank+ " adventurer.\n";
    
    }

    
    
}