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
    private Player adventurer; //stores the single instance of Player.
    private boolean isVerbose = false;
    private QuestGiver qG; 
    private AutoKiller aK; 
    private ItemStealer iS; 

    /**
     * Constructs a GameState instance and a new Player.
     */
    private GameState(){
        adventurer = new Player();
    }


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
     * Resets the game state with a new dungeon and entry point for the player.
     * @param dungeon the new dungeon. 
     */
    void initialize(Dungeon dungeon){
            adventurer.setCurrentRoom(dungeon.getEntry());
            adventurer.setCurrentDungeon(dungeon);
            
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

            saver.println(GameConfig.VERSION);
            adventurer.getCurrentDungeon().storeState(saver);
            adventurer.storeState(saver);
            

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
                if(!line.equals(GameConfig.VERSION)){
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

                dungeon.restoreState(input);

                //if the "Adventurer state:" title is not found.
                line = input.nextLine();
                if(!line.equals("Adventurer state:")){
                        throw new IllegalSaveFormatException();
                }

                adventurer.restoreState(input, dungeon);
                input.close();
            }
            catch(FileNotFoundException e){
                throw new IllegalSaveFormatException("The save file '" + fileName + "' cannot be found.");
            }
    }
    
    /**
     * Returns GameState's instance of the Player class.
     * @return adventurer the player object.
     */
    Player getAdventurer(){
	return adventurer;
    }

    /**
     * Determines if the player has met the criteria for winning the game.
     * @return if the player has won the game.
     */
    boolean hasWon(){
        return adventurer.hasWon();
    }


    /**
     * Determines if the player has met the criteria for losing the game.
     * @return if the player has lost the game.
     */
    boolean hasLost(){
        return adventurer.hasDied();
    }
    void setVerbose(boolean isVerbose){
        this.isVerbose = isVerbose;
    }
    boolean getVerbose(){
        return isVerbose;
    }
}
