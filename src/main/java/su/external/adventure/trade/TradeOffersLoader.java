package su.external.adventure.trade;

import net.dries007.tfc.common.items.TFCItems;
import net.dries007.tfc.util.Metal;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import su.external.adventure.Adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TradeOffersLoader {
    // Shitcoding time
    // TODO add item tags
    public static final float TIER_MODIFIER = 1.5F;

    public static final Map<String, List<TradeOffer>> CATEGORIES = new HashMap<>();
    private static String current_name;
    private static Item current_item;
    public static void load() {
        Adventure.LOGGER.info("Started Loading Trade Offers");
        for (Item item : ForgeRegistries.ITEMS){
            current_name = item.toString();
            current_item = item;
            loadFood();
            loadMisc();
        }
        loadMetal();
        Adventure.LOGGER.info("Loaded Trade Offers");
    }
    private static void loadFood(){
        if (current_name.startsWith("food")){
            // Crutch time
            loadCategory("Fruits", 50);
            loadCategory("Meats", 25);
            loadCategory("Dairy", 100);
            loadCategory("Breads", 65);
            loadCategory("Grains", 55);
            loadCategory("Vegetables", 65);
            loadCategory("Raw Meats", 50);
            loadCategory("Dough", 75);

        }
    }
    private static void loadMisc(){
        loadContainsCategory("Soup", 150);
        loadContainsCategory("Jar", "_jar", 200);
        loadContainsCategory("Honey", 150);
        loadContainsCategory("Cheese", "cheese", "cloth", 350);
        loadContainsCategory("Gem", 500);
        loadContainsCategory("Plant", 5);
        loadContainsCategory("Cloth", "_cloth", 100);
        loadContainsCategory("Cloth", "fabric", 140);
        loadContainsCategory("Coal", "charcoal", 5);
        loadContainsCategory("Coal", "_coal", 10);
        loadContainsCategory("Dope", "cocaine_powder", 250);
        loadContainsCategory("Dope", "cigar", 150);
        loadContainsCategory("Dope", "joint", 100);
        loadContainsCategory("Alcohol", "barrel", 1000);
    }
    private static void loadMetal(){
        for (Metal.Default metal : Metal.Default.values()) {
            if(!metal.hasParts()) return;
            int price = (int)((Math.pow(TIER_MODIFIER, metal.metalTier().ordinal() + 1)) * 100);
            loadCategory("Ingot", metal, Metal.ItemType.INGOT, price);
            loadArmor(metal, price);
            loadTool(metal, price);
            loadWeapon(metal, price);
        }
    }
    private static void loadArmor(Metal.Default metal, int price) {
        if(!metal.hasArmor()) return;
        loadCategory("Armor", metal, Metal.ItemType.HELMET, price * 6);
        loadCategory("Armor", metal, Metal.ItemType.CHESTPLATE, price * 6);
        loadCategory("Armor", metal, Metal.ItemType.GREAVES, price * 8);
        loadCategory("Armor", metal, Metal.ItemType.BOOTS, price * 4);
    }
    private static void loadTool(Metal.Default metal, int price) {
        if(!metal.hasTools()) return;
        loadCategory("Tool", metal, Metal.ItemType.PICKAXE, price);
        loadCategory("Tool", metal, Metal.ItemType.PROPICK, price);
        loadCategory("Tool", metal, Metal.ItemType.AXE, price);
        loadCategory("Tool", metal, Metal.ItemType.SHOVEL, price);
        loadCategory("Tool", metal, Metal.ItemType.SAW, price);
        loadCategory("Tool", metal, Metal.ItemType.CHISEL, price);
        loadCategory("Tool", metal, Metal.ItemType.HAMMER, price);
        loadCategory("Tool", metal, Metal.ItemType.SCYTHE, price);
        loadCategory("Tool", metal, Metal.ItemType.KNIFE, price);
        loadCategory("Tool", metal, Metal.ItemType.HOE, price);
        loadCategory("Tool", metal, Metal.ItemType.FISHING_ROD, price * 2);
        loadCategory("Tool", metal, Metal.ItemType.SHEARS, price * 2);
    }
    private static void loadWeapon(Metal.Default metal, int price) {
        if(!metal.hasTools()) return;
        loadCategory("Weapon", metal, Metal.ItemType.SWORD, price * 2);
        loadCategory("Weapon", metal, Metal.ItemType.MACE, price * 2);
        loadCategory("Weapon", metal, Metal.ItemType.SHIELD, price * 4);
        loadCategory("Weapon", metal, Metal.ItemType.JAVELIN, price);
    }
    private static void loadContainsCategory(String category, int price){
        loadContainsCategory(category, category.toLowerCase(), price);
    }
    private static void loadContainsCategory(String category, String needle, int price){
        if(current_name.contains(needle))
            loadCategory(category, current_item, price);
    }
    private static void loadContainsCategory(String category, String needle, String not, int price){
        if(!current_name.contains(not))
            loadContainsCategory(category, needle, price);
    }
    private static void loadCategory(String category, Item item, int price) {
        Adventure.LOGGER.info("Loaded: {} to {}", item.toString(), category);
        CATEGORIES.computeIfAbsent(category, k -> new ArrayList<>())
                .add(new TradeOffer(item, price));
    }
    private static void loadCategory(String category, int price) {
        loadCategory(category, current_item, price);
    }
    private static void loadCategory(String category, Metal.Default metal, Metal.ItemType itemType, int price) {
        String metal_name = (metal.name().substring(0, 1).toUpperCase() + metal.name().substring(1).toLowerCase()).replace("_", " ");
        loadCategory(metal_name + " " + category, metalItem(metal, itemType), price);
    }
    private static Item metalItem(Metal.Default metal, Metal.ItemType itemType) {
        return TFCItems.METAL_ITEMS.get(metal).get(itemType).get();
    }
}