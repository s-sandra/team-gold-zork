package team_gold_zork;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class allows for the creation and storage of rooms.
 * @author team_gold
 * @version 4
 */
public class Room {
    private String title; //stores the name of the room.
    private String desc = ""; //stores the description of the room.
    private boolean beenHere = false; //determines if the adventurer has already visited the room.
    private ArrayList<Exit> exits = new ArrayList<>(); //stores all the exits in the room.
    private ArrayList<Item> contents = new ArrayList<>(); //stores all the items in the room.


    /**
     * This constructs a Room object, as well as the items in it.
     * @param s the Scanner reading a bork file.
     * @param d the Dungeon the room is in.
     * @param initState whether the room should be restored or reset.
     * @throws NoRoomException if the room description has ended.
     * @throws NoItemException if an item in the room's contents is invalid.
     */
    public Room(Scanner s, Dungeon d, boolean initState) throws NoRoomException, NoItemException{
            String input = s.nextLine();

            //if there are no new rooms.
            if(input.equals("==="))
            {
                    throw new NoRoomException();
            }
            title = input;
            input = s.nextLine();

            //checks if the room contains items.
            if(input.startsWith("Contents:")) {

                    //if the dungeon is not being reset.
                    if(initState) {
                            storeContents(input, d);
                    }
                    input = s.nextLine();
            }

            //while the room description has not ended,
            while(!input.equals("---")){
                    desc += input + "\n";
                    input = s.nextLine();
            }
    }


    /**
     * This helper method stores the items to the room given a list of contents.
     * @param contents the list of items in the room.
     * @param d the Dungeon the items are in.
     * @throws NoItemException if an item in the room is not valid.
     */
    private void storeContents(String contents, Dungeon d)throws NoItemException{
            contents = contents.substring(contents.indexOf(":") + 2); //chops off the data to the left of the colon.

            //chops off all the commas from the list of items.
            String[] items = contents.split(",");
            for(String item: items){
                    add(d.getItem(item));
            }
    }


    /**
     * Adds an item to the contents of the room.
     * @param item	the item to add.
     */
    void add(Item item){
            contents.add(item);
    }


    /**
     * Removes an item from the contents of the room.
     * @param item the item to remove.
     */
    void remove(Item item){
            contents.remove(item);
    }


    /**
     * Gets the item with a given name from the room's contents.
     * @param name the name of the item.
     * @return	the item in the room's contents.
     * @throws NoItemException	if the item is not in the room.
     */
    Item getItemNamed(String name) throws NoItemException{
            for(Item item: contents){
                    if(item.goesBy(name)){
                            return item;
                    }
            }
            throw new NoItemException("There's no " + name + " in here.");
    }


    /**
     * Gets all the items contained in the room.
     * @return the contents of the room
     */
    ArrayList<Item> getContents(){
            return contents;
    }


    /**
     * This constructs a Room object with its name.
     * @param title	the name of the Room object.
     */
    Room(String title){
            this.title = title;
    }


    /**
     * This method returns the name of the Room.
     * @return title the name of the Room object.
     */
    public String getTitle(){
            return title;
    }


    /**
     * This method sets the description of the Room when creating a dungeon.
     * @param desc the desired description of the Room object.
     */
    public void setDesc(String desc){
            this.desc = desc;
    }


    /**
     * This method returns the description of a Room.
     * @return desc	the description of the Room object.
     */
    String describe(){

            if(!beenHere){
                    beenHere = true;
                    return title + "\n" + desc + describeItems() + describeExits();
            }
            return title + describeItems() + describeExits();
    }


    /**
     * This method returns the description of the room's contents.
     * @return a description of all the items in the room.
     */
    String describeItems(){

            if(contents.isEmpty()){
                    return "";
            }
            String desc = "\n";

            for(int i = 0; i < contents.size(); i++){
                    desc += contents.get(i).toString();

                    //if the room has more items.
                    if(i + 1 < contents.size()){
                            desc += "\n";
                    }
            }

            return desc;
    }

    /**
     * This method returns the description of all the room's exits.
     * @return a description of all the exits.
     */
    String describeExits(){

            if(desc.isEmpty()){
                    return "";
            }

            String desc = "\n";

            for(Exit exit: exits){
                    desc += exit.describe() + ". ";
            }

            return desc + "\n";
    }


    /**
     * This method returns the Room object in the given direction, if applicable.
     * @param dir the desired direction through which to exit.
     * @return the Room object in the desired direction.
     */
    Room leaveBy(String dir){
            int exitIndex = indexOf(dir);
            if(exitIndex > -1){
                    return exits.get(exitIndex).getDest();
            }
            return null;
    }

    /**
     * This helper method determines the index of an Exit object in the ArrayList exits.
     * @param dir the desired direction.
     * @return the index of the Exit with the desired direction (-1 for not found).
     */
    private int indexOf(String dir){
            for(int i = 0; i < exits.size(); i++){
                    Exit exit = exits.get(i);
                    if(exit.getDir().equals(dir)){
                            return i;
                    }
            }
            return -1;
    }


    /**
     * This method adds a new exit to the exits ArrayList.
     * @param exit		the exit to be added to the Room.
     */
    public void addExit(Exit exit){
            exits.add(exit);
    }
    
    
    /**
     * Returns the Exit in the room with the given name.
     * @param name the name of the Exit.
     * @return the Exit with the given name.
     * @throws NoExitException if the Exit is not found.
     */
    Exit getExitNamed(String name) throws NoExitException{
        return null;
    }


    /**
     * Stores the state of the room to a .sav file.
     * @param w the PrintWriter for outputting to a .sav file.
     */
    void storeState(PrintWriter w){
            w.println(title + ":");
            w.println("beenHere=" + beenHere);

            //if the room has items in it.
            if(contents.size() > 0) {
                    w.print("Contents: ");
                    for (int i = 0; i < contents.size(); i++) {
                            w.print(contents.get(i).getPrimaryName());

                            //if the room has more items.
                            if (i + 1 < contents.size()) {
                                    w.print(",");
                            }
                    }
                    w.println();
            }
            w.println("---");
    }


    /**
     * Restores the state of the room, including items, from a .sav file.
     * @param s the Scanner reading the .sav file.
     * @param d the Dungeon the room is in.
     * @throws IllegalSaveFormatException if the room description contains invalid contents.
     */
    void restoreState(Scanner s, Dungeon d) throws IllegalSaveFormatException{
            String input = s.nextLine();

            //while there are more room states to store,
            while(!input.equals("---")){
                    if(input.contains("beenHere")){
                            if(input.contains("true")){
                                    beenHere = true;
                            }
                            else{
                                    beenHere = false;
                            }
                    }
                    if(input.startsWith("Contents:")){

                            try{ //the room description might contain invalid contents.
                                    storeContents(input, d);
                            }
                            catch(NoItemException e){
                                    throw new IllegalSaveFormatException("Corrupted save file: " + e.getMessage());
                            }

                    }
                    input = s.nextLine();
            }
    }
}

/**
 * This Exception is thrown when there are no more rooms to 
 * read from a file.
 */
class NoRoomException extends Exception{}