# Adventure
Mod for Adventure TFC

Features:
- Some hostile entities that drop coins.
- Goblin, pirate invasion, slime day events.
- Trader that exchange denomination and buying goods.
- Command /claim to claim chunks for 10000^1.5*{player chunks} coins
- Almost full config for entities

Notes:
- Coin drop based on entity equipment
- EntityEvent can be configured by datapack see [Entity Rain Mod](https://github.com/LukeGrahamLandry/smells-fishy-mod) 
- Drops can be confured by loot tables
- Summon trader `/summon adventure:trader` and update offers `/trades` by yourself

Dependencies: 
- TerraFirmaCraft-Forge-1.18.2-2.2.18.jar (Used TFC armor, weapons, JavelinGoal)
- ftb-chunks-forge-1802.3.17-build.265.jar (Chunk claiming for coins)
- decay_2012-1.0.2.jar (Trader decay modifier)
- firmalife-1.2.9.jar (idk)
- ftb-teams-forge-1802.2.11-build.107.jar (Nested dependency)
- ftb-library-forge-1802.3.11-build.177.jar (Nested dependency)
- architectury-4.11.93-forge.jar (Nested dependency)
- Patchouli-1.18.2-71.1.jar (Nested dependency)
- jei-1.18.2-9.7.2.1001.jar (Nested dependency)

Optional:
- ez_supervisor-1.0.3.jar (For spawning bandits at night (Thanks to Forge, I didn't figure out how to handle it))

##
Used [Entity Rain Mod - Smells Fishy](https://github.com/LukeGrahamLandry/smells-fishy-mod) code for spawn events

## Changelog:

UPD Adventure-1.0
- Moved EntityRainEvent to adventure.entityrain
- Added a message during the start of the event

UPD Adventure-1.1
- Implemented Bandit and Brigand. Added TFC as a dependency for javelins
- Forge is total shit, there is no info even in the discord about natural mob spawning
- Seems to have fixed weapons for bandits
- Added coins: 1, 10, 100
- Wrote the drop of coins depending on the tier of armor and weapons, calculating the required denomination
- Don't want to test, seems to work, with colored steel ~100 coins

UPD Adventure-1.2
- Refactored the abstraction
- Added 3 types of goblins: knight, peon, archer
- Added goblin invasion event
- Added 4 types of pirates: captain, corsair, crossbowman, laborer
- They could use some hats
- Added pirate invasion event
- Tested, the mobs are tough
- Added a chance for event, weights seem to be off
- Added drops from https://misode.github.io/loot-table/

UPD Adventure-1.3
- Added a trader for exchanging coins
- Should have used TFC API for metals from mobs
- Found out that I'm retard, weights are normal

UPD Adventure-1.4
- Turns out you can't render mobs on the server
- Fixed drops
- Added dependency FTBChunks
- The /claim command claims a chunk for 10000^1.5*{number of claimed chunks} coins
- Wasted 2 days to find out that LexMenos is a faggot
- Refactored some trash in main class

UPD Adventure-1.5
- Added torches in offhand for bandits
- Added lifetime for entityrain
- Added big slime boss for slime day
- Added boss counter feature for entityrain
- Added acceptable items for trader
- Implemented trade loader and updating
- Added Parchment, idk what is it
- Added crutch for selling items with nutrient

UPD Adventure-1.6.0
- Borrowed some code and assets from some big mod
- Refactored some code and added fucking configs
- Added some logs
- Synced version with Adventure TFC and added server version check
- Fixed crossbower crossbow goal

## Loot tables

| Entity                             | Seeds 0.05                                  | Special                           | Gem Powder 0.05                                                       |
|------------------------------------|---------------------------------------------|-----------------------------------|-----------------------------------------------------------------------|
| entity.adventure.bandit            | rice beet garlic carrot tomato hop coffea   |                                   |                                                                       |
| entity.adventure.brigand           | wheat pumpkin potato flax hemp weld         | bow? : 0-4 arrow                  |                                                                       |
| entity.adventure.goblin_archer     | maize soybean coffea cotton tobacco         | 0.05 leather 0-4 arrow            |                                                                       |
| entity.adventure.goblin_warrior    | barley potato carrot peyote coca            | 0.05 metal/sheet/wrought_iron     |                                                                       |
| entity.adventure.goblin_peon       | wheat onion agave woad                      | 0.05 1-2 leather_strip            |                                                                       |
| entity.adventure.pirate_captain    | rye cabbage jute papyrus sugarcane coffea   | 0.075 1-4 raw_gold 0-16 gunpowder | amethyst diamond emerald lapis_lazuli opal pyrite ruby sapphire topaz |
| entity.adventure.pirate_corsair    | oat carrot melon sugarcane coca hop         | 0.025 1-4 raw_gold                | ruby sapphire topaz                                                   |
| entity.adventure.pirate_crossbower | wheat garlic squash sugarcane cotton indigo | 0.05 1-4 raw_gold arrow           | amethyst diamond emerald                                              |
| entity.adventure.pirate_deckhand   | rice soybean sugarcane madder tobacco       | 0.025 1-4 raw_gold                | lapis_lazuli opal pyrite                                              |
