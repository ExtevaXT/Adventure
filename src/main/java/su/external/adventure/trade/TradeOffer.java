package su.external.adventure.trade;

import net.minecraft.world.item.Item;

public class TradeOffer {
    public TradeOffer(Item item, int price) {
        this.item = item;
        this.price = price;
    }

    public Item item;
    public int price;
}
