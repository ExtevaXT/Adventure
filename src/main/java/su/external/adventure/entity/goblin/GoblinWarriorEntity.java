package su.external.adventure.entity.goblin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractMeleeEntity;

public class GoblinWarriorEntity  extends AbstractMeleeEntity {
    protected static final String[] WEAPONS = {"sword", "mace"};
    public GoblinWarriorEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setWeapon();
        METALS = new String[] {"wrought_iron","steel"};
        setArmor();
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.MAINHAND, "metal/" + WEAPONS[random.nextInt(WEAPONS.length)] +"/"+ METALS[weapon_tier]);
        setEquipment(EquipmentSlot.OFFHAND, "metal/shield/"+ METALS[weapon_tier]);
        super.setWeapon();
    }
    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHitIn) {
        coin_multiplier = Config.goblinWarrior.coinMultiplier.get().floatValue();
        super.dropCustomDeathLoot(source, looting, recentlyHitIn);
    }
}
