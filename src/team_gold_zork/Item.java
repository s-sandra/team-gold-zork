package team_gold_zork;

import java.util.Hashtable;
import java.util.Scanner;

/**
 * This class stores attributes for an item contained within a dungeon
 * @author team_gold
 * @version 4
 */
public class Item {
    private String primaryName; //the name of the item in camelCase.
    private String secondaryName; //the name of the item without camelCase.
    private int weight; //how much the item weighs.
    private Hashtable<String, String> messages = new Hashtable<>(); //stores the message corresponding with the verb key.


    /**
     * This constructs an Item object given a scanner reading a bork file.
     * @param s the scanner reading the bork file.
     * @throws NoItemException  if there are no more items to read from the file.
     */
    public Item(Scanner s) throws NoItemException{
        String input = s.nextLine();

        //if there are no new items.
        if(input.equals("==="))
        {
            throw new NoItemException("");
        }
        primaryName = input;
        setSecondaryName();
        input = s.nextLine();

        weight = Integer.parseInt(input);
        input = s.nextLine();

        //while the verbs description has not ended.
        while(!input.equals("---")){
            String verbLine = input;
            String verb = verbLine.substring(0, verbLine.indexOf(":")); //chops off data to the right of colon.
            String message = verbLine.substring(verbLine.indexOf(":") + 1); //chops off data to the left of colon.
            input = s.nextLine();

            //while the message description has not ended.
            while(!input.contains(":") && !input.equals("---")){
                message += "\n" + input;
                input = s.nextLine();
            }
            messages.put(verb, message);
        }
    }


    /**
     * This helper method gives an item a secondary name,
     * if the primaryName is in camelCase.
     */
    private void setSecondaryName(){
        secondaryName = "";
        boolean isProperNoun = false;
        for(int i = 0; i < primaryName.length(); i++){
            Character letter = primaryName.charAt(i);

            //if the primary name is a proper noun.
            if(Character.isUpperCase(letter) && i == 0){
                isProperNoun = true;
                secondaryName += letter;
            }

            //if the primary name is a proper name, then keep capitalization.
            else if(Character.isUpperCase(letter) && isProperNoun){
                secondaryName += " " + letter;
            }

            //if it's capitalized and not a proper noun, then remove capitalization.
            else if(Character.isUpperCase(letter)){
                secondaryName += " " + Character.toLowerCase(letter);
            }

            //if it's not capitalized,
            else{
                secondaryName += letter;
            }
        }
    }


    /**
     * Determines if the given name corresponds to the item's
     * primary or secondary name.
     * @param name the name in question.
     * @return if the item goes by the given name.
     */
    public boolean goesBy(String name){
        return primaryName.equals(name) || secondaryName.toLowerCase().equals(name.toLowerCase());
    }


    /**
     * Gets the name of the item in camelCase.
     * @return the name of the item in camelCase.
     */
    public String getPrimaryName(){
        return primaryName;
    }


    /**
     * Gets the name of the item without camelCase.
     * @return the other name of the item.
     */
    public String getSecondaryName(){
        return secondaryName;
    }


    /**
     * Gets the message associated with a verb.
     * @param verb  the action to be done on the item.
     * @return  the message detailing the result of the action.
     */
    public String getMessageForVerb(String verb){
        return messages.get(verb);
    }



    /**
     * Determines if the item supports a given verb.
     * @param verb the verb in question.
     * @return whether the verb can be performed on the item.
     */
    public boolean containsVerb(String verb){
        return messages.containsKey(verb);
    }


    /**
     * Defines what to print for an item object.
     * @return  the String representing the item object.
     */
    public String toString(){
        return "There is a " + secondaryName + " here.";
    }
    /**
     * This method will add a transformed item with a given name to the Dungeon using
     * the ArrayList of transformed items in Dungeon.
     * @param newItem the transformed Item to add to the Dungeon.
     * @author Margaux Tucker
     */
    public void transform(Item newItem){
        
    }
    /**
     * This method will remove an already existing item from the dungeon
     * and player's inventory, if needed.
     * @param removedItem the item that is being removed
     * @author Margaux Tucker
     */
    public void disappear(Item removedItem){
        
    }
}
