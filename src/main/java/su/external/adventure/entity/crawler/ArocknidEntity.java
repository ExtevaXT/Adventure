package su.external.adventure.entity.crawler;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import su.external.adventure.client.AdventureAnimations;
import su.external.adventure.entity.base.AbstractCrawlerEntity;


public class ArocknidEntity extends AbstractCrawlerEntity implements IAnimatable {
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.<Byte>defineId(ArocknidEntity.class, EntityDataSerializers.BYTE);

    public ArocknidEntity(EntityType<? extends ArocknidEntity> entityType, Level world) {
        super(entityType, world);
    }
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(CLIMBING, (byte)0);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 0.59375f;
    }

    @Override
    public void tick() {
        super.tick();

        if (!level.isClientSide)
            setBesideClimbableBlock(this.horizontalCollision);
    }

    @Override
    public boolean onClimbable() {
        return isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return (this.entityData.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte climbingBit = this.entityData.get(CLIMBING);

        if (climbing) {
            climbingBit = (byte)(climbingBit | 1);
        }
        else {
            climbingBit = (byte)(climbingBit & -2);
        }

        this.entityData.set(CLIMBING, climbingBit);
    }
    @Override
    protected void onAttack(Entity target) {
        if(target instanceof Player player)
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 120));
    }

    @Override
    protected int getAttackSwingDuration() {
        return 8;
    }

    @Override
    protected int getPreAttackTime() {
        return 2;
    }


    private AnimationFactory factory = new AnimationFactory(this);

//    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
//        if (event.isMoving()) {
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("move.walk", true));
//            return PlayState.CONTINUE;
//        }
//        return PlayState.CONTINUE;
//    }
//    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
//        if(this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)){
//            event.getController().markNeedsReload();
//            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack.bite", false));
//            this.swinging = false;
//        }
//
//        return PlayState.CONTINUE;
//    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(AdventureAnimations.genericAttackAnimation(this, AdventureAnimations.ATTACK_BITE));
        data.addAnimationController(AdventureAnimations.genericWalkIdleController(this));
//        data.addAnimationController(new AnimationController(this, "controller",
//                0, this::predicate));
//        data.addAnimationController(new AnimationController(this, "attackController",
//                0, this::attackPredicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        controllers.add(
//                DefaultAnimations.genericWalkIdleController(this),
//                AoAAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_BITE));
//        // TODO ranged animation exists
//    }
}
