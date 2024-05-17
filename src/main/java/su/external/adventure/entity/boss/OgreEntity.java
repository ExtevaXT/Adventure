package su.external.adventure.entity.boss;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.constant.DefaultAnimations;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.attack.AnimatableMeleeAttack;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.CustomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.ReactToUnreachableTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FleeTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.WalkOrRunToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyPlayersSensor;
import net.tslat.smartbrainlib.registry.SBLMemoryTypes;
import net.tslat.smartbrainlib.util.BrainUtils;
import su.external.adventure.Adventure;
import su.external.adventure.config.Config;
import su.external.adventure.entity.tasks.ChargeAttack;
import su.external.adventure.entity.tasks.GroundSlamAttack;
import su.external.adventure.init.AdventureSounds;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class OgreEntity extends Monster implements GeoEntity, SmartBrainOwner<OgreEntity> {
    private SwingData swingData;
    public int ogreType;
    private static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Boolean> ENRAGED = SynchedEntityData.defineId(OgreEntity.class, EntityDataSerializers.BOOLEAN);
    private static final AttributeModifier ENRAGED_DAMAGE_MOD = new AttributeModifier(UUID.fromString("104c09f0-28cc-43dd-81c0-10de6b3083bd"), "EnragedDamageModifier", 1.15f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ENRAGED_ARMOUR_MOD = new AttributeModifier(UUID.fromString("bbbdf964-689b-4bcf-9a23-122a7bba682e"), "EnragedArmourModifier", 1.25f, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final AttributeModifier ENRAGED_TOUGHNESS_MOD = new AttributeModifier(UUID.fromString("ac843c67-4731-4e77-85e9-6992bd92ae4b"), "EnragedToughnessModifier", 1.35f, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final int AXE_SWING_STATE = 0;
    private static final int AXE_SLAM_STATE = 1;
    private static final int CHARGE_STATE = 2;

    private final AnimatableInstanceCache geoCache = AzureLibUtil.createInstanceCache(this);
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public OgreEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        ogreType = getRandom().nextInt(4) + 1;
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.ogreEntity.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.ogreEntity.attackDamage.get())
                .add(Attributes.ATTACK_SPEED, Config.ogreEntity.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.ogreEntity.movementSpeed.get())
                .add(Attributes.FOLLOW_RANGE, Config.ogreEntity.followRange.get());
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return AdventureSounds.ENTITY_OGRE_DEATH.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AdventureSounds.ENTITY_OGRE_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return AdventureSounds.ENTITY_OGRE_AMBIENT.get();
    }
    //    @Nullable
//    @Override
//    protected SoundEvent getStepSound(BlockPos pos, BlockState blockState) {
//        return AoASounds.ENTITY_GENERIC_HEAVY_STEP.get();
//    }

    public void setSharedFlag(int pFlag, boolean pSet) {
        super.setSharedFlag(pFlag, pSet);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return 3.0625f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ATTACK_STATE, 0);
        swingData = new SwingData();
        swingData.put(AXE_SWING_STATE, new SwingData.Swing(20, 13, RawAnimation.begin().thenPlay("attack.axe_swing")));
        swingData.put(AXE_SLAM_STATE, new SwingData.Swing(20, 17, RawAnimation.begin().thenPlay("attack.axe_slam")));
        swingData.put(CHARGE_STATE, new SwingData.Swing(0, 0, RawAnimation.begin().thenPlay("attack.axe_slam")));
        getEntityData().define(ENRAGED, false);
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        tickBrain(this);
        if (getHealth() / getMaxHealth() < 0.25 && !isEnraged())
            enrage();
    }
    @Nullable
    @Override
    public LivingEntity getTarget() {
        return BrainUtils.getTargetOfEntity(this, super.getTarget());
    }
    @Override
    protected Brain.Provider<OgreEntity> brainProvider() {
        return new SmartBrainProvider(this);
    }

    protected void enrage() {
        if (!isEnraged()) {
            getEntityData().set(ENRAGED, true);
            getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(ENRAGED_DAMAGE_MOD);
            getAttribute(Attributes.ARMOR).addPermanentModifier(ENRAGED_ARMOUR_MOD);
            getAttribute(Attributes.ARMOR_TOUGHNESS).addPermanentModifier(ENRAGED_TOUGHNESS_MOD);
            triggerAnim("arms_controller", "enrage");
            this.playSound(AdventureSounds.ENTITY_OGRE_ENRAGE.get(), this.getSoundVolume(), this.getVoicePitch());
            BrainUtils.setForgettableMemory(this, MemoryModuleType.ATTACK_COOLING_DOWN, true, 100);
            if(random.nextFloat() < 0.1f)
                level.explode(this, getX(), getY(), getZ(), 5f, Explosion.BlockInteraction.NONE);
        }
        Adventure.LOGGER.info("Ogre enraged");
    }

    public boolean isEnraged() {
        return getEntityData().get(ENRAGED);
    }

//    @Override
//    public boolean canDisableShield() {
//        return !ATTACK_STATE.is(this, AXE_SLAM_STATE);
//    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        compound.putBoolean("Enraged", isEnraged());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.contains("Enraged"))
            getEntityData().set(ENRAGED, compound.getBoolean("Enraged"));
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity target) {
        return super.getMeleeAttackRangeSqr(target) * Config.ogreEntity.attackRange.get();
    }
    @Override
    public List<ExtendedSensor<OgreEntity>> getSensors() {
        return ObjectArrayList.of(
                new NearbyPlayersSensor<>(),
                new HurtBySensor<>(),
                new UnreachableTargetSensor<OgreEntity>().afterScanning(entity -> {
                    if (!BrainUtils.hasMemory(entity, SBLMemoryTypes.TARGET_UNREACHABLE.get()) && !entityData.get(ATTACK_STATE).equals(CHARGE_STATE))
                        entityData.set(ATTACK_STATE, AXE_SWING_STATE);
                })
        );
    }

    @Override
    public BrainActivityGroup<OgreEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(
                new LookAtTarget<>()
                        .startCondition(entity -> !entityData.get(ATTACK_STATE).equals(CHARGE_STATE)),
                new WalkOrRunToWalkTarget<>()
                        .startCondition(entity -> !entityData.get(ATTACK_STATE).equals(CHARGE_STATE)));
    }

    @Override
    public BrainActivityGroup<OgreEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(
                new FirstApplicableBehaviour<OgreEntity>(
                        new TargetOrRetaliate<>()
                                .useMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER)
                                .attackablePredicate(target -> target.isAlive() && (!(target instanceof Player player) || !player.getAbilities().invulnerable) && !isAlliedTo(target))
                                .startCondition(entity -> !entityData.get(ATTACK_STATE).equals(CHARGE_STATE)),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(15, 45))),
                new OneRandomBehaviour<>(
                        new SetRandomWalkTarget<>().speedModifier(0.9f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(30, 60))));
    }

    @Override
    public BrainActivityGroup<OgreEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>(),
                new SetWalkTargetToAttackTarget<>()
                        .speedMod(1.25f)
                        .startCondition(entity -> !entityData.get(ATTACK_STATE).equals(CHARGE_STATE)),
                new FirstApplicableBehaviour<>(
                        new OneRandomBehaviour<>(
                                Pair.of(
                                        new GroundSlamAttack<>(getSwingWarmupTicks(AXE_SLAM_STATE))
                                                .slamAtTarget()
                                                .radius(4)
                                                .requiresTarget()
                                                .whenActivating(entity -> this.level.playSound(null, getX(), getY(), getZ(), SoundEvents.SHIELD_BREAK, this.getSoundSource(), 1, 1))
                                                .cooldownFor(entity -> (int)(getSwingDurationTicks(AXE_SLAM_STATE) * entity.getRandom().nextFloat() * 2))
                                                .startCondition(mob -> entityData.get(ATTACK_STATE).equals(AXE_SLAM_STATE))
                                                .whenStopping(entity -> entityData.set(ATTACK_STATE, AXE_SWING_STATE)),
                                        20),
                                Pair.of(
                                        new FleeTarget<>().fleeDistance(40).startCondition(entity -> BrainUtils.hasMemory(entity, SBLMemoryTypes.TARGET_UNREACHABLE.get()) && isEnraged()),
                                        1)),
                        new ReactToUnreachableTarget<>()
                                .timeBeforeReacting(entity -> 60)
                                .reaction((entity, isTowering) -> {
                                    enrage();
                                    entityData.set(ATTACK_STATE, AXE_SLAM_STATE);
                                }),
                        new AnimatableMeleeAttack<>(getSwingWarmupTicks(AXE_SWING_STATE))
                                .attackInterval(entity -> getSwingDurationTicks(AXE_SWING_STATE) + entity.getRandom().nextInt(5, 25))
                                .startCondition(mob -> entityData.get(ATTACK_STATE).equals(AXE_SWING_STATE)),
                        new OneRandomBehaviour<>(
                                new CustomBehaviour<>(entity -> {
                                    BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, 90);
                                    triggerAnim("arms_controller", "belly_drum");
                                    this.playSound(AdventureSounds.ENTITY_OGRE_BELLY_DRUM.get(), this.getSoundVolume(), this.getVoicePitch());
                                    addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, 1));
                                    addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100));
                                    addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100, 1));
                                })
                                        .startCondition(entity -> BrainUtils.hasMemory(entity, MemoryModuleType.ATTACK_TARGET) && !BrainUtils.hasMemory(entity, SBLMemoryTypes.SPECIAL_ATTACK_COOLDOWN.get()))
                                        .cooldownFor(entity -> entity.getRandom().nextInt(200, 400))
                                        .whenStopping(entity -> BrainUtils.setForgettableMemory(entity, SBLMemoryTypes.SPECIAL_ATTACK_COOLDOWN.get(), true, 150)),
                                new ChargeAttack<>(38)
                                        .speedModifier(1.5f)
                                        .whenStarting(entity -> {
                                            triggerAnim("arms_controller", "charge_up");
                                            entityData.set(ATTACK_STATE, CHARGE_STATE);
                                        })
                                        .whenStopping(entity -> {
                                            entityData.set(ATTACK_STATE, AXE_SWING_STATE);
                                            BrainUtils.setForgettableMemory(entity, SBLMemoryTypes.SPECIAL_ATTACK_COOLDOWN.get(), true, 150);
                                        })
                                        .cooldownFor(entity -> entity.getRandom().nextInt(100, 400))
                        )));
    }
    private static final RawAnimation WALK_BOTTOM_HALF = RawAnimation.begin().thenPlay("move.walk.bottom_half");
    private static final RawAnimation WALK_TOP_HALF = RawAnimation.begin().thenPlay("move.walk.top_half");
    private static final RawAnimation RUN_BOTTOM_HALF = RawAnimation.begin().thenPlay("move.run.bottom_half");
    private static final RawAnimation RUN_TOP_HALF = RawAnimation.begin().thenPlay("move.run.top_half");
    private static final RawAnimation ENRAGE = RawAnimation.begin().thenPlay("misc.enrage");
    private static final RawAnimation ENRAGED_IDLE = RawAnimation.begin().thenPlay("misc.idle.enraged");
    private static final RawAnimation CHARGE_UP = RawAnimation.begin().thenPlay("misc.charge_up");
    private static final RawAnimation CHARGE = RawAnimation.begin().thenPlay("move.charge");
    private static final RawAnimation BELLY_DRUM = RawAnimation.begin().thenPlay("misc.belly_drum");

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "legs_controller", 0, state -> {
            if (state.isMoving())
                return state.setAndContinue(isSprinting() ? RUN_BOTTOM_HALF : WALK_BOTTOM_HALF);

            return PlayState.STOP;
        }));
        controllers.add(new AnimationController<>(this, "arms_controller", 3, state -> {
            if (entityData.get(ATTACK_STATE).equals(CHARGE_STATE))
                return state.setAndContinue(CHARGE);

            if (this.swinging)
                return state.setAndContinue(getSwingAnimation(entityData.get(ATTACK_STATE)));

            if (state.isMoving()) {
                return state.setAndContinue(isSprinting() ? RUN_TOP_HALF : WALK_TOP_HALF);
            }
            else {
                return state.setAndContinue(isEnraged() ? ENRAGED_IDLE : DefaultAnimations.IDLE);
            }
        }).triggerableAnim("enrage", ENRAGE).triggerableAnim("belly_drum", BELLY_DRUM).triggerableAnim("charge_up", CHARGE_UP));
    }
    public record SwingData(Int2ObjectMap<Swing> data) {
        public record Swing(int animLength, int warmupTicks, RawAnimation anim) {}

        public SwingData() {
            this(Util.make(new Int2ObjectOpenHashMap<>(), map -> map.defaultReturnValue(new Swing(0, 0, RawAnimation.begin()))));
        }

        public void put(int key, Swing swing) {
            this.data.put(key, swing);
        }

        public Swing getSwing(int key) {
            return this.data.get(key);
        }

        public int getSwingLength(int key) {
            return getSwing(key).animLength();
        }

        public int getSwingPreHurtTime(int key) {
            return getSwing(key).warmupTicks();
        }

        public RawAnimation getSwingAnimation(int key) {
            return getSwing(key).anim();
        }
    }
    public SwingData getSwingData() {
        return this.swingData;
    }

    protected RawAnimation getSwingAnimation() {
        return getSwingAnimation(entityData.get(ATTACK_STATE));
    }

    protected RawAnimation getSwingAnimation(int state) {
        return this.swingData.getSwingAnimation(state);
    }

    protected int getSwingDurationTicks() {
        return getSwingDurationTicks(entityData.get(ATTACK_STATE));
    }

    protected int getSwingDurationTicks(int state) {
        return this.swingData.getSwingLength(state);
    }

    protected int getSwingWarmupTicks() {
        return getSwingWarmupTicks(entityData.get(ATTACK_STATE));
    }

    protected int getSwingWarmupTicks(int state) {
        return this.swingData.getSwingPreHurtTime(state);
    }
}