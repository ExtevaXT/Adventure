package su.external.adventure.entity;

import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import su.external.adventure.Adventure;
import su.external.adventure.client.renderer.ArocknidRenderer;
import su.external.adventure.client.renderer.CaveCreepRenderer;
import su.external.adventure.client.renderer.HumanoidRenderer;
import su.external.adventure.client.renderer.OgreRenderer;
import su.external.adventure.entity.bandit.BanditEntity;
import su.external.adventure.entity.bandit.BrigandEntity;
import su.external.adventure.entity.boss.BigSlimeEntity;
import su.external.adventure.entity.boss.OgreEntity;
import su.external.adventure.entity.crawler.ArocknidEntity;
import su.external.adventure.entity.crawler.CaveCreepEntity;
import su.external.adventure.entity.goblin.GoblinArcherEntity;
import su.external.adventure.entity.goblin.GoblinPeonEntity;
import su.external.adventure.entity.goblin.GoblinWarriorEntity;
import su.external.adventure.entity.pirate.PirateCaptainEntity;
import su.external.adventure.entity.pirate.PirateCorsairEntity;
import su.external.adventure.entity.pirate.PirateCrossbowerEntity;
import su.external.adventure.entity.pirate.PirateDeckhandEntity;
import su.external.adventure.entity.trader.TraderEntity;

