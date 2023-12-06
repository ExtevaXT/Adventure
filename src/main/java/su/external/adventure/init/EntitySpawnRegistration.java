package su.external.adventure.init;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.levelgen.Heightmap;
import su.external.adventure.entity.AdventureEntityTypes;
import su.external.adventure.entity.boss.BigSlimeEntity;

public class EntitySpawnRegistration {
    public static void registerEntityWorldSpawns() {
        // I don't fucking know how to make them spawn standalone, used EZ Supervisor
        SpawnPlacements.register(AdventureEntityTypes.BANDIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.BRIGAND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.GOBLIN_ARCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.GOBLIN_PEON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.GOBLIN_WARRIOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);

        SpawnPlacements.register(AdventureEntityTypes.PIRATE_CAPTAIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.PIRATE_CORSAIR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.PIRATE_CROSSBOWER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(AdventureEntityTypes.PIRATE_DECKHAND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);

        SpawnPlacements.register(AdventureEntityTypes.BIG_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, BigSlimeEntity::checkMobSpawnRules);
    }
}