package team_gold_zork;

/**
 * This exception is thrown if a user attempts to go through
 * a locked door.
 * @author team_gold
 * @version 4
 */
public class LockedExitException extends Exception{
    public LockedExitException(String errorMsg){
        super(errorMsg);
    }
}

