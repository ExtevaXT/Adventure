package su.external.adventure.entity.bandit;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.dries007.tfc.common.entities.ai.JavelinAttackGoal;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractRangedEntity;

public class BrigandEntity extends AbstractRangedEntity {
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RangedBowAttackGoal<>(this,
                Config.brigand.speedModifier.get(),
                Config.brigand.attackIntervalMin.get(),
                Config.brigand.attackRadius.get().floatValue()
        ));
        this.goalSelector.addGoal(4, new JavelinAttackGoal<>(this,
                Config.brigand.speedModifier.get(),
                Config.brigand.attackRadius.get().floatValue()
        ));
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        if(random.nextBoolean()) setEquipment(EquipmentSlot.MAINHAND, Items.BOW);
        else setEquipment(EquipmentSlot.MAINHAND, "metal/javelin/" + METALS[weapon_tier]);
        setEquipment(EquipmentSlot.OFFHAND, Items.TORCH);
        super.setWeapon();
    }
    public BrigandEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        if(random.nextInt(100) < ARMOR_PROBABILITY) setArmor();
        setWeapon();
    }
}
