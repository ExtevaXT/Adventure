package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;


public class MonsterEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double>  maxHealth, attackDamage, attackSpeed, movementSpeed, followRange;
    public MonsterEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("MonsterEntity");
        maxHealth = builder.define("maxHealth", 20D);
        attackDamage = builder.define("attackDamage", 1D);
        attackSpeed = builder.define("attackSpeed", 1.5D);
        movementSpeed = builder.define("movementSpeed", 0.3D);
        followRange = builder.define("followRange", 16D);
        builder.pop();
    }
}