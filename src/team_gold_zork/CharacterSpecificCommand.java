/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package team_gold_zork;

/**
 *Deals with commands that can only be performed on certain Characters
 * @author Lauren
 * 
 **/
 class CharacterSpecificCommand extends Command{
    private String verb; //stores what a player wants to do with a character.
    private String characterName; //stores the name of the character to perform an action on.


    /**
     * Constructs a CharacterSpecificCommand.
     * @param verb  the desired action to be performed on a Character.
     * @param characterName  the name of the Character involved in the action.
     */
    CharacterSpecificCommand(String verb, String characterName){
        this.verb = verb;
        this.characterName = characterName;
    }


    /**
    /* Executes a command to do something with a Character.
     * @return  the result of the command.
     */
    String execute(){
        Room currentRoom = state.getAdventurer().getCurrentRoom();
        String message = "";
        NPC character;

        try{
            character = currentRoom.getNPCNamed(characterName);
        }
        catch(NoCharacterException e){
            return "There's no " + characterName + " here.\n";
        }

        message = character.getMessageForVerb(verb);

        if(message == null){
            return "You can't " + verb + " the " + characterName + ".\n";
        }

        String events = character.getEventForVerb(verb);
        if(events != null){
            Event result = new Event(events);
            try{
                message += "\n" + result.execute();
            }
            catch(NoItemException e){}

        }
        state.getAdventurer().passTime();
        String healthWarning = state.getAdventurer().checkHealth();

        if(!healthWarning.isEmpty()){
            healthWarning += "\n";
        }

        if(message.endsWith("\n")){
            return message;
        }

        return message + "\n" + healthWarning;
    }
}
