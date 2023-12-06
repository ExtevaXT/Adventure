package su.external.adventure.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AdventureCreativeModeTab {
    public static final CreativeModeTab ADVENTURE_TAB = new CreativeModeTab("adventuretab") {
        @Override
        public @NotNull ItemStack makeIcon() {
            return new ItemStack(AdventureItems.COIN100.get());
        }
    };
}
