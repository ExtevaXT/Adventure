package su.external.adventure.entity.pirate;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.LlamaSpit;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractHumanoidEntity;
import su.external.adventure.entity.base.AbstractRaidEntity;

public class PirateCaptainEntity extends AbstractRaidEntity {
    public PirateCaptainEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setWeapon();
        METALS = new String[] {"black_steel","blue_steel","red_steel"};
        setArmor();
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return AbstractHumanoidEntity.bakeAttributes()
                .add(Attributes.MAX_HEALTH, Config.pirateCaptain.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.pirateCaptain.attackDamage.get())
                .add(Attributes.MOVEMENT_SPEED, Config.pirateCaptain.movementSpeed.get());
    }
    @Override
    protected void setArmor() {
        armor_tier = 4;
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.MAINHAND, "metal/sword/"+ METALS[weapon_tier]);
        setEquipment(EquipmentSlot.OFFHAND, Items.SPYGLASS);
        super.setWeapon();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        // Shoot every 5s
        if (this.tickCount % (20 * 5f) == 0) {
            LivingEntity target = getTarget();
            if (target != null) {
                // Shoot in target or yourself
                shoot(random.nextBoolean() ? target : this);
            }
        }
    }
    public void shoot(LivingEntity target){
        Vec3 vec3 = getViewVector(1.0F);
        double x = target.getX() + random.nextFloat(-2, 2);
        double y = target.getY() + random.nextFloat(-2, 2);
        double z = target.getZ() + random.nextFloat(-2, 2);
        SmallFireball projectile = new SmallFireball(level, this, x, y, z);
        projectile.setPos(getX() + vec3.x * 1.5D, getY(), projectile.getZ() + vec3.z * 1.5D);
        level.addFreshEntity(projectile);
        this.level.explode(this, x, y, z,
                random.nextInt(1, 2), Explosion.BlockInteraction.NONE);
        target.hurt(DamageSource.MAGIC, random.nextFloat(0, 2));
    }
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        // Leap in random direction
        if(random.nextBoolean()){
            teleportTo(getX() + random.nextFloat(-2, 2), getY() + 1, getZ() + random.nextFloat(-2, 2));
        }
        return super.hurt(pSource, pAmount);
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        coin_multiplier = Config.pirateCaptain.coinMultiplier.get().floatValue();
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENDER_DRAGON_FLAP, this.getSoundSource(), 3.0F, 1.0F, false);
    }
}
