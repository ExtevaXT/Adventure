package su.external.adventure.entity.pirate;

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

public class PirateCorsairEntity extends AbstractRaidEntity {
    protected static final String[] WEAPONS = {"sword", "knife"};
    public PirateCorsairEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setWeapon();
        METALS = new String[] {"black_bronze", "wrought_iron", "steel", "black_steel"};
        setArmor();
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return AbstractHumanoidEntity.bakeAttributes()
                .add(Attributes.MAX_HEALTH, Config.pirateCorsair.maxHealth.get())
                .add(Attributes.ATTACK_DAMAGE, Config.pirateCorsair.attackDamage.get());
    }

    @Override
    protected void setArmor() {
        armor_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.CHEST, "metal/chestplate/"+METALS[armor_tier]);
        for (int i = 0; i < 4; i++)
            this.armorDropChances[i] = armor_tier < 4 ? 0.01F : 0F;
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.MAINHAND, "metal/" + WEAPONS[random.nextInt(WEAPONS.length)] +"/"+ METALS[weapon_tier]);
        super.setWeapon();
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        coin_multiplier = Config.pirateCorsair.coinMultiplier.get().floatValue();
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }
}
