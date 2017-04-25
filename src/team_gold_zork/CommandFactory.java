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
	private String[] prepositions = {"with", "at", "from", "into" , "during", "until", "against", "among",
			"throughout", "towards", "upon", "of", "to", "in", "on", "by", "about", "like", "through",
			"over", "before", "between", "after", "since", "without", "under", "within", "along", "following",
			"across", "behind", "beyond", "up", "out", "around", "down", "off", "above", "near", "back"};

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
		String command = commandString.trim();
		command = command.replace(".", "");
		if(command.contains(" the ")){
			command = command.replace("the", "");
		}

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
			String itemName = dungeon.getItemNameIn(command, charName);

			//if the NPC or item was not found in the dungeon, parse the command.
			if(charName.isEmpty() || itemName.isEmpty()){
				String[] giveCommand = command.split(" ");
				for(String word : giveCommand){
					if(word.equals("the") || word.equals("give") || word.equals("to") || word.equals(".")){
						continue;
					}
					if(charName.contains(word) || itemName.contains(word)){
						continue;
					}
					else if(charName.isEmpty()){
						charName = word;
					}
					else if(itemName.isEmpty()){
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

			String[] commandLine = command.split(" ");

			//if the user wants to unlock something,
			if(command.startsWith("unlock") || command.startsWith("open")){
				String keyName = "";

				noun = command.substring(command.indexOf(" ") + 1, command.length()).trim();

				//if the noun is followed by a "with", removes it.
				if(command.contains("with")){
					noun = noun.substring(0, noun.indexOf("with")).trim();
					keyName = command.substring(command.indexOf("with") + 4, command.length()).trim();
				}

				return new UnlockCommand(noun, keyName);
			}
			else{
				for(String word : commandLine){
					if(isPreposition(word)){
						verb += " " + word;
						continue;
					}
					if(word.equals("the")){
						continue;
					}
					else if(verb.isEmpty()){
						verb += word;
					}
					else if(noun.isEmpty()){
						noun += word;
					}
					else if(!verb.isEmpty() && !noun.isEmpty()){
						noun += " " + word;
					}
				}
			}


			//searches for item-specific commands in the command line.
			String itemVerb = dungeon.getItemVerbIn(command);
			String itemName = dungeon.getItemNameIn(command, itemVerb);

			if(verb.equals("drop")){
				itemName = dungeon.getItemNameIn(command, verb);
				if(itemName.isEmpty()){
					return new DropCommand(noun);
				}
				return new DropCommand(itemName);
			}

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
		if(verb.equals("take")){
			return new TakeCommand(noun);
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


	/**
	 * This helper method determines if a word in the player's command
	 * is a preposition.
	 * @param word a word in the player's command.
	 * @return if the word is a preposition.
	 */
	private boolean isPreposition(String word){
	 	for(String prep : prepositions){
	 		if(prep.equals(word)){
	 			return true;
			}
		}
		return false;
	}

}
