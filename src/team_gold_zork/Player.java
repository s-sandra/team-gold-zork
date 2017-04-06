package team_gold_zork;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Scanner;
/**
 *This class stores attributes related to the adventurer
 * @author Sandra Shtabnaya
 */
class Player extends Character{
   
    private int damage;
    private int fatigue;
    private int hunger;
    private int score;
    private String rank;
    private boolean hasWon;
    private boolean hasDied;
 
    /**
     * Creates a new player from scratch.
     */
    Player(){
        rank = GameConfig.RANK[0].getTitle();
        score = 0;
        damage = 0;
        hunger = 0;
        fatigue = 0;
        hasWon = false;
        name = "adventurer";
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
        hunger += n;
    }


    /**
     * Modifies the player's fatigue.
     * @param n The number to add to the player's fatigue. This number 
     * is negative if the fatigue should diminish.
     */
    void addFatigue(int n){
        fatigue += n;
    }


    /**
     * Modifies the player's damage.
     * @param n The number to add to the player's damage. This number 
     * is negative if the player is wounded and positive if the player
     * is healed.
     */
    void addDamage(int n){
        damage += n;
    }


    /**
     * Prints out the state of the player's health, if a 
     * threshold for damage, fatigue or hunger has been reached. 
     * @return the warning message associated with the player's health.
     * If a threshold has not been reached, then it returns an empty string.
     */
    String getHealthWarning(){
        String healthWarning = "";

        if(damage == 0){
            healthWarning += "You are fit as a fiddle!";
        }
        else if(isMinor(damage)){
            healthWarning += "You have minor wounds.";
        }
        else if(isModerate(damage)){
            healthWarning += "You have moderate wounds.";
        }
        else if(isCritical(damage)){
            healthWarning += "You are near death from your wounds.";
        }
        else{
            healthWarning += "You have died from your wounds.";
            hasDied = true;
        }

        return healthWarning;
    }


    /**
     * This helper method determines if the given health point value
     * has reached a critical level.
     * @param healthValue the point value of the aspect of health being assessed.
     * @return if the given health point value is critical.
     */
    private boolean isCritical(int healthValue){
        return healthValue > GameConfig.MAX_THRESHOLD;
    }


    /**
     * This helper method determines if the given health point value
     * has reached a moderate level.
     * @param healthValue the point value of the aspect of health being assessed.
     * @return if the given health point value is moderate.
     */
    private boolean isModerate(int healthValue){
        return healthValue < GameConfig.MAX_THRESHOLD && healthValue >= GameConfig.MID_THRESHOLD;
    }


    /**
     * This helper method determines if the given health point value
     * has reached a minor level.
     * @param healthValue the point value of the aspect of health being assessed.
     * @return if the given health point value is minor.
     */
    private boolean isMinor(int healthValue){
        return healthValue > 0 && healthValue <= GameConfig.MIN_THRESHOLD;
    }


    /**
     * Determines whether the player has died.
     * @return If the player has died. 
     */
    boolean hasDied(){
        return hasDied;
    }
    
    
    /**
     * Modifies the player's score.
     * @param n The points added to the player's score. This number is 
     * negative if points should be subtracted. 
     */
    void addScore(int n){
        score += n;
    }
    
    /**
     * Returns the player's rank based on their score.
     * @return rank the player's rank.
     */
    public String getRank(){
        return rank; 
    }


    /**
     * Determines whether the player has won the game.
     * @return If the player has won.
     */
    boolean hasWon(){
        return hasWon;
    }


    /**
     * Changes the player's hasWon boolean to true.
     */
    void setHasWon(){
        hasWon = true;
    }


    /**
     * Changes the player's fatigue, damage and hunger to reflect the
     * health consequences of a player going to sleep.
     */
    void sleep(){
    }
        
}