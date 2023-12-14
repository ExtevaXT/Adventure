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
//public class DoublerEntity extends AbstractCrawlerEntity {
//    public DoublerEntity(EntityType<? extends DoublerEntity> entityType, Level world) {
//        super(entityType, world);
//    }
//
//    @Override
//    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
//        return 4f;
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getAmbientSound() {
//        return AoASounds.ENTITY_DOUBLER_AMBIENT.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getDeathSound() {
//        return AoASounds.ENTITY_DOUBLER_DEATH.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getHurtSound(DamageSource source) {
//        return AoASounds.ENTITY_DOUBLER_HURT.get();
//    }
//
//    @Override
//    public void aiStep() {
//        super.aiStep();
//
//        Player closestPlayer = level().getNearestPlayer(getX(), getY(), getZ(), 10, pl -> PlayerUtil.shouldPlayerBeAffected((Player)pl));
//
//        if (closestPlayer != null)
//            EntityUtil.applyPotions(closestPlayer, new EffectBuilder(MobEffects.BLINDNESS, 30));
//    }
//
//    @Override
//    protected int getAttackSwingDuration() {
//        return 41;
//    }
//
//    @Override
//    protected int getPreAttackTime() {
//        return 23;
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        controllers.add(
//                DefaultAnimations.genericWalkController(this),
//                AoAAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_SLAM));
//    }
//}
