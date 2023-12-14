package su.external.adventure.entity.goblin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractRangedEntity;

public class GoblinArcherEntity extends AbstractRangedEntity {
    public GoblinArcherEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setArmor();
        setWeapon();
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RangedBowAttackGoal<>(this,
                Config.goblinArcher.speedModifier.get(),
                Config.goblinArcher.attackIntervalMin.get(),
                Config.goblinArcher.attackRadius.get().floatValue()
        ));
    }
    @Override
    protected void setArmor() {
        armor_tier = 1;
        setEquipment(EquipmentSlot.HEAD, "metal/helmet/bismuth_bronze");
        for (int i = 0; i < 4; i++)
            this.armorDropChances[i] = armor_tier < 4 ? 0.01F : 0F;
    }
    @Override
    protected void setWeapon() {
        weapon_tier = 4;
        setEquipment(EquipmentSlot.MAINHAND, Items.BOW);
        super.setWeapon();
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        coin_multiplier = Config.goblinArcher.coinMultiplier.get().floatValue();
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }
}
