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
    private String verb; //stores what a player wants to do with an object.
    private String noun; //stores the name of the object a player wants to manipulate.
    private Character character; //stores the character to perform an action on.


    /**
     * Constructs an ItemSpecificCommand.
     * @param verb  the desired action to be performed on an Item.
     * @param noun  the name of the Item to manipulate.
     */
    CharacterSpecificCommand(String verb, String noun){
        this.verb = verb;
        this.noun = noun;
    }


    /**
//     * Executes a command to do something to an Character
     * @return  the result of the command.
     */
    String execute(){
    }
    }
