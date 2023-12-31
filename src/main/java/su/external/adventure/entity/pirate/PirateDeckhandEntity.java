package su.external.adventure.entity.pirate;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import su.external.adventure.config.Config;
import su.external.adventure.entity.base.AbstractHumanoidEntity;
import su.external.adventure.entity.base.AbstractMeleeEntity;

public class PirateDeckhandEntity extends AbstractMeleeEntity {
    protected static final String[] WEAPONS = {"sword", "knife"};
    public PirateDeckhandEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        setWeapon();
        METALS = new String[] {"black_bronze", "wrought_iron", "steel", "black_steel"};
        setArmor();
    }
    public static AttributeSupplier.Builder bakeAttributes() {
        return AbstractHumanoidEntity.bakeAttributes()
                .add(Attributes.ATTACK_SPEED, Config.pirateDeckhand.attackSpeed.get())
                .add(Attributes.MOVEMENT_SPEED, Config.pirateDeckhand.movementSpeed.get());
    }
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.3F));
    }
    @Override
    protected void setArmor() {
        armor_tier = 1;
    }
    @Override
    protected void setWeapon() {
        weapon_tier = random.nextInt(METALS.length);
        setEquipment(EquipmentSlot.MAINHAND, "metal/" + WEAPONS[random.nextInt(WEAPONS.length)] +"/"+ METALS[weapon_tier]);
        setEquipment(EquipmentSlot.OFFHAND, "metal/" + WEAPONS[random.nextInt(WEAPONS.length)] +"/"+ METALS[weapon_tier]);
        super.setWeapon();
    }
}
