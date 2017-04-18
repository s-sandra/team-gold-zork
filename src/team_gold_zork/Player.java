package team_gold_zork;

import java.io.PrintWriter;
import java.util.ArrayList;
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
    private boolean hasFainted;
 
    /**
     * Creates a new player from scratch.
     */
    Player(){
        rank = GameConfig.RANK[0].getTitle();
        score = 0;
        damage = 0;
        fatigue = 0;
        hunger = 0;
        hasWon = false;
        hasDied = false;
        hasFainted = false;
        name = "adventurer";
    }
    
    /**
     * Stores the state of the player to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
        w.println("Adventurer state:");
            w.println("Current room: " + getCurrentRoom().getTitle());

            //if the player has items in their inventory at save time.
            if(!isEmptyInventory()){
                    w.print("Inventory: ");
                    ArrayList<String> itemNames = getInventoryNames(true);

                    for(int i = 0; i < itemNames.size(); i++){
                            w.print(itemNames.get(i));

                            //if the inventory has more items.
                            if(i + 1 < itemNames.size()){
                                    w.print(",");
                            }
                    }
                    w.write("\n");
            }
        w.write("Damage: " + damage + "\n");
        w.write("Hunger: " + hunger + "\n");
        w.write("Fatigue: " + fatigue + "\n");
        w.write("Score: " + score + "\n");
        w.write("Rank: " + rank + "\n");
    }
    
    /**
     * Restores the state of a player from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @throws IllegalSaveFormatException If the player description contains invalid contents.
     */
    void restoreState(Scanner s)throws IllegalSaveFormatException{
        String line = s.nextLine();

        //if the "Current room:" title is not found.
        if(!line.startsWith("Current room:")){
            throw new IllegalSaveFormatException();
        }

        //reads in the player's current room.
        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        currentRoom = currentDungeon.getRoom(line);
        currentDungeon.setEntry(currentRoom);

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

        //if the "Damage:" title is not found.
        if(!line.startsWith("Damage:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        damage = Integer.parseInt(line);

        //if the "Hunger:" title is not found.
        if(!line.startsWith("Hunger:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        damage = Integer.parseInt(line);

        //if the "Fatigue:" title is not found.
        if(!line.startsWith("Fatigue:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        damage = Integer.parseInt(line);


        //if the "Score:" title is not found.
        line = s.nextLine();
        if(!line.startsWith("Score:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        score = Integer.parseInt(line);

        //if the "Rank:" title is not found.
        line = s.nextLine();
        if(!line.startsWith("Rank:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        rank = line;
    }

   
    /**
     * Modifies the player's hunger.
     * @param n The number to add to the player's hunger. This number 
     * is negative if the hunger should diminish.
     * @throws HealthStateException if the player is not hungry.
     */
    void addHunger(int n) throws HealthStateException{
        if(hunger == 0){
            throw new HealthStateException("You are not hungry.");
        }

        hunger += n;

        //if adding hunger makes hunger negative, sets hunger to zero.
        if(hunger < 0){
            hunger = 0;
        }

        if(isDeadly(hunger)){
            hasDied = true;
        }
    }


    /**
     * Modifies the player's fatigue.
     * @param n The number to add to the player's fatigue. This number 
     * is negative if the fatigue should diminish.
     * @throws HealthStateException if the player is not tired.
     */
    void addFatigue(int n) throws HealthStateException{
        if(fatigue == 0){
            throw new HealthStateException("You are not tired right now.");
        }

        fatigue += n;

        //if adding fatigue makes it negative, sets fatigue to zero.
        if(fatigue < 0){
            fatigue = 0;
        }

        if(isDeadly(fatigue)){
            hasFainted = true;
            sleep();
        }
    }


    /**
     * Modifies the player's damage.
     * @param n The number to add to the player's damage. This number 
     * is negative if the player is wounded and positive if the player
     * is healed.
     * @throws HealthStateException if the player does not need healing.
     */
    void addDamage(int n) throws HealthStateException{
        if(damage == 0){
            throw new HealthStateException("You do not need healing.");
        }

        damage += n;

        //if adding damage made it negative, sets damage to zero.
        if(damage < 0){
            damage = 0;
        }

        if(isDeadly(damage)){
            hasDied = true;
        }
    }


    /**
     * Prints out the state of the player's health, if a 
     * threshold for damage, fatigue or hunger has been reached.
     * @return the warning message associated with the player's health.
     * If a threshold has not been reached, then it returns an empty string.
     */
    String getHealthWarning(){
        String healthWarning = "";

        if(hasDied){
            healthWarning += "You have died from";
            if(damage >= 0){
                healthWarning += " your wounds.";
            }
            else if(hunger >= 0){
                healthWarning += " hunger.";
            }
            return healthWarning;
        }
        else if(damage <= 0){
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

        if(fatigue <= 0){
            healthWarning += "You have plenty of energy.";
        }
        else if(isMinor(fatigue)){
            healthWarning += "You are slightly tired.";
        }
        else if(isModerate(fatigue)){
            healthWarning += "Your eyes droop from your fatigue.";
        }
        else if(isCritical(fatigue)){
            healthWarning += "You are getting dangerously exhausted.";
        }

        if(hunger <= 0){
            healthWarning += "You are full.";
        }
        else if(isMinor(hunger)){
            healthWarning += "You feel a bit peckish.";
        }
        else if(isModerate(hunger)){
            healthWarning += "Your stomach growls ferociously.";
        }
        else if(isCritical(hunger)){
            healthWarning += "You feel light-headed from starvation.";
        }

        return healthWarning;
    }


    /**
     * This helper method determines if the given health point value
     * has reached a deadly level
     * @param healthValue the point value of the aspect of health being assessed.
     * @return if the given health point value is deadly.
     */
    private boolean isDeadly(int healthValue){
        return healthValue > GameConfig.MAX_THRESHOLD;
    }


    /**
     * This helper method determines if the given health point value
     * has reached a moderate level.
     * @param healthValue the point value of the aspect of health being assessed.
     * @return if the given health point value is moderate.
     */
    private boolean isModerate(int healthValue){
        return healthValue <= GameConfig.MID_THRESHOLD && healthValue > GameConfig.MIN_THRESHOLD;
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
     * This helper method determines if the given health point value
     * has reached a near death level.
     * @param healthValue the point value of the aspect of health being assessed.
     * @return if the given health point value is critical.
     */
    private boolean isCritical(int healthValue){
        return healthValue > GameConfig.MIN_THRESHOLD && healthValue < GameConfig.MAX_THRESHOLD;
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
        setRank();
    }
    
    void setRank(){
        int midRankMinScore = GameConfig.RANK[1].getLowerRange();
        int highRankMinScore = GameConfig.RANK[2].getLowerRange();

        String amatuerRank = GameConfig.RANK[0].getTitle();
        String intermediateRank = GameConfig.RANK[1].getTitle();
        String expertRank = GameConfig.RANK[2].getTitle();

        if(score < midRankMinScore){
            this.rank = amatuerRank;
        } else if (score >= midRankMinScore
                && score < highRankMinScore){
            this.rank = intermediateRank;
        } else if (score >= highRankMinScore){
            this.rank = expertRank;
        }
    }
    
    /**
     * Returns the player's rank based on their score.
     * @return rank the player's rank.
     */
    String getRank(){
        return rank; 
    }
    /**
     * Returns the player's score
     * @return player's score.
     */
    int getScore(){
        return score;
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
    void kill(){
        hasDied = true;
    }
  
    int getDamage(){
        return damage; 
    }
    

    /**
     * Changes the player's fatigue, damage and hunger to reflect the
     * health consequences of a player going to sleep.
     * @return a message detailing the result of the sleep command.
     */
    String sleep(){
        String sleepMessage = "";

        if(fatigue == 0){
            return "You aren't tired.";
        }

        try{
            addHunger(fatigue);
        }
        catch(HealthStateException e){}

        fatigue = 0;
        damage = 0;

        if(hasFainted){
            hasFainted = false;
            sleepMessage += "You have collapsed from utter exhaustion. Sweet sleep sooths your fatigue.";
        }
        else{
            sleepMessage += "You curl up into a deep slumber.";
        }

        return sleepMessage + " As a result of your nap:\n " + getHealthWarning();
    }


    /**
     * Gets the total weight of all the items in the player's inventory.
     * @return the weight of all the items in the player's inventory.
     */
    int getInventoryWeight(){
        int inventoryWeight = 0;

        for(Item item: inventory){
            inventoryWeight += item.getWeight();
        }

        return inventoryWeight;
    }
        
}
