//package su.external.adventure.entity.base;
//
//import net.minecraft.network.syncher.EntityDataAccessor;
//import net.minecraft.network.syncher.EntityDataSerializers;
//import net.minecraft.network.syncher.SynchedEntityData;
//import net.minecraft.sounds.SoundEvent;
//import net.minecraft.world.entity.*;
//import net.minecraft.world.entity.ai.navigation.PathNavigation;
//import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
//import net.minecraft.world.entity.monster.Monster;
//import net.minecraft.world.level.Level;
//import su.external.adventure.entity.base.AbstractCrawlerEntity;
//
//import javax.annotation.Nullable;
//
//
//public class AbstractCrawlerEntity extends Monster {
//    protected AbstractCrawlerEntity(EntityType<? extends Monster> entityType, Level level) {
//        super(entityType, level);
//    }
//    @Nullable
//    @Override
//    protected SoundEvent getAmbientSound() {
//        return AoASounds.ENTITY_CAVE_BUG_AMBIENT.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getDeathSound() {
//        return AoASounds.ENTITY_CAVE_BUG_DEATH.get();
//    }
//
//    @Nullable
//    @Override
//    protected SoundEvent getHurtSound(DamageSource source) {
//        return AoASounds.ENTITY_CAVE_BUG_HURT.get();
//    }
//
//    @Override
//    protected SoundEvent getStepSound(BlockPos pos, BlockState blockState) {
//        return SoundEvents.SPIDER_STEP;
//    }
//
//    @Override
//    public MobType getMobType() {
//        return MobType.ARTHROPOD;
//    }
//}
