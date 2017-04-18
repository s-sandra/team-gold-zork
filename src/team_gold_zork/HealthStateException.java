package team_gold_zork;

/**
 * This exception is thrown if a user's health state does not require
 * food, sleep or healing.
 * @author team_gold
 * @version 4
 */
public class HealthStateException extends Exception{
    public HealthStateException(String errorMsg){
        super(errorMsg);
    }
}
