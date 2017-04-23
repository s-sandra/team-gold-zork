package team_gold_zork;

/**
 * Deals with illegal commands.
 * @author team_gold
 * @version 4
 */
class UnknownCommand extends Command {
    private String bogusCommand; //stores the illegal command.


    /**
     * Constructs an UnknownCommand object.
     * @param bogusCommand the unsupported command.
     */
    UnknownCommand(String bogusCommand) {
        this.bogusCommand = bogusCommand;
    }


    /**
     * Responds to an illegal command.
     * @return the error message.
     */
    String execute() {
        if(bogusCommand.startsWith("verbose")){
            return "Do you want to turn verbose on or off?\n";
        }
        //return capitalize(bogusCommand).trim() + " what?\n";
        return "You can't " + bogusCommand.toLowerCase() + ".\n";
    }


    /**
     * This helper method capitalizes the first character of a given command.
     * @param command the command to capitalize.
     * @return the capitalized command.
     */
    private String capitalize(String command){
        if(command.length() >= 2){
            command = command.substring(0, 1).toUpperCase() + command.substring(1);
        }
        else{
            command = command.toUpperCase();
        }

        return command;
    }
}
