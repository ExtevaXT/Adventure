package su.external.adventure.helpers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import su.external.adventure.item.AdventureItems;

import java.util.function.BiConsumer;

public class CoinHelper {
    public static final int[] VALUES = {100, 10, 1};
    private static int iterateCoins(Player player, int toTake, BiConsumer<ItemStack, Integer> action) {
        int amount = 0;
        for (int value : VALUES)
            for (ItemStack stack : player.getInventory().items)
                if (stack.is(AdventureItems.COIN.get(value).get())) {
                    int stackValue = stack.getCount() * value;
                    amount += stackValue;
                    if (toTake > 0 && amount >= toTake) {
                        action.accept(stack, toTake);
                        return amount;
                    }
                    action.accept(stack, stackValue);
                }
        return amount;
    }
    public static int countCoins(Player player) {
        return iterateCoins(player, 0, (stack, value) -> {});
    }

    public static int takeCoins(Player player, int toTake) {
        return iterateCoins(player, toTake, (stack, value) -> player.getInventory().removeItem(stack));
    }
    public static int takeCoins(Player player) {
        return iterateCoins(player, 0, (stack, value) -> player.getInventory().removeItem(stack));
    }
}
