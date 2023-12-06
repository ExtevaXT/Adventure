package su.external.adventure.entity.base;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public abstract class AbstractRaidEntity extends AbstractMeleeEntity {
    private final BreakDoorGoal breakDoorGoal = new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE);
    private boolean canBreakDoors = true;
    public AbstractRaidEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setCanBreakDoors(true);
    }
    private static final Predicate<Difficulty> DOOR_BREAKING_PREDICATE = (difficulty) -> difficulty != Difficulty.PEACEFUL;
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new BreakDoorGoal(this, DOOR_BREAKING_PREDICATE));
    }
    public void setCanBreakDoors(boolean canBreakDoors) {
        if (GoalUtils.hasGroundPathNavigation(this)) {
            if (this.canBreakDoors != canBreakDoors) {
                this.canBreakDoors = canBreakDoors;
                ((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(canBreakDoors);
                if (canBreakDoors) {
                    this.goalSelector.addGoal(1, this.breakDoorGoal);
                } else {
                    this.goalSelector.removeGoal(this.breakDoorGoal);
                }
            }
        } else if (this.canBreakDoors) {
            this.goalSelector.removeGoal(this.breakDoorGoal);
            this.canBreakDoors = false;
        }

    }
}
