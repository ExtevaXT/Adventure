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

//    public static RegistryObject<SoundEvent> ENTITY_CAVE_BUG_AMBIENT = registerSoundEvent("citrine_lamp_break");
//    public static RegistryObject<SoundEvent> ENTITY_CAVE_BUG_DEATH = registerSoundEvent("citrine_lamp_step");
//    public static RegistryObject<SoundEvent> ENTITY_CAVE_BUG_HURT = registerSoundEvent("citrine_lamp_place");
//    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_AMBIENT = registerSoundEvent("citrine_lamp_hit");
//    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_DEATH = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_DOUBLER_AMBIENT = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_DOUBLER_DEATH = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_DOUBLER_HURT = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_ROCKBITER_AMBIENT = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_ROCKBITER_DEATH = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_ROCKBITER_HURT = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_DEATH = registerSoundEvent("citrine_lamp_fall");
//    public static RegistryObject<SoundEvent> ENTITY_CAVE_CREEP_DEATH = registerSoundEvent("citrine_lamp_fall");



    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(Adventure.MOD_ID, name)));
    }
    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
