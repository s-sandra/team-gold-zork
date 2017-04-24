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
    private boolean isDark = false; //determines if the room is dark or light.
    private ArrayList<Exit> exits = new ArrayList<>(); //stores all the exits in the room.
    private ArrayList<Item> contents = new ArrayList<>(); //stores all the items in the room.
    private ArrayList<NPC> npcs = new ArrayList<>(); //stores all the npcs in the room.

    /**
     * This constructs a Room object, as well as the items in it.
     * @param s the Scanner reading a zork file.
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

            //check to see if isDark line is here. If yes, set isDark variable. If not, continue.
            if(input.startsWith("isDark")){
                isDark = true;
                input = s.nextLine();
            }
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
     * Determines if the given room is the same as this room.
     * @return if the given room has the same name as the room
     * the method was called on.
     */
    boolean equals(Room otherRoom){
        return otherRoom.getTitle().equals(title);
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
     * Gets the NPC with a given name from the room's contents.
     * @param name the name of the NPC.
     * @return	the NPC in the room's contents.
     * @throws NoCharacterException	if the NPC is not in the room.
     */
    NPC getNPCNamed(String name) throws NoCharacterException{
        for(NPC npc: npcs){
            if(npc.getName().equals(name)){
                return npc;
            }
        }
        throw new NoCharacterException();
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
     * Returns whether the player has been in this room before.
     * @return whether the player has been here.
     */
    boolean hasBeenThere(){
        return beenHere;
    }

    /**
     * Checks for items in the room that are emitting light.
     * @return if the room is light.
     */
    boolean isLight(){
        if(isDark){
            for(Item item : contents){
                if(item.isOn()){
                    return true;
                }
            }
            return GameState.instance().getAdventurer().hasLightSource();
        }
        return true;
    }

    /**
     * This method returns the description of a Room.
     * @return desc	the description of the Room object.
     */
    String describe(){
        boolean isVerbose = GameState.instance().getVerbose();
        String description = "";

            if(!beenHere|| isVerbose){
                if(!isLight()){
                    description = "It is pitch black in here!\n";
                }
                else{
                    description = title + "\n" + desc + describeItems() + describeNPCs() + describeExits();
                }
                beenHere = true;
            }
            else{
                if(!isLight()){
                    description = "It is pitch black in here!\n";
                }
                else{
                    description = title + describeItems() + describeNPCs() + describeExits();
                }
            }
      return description;
    }

    /**
     * This method returns the description of the room's characters.
     * @return a description of all the characters in the room.
     */
    String describeNPCs(){
        if(npcs.isEmpty()){
            return "";
        }
        String desc = "\n";

        for(int i = 0; i < npcs.size(); i++){
            desc += npcs.get(i).describe();
            String npcAction = npcs.get(i).greetPlayer();
            if(!npcAction.isEmpty()){
                desc += "\n" + npcAction;
            }

            //if the room has more items.
            if(i + 1 < npcs.size()){
                desc += "\n";
            }
        }

        return desc;
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
                    desc += contents.get(i).describe();

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
     * @throws LockedExitException if the exit in the given direction is locked.
     */
    Room leaveBy(String dir) throws LockedExitException{
            int exitIndex = indexOf(dir);
            if(exitIndex > -1){
                Exit door = exits.get(exitIndex);
                if(door.isLocked()){
                    throw new LockedExitException("You can't go " + dir + ". The door is locked.\n");
                }
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
        for(Exit door : exits){
            if(door.isNamed(name)){
                return door;
            }
        }
        throw new NoExitException();
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
            
          storeLockedExits(w);
            
            w.println("---");
    }
    
    void storeLockedExits(PrintWriter w)
       {
          String directions = ""; 
            for(int i = 0; i < exits.size(); i++){
                if (exits.get(i).isLocked()){

                    if(i + 1 == exits.size()){
                        directions = directions + exits.get(i).getDir();
                    }
                    else {
                        directions = directions + exits.get(i).getDir() + ",";
                    }
                }
            }

            if(!directions.isEmpty()) {
                w.println("Locked exits: " + directions);
            }
            
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

                    if(input.startsWith("Locked exits: ")){
                        String lockedDirs = input.substring(input.indexOf(":") + 1); //chops off data to the left of semi-colon.
                        String[] lockedDoors = lockedDirs.split(",");

                        for(String door: lockedDoors){
                            for(Exit exit : exits){
                                if(exit.getDir().equals(door)){
                                    exit.lock();
                                }
                            }
                        }
                    }
            }
    }
    /**
     * gets the numbe of exits in a room
     * @return exitCount
     */
    int getNumExit()
    {
      return exits.size();
    }
     /**
     * Adds an npc to the contents of the room.
     * @param npc	the item to add.
     */
    void add(NPC npc){
        if(!npcs.contains(npc))
            npcs.add(npc);
    }
    
    
}

/**
 * This Exception is thrown when there are no more rooms to 
 * read from a file.
 */
class NoRoomException extends Exception{}
