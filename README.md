# team-gold-zork

Lauren, Sandra, Katie, Margaux<br>
Group Gold

Welcome to the world of felines! In our own twist of the popular text adventure game, Zork, users will be playing as a cat to explore their house and neighborhood in search of their favorite toy. Along the way, the user will encounter different items with special abilities to assist them on their quest. But, it’s not all fun and games in a cat’s world! The user must be weary of their damage, fatigue, and hunger throughout the game, while also dodging dreadful dogs and other sneaky characters that damage health and steal items. To do this, our system must be thoroughly organized. Thus, a Player class and many different commands are added to handle the events such as dying, being wounded, fighting, teleporting, eating, winning, etc. Additionally, we implemented 6 game features, which include verbose mode, light, locked rooms, NPCs, hunger, and fatigue.

## SUPPLEMENTARY FEATURES

### Verbose Mode:
The verbose mode feature allows the user to always see the room description, when it would otherwise only be available upon entering a room for the first time.

Verbose Mode Activation Use-Case
<blockquote>1.    The user types “verbose on” to activate verbose mode.<br>
2.    The game always prints full room descriptions.<br></blockquote>

Verbose Mode Deactivation Use-Case
<blockquote>1.  The user types “verbose off” to deactivate verbose mode.<br> 
2. The game prints room descriptions according to default mode.<br></blockquote>
 

### Light:
The light feature introduces dark rooms into the game, which are dangerous to the player unless they obtain an item that can provide a light source. It allows such items to change a room’s state from dark to light using a light event.

Light Use-Case
<blockquote>1. The user receives an item that can serve as a light source.<br>
2. The user enters a dark room.<br> 
3. The user activates the light source.<br>
4. The room lights up.<br></blockquote>

Light Use-Case Variation #1
<blockquote>3.1 The user attempts to activate the light source.<br>
3.2 The light source has been expended and cannot be activated.</blockquote>

Light Use-Case Variation #2
<blockquote>4.1 The light source fails while inside the dark room.<br>
4.2 The room becomes dark.<br>
4.3 The user is attacked.<br></blockquote> 

### Locked Doors:
The locked feature will allow dungeons in the game to contain certain doors that remain locked until the user obtains and uses a key to unlock them.

Locked Door Use Case
<blockquote>1.    The user attempts to go through a locked door.<br>
2.    The game informs the user that the door is locked.<br>
3.    The user finds the key to unlock the door.<br>
4.    The user unlocks the door using the key.<br></blockquote>

Locked Door Variation
<blockquote>3.1 The user finds an object.<br>
3.2 The user attempts to unlock the door with the object.<br>
3.3 The game informs the user that they cannot open the door with the object.<br></blockquote>
 
 
### NPC:
The NPC feature will allow the user to encounter three types of NPCs. An AutoKiller terminates the player’s life if they are unable to escape, an ItemStealer takes a specific item from a user’s inventory, and a QuestGiver asks the user to perform a specific task in exchange for a reward.

AutoKiller NPC Encounter Use-Case
<blockquote>1.   The user walks into room with an autokiller NPC.<br>
2.    The user is unable to avoid the autokiller.<br>
3.    The autokiller damages the player.<br> 
4.    The user dies.<br>
5.   The game ends.<br></blockquote>

AutoKiller NPC Encounter Variation #1
<blockquote>2.1 The user walks into a room with an autokiller NPC and exit(s) for escape.<br>
2.2 The game warns the user about the potentially deadly NPC.<br>
2.3 The user avoids the NPC by leaving through another exit.<br></blockquote>

AutoKiller NPC Encounter Variation #2
<blockquote>2.1 The user walks into a room with an autokiller NPC and exit(s) for escape.<br>
2.2 The game warns the user about the potentially deadly NPC.<br>
2.3 The user provokes the NPC.<br> 
2.4 Continue to Step 3.<br></blockquote> 

 
### Hunger:
In addition to averting NPCs, the player must also maintain their level of hunger, which increases by one point each time a player executes a valid command. In order to avoid the point limit for hunger, the player must consume food, which resets the hunger level back to zero.

Hunger Calculation Use-Case
<blockquote>1.    The player performs a valid command unrelated to consuming food.<br>
2.    The game increases the hunger level by one point.<br>
3.    The player consumes food.<br>
4.    The game decreases the hunger level by the weight of the food consumed.<br></blockquote>

Hunger Calculation Variation
<blockquote>2.1 The player neglects their need to eat and reaches the maximum level of hunger.<br>
2.2 The game informs the player that they have died.<br>
2.3 The game ends.<br></blockquote>

### Fatigue:
Similar to hunger, the fatigue feature will obligate the player to find a safe place to sleep if their exhaustion level reaches a maximum threshold. However, unlike the health feature, if the user is carrying items, the rate of increase in fatigue is equivalent to the weight of their inventory. Otherwise, fatigue rises by one point.

Fatigue Calculation Use-Case
<blockquote>1.    The player performs a valid command.<br>
2.    The game increases the fatigue level by one point, multiplied by the weight of the player’s inventory.<br>
3.    The player goes to sleep.<br>
4.    The game adds the current fatigue points to the level of hunger.<br>
5.    The game resets the level of fatigue and damage to zero.<br></blockquote>

Fatigue Calculation Variation
<blockquote>2.1 The player neglects their need to sleep and reaches the maximum level of fatigue.<br>
2.2 The player falls asleep on the spot.<br>
2.3 Continue to Step 4.<br></blockquote> 
