package su.external.adventure.entity.pirate;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
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
        super.setWeapon();
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
