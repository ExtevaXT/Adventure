package su.external.adventure.entity.crawler;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import su.external.adventure.client.AdventureAnimations;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractCrawlerEntity;
import su.external.adventure.init.AdventureSounds;

import javax.annotation.Nullable;


public class CaveCreepEntity extends AbstractCrawlerEntity implements IAnimatable {
    private static final double MIN_LIFTED_DISTANCE = Config.caveCreepEntity.minLiftedDistance.get();
    public static final AnimationBuilder LIFT_HOLD_ANIM = new AnimationBuilder().addAnimation("misc.lift.hold");
    public static final AnimationBuilder LIFT_ANIM = new AnimationBuilder().addAnimation("misc.lift").addAnimation("misc.lift.hold");
    public static final AnimationBuilder DROP_ANIM = new AnimationBuilder().addAnimation("misc.drop");
    public static final AnimationBuilder TRANSITION_WALK_ANIM = new AnimationBuilder().addAnimation("move.walk.transition");
    public static final AnimationBuilder LIFTED_WALK_ANIM = new AnimationBuilder().addAnimation("move.walk.lifted");
    private static final EntityDataAccessor<Integer> LAST_AGGRO_TIME = SynchedEntityData.defineId(CaveCreepEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_AGGRO = SynchedEntityData.defineId(CaveCreepEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> DISTANCE_TO_TARGET = SynchedEntityData.defineId(CaveCreepEntity.class, EntityDataSerializers.FLOAT);
    private int lastAggroChange = -1;
    private boolean isAggro = false;
    private float distanceToTarget = -1F;
    public CaveCreepEntity(EntityType<? extends CaveCreepEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();

        getEntityData().define(LAST_AGGRO_TIME, -1);
        getEntityData().define(IS_AGGRO, false);
        getEntityData().define(DISTANCE_TO_TARGET, -1F);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AdventureSounds.ENTITY_CAVE_CREEP_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AdventureSounds.ENTITY_CAVE_CREEP_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AdventureSounds.ENTITY_CAVE_CREEP_HURT.get();
    }


    @Override
    public void setTarget(@Nullable LivingEntity target) {
        LivingEntity oldTarget = getTarget();

        super.setTarget(target);

        if (target != oldTarget) {
            this.lastAggroChange = this.tickCount;

            getEntityData().set(LAST_AGGRO_TIME, this.lastAggroChange);
            getEntityData().set(IS_AGGRO, getTarget() != null);
            getEntityData().set(DISTANCE_TO_TARGET, getTarget() != null ? getTarget().distanceTo(this) : -1F);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);

        if (key.equals(LAST_AGGRO_TIME)) {
            this.lastAggroChange = getEntityData().get(LAST_AGGRO_TIME);
        }
        else if (key.equals(IS_AGGRO)) {
            this.isAggro = getEntityData().get(IS_AGGRO);
        }
        else if (key.equals(DISTANCE_TO_TARGET)) {
            this.distanceToTarget = getEntityData().get(DISTANCE_TO_TARGET);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (lastAggroChange >= 0 && tickCount - lastAggroChange > 10)
            lastAggroChange = -1;
    }

    @Override
    protected int getAttackSwingDuration() {
        return 11;
    }

    @Override
    protected int getPreAttackTime() {
        return 6;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(AdventureAnimations.genericAttackAnimation(this, AdventureAnimations.ATTACK_BITE));
        data.addAnimationController(new AnimationController<>(this, "movement", 0, state -> {
            if (state.isMoving()) {
                if (lastAggroChange >= 0){
                    state.getController().setAnimation(TRANSITION_WALK_ANIM);
                    return PlayState.CONTINUE;
                }
                if (isAggro && distanceToTarget > MIN_LIFTED_DISTANCE){
                    state.getController().setAnimation(LIFTED_WALK_ANIM);
                    return PlayState.CONTINUE;
                }

                state.getController().setAnimation(AdventureAnimations.WALK);
                return PlayState.CONTINUE;
            }
            else if (isAggro) {
                if (lastAggroChange == -1)
                    state.getController().setAnimation(LIFT_HOLD_ANIM);

                return PlayState.CONTINUE;
            }

            return PlayState.STOP;
        }));
        data.addAnimationController((new AnimationController<>(this, "lifts", 0, state -> {
            if (this.lastAggroChange >= 0){
                state.getController().setAnimation(this.isAggro ? LIFT_ANIM : DROP_ANIM);
                return PlayState.CONTINUE;
            }

            return PlayState.STOP;
        })));
    }
    private AnimationFactory factory = new AnimationFactory(this);

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
