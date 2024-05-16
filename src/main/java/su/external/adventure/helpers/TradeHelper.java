package su.external.adventure.helpers;

import com.eerussianguy.decay_2012.Decay2012;
import net.dries007.tfc.common.capabilities.food.FoodData;
import net.dries007.tfc.common.fluids.Alcohol;
import net.dries007.tfc.util.Helpers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import su.external.adventure.Adventure;
import su.external.adventure.trade.TradeOffer;
import su.external.adventure.trade.TradeOffersLoader;

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
    public static boolean isNutrientCategory(String category) {
        String[] foods = {"Fruits", "Meats", "Dairy", "Breads", "Grains", "Vegetables", "Raw Meats", "Dough"};
        return Arrays.asList(foods).contains(category);
    }
    public static boolean hasNutrient(ItemStack stack, Player player, String nutrient) {
        // Need to get TagKey from string because these bastards didn't make them in TFCTags
        // tfc:foods/fruits

        // String tooltip = stack.getTooltipLines(player, TooltipFlag.Default.NORMAL).toString();
        // return tooltip.contains(nutrient.toLowerCase());

        TagKey<Item> tag = ItemTags.create(Helpers.identifier("foods/" + nutrient.toLowerCase().replaceAll(" ", "_")));
        return Helpers.isItem(stack, tag);

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