package team_gold_zork;

/**
 * This exception is thrown if the save file 
 * does not follow the proper format.
 * @author team_gold
 * @version 4
 */
public class IllegalSaveFormatException extends Exception{
	public IllegalSaveFormatException(String errorMsg) {
		super("Corrupted save file: " + errorMsg);
	}
	public IllegalSaveFormatException() { super("Corrupted save file.");}
}
