package team_gold_zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * This class stores the current location and dungeon of the player.
 * @author team_gold
 * @version 4
 */
class GameState {
    private static GameState theInstance; //stores the single instance of GameState.

    /**
     * Constructs a GameState instance.
     */
    private GameState(){}


    /**
     * Returns the one instance of GameState.
     * @return	the GameState object.
     */
    static synchronized GameState instance(){
            if(theInstance == null){
                    theInstance = new GameState();
            }
            return theInstance;
    }


    /**
     * Resets the game state with a new dungeon and entry point.
     */
    void initialize(Dungeon dungeon){
            currentRoom = dungeon.getEntry();
            currentDungeon = dungeon;
    }



    /**
     * Stores the game state into a given .sav file.
     * @param saveName the name of the file to save to.
     * @throws IOException	if the file cannot be closed or written to.
     */
    void store(String saveName) throws IOException{
            FileWriter writer;
            PrintWriter saver;

            writer = new FileWriter(saveName);
            saver = new PrintWriter(writer);

            saver.println(VERSION);
            currentDungeon.storeState(saver);

            saver.println("Adventurer:");
            saver.println("Current room: " + getAdventurersCurrentRoom().getTitle());

            //if the player has items in their inventory at save time.
            if(!inventory.isEmpty()){
                    saver.print("Inventory: ");
                    ArrayList<String> itemNames = getInventoryNames(true);

                    for(int i = 0; i < itemNames.size(); i++){
                            saver.print(itemNames.get(i));

                            //if the inventory has more items.
                            if(i + 1 < itemNames.size()){
                                    saver.print(",");
                            }
                    }
            }

            writer.close();
            saver.close();
    }


    /**
     * Restores a previously saved game from a .sav file.
     * @param fileName the name of the .sav file containing the game state.
     * @throws IllegalSaveFormatException if the file does not follow the proper format.
     * @throws IllegalDungeonFormatException if the dungeon cannot be created.
     */
    void restore(String fileName) throws IllegalSaveFormatException, IllegalDungeonFormatException{
            Scanner input;
            String line;

            try{
                input = new Scanner(new File(fileName));
                line = input.nextLine();
                if(!line.equals(VERSION)){
                        input.close();
                        throw new IllegalSaveFormatException("The save file format + '" + line + "' is not compatible "
                                        + "with the current version of bork.");
                }

                //if the "save data" title is not found.
                line = input.nextLine();
                if(!line.equals("save data")){
                        throw new IllegalSaveFormatException();
                }

                String borkFile = input.nextLine();
                borkFile = borkFile.substring(borkFile.indexOf(":") + 2);

                Dungeon dungeon = new Dungeon(borkFile, false);
                initialize(dungeon);

                //if the "Room states:" title is not found.
                line = input.nextLine();
                if(!line.equals("Room states:")){
                        throw new IllegalSaveFormatException();
                }

                dungeon.restoreState(input);

                //if the "Adventurer:" title is not found.
                line = input.nextLine();
                if(!line.equals("Adventurer:")){
                        throw new IllegalSaveFormatException();
                }

                String entryRoom = input.nextLine();
                entryRoom = entryRoom.substring(entryRoom.indexOf(":") + 2); //chops off data to the left of colon.
                currentRoom = dungeon.getRoom(entryRoom);
                dungeon.setEntry(currentRoom);

                //if the player had items in their inventory at save time.
                if(input.hasNextLine()){
                        String inventory = input.nextLine();
                        inventory = inventory.substring(inventory.indexOf(":") + 2); //chops off data to the left of colon.

                        //chops off the commas from the inventory list.
                        String[] items = inventory.split(",");
                        for(String item: items){
                                try{ //the inventory might contain invalid items.
                                        addToInventory(dungeon.getItem(item));
                                }
                                catch(NoItemException e){
                                        throw new IllegalSaveFormatException(e.getMessage());
                                }
                        }
                }

                input.close();
            }
            catch(FileNotFoundException e){
                throw new IllegalSaveFormatException("The save file '" + fileName + "' cannot be found.");
            }
    }
    
    /**
     * Returns the player's current room.
     * @return currentRoom the player's current room.
     */
    Room getAdventurersCurrentRoom(){
	return null;
    }
    
    
    /**
    * Changes the player's current room.
    * @param room the player's current room.
    */
    void setAdventurerCurrentRoom(Room room){
    }

    /**
     * Determines if the player has met the criteria for winning the game.
     * @return if the player has won the game.
     */
    boolean hasWon(){
        return true;
    }


    /**
     * Determines if the player has met the criteria for losing the game.
     * @return if the player has lost the game.
     */
    boolean hasLost(){
        return true;
    }
}
