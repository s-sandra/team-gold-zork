package team_gold_zork;

/** This class deals with the player
 * attempting to give an NPC an item.
 */
public class GiveCommand extends Command{
    String gift;
    String receiver;


    /**
     * Constructs a GiveCommand.
     * @param itemName the name of the item to give.
     * @param charName the name of the NPC to give the item to.
     */
    GiveCommand(String charName, String itemName){
        gift = itemName;
        receiver = charName;
    }

    /**
     * Executes a GiveCommand.
     * @return the message detailing the result of the command.
     */
    String execute(){
        if(gift.isEmpty() && receiver.isEmpty()){
            return "Give what to who?\n";
        }

        if(gift.isEmpty()){
            return "Give what to the " + receiver + "?\n";
        }

        if(receiver.isEmpty()){
            return "Give the " + gift + " to who?\n";
        }

        Player adventurer = state.getAdventurer();
        Item item;

        try{
            item = adventurer.getItemFromInventoryNamed(gift);
        }
        catch(NoItemException e){
            try{
                item = adventurer.getItemInVicinityNamed(gift);
            }
            catch(NoItemException w){
                return "You don't have the " + gift + ".\n";
            }
        }

        NPC npc;

        try{
            npc = adventurer.getCurrentRoom().getNPCNamed(receiver);
        }
        catch(NoCharacterException e){
            return "There's no " + receiver + " here.\n";
        }

        String message = npc.give(item);
        Event event = new Event("Disappear(" + item.getPrimaryName() + ")", item);
        try{
            event.execute();
        }
        catch(NoItemException e){}

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
