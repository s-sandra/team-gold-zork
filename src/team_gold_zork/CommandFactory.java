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

		GameState state = GameState.instance();
		Dungeon dungeon = state.getAdventurer().getDungeon();

		if(command.startsWith("give")){
			String charName = dungeon.getNPCNameIn(command);
			String itemName = dungeon.getItemNameIn(command);

			//if the NPC or item was not found in the dungeon, parse the command.
			if(charName.isEmpty() || itemName.isEmpty()){
				String[] giveCommand = command.split(" ");
				for(String word : giveCommand){
					if(word.equals("the") || word.equals("give") || word.equals("to")){
						continue;
					}
					if(charName.contains(word) || itemName.contains(word)){
						continue;
					}
					if(charName.isEmpty()){
						charName = word;
					}
					if(itemName.isEmpty()){
						itemName = word;
					}
				}
			}

			return new GiveCommand(charName, itemName);
		}


		String verb = "";
		String noun = "";

		//if the command contains more than one word.
		if(command.contains(" ")){

			//parses for non-item specific commands.
			if(command.contains("the")){
				verb = command.substring(0, command.indexOf("the")).trim();
				noun = command.substring(command.indexOf("the") + 3, command.length()).trim();
			}
			else{
				verb = command.substring(0, command.indexOf(" ") + 1).trim();
				noun = command.substring(command.indexOf(" ") + 1, command.length()).trim();
			}


			//if the user wants to unlock something,
			if(verb.startsWith("unlock") || verb.startsWith("open")){
				String keyName = "";

				//if the noun is followed by a "with", removes it.
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

			if(verb.equals("take")){
				return new TakeCommand(noun);
			}
			if(verb.equals("drop")){
				return new DropCommand(noun);
			}

			//searches for item-specific commands in the command line.
			String itemVerb = dungeon.getItemVerbIn(command);
			String itemName = dungeon.getItemNameIn(command);

			//searches for npc-specific commands in the command line.
			String charVerb = dungeon.getCharacterVerbIn(command);
			String charName = dungeon.getNPCNameIn(command);

			if(!charVerb.isEmpty() && !charName.isEmpty()){
				return new CharacterSpecificCommand(charVerb, charName);
			}
			else if(!itemVerb.isEmpty() && !itemName.isEmpty()){
				return new ItemSpecificCommand(itemVerb, itemName);
			}
			else if(charVerb.isEmpty() && !charName.isEmpty()){
				return new CharacterSpecificCommand(verb, charName);
			}
			else if(charVerb.isEmpty() && itemVerb.isEmpty() && !itemName.isEmpty()){
				return new ItemSpecificCommand(verb, itemName);
			}
			else if(!charVerb.isEmpty() && charName.isEmpty() && itemName.isEmpty()){
				return new CharacterSpecificCommand(charVerb, noun);
			}
			else if(!itemVerb.isEmpty() && itemName.isEmpty()){
				return new ItemSpecificCommand(itemVerb, noun);
			}

			return new ItemSpecificCommand(verb, noun);
		}

		return new UnknownCommand(commandString);
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
