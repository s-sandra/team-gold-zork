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
    private String deathDesc;
    private boolean hungerHasPassedThreshold = false;
    private boolean damageHasPassedThreshold = false;
    private boolean fatigueHasPassedThreshold = false;

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
    void restoreState(Scanner s, Dungeon d)throws IllegalSaveFormatException{
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
        line = s.nextLine();

        //if the "Hunger:" title is not found.
        if(!line.startsWith("Hunger:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        hunger = Integer.parseInt(line);
        line = s.nextLine();

        //if the "Fatigue:" title is not found.
        if(!line.startsWith("Fatigue:")){
            throw new IllegalSaveFormatException();
        }

        line = line.substring(line.indexOf(":") + 2); //chops off data to the left of colon.
        fatigue = Integer.parseInt(line);

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


    boolean hasLightSource(){
        for(Item item: inventory){
            if(item.isOn()){
                return true;
            }
        }
        return false;
    }


    /**
     * Modifies the player's hunger.
     * @param n The number to add to the player's hunger. This number 
     * is negative if the hunger should diminish.
     * @throws HealthStateException if the player is not hungry.
     */
    void addHunger(int n) throws HealthStateException{
        if(n < 0 && hunger == 0){
            throw new HealthStateException("You are not hungry.");
        }

        if(hasPassedThreshold(hunger, hunger + n)){
            hungerHasPassedThreshold = true;
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
        if(n < 0 && fatigue == 0){
            throw new HealthStateException("You are not tired right now.");
        }

        if(hasPassedThreshold(fatigue, fatigue + n)){
            fatigueHasPassedThreshold = true;
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
        if(n < 0 && damage == 0){
            throw new HealthStateException("You do not need healing.");
        }

        if(hasPassedThreshold(damage, damage + n)){
            damageHasPassedThreshold = true;
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
     * This helper method determines if changing health
     * causes the health to pass a moderate or critical threshold.
     * @param healthBefore the health value prior to changing a health component.
     * @param healthAfter the health value after changing a health component.
     * @return whether the new health value has passed a threshold.
     */
    private boolean hasPassedThreshold(int healthBefore, int healthAfter){
        int midLevel = GameConfig.MID_THRESHOLD;
        int minLevel = GameConfig.MIN_THRESHOLD;

        //if the health change has passed the minor threshold
        if(healthBefore <= minLevel && healthAfter > minLevel){
            return true;
        }

        //if the health change has passed the moderate threshold
        if(healthBefore <= midLevel && healthAfter > midLevel){
            return true;
        }

        return false;
    }


    /**
     * Prints out the state of the player's damage, based on the
     * minor, moderate or critical thresholds.
     * @return the warning message associated with the player's damage.
     */
    String getDamageWarning() {
        String damageWarning = "";

        if(damage <= 0){
            damageWarning += "You have no wounds.";
        }
        else if(isMinor(damage)){
            damageWarning += "You have minor wounds.";
        }
        else if(isModerate(damage)){
            damageWarning += "You have moderate wounds.";
        }
        else if(isCritical(damage)){
            damageWarning += "You are near death from your wounds.";
        }
        return damageWarning;
    }


    /**
     * Prints out the state of the player's hunger, based on the
     * minor, moderate or critical thresholds.
     * @return the warning message associated with the player's hunger.
     */
    String getHungerWarning() {
        String hungerWarning = "";

        if(hunger <= 0){
            hungerWarning += "You are full.";
        }
        else if(isMinor(hunger)){
            hungerWarning += "You feel a bit peckish.";
        }
        else if(isModerate(hunger)){
            hungerWarning += "Your stomach growls ferociously.";
        }
        else if(isCritical(hunger)){
            hungerWarning += "You feel light-headed from starvation.";
        }

        return hungerWarning;
    }


    /**
     * Prints out the state of the player's fatigue, based on the
     * minor, moderate or critical thresholds.
     * @return the warning message associated with the player's fatigue.
     */
    String getFatigueWarning() {
        String fatigueWarning = "";

        if(fatigue <= 0){
            fatigueWarning += "You feel energetic.";
        }
        else if(isMinor(fatigue)){
            fatigueWarning += "You are slightly tired.";
        }
        else if(isModerate(fatigue)){
            fatigueWarning += "Your eyes droop from fatigue.";
        }
        else if(isCritical(fatigue)){
            fatigueWarning += "You are getting dangerously exhausted.";
        }

        return fatigueWarning;
    }


    /**
     * Prints out the state of the player's total health, based on the
     * threshold for damage, fatigue or hunger.
     * @return the warning message associated with the player's health.
     */
    String getHealthWarning(){
        String healthWarning = "";

        if(hasDied){
            if(isDeadly(damage)){
                healthWarning += "Your wounds were too severe. You have died.";
            }
            else if(isDeadly(hunger)){
                healthWarning += "You have died from your hunger.";
            }
            else if(deathDesc != null){
                healthWarning += deathDesc;
            }
            return healthWarning;
        }

        healthWarning += getDamageWarning();

        if(healthWarning.endsWith(".")){
            healthWarning += " ";
        }

        healthWarning += getHungerWarning();

        if(healthWarning.endsWith(".")){
            healthWarning += " ";
        }

        healthWarning += getFatigueWarning();


        return healthWarning;
    }


    /**
     * This method warns the player that they have reached a threshold for
     * health, hunger or fatigue, if applicable.
     * @return the warning associated with an aspect of the player's health.
     */
    String checkHealth(){
        String healthWarning = "";
        if(hungerHasPassedThreshold){
            healthWarning += getHungerWarning();
            hungerHasPassedThreshold = false;
        }
        if(fatigueHasPassedThreshold){
            if(healthWarning.endsWith(".")){
                healthWarning += " ";
            }
            healthWarning += getFatigueWarning();
            fatigueHasPassedThreshold = false;
        }
        if(damageHasPassedThreshold){
            if(healthWarning.endsWith(".")){
                healthWarning += " ";
            }
            healthWarning += getDamageWarning();
            damageHasPassedThreshold = false;
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
        return healthValue > GameConfig.MIN_THRESHOLD && healthValue <= GameConfig.MAX_THRESHOLD;
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

        return sleepMessage + " After your nap, you find:\n" + getHealthWarning();
    }


    /**
     * Recalculates fatigue and hunger to reflect the passage of time,
     * measured by the execution of a valid command.
     */
    void passTime(){
        try{
            addFatigue(1 + getInventoryWeight() / 20);
            addHunger(1);
        }
        catch(HealthStateException e){};
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
    void kill(String deathMsg){
        deathDesc = deathMsg;
        hasDied = true;
    }
}
