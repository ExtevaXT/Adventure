package su.external.adventure.entity.trader;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import su.external.adventure.item.AdventureItems;
import su.external.adventure.helpers.CoinHelper;
import su.external.adventure.helpers.TradeHelper;
import su.external.adventure.trade.TradeOffer;

import java.util.List;

public class TraderEntity extends Mob {
    public List<TradeOffer> acceptable;
    public TraderEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
    }
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() instanceof Player player && player.isCreative())
            return super.hurt(source, amount);
        return false;
    }
    @Override
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand hand) {
        acceptable = TradeHelper.OFFERS.values().stream().flatMap(List::stream).toList();
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack stack = player.getItemInHand(hand);
            //Adventure.LOGGER.info(stack.getTooltipLines(player, TooltipFlag.Default.NORMAL));
            // Give coins for item in hand or exchange coins
            if(!stack.isEmpty()) {
                if(player.isShiftKeyDown())
                    sellItemStack(player, stack);
                else
                    checkPrice(player, stack);
            }
            else exchangeCoins(player);

            return InteractionResult.SUCCESS;
        }
        return super.interactAt(player, vec3, hand);
    }

    public static AttributeSupplier.Builder bakeAttributes() {
        return Mob.createMobAttributes();
    }
    public void giveCoins(Player player, int amount){
        int[] values = { 100, 10, 1 };
        for (int value : values) {
            int coins = amount / value;
            amount %= value;
            if (coins != 0){
                ItemStack stack = new ItemStack(AdventureItems.COIN.get(value).get(), coins);
                if(!player.getInventory().add(stack))
                    this.spawnAtLocation(stack);
            }
        }
    }
    public void sellItemStack(Player player, ItemStack stack){
        for(String category : TradeHelper.OFFERS.keySet()){
            for(TradeOffer offer : TradeHelper.OFFERS.get(category)){
                if(offer.item == stack.getItem()){
                    float modifier = TradeHelper.modifier(stack);
                    if(TradeHelper.isNutrientCategory(category) && !TradeHelper.hasNutrient(stack, player, category)) modifier = 0;
                    if(category.equals("Alcohol")) modifier *= TradeHelper.alcoholAmount(stack) / 10000;
                    if(modifier != 0) {
                        player.getInventory().removeItem(stack);
                        int price = (int)(offer.price * modifier * stack.getCount());
                        giveCoins(player, price);
                        player.sendMessage(new TranslatableComponent("trader.sold", price), player.getUUID());
                        return;
                    }
                }
            }
        }
        acceptableInfo(player);
    }
    public void checkPrice(Player player, ItemStack stack){
        for(TradeOffer offer : acceptable){
            if(offer.item == stack.getItem()){
                float modifier = TradeHelper.modifier(stack);
                if(modifier != 0) {
                    int price = (int)(offer.price * modifier * stack.getCount());
                    player.sendMessage(new TranslatableComponent("trader.check", price), player.getUUID());
                    return;
                }
            }
        }
        acceptableInfo(player);
    }
    public void exchangeCoins(Player player){
        int amount = CoinHelper.takeCoins(player);
        if(amount != 0){
            giveCoins(player, amount);
            player.sendMessage(new TranslatableComponent("trader.exchange", amount), player.getUUID());
            return;
        }
        acceptableInfo(player);
    }
    public void acceptableInfo(Player player){
        player.sendMessage(new TranslatableComponent("trader.acceptable", TradeHelper.MESSAGE), player.getUUID());
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return false;
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }
    @Override
    public boolean canBeCollidedWith() {
        return false;
    }
    @Override
    public boolean isPushedByFluid() {
        return false;
    }
    @Override
    public void push(Entity entity) {}
}
