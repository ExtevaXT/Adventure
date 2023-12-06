package su.external.adventure.entity.bandit;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import su.external.adventure.entity.base.AbstractRaidEntity;

public class BanditEntity extends AbstractRaidEntity {
    protected static final String[] WEAPONS = {"sword", "mace", "javelin", "knife"};
    public BanditEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        if(random.nextInt(100) < ARMOR_PROBABILITY) setArmor();
        setWeapon();
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.MAINHAND, "metal/" + WEAPONS[random.nextInt(WEAPONS.length)] +"/"+ METALS[weapon_tier]);
        setEquipment(EquipmentSlot.OFFHAND, Items.TORCH);
        super.setWeapon();
    }

}
