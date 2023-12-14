//package su.external.adventure.entity.crawlers;
//
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.entity.*;
//import net.minecraft.world.entity.ai.navigation.PathNavigation;
//import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
//import net.minecraft.world.level.Level;
//import su.external.adventure.entity.base.AbstractCrawlerEntity;
//
//
//public class RockCrawlerEntity extends AbstractCrawlerEntity {
//    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.<Byte>defineId(RockCrawlerEntity.class, EntityDataSerializers.BYTE);
//
//    public RockCrawlerEntity(EntityType<? extends RockCrawlerEntity> entityType, Level world) {
//        super(entityType, world);
//    }
//
//    @Override
//    protected void defineSynchedData() {
//        super.defineSynchedData();
//        this.entityData.define(CLIMBING, (byte)0);
//    }
//
//    /*@Override
//    protected void registerGoals() {
//        goalSelector.addGoal(1, new FloatGoal(this));
//        goalSelector.addGoal(2, new LeapAtTargetGoal(this, 0.4f));
//        goalSelector.addGoal(3, new MeleeAttackGoal(this, 1, true));
//        goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1));
//        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8f));
//        goalSelector.addGoal(8, new RandomLookAroundGoal(this));
//        targetSelector.addGoal(1, new HurtByTargetGoal(this));
//        targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
//    }*/
//
//    @Override
//    protected PathNavigation createNavigation(Level world) {
//        return new WallClimberNavigation(this, world);
//    }
//
//    @Override
//    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
//        return 1.6875f;
//    }
//
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
//                DefaultAnimations.genericWalkController(this),
//                AoAAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_BITE));
//        // TODO strafing animation
//    }
//}
