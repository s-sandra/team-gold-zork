package team_gold_zork;

import java.util.Arrays;
import java.util.List;

/**
 * This Singleton class parses a command from a user and creates a Command
 * object for it.
 * @author team_gold
 * @version 4
 */
class CommandFactory {
	private static CommandFactory theInstance; //stores the one instance of CommandFactory.

	/**
	 * Constructs a CommandFactory object.
	 */
	private CommandFactory(){}
	
	/**
	 * Creates only one instance of CommandFactory.
	 * @return	the one instance of CommandFactory.
	 */
	public static synchronized CommandFactory instance(){
		if(theInstance == null){
			theInstance = new CommandFactory();
		}
		return theInstance;
	}
	
	/**
	 * Parses a given player's command for meaning.
	 * @param commandString	the player's command.
	 * @return	a Command object.
	 */
	public Command parse(String commandString){
		String command = commandString.toLowerCase().trim();
		if(commandString.endsWith(".sav")){
			return new SaveCommand(commandString);
		}
		if(isDirection(command)){
			return new MovementCommand(command);
		}
		if(command.equals("i") || command.equals("inventory")){
			return new InventoryCommand();
		}
                if(command.equals("score")){
                    return new ScoreCommand();
                }
                if(command.equals("health")){
                    return new HealthCommand();
                }
                if(command.equals("sleep")){
                	return new SleepCommand();
                }
                if(command.equals("verbose on")){
                    return new VerboseCommand(true);
                }
                if(command.equals("verbose off")){
                    return new VerboseCommand(false);
                }
                if(command.startsWith("verbose")){
                	return new UnknownCommand(commandString);
				}
				//if the command starts with give,
				//instantiate a GiveCommand, by parsing the
				//name of the character to receive an item,
				//and the item to be received, then passing both to
				//the GiveCommand.


		String verb = "";
		String noun = "";
		GameState state = GameState.instance();
		Dungeon dungeon = state.getAdventurer().getDungeon();

		//if the command contains more than one word.
		if(command.contains(" ")){

			String v = dungeon.getItemVerbIn(command);
			if(v != null) {
				//splits up the command into verb-noun.
				verb = command.substring(0, command.indexOf(v) + v.length());
				noun = command.substring(command.indexOf(v) + v.length() + 1, command.length()).trim();
			}

			v = dungeon.getCharacterVerbIn(command);
			if(v != null){
				//parse for verb.
				//parse for character name.
				//create character specific command.
			}

			//splits up the command into verb-noun.
			verb = command.substring(0, command.indexOf(" "));
			noun = command.substring(command.indexOf(" ") + 1, command.length()).trim();

			//if the verb and noun are split with a "the", removes it.
			if(noun.startsWith("the")){
				noun = command.substring(command.indexOf("the") + 3, command.length()).trim();
			}

			if(verb.equals("unlock") || verb.equals("open")){
				String keyName = "";

				if(command.contains("with")){
					noun = noun.substring(0, noun.indexOf("with")).trim();
					keyName = command.substring(command.indexOf("with") + 4, command.length()).trim();
				}

				//if the key name is preceded with a "the", removes it.
				if(keyName.startsWith("the")){
					keyName = keyName.substring(keyName.indexOf("the") + 3, keyName.length()).trim();
				}
				return new UnlockCommand(noun, keyName);
			}

		}
		else{
			verb = command;
		}

		//if the command doesn't have a verb-noun structure.
		if(noun.isEmpty()) {
			return new UnknownCommand(commandString);
		}
		if(verb.equals("take")){
			return new TakeCommand(noun);
		}
		if(verb.equals("drop")){
			return new DropCommand(noun);
		}

		return new ItemSpecificCommand(verb, noun);

	}



	 /** 
          * This helper method determines if the given input is a direction.
          * @param direction the given string representing a direction. 
          * @return if the input is a direction.
          */
	 private boolean isDirection(String direction){
		direction = direction.toLowerCase();
	 	List all = Arrays.asList("e", "w", "s", "n", "d", "u");

	 	return all.contains(direction);
	}

}