public class AdventureEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Adventure.MOD_ID);
    public static final RegistryObject<EntityType<BanditEntity>> BANDIT = register("bandit", BanditEntity::new);
    public static final RegistryObject<EntityType<BrigandEntity>> BRIGAND = register("brigand", BrigandEntity::new);
    public static final RegistryObject<EntityType<GoblinArcherEntity>> GOBLIN_ARCHER = register("goblin_archer", GoblinArcherEntity::new);
    public static final RegistryObject<EntityType<GoblinWarriorEntity>> GOBLIN_WARRIOR = register("goblin_warrior", GoblinWarriorEntity::new);
    public static final RegistryObject<EntityType<GoblinPeonEntity>> GOBLIN_PEON = register("goblin_peon", GoblinPeonEntity::new);
    public static final RegistryObject<EntityType<PirateCaptainEntity>> PIRATE_CAPTAIN = register("pirate_captain", PirateCaptainEntity::new);
    public static final RegistryObject<EntityType<PirateCorsairEntity>> PIRATE_CORSAIR = register("pirate_corsair", PirateCorsairEntity::new);
    public static final RegistryObject<EntityType<PirateCrossbowerEntity>> PIRATE_CROSSBOWER = register("pirate_crossbower", PirateCrossbowerEntity::new);
    public static final RegistryObject<EntityType<PirateDeckhandEntity>> PIRATE_DECKHAND = register("pirate_deckhand", PirateDeckhandEntity::new);
    public static final RegistryObject<EntityType<TraderEntity>> TRADER = register("trader", TraderEntity::new, MobCategory.MISC, 0.5625f, 2.0f, true);
    public static final RegistryObject<EntityType<BigSlimeEntity>> BIG_SLIME = register("big_slime", BigSlimeEntity::new, MobCategory.MONSTER, 3f, 3f, false);
    public static final RegistryObject<EntityType<ArocknidEntity>> AROCKNID = register("arocknid", ArocknidEntity::new, MobCategory.MONSTER, 0.875f, 1f, false);
    public static final RegistryObject<EntityType<CaveCreepEntity>> CAVE_CREEP = register("cave_creep", CaveCreepEntity::new, MobCategory.MONSTER, 9 / 16f, 13 / 16f, false);
    public static final RegistryObject<EntityType<OgreEntity>> OGRE = register("ogre", OgreEntity::new, MobCategory.MONSTER, 22 / 16f, 54 / 16f, false);
    private static <T extends Mob> RegistryObject<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory) {
        return register(name, factory, MobCategory.MONSTER, 0.5625f, 2.0f, false);
    }
    private static <T extends Mob> RegistryObject<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, boolean save) {
        EntityType.Builder<T> builder = EntityType.Builder.of(factory, category).sized(width, height);
        if (!save) builder.noSave();
        return ENTITY_TYPES.register(name, () ->
                builder.build(new ResourceLocation(Adventure.MOD_ID, name).toString()));
    }
    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent event) {
        Adventure.LOGGER.info("createAttributes");
        Adventure.LOGGER.info(PirateCaptainEntity.bakeAttributes());
        Adventure.LOGGER.info(PirateCaptainEntity.bakeAttributes().build().getValue(Attributes.MAX_HEALTH));
        event.put(BANDIT.get(), BanditEntity.bakeAttributes().build());
        event.put(BRIGAND.get(), BrigandEntity.bakeAttributes().build());
        event.put(GOBLIN_WARRIOR.get(), GoblinWarriorEntity.bakeAttributes().build());
        event.put(GOBLIN_ARCHER.get(), GoblinArcherEntity.bakeAttributes().build());
        event.put(GOBLIN_PEON.get(), GoblinPeonEntity.bakeAttributes().build());
        event.put(PIRATE_CAPTAIN.get(), PirateCaptainEntity.bakeAttributes().build());
        event.put(PIRATE_CROSSBOWER.get(), PirateCrossbowerEntity.bakeAttributes().build());
        event.put(PIRATE_DECKHAND.get(), PirateDeckhandEntity.bakeAttributes().build());
        event.put(PIRATE_CORSAIR.get(), PirateCorsairEntity.bakeAttributes().build());
        event.put(TRADER.get(), TraderEntity.bakeAttributes().build());
        event.put(BIG_SLIME.get(), BigSlimeEntity.bakeAttributes().build());
        event.put(AROCKNID.get(), ArocknidEntity.bakeAttributes().build());
        event.put(CAVE_CREEP.get(), CaveCreepEntity.bakeAttributes().build());
        event.put(OGRE.get(), OgreEntity.bakeAttributes().build());
    }
    public static void registerEntityWorldSpawns() {
        SpawnPlacements.register(BANDIT.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, BanditEntity::checkBanditSpawnRules);
        SpawnPlacements.register(BRIGAND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, BanditEntity::checkBanditSpawnRules);
        SpawnPlacements.register(GOBLIN_ARCHER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(GOBLIN_PEON.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(GOBLIN_WARRIOR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(PIRATE_CAPTAIN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(PIRATE_CORSAIR.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(PIRATE_CROSSBOWER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(PIRATE_DECKHAND.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(BIG_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, BigSlimeEntity::checkMobSpawnRules);
        SpawnPlacements.register(AROCKNID.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.WORLD_SURFACE, ArocknidEntity::checkCrawlerSpawnRules);
        SpawnPlacements.register(CAVE_CREEP.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.WORLD_SURFACE, CaveCreepEntity::checkCrawlerSpawnRules);
        SpawnPlacements.register(OGRE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.WORLD_SURFACE, OgreEntity::checkMobSpawnRules);
    }
    @OnlyIn(Dist.CLIENT)
    public static void registerEntityRenderers() {
        EntityRenderers.register(BANDIT.get(), (context) -> new HumanoidRenderer(context, "bandit.png"));
        EntityRenderers.register(BRIGAND.get(), (context) -> new HumanoidRenderer(context, "brigand.png"));
        EntityRenderers.register(GOBLIN_ARCHER.get(), (context) -> new HumanoidRenderer(context, "goblin.png"));
        EntityRenderers.register(GOBLIN_PEON.get(), (context) -> new HumanoidRenderer(context, "goblin.png"));
        EntityRenderers.register(GOBLIN_WARRIOR.get(), (context) -> new HumanoidRenderer(context, "goblin.png"));
        EntityRenderers.register(PIRATE_CAPTAIN.get(), (context) -> new HumanoidRenderer(context, "pirate_captain.png"));
        EntityRenderers.register(PIRATE_CORSAIR.get(), (context) -> new HumanoidRenderer(context, "pirate.png"));
        EntityRenderers.register(PIRATE_CROSSBOWER.get(), (context) -> new HumanoidRenderer(context, "pirate.png"));
        EntityRenderers.register(PIRATE_DECKHAND.get(), (context) -> new HumanoidRenderer(context, "pirate.png"));
        EntityRenderers.register(TRADER.get(), (context) -> new HumanoidRenderer(context, "merchant.png"));
        EntityRenderers.register(BIG_SLIME.get(), SlimeRenderer::new);
        EntityRenderers.register(AROCKNID.get(), ArocknidRenderer::new);
        EntityRenderers.register(CAVE_CREEP.get(), CaveCreepRenderer::new);
        EntityRenderers.register(OGRE.get(), OgreRenderer::new);
    }
}
