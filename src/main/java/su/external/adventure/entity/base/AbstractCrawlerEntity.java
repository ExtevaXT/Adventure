package su.external.adventure.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import su.external.adventure.Adventure;
import su.external.adventure.config.Config;
import su.external.adventure.init.AdventureSounds;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Random;


public abstract class AbstractCrawlerEntity extends AbstractMonsterEntity {
    protected AbstractCrawlerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this,
                Config.meleeEntity.speedModifier.get(),
                Config.meleeEntity.followingTargetEvenIfNotSeen.get()
        ));
    }

    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.crawlerEntity.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.crawlerEntity.attackDamage.get())
                .add(Attributes.ATTACK_SPEED, Config.crawlerEntity.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.crawlerEntity.movementSpeed.get())
                .add(Attributes.FOLLOW_RANGE, Config.crawlerEntity.followRange.get());
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);

        if (source.getEntity() instanceof Player) {
            Level level = this.getLevel();
            BlockPos pos = this.blockPosition();
            int radius = Config.crawlerEntity.scanRadius.get();

            HashMap<Block, Integer> ores = new HashMap<>();
            BlockPos.betweenClosedStream(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))
                    .map(checkPos -> level.getBlockState(checkPos).getBlock())
                    .filter(block -> block.toString().contains("ore/"))
                    .forEach(block -> ores.merge(block, 1, Integer::sum));
            if(ores.size() > 0){
                Block block = ((Block) ores.keySet().toArray()[random.nextInt(ores.size())]);
                int count = random.nextInt(1, Math.min(Config.crawlerEntity.maxDrop.get(), ores.get(block)));
                this.spawnAtLocation(new ItemStack(block, count));
                Adventure.LOGGER.info("adventure.crawler {}, ores: {}", block, ores);
            }
        }

    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AdventureSounds.ENTITY_CAVE_BUG_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AdventureSounds.ENTITY_CAVE_BUG_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AdventureSounds.ENTITY_CAVE_BUG_HURT.get();
    }
    protected SoundEvent getStepSound(BlockPos pos, BlockState blockState) {
        return SoundEvents.SPIDER_STEP;
    }
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        SoundEvent stepSound = getStepSound(pos, blockIn);

        if (stepSound != null)
            playSound(stepSound, 0.15F, 1.0F);
    }
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }
    protected int getAttackSwingDuration() {
        return 6;
    }

    protected int getPreAttackTime() {
        return 0;
    }
    protected void onAttack(Entity target) {}
    public static boolean checkCrawlerSpawnRules(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return pPos.getY() <= pLevel.getSeaLevel() && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }
}
