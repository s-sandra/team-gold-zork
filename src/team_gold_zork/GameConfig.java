
package team_gold_zork;

/**
 * This class stores all static fields which remain 
 * constant throughout the game.
 * @author Sandra Shtabnaya
 */
class GameConfig {
    static final String VERSION = "Group Bork v1.0";
    
    //Thresholds for player's health.
    static final int MIN_THRESHOLD = 5;
    static final int MID_THRESHOLD = 10;
    static final int MAX_THRESHOLD = 15;

    //List of ranks for the player.
    static final Rank[] RANK = {new Rank(5, "an amatuer"), new Rank(10, "an intermediate"),
                                new Rank(11, "an expert")};
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
