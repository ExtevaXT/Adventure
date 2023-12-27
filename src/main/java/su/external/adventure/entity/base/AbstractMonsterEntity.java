package su.external.adventure.entity.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import su.external.adventure.config.Config;

public abstract class AbstractMonsterEntity extends Monster {
    protected AbstractMonsterEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.monsterEntity.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.monsterEntity.attackDamage.get())
                .add(Attributes.ATTACK_SPEED, Config.monsterEntity.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.monsterEntity.movementSpeed.get())
                .add(Attributes.FOLLOW_RANGE, Config.monsterEntity.followRange.get());
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
}
