package su.external.adventure.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import su.external.adventure.Adventure;

import java.util.HashMap;
import java.util.Map;

public class AdventureItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Adventure.MOD_ID);

    public static final RegistryObject<Item> COIN1 = ITEMS.register("coin1",
            () -> new Item(new Item.Properties().tab(AdventureCreativeModeTab.ADVENTURE_TAB)));
    public static final RegistryObject<Item> COIN10 = ITEMS.register("coin10",
            () -> new Item(new Item.Properties().tab(AdventureCreativeModeTab.ADVENTURE_TAB)));
    public static final RegistryObject<Item> COIN100 = ITEMS.register("coin100",
            () -> new Item(new Item.Properties().tab(AdventureCreativeModeTab.ADVENTURE_TAB)));
    public static final Map<Integer, RegistryObject<Item>> COIN = new HashMap<>();

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);

        // New technologies
        COIN.put(100, AdventureItems.COIN100);
        COIN.put(10, AdventureItems.COIN10);
        COIN.put(1, AdventureItems.COIN1);
    }
}
