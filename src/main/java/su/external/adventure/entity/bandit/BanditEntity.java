package su.external.adventure.entity.bandit;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import su.external.adventure.entity.base.AbstractRaidEntity;

import java.util.Random;

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
    public static boolean checkBanditSpawnRules(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, Random pRandom) {
        return pPos.getY() >= pLevel.getSeaLevel() && isDarkEnoughToSpawn(pLevel, pPos, pRandom) && checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }
}
