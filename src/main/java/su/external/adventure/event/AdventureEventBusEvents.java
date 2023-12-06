package su.external.adventure.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import su.external.adventure.Adventure;
import su.external.adventure.entity.AdventureEntityTypes;
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

@Mod.EventBusSubscriber(modid = Adventure.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdventureEventBusEvents {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(AdventureEntityTypes.BANDIT.get(), BanditEntity.setAttributes());
        event.put(AdventureEntityTypes.BRIGAND.get(), BrigandEntity.setAttributes());
        event.put(AdventureEntityTypes.GOBLIN_WARRIOR.get(), GoblinWarriorEntity.setAttributes());
        event.put(AdventureEntityTypes.GOBLIN_ARCHER.get(), GoblinArcherEntity.setAttributes());
        event.put(AdventureEntityTypes.GOBLIN_PEON.get(), GoblinPeonEntity.setAttributes());
        event.put(AdventureEntityTypes.PIRATE_CAPTAIN.get(), PirateCaptainEntity.setAttributes());
        event.put(AdventureEntityTypes.PIRATE_CROSSBOWER.get(), PirateCrossbowerEntity.setAttributes());
        event.put(AdventureEntityTypes.PIRATE_DECKHAND.get(), PirateDeckhandEntity.setAttributes());
        event.put(AdventureEntityTypes.PIRATE_CORSAIR.get(), PirateCorsairEntity.setAttributes());
        event.put(AdventureEntityTypes.TRADER.get(), TraderEntity.setAttributes());
        event.put(AdventureEntityTypes.BIG_SLIME.get(), BigSlimeEntity.setAttributes());

    }
}