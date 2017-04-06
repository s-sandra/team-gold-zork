package team_gold_zork;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 * This class stores attributes related to the adventurer. 
 * @author Sandra Shtabnaya
 */
class Player extends Character{
    private int damage;
    private int fatigue;
    private int hunger;
    
    private int score;
    private String rank;
    private boolean hasWon;

 
    /**
     * Creates a new player from scratch.
     */
    Player(){
    }
    
    /**
     * Stores the state of the player to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        
    }
    
    /**
     * Restores the state of a player from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the player description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        
    }
    
   


   
    /**
     * Modifies the player's hunger.
     * @param n The number to add to the player's hunger. This number 
     * is negative if the hunger should diminish.
     */
    void addHunger(int n){
        
    }
    
    
    /**
     * Modifies the player's fatigue.
     * @param n The number to add to the player's fatigue. This number 
     * is negative if the fatigue should diminish.
     */
    void addFatigue(int n){
        
    }
    
    /**
     * Modifies the player's damage.
     * @param n The number to add to the player's damage. This number 
     * is negative if the player is wounded and positive if the player
     * is healed.
     */
    void addDamage(int n){
        
    }
    
    /**
     * Prints out the state of the player's health, if a 
     * threshold for damage, fatigue or hunger has been reached. 
     * @return the warning message associated with the player's health.
     * If a threshold has not been reached, then it returns an empty string.
     */
    String getHealthWarning(){
        return "";
    }
    
    
    /**
     * Determines whether the player has died.
     * @return If the player has died. 
     */
    boolean hasDied(){
        return true;
    }
    
    /**
     * Determines whether the player has won the game.
     * @return Whether the player has won. 
     */
    boolean hasWon(){
        return true;
    }
    
    
    /**
     * Modifies the player's score.
     * @param n The points added to the player's score. This number is 
     * negative if points should be subtracted. 
     */
    void addScore(int n){
        
    }
        
}
