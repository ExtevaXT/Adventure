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
//public class RockbiterEntity extends AbstractCrawlerEntity {
//    public RockbiterEntity(EntityType<? extends RockbiterEntity> entityType, Level world) {
//        super(entityType, world);
//    }
//
//    @Override
//    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
//        return 13 / 16f;
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getAmbientSound() {
//        return AoASounds.ENTITY_ROCKBITER_AMBIENT.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getDeathSound() {
//        return AoASounds.ENTITY_ROCKBITER_DEATH.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getHurtSound(DamageSource source) {
//        return AoASounds.ENTITY_ROCKBITER_HURT.get();
//    }
//
//    @Override
//    protected int getAttackSwingDuration() {
//        return 8;
//    }
//
//    @Override
//    protected int getPreAttackTime() {
//        return 3;
//    }
//
//    @Override
//    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
//        controllers.add(
//                DefaultAnimations.genericWalkController(this),
//                AoAAnimations.genericAttackAnimation(this, DefaultAnimations.ATTACK_BITE));
//        // TODO has block animation
//    }
//}
