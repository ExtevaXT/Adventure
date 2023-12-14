package su.external.adventure.entity.base;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import su.external.adventure.Adventure;
import su.external.adventure.config.Config;
import su.external.adventure.item.AdventureItems;

public abstract class AbstractHumanoidEntity extends Monster {
    protected String[] METALS = Config.humanoidEntity.metals.get().toArray(String[]::new);
    protected int armor_tier = -1;
    protected int weapon_tier = -1;
    protected float coin_multiplier = Config.humanoidEntity.coinMultiplier.get().floatValue();
    protected static final float ARMOR_PROBABILITY = Config.humanoidEntity.armorProbability.get().floatValue();
    protected AbstractHumanoidEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.humanoidEntity.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.humanoidEntity.attackDamage.get())
                .add(Attributes.ATTACK_SPEED, Config.humanoidEntity.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.humanoidEntity.movementSpeed.get())
                .add(Attributes.FOLLOW_RANGE, Config.humanoidEntity.followRange.get());
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0));

        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(1, (new NearestAttackableTargetGoal(this, Player.class, true)));
    }

    protected void setArmor() {
        armor_tier = random.nextInt(METALS.length);
        String metal = METALS[armor_tier];

        setEquipment(EquipmentSlot.HEAD, "metal/helmet/" + metal);
        setEquipment(EquipmentSlot.CHEST, "metal/chestplate/" + metal);
        setEquipment(EquipmentSlot.LEGS, "metal/greaves/" + metal);
        setEquipment(EquipmentSlot.FEET, "metal/boots/" + metal);

        for (int i = 0; i < 4; i++)
            this.armorDropChances[i] = armor_tier < 4 ? 0.01F : 0F;
    }
    protected void setWeapon() {
        for (int i = 0; i < 2; i++)
            handDropChances[i] = weapon_tier < 4 ? 0.025F : 0F;
    }
    protected void setEquipment(EquipmentSlot slot, String path) {
        setEquipment(slot, Accessor(path));
    }
    protected void setEquipment(EquipmentSlot slot, Item item) {
        ItemStack equipment = new ItemStack(item);
        //equipment.setDamageValue(random.nextInt(100));
        this.setEquipment(slot, equipment);
    }
    protected void setEquipment(EquipmentSlot slot, ItemStack equipment) {
        this.setItemSlot(slot, equipment);
    }
    protected Item Accessor(String name) {
        ResourceLocation itemLocation = new ResourceLocation("tfc", name);
        return Registry.ITEM.get(itemLocation);
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
        // Drop some coins
        if (source.getEntity() instanceof Player) {
            // funny maths

            //armor is more significant
            float modifier = Math.max(1, (armor_tier + 1) / 2F + (weapon_tier + 1) / 8F);
            int min = (armor_tier + 1) * 10;
            int max = Math.round((min + 10) * modifier);
            int amount = Math.round(random.nextInt(min, max) * coin_multiplier);
            Adventure.LOGGER.info("adventure.entity coin amount =  " + amount + " a:" + armor_tier+ " w:"+weapon_tier);

            int[] values = { 100, 10, 1 };
            for (int value : values) {
                int coins = amount / value;
                amount %= value;
                if (coins != 0)
                    this.spawnAtLocation(new ItemStack(AdventureItems.COIN.get(value).get(), coins));
            }
        }
    }
}
