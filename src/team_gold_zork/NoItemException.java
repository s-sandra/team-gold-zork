package team_gold_zork;

/** This exception is thrown if a room, inventory or bork file contains no more items.
 * @author team_gold
 * @version 4
 */
public class NoItemException extends Exception{
    public NoItemException(String errorMsg) {
        super(errorMsg);
    }
}

