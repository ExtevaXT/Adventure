package su.external.adventure.entity.pirate;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractRangedEntity;

public class PirateCrossbowerEntity extends AbstractRangedEntity implements CrossbowAttackMob {
    public PirateCrossbowerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setArmor();
        setWeapon();
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new RangedCrossbowAttackGoal<>(this, 1.0D, 8.0F));
    }
    @Override
    protected void setArmor() {
        armor_tier = 3;
    }
    @Override
    protected void setWeapon() {
        weapon_tier = 6;
        setEquipment(EquipmentSlot.MAINHAND, Items.CROSSBOW);
        super.setWeapon();
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        coin_multiplier = Config.pirateCrossbower.coinMultiplier.get().floatValue();
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }

    // Copied from Pillager
    private static final EntityDataAccessor<Boolean> IS_CHARGING_CROSSBOW = SynchedEntityData.defineId(Pillager.class, EntityDataSerializers.BOOLEAN);
    private static final float CROSSBOW_POWER = 1.6F;
    public boolean canFireProjectileWeapon(ProjectileWeaponItem p_33280_) {
        return p_33280_ == Items.CROSSBOW;
    }
    public boolean isChargingCrossbow() {
        return this.entityData.get(IS_CHARGING_CROSSBOW);
    }
    public void setChargingCrossbow(boolean pIsCharging) {
        this.entityData.set(IS_CHARGING_CROSSBOW, pIsCharging);
    }
    public void onCrossbowAttackPerformed() {
        this.noActionTime = 0;
    }
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_CHARGING_CROSSBOW, false);
    }
    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        this.performCrossbowAttack(this, 1.6F);
    }

    public void shootCrossbowProjectile(LivingEntity p_33275_, ItemStack p_33276_, Projectile p_33277_, float p_33278_) {
        this.shootCrossbowProjectile(this, p_33275_, p_33277_, p_33278_, 1.6F);
    }

}
