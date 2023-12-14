package su.external.adventure.entity.boss;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import su.external.adventure.config.Config;

public class BigSlimeEntity extends Slime {
    private final ServerBossEvent bossEvent = (ServerBossEvent)(new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.GREEN, BossEvent.BossBarOverlay.PROGRESS));
    public BigSlimeEntity(EntityType<? extends Slime> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 2F));
    }

    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void customServerAiStep() {
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        super.customServerAiStep();
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, Config.bigSlime.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.bigSlime.attackDamage.get())
                .add(Attributes.ATTACK_SPEED, Config.bigSlime.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.bigSlime.movementSpeed.get())
                .add(Attributes.FOLLOW_RANGE, Config.bigSlime.followRange.get());
    }
    @Override
    protected ResourceLocation getDefaultLootTable() {
        return this.getType().getDefaultLootTable();
    }

    @Override
    public void die(DamageSource source) {
        if(source.getEntity() instanceof Player killer)
            for (Player player : this.getLevel().players())
                player.sendMessage(new TranslatableComponent("boss.kill", this.getName(), killer), player.getUUID());
        super.die(source);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.setSize(10, true);
        for (int i = 0; i < 3; i++)
            this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.BUCKET_FILL_FISH, this.getSoundSource(), 1.0F, random.nextFloat(), false);
    }

    @Override
    public boolean alwaysAccepts() {
        return super.alwaysAccepts();
    }

    @Override
    public void remove(Entity.RemovalReason p_146834_) {
        this.setRemoved(p_146834_);
        if (p_146834_ == Entity.RemovalReason.KILLED) {
            this.gameEvent(GameEvent.ENTITY_KILLED);
        }
        this.invalidateCaps();

    }
}
