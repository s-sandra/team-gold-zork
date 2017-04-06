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
        return null;
    }
}
