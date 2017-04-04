package team_gold_zork;

import java.io.IOException;
/**
 * This class takes in a command from the user and executes it.
 * @author team_gold
 * @version 4
 */
abstract class Command {
	GameState state = GameState.instance(); //stores the state of the game.

	/**
	 * Executes a command from the user and updates the state of the game (if applicable).
	 * @throws IOException	if the command deals with files.
	 * @throws NoItemException if the command deals with taking or dropping.
	 * @return the result of the command.
	 */
	abstract String execute() throws IOException, NoItemException;
}
