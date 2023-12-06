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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import su.external.adventure.entity.base.AbstractRangedEntity;

public class PirateCrossbowerEntity extends AbstractRangedEntity{
    public PirateCrossbowerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setArmor();
        setWeapon();
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F));
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
        coin_multiplier = 1.5F;
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }
}
