package team_gold_zork;

import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Scanner;
import java.lang.Character;

/**
 * This class stores attributes for an item contained within a dungeon
 * @author team_gold
 * @version 4
 */
public class Item {
    private String primaryName; //the name of the item in camelCase.
    private String secondaryName; //the name of the item without camelCase.
    private int weight; //how much the item weighs.
    private String desc; //the description of the object.
    private Hashtable<String, String> messages = new Hashtable<>(); //stores the message corresponding with the verb key.
    private Hashtable<String, String> events = new Hashtable<>(); //stores the events corresponding with the verb key.
    GameState state = GameState.instance();
    private boolean isOn = false;

    /**
     * This constructs an Item object given a scanner reading a zork file.
     * @param s the scanner reading the zork file.
     * @throws NoItemException  if there are no more items to read from the file.
     */
    public Item(Scanner s, Dungeon d) throws NoItemException{
        String input = s.nextLine();
        Dungeon dungeon = d;

        //if there are no new items.
        if(input.equals("==="))
        {
            throw new NoItemException("");
        }
        primaryName = input;
        setSecondaryName();
        input = s.nextLine();

        desc = input;
        input = s.nextLine();

        weight = Integer.parseInt(input);
        input = s.nextLine();

        //while the verbs description has not ended.
        while(!input.equals("---")){
            String verbLine = input;
            String verb = "";
            String message = "";

            //checks to see if the verb triggers events.
            if(verbLine.contains("[")){
                verb = verbLine.substring(0, verbLine.indexOf("[")); //chops off data to the right of bracket.
                String event = verbLine.substring(verbLine.indexOf("[") + 1, verbLine.indexOf("]")); //gets all events between brackets.
                events.put(verb, event);
            }
            else{
                verb = verbLine.substring(0, verbLine.indexOf(":")); //chops off data to the right of colon.
            }

            message = verbLine.substring(verbLine.indexOf(":") + 1); //chops off data to the left of colon.
            input = s.nextLine();

            //while the message description has not ended.
            while(!input.contains(":") && !input.equals("---")){
                message += "\n" + input;
                input = s.nextLine();
            }
            messages.put(verb, message);
            dungeon.addItemVerb(verb);
        }
    }

    void storeState(PrintWriter w){
        if(isOn){
            w.println(primaryName + ":");
            w.println("isOn=true");
            w.println("---");
        }
    }

    void restoreState(Scanner s, Dungeon d){
        String input = s.nextLine();
        if(input.startsWith("isOn")){
            isOn = true;
        }
        s.nextLine(); //reads in end of item descriptions.
    }

    void setIsOn(boolean state){
        isOn = state;
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

            else if(Character.isDigit(letter)){
                secondaryName += " " + letter;
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
     * Gets the event(s) associated with a verb.
     * @param verb  the action to be done on the item.
     * @return  the message detailing the event(s) of the action.
     */
    public String getEventForVerb(String verb){
        return events.get(verb);
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
     * Defines what to print for an item object.
     * @return  the String representing the item object.
     */
    public String describe(){
        return desc;
    }

    /**
     * This method will remove this item from the dungeon
     * and player's inventory, if needed.
     * @author Margaux Tucker
     */
    public void disappear(){
        Player player = state.getAdventurer();
        player.removeFromInventory(this);
        Room room = player.getCurrentRoom();
        room.remove(this);
        Dungeon dungeon = player.getCurrentDungeon();
        dungeon.remove(this);
    }

    /**
     * Returns the weight of this item.
     * @return how much this item weighs.
     */
    public int getWeight(){
        return weight;
    }

    public boolean isOn(){
        return isOn;
    }
}
