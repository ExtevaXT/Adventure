package su.external.adventure.helpers;

import com.eerussianguy.decay_2012.Decay2012;
import net.dries007.tfc.common.capabilities.food.FoodData;
import net.dries007.tfc.common.fluids.Alcohol;
import net.dries007.tfc.util.Helpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import su.external.adventure.Adventure;
import su.external.adventure.trade.TradeOffersLoader;
import su.external.adventure.trade.TradeOffer;

import java.util.*;

public class TradeHelper {
    public static String MESSAGE;
    public static Map<String, List<TradeOffer>> OFFERS = new HashMap<>();

    public static void update() {
        MESSAGE = "";
        OFFERS.clear();
        List<String> categories = new ArrayList<>(TradeOffersLoader.CATEGORIES.keySet());
        Collections.shuffle(categories);

        categories.subList(0, 3).forEach(category -> {
            OFFERS.put(category, TradeOffersLoader.CATEGORIES.get(category));
            MESSAGE += category + ", ";
        });

        TradeHelper.MESSAGE = MESSAGE.substring(0, MESSAGE.length() - 2);;
    }
    public static float modifier(ItemStack stack) {
        // Built-in Java Crutches
        final float[] modifier = {1F};
        Decay2012.ifFood(stack, food -> {
            if (stack.getTag() != null) {
                FoodData data = FoodData.read(stack.getTag());
                modifier[0] += data.saturation() / 50;
                modifier[0] += data.water() / 50;
                modifier[0] += data.decayModifier() / 2;
            }
            modifier[0] *=  1 - Decay2012.getPercentDecayed(food, true) * 2;
            modifier[0] /= stack.getMaxStackSize();
        });
        Adventure.LOGGER.info("Modifier - {} for {}", modifier[0], stack.getItem().toString());
        return modifier[0];
    }
    // TODO just use item tags
    // Need to inject this crutch into TFC ClientHelpers.hasShiftDown()
    // Or just somehow parse nutrients from ALL jsons
    public static boolean shiftDown;
    public static boolean isNutrientCategory(String category) {
        return category.equals("Grain") || category.equals("Protein") || category.equals("Vegetable") || category.equals("Fruit") || category.equals("Diary");
    }
    // Works only with shift pressed
    public static boolean hasNutrient(ItemStack stack, Player player, String nutrient) {
        //shiftDown = true;
        String tooltip = stack.getTooltipLines(player, TooltipFlag.Default.NORMAL).toString();
        //shiftDown = false;
        return tooltip.contains(nutrient.toLowerCase());

    }
    public static float alcoholAmount(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains(Helpers.BLOCK_ENTITY_TAG, Tag.TAG_COMPOUND)) {
            CompoundTag blockEntityTag = stack.getTagElement(Helpers.BLOCK_ENTITY_TAG);
            CompoundTag inventory = blockEntityTag.getCompound("inventory");
            CompoundTag tank = inventory.getCompound("tank");
            if (tank.contains("FluidName") && tank.contains("Amount")) {
                String fluidName = tank.getString("FluidName");
                int amount = tank.getInt("Amount");
                for (Alcohol alcohol : Alcohol.values())
                    if (fluidName.equals("tfc:" + alcohol.name().toLowerCase()))
                        return amount;
            }
        }
        return 0;
    }
}