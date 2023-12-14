package su.external.adventure.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import su.external.adventure.Adventure;
import su.external.adventure.entity.bandit.BanditEntity;
import su.external.adventure.entity.bandit.BrigandEntity;
import su.external.adventure.entity.boss.BigSlimeEntity;
import su.external.adventure.entity.goblin.GoblinArcherEntity;
import su.external.adventure.entity.goblin.GoblinPeonEntity;
import su.external.adventure.entity.goblin.GoblinWarriorEntity;
import su.external.adventure.entity.pirate.PirateCaptainEntity;
import su.external.adventure.entity.pirate.PirateCorsairEntity;
import su.external.adventure.entity.pirate.PirateCrossbowerEntity;
import su.external.adventure.entity.pirate.PirateDeckhandEntity;
import su.external.adventure.entity.trader.TraderEntity;

public class AdventureEntityTypes {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Adventure.MOD_ID);
    public static final RegistryObject<EntityType<BanditEntity>> BANDIT = registerEntity("bandit", BanditEntity::new);
    public static final RegistryObject<EntityType<BrigandEntity>> BRIGAND = registerEntity("brigand", BrigandEntity::new);
    public static final RegistryObject<EntityType<GoblinArcherEntity>> GOBLIN_ARCHER = registerEntity("goblin_archer", GoblinArcherEntity::new);
    public static final RegistryObject<EntityType<GoblinWarriorEntity>> GOBLIN_WARRIOR = registerEntity("goblin_warrior", GoblinWarriorEntity::new);
    public static final RegistryObject<EntityType<GoblinPeonEntity>> GOBLIN_PEON = registerEntity("goblin_peon", GoblinPeonEntity::new);
    public static final RegistryObject<EntityType<PirateCaptainEntity>> PIRATE_CAPTAIN = registerEntity("pirate_captain", PirateCaptainEntity::new);
    public static final RegistryObject<EntityType<PirateCorsairEntity>> PIRATE_CORSAIR = registerEntity("pirate_corsair", PirateCorsairEntity::new);
    public static final RegistryObject<EntityType<PirateCrossbowerEntity>> PIRATE_CROSSBOWER = registerEntity("pirate_crossbower", PirateCrossbowerEntity::new);
    public static final RegistryObject<EntityType<PirateDeckhandEntity>> PIRATE_DECKHAND = registerEntity("pirate_deckhand", PirateDeckhandEntity::new);
    public static final RegistryObject<EntityType<TraderEntity>> TRADER =
            ENTITY_TYPES.register("trader",
                    () -> EntityType.Builder.of(TraderEntity::new, MobCategory.MISC)
                            .sized(0.5625f, 2.0f)
                            .build(new ResourceLocation(Adventure.MOD_ID, "trader").toString()));
    public static final RegistryObject<EntityType<BigSlimeEntity>> BIG_SLIME =
            ENTITY_TYPES.register("big_slime",
                    () -> EntityType.Builder.of(BigSlimeEntity::new, MobCategory.CREATURE).noSave()
                            .sized(3f, 3f)
                            .build(new ResourceLocation(Adventure.MOD_ID, "big_slime").toString()));

    private static <T extends Mob> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory) {
        return ENTITY_TYPES.register(name, () ->
                EntityType.Builder.of(factory, MobCategory.MONSTER).noSave()
                        .sized(0.5625f, 2.0f)
                        .build(new ResourceLocation(Adventure.MOD_ID, name).toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }


    @SubscribeEvent
    public static void createAttributes(EntityAttributeCreationEvent event) {
        Adventure.LOGGER.info("createAttributes");
        Adventure.LOGGER.info(PirateCaptainEntity.bakeAttributes());
        Adventure.LOGGER.info(PirateCaptainEntity.bakeAttributes().build().getValue(Attributes.MAX_HEALTH));
        event.put(AdventureEntityTypes.BANDIT.get(), BanditEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.BRIGAND.get(), BrigandEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.GOBLIN_WARRIOR.get(), GoblinWarriorEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.GOBLIN_ARCHER.get(), GoblinArcherEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.GOBLIN_PEON.get(), GoblinPeonEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.PIRATE_CAPTAIN.get(), PirateCaptainEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.PIRATE_CROSSBOWER.get(), PirateCrossbowerEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.PIRATE_DECKHAND.get(), PirateDeckhandEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.PIRATE_CORSAIR.get(), PirateCorsairEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.TRADER.get(), TraderEntity.bakeAttributes().build());
        event.put(AdventureEntityTypes.BIG_SLIME.get(), BigSlimeEntity.bakeAttributes().build());
    }
}
