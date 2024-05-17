package su.external.adventure.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import su.external.adventure.Adventure;

import java.util.HashMap;
import java.util.Map;

public class AdventureSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Adventure.MOD_ID);

    public static RegistryObject<SoundEvent> ENTITY_CAVE_BUG_AMBIENT = register("entity.cave_bug.ambient");
    public static RegistryObject<SoundEvent> ENTITY_CAVE_BUG_DEATH = register("entity.cave_bug.death");
    public static RegistryObject<SoundEvent> ENTITY_CAVE_BUG_HURT = register("entity.cave_bug.hurt");
    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_AMBIENT = register("entity.cave_creep.ambient");
    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_DEATH = register("entity.cave_creep.death");
    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_HURT = register("entity.cave_creep.hurt");

    public static RegistryObject<SoundEvent> ENTITY_OGRE_BELLY_DRUM = register("entity.ogre.belly_drum");
    public static RegistryObject<SoundEvent> ENTITY_OGRE_ENRAGE = register("entity.ogre.enrage");
    public static RegistryObject<SoundEvent> ENTITY_OGRE_HURT = register("entity.ogre.hurt");
    public static RegistryObject<SoundEvent> ENTITY_OGRE_DEATH = register("entity.ogre.death");
    public static RegistryObject<SoundEvent> ENTITY_OGRE_AMBIENT = register("entity.ogre.ambient");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Adventure.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
