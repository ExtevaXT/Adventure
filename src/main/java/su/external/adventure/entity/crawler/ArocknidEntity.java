//package su.external.adventure.entity.crawlers;
//
//import net.minecraft.core.BlockPos;
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.world.damagesource.DamageSource;
//import net.minecraft.world.entity.*;
//import net.minecraft.world.entity.ai.navigation.PathNavigation;
//import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import su.external.adventure.entity.base.AbstractCrawlerEntity;
//
//import javax.annotation.Nullable;
//
//
//public class ArocknidEntity extends AbstractCrawlerEntity {
//    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.<Byte>defineId(ArocknidEntity.class, EntityDataSerializers.BYTE);
//
//    public ArocknidEntity(EntityType<? extends ArocknidEntity> entityType, Level world) {
//        super(entityType, world);
//    }
//
//    @Override
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(CLIMBING, (byte)0);
//    }
//
//    @Override
//    protected PathNavigation createNavigation(Level world) {
//        return new WallClimberNavigation(this, world);
//    }
//
//    @Override
//    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
//        return 0.59375f;
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//
//        if (!level().isClientSide)
//            setBesideClimbableBlock(this.horizontalCollision);
//    }
//
//    @Override
//    public boolean onClimbable() {
//        return isBesideClimbableBlock();
//    }
//
//    public boolean isBesideClimbableBlock() {
//        return (this.entityData.get(CLIMBING) & 1) != 0;
//    }
//
//    public void setBesideClimbableBlock(boolean climbing) {
//        byte climbingBit = this.entityData.get(CLIMBING);
//
//        if (climbing) {
//            climbingBit = (byte)(climbingBit | 1);
//        }
//        else {
//            climbingBit = (byte)(climbingBit & -2);
//        }
//
//        this.entityData.set(CLIMBING, climbingBit);
//    }
//
//    @Override
//    protected void onAttack(Entity target) {
//        EntityUtil.applyPotions(target, new EffectBuilder(MobEffects.WEAKNESS, 120));
//    }
//
//    @Override
//    protected int getAttackSwingDuration() {
//        return 8;
//    }
//
//    @Override
//    protected int getPreAttackTime() {
//        return 2;
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        controllers.add(
//                DefaultAnimations.genericWalkIdleController(this),
//                AoAAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_BITE));
//        // TODO ranged animation exists
//    }
//}
