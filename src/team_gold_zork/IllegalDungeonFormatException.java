package team_gold_zork;

/**
 * This exception is thrown if the bork file
 * does not follow the proper format.
 * @author team_gold
 * @version 4
 */
public class IllegalDungeonFormatException extends Exception{
	public IllegalDungeonFormatException(String errorMsg) {
		super("Illegal zork file format: " + errorMsg);
	}
}
