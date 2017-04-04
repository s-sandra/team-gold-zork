package team_gold_zork;

/**
 * This exception is thrown if a user enters an illegal command.
 * @author team_gold
 * @version 4
 */
public class IllegalCommandException extends Exception{
    public IllegalCommandException(String errorMsg){
        super(errorMsg);
    }
}
