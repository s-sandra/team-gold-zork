
package team_gold_zork;

/**
 * This class stores all static fields which remain 
 * constant throughout the game.
 * @author Sandra Shtabnaya
 */
class GameConfig {
    static final String VERSION = "Group Bork v2.0";
    
    //Thresholds for player's health.
    static final int MIN_THRESHOLD = 20;
    static final int MID_THRESHOLD = 40;
    static final int MAX_THRESHOLD = 60;

    //List of ranks for the player.
    static final Rank[] RANK = {new Rank(0, "an amatuer"), new Rank(25, "an intermediate"),
                                new Rank(50, "an expert")};
}

/**
 * This class stores attributes related to a player's rank.
 */
class Rank {
    private int lowerRange; //the first point value of the rank.
    private String title; //the name of the rank.

    Rank(int lowerRange, String title) {
        this.lowerRange = lowerRange;
        this.title = title;

    }

    String getTitle() {
        return title;
    }

    int getLowerRange() {
        return lowerRange;
    }
}
