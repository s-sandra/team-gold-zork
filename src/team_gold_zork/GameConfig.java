
package team_gold_zork;

/**
 * This class stores all static fields which remain 
 * constant throughout the game.
 * @author Sandra Shtabnaya
 */
class GameConfig {
    static final String VERSION = "Group Bork v1.0";
    
    //Thresholds for player's health.
    static final int MIN_THRESHOLD = 0;
    static final int MID_THRESHOLD = 0;
    static final int MAX_THRESHOLD = 0;

    //List of ranks for the player.
    static final Rank[] RANK = {new Rank(50, "amatuer")};
}

/**
 * This class stores attributes related to a player's rank.
 */
class Rank {
    private int upperRange; //the last point value of the rank.
    private String title; //the name of the rank.

    Rank(int upperRange, String title) {
        this.upperRange = upperRange;
        this.title = title;
    }

    String getTitle() {
        return title;
    }

    int getUpperRange() {
        return upperRange;
    }
}
