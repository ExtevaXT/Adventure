package su.external.adventure.entity.goblin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractHumanoidEntity;
import su.external.adventure.entity.base.AbstractMeleeEntity;

public class GoblinPeonEntity extends AbstractMeleeEntity {
    public GoblinPeonEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setArmor();
        setWeapon();
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return AbstractHumanoidEntity.bakeAttributes()
                .add(Attributes.ATTACK_SPEED, Config.goblinPeon.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.goblinPeon.movementSpeed.get());
    }
    @Override
    protected void setArmor() {
        armor_tier = 1;
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.MAINHAND, "metal/knife/"+ METALS[weapon_tier]);
        super.setWeapon();
    }
}
