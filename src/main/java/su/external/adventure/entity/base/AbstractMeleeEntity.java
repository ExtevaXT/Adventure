package su.external.adventure.entity.base;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class AbstractMeleeEntity extends AbstractHumanoidEntity {
    public AbstractMeleeEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ARMOR_EQUIP_CHAIN;
    }
}
