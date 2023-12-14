package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class HumanoidEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double> armorProbability, coinMultiplier , maxHealth, attackDamage, attackSpeed, movementSpeed, followRange;
    public final ForgeConfigSpec.ConfigValue<List<String>> metals;
    public HumanoidEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("HumanoidEntity");
        metals = builder.comment("Base metals for entities").define("metals",
                Arrays.asList("copper", "bronze", "bismuth_bronze", "black_bronze", "wrought_iron", "steel", "black_steel", "red_steel", "blue_steel"));
        armorProbability = builder.define("armorProbability", 10D);
        coinMultiplier = builder.define("coinMultiplier", 1D);
        maxHealth = builder.define("maxHealth", 20D);
        attackDamage = builder.define("attackDamage", 1D);
        attackSpeed = builder.define("attackSpeed", 1.5D);
        movementSpeed = builder.define("movementSpeed", 0.3D);
        followRange = builder.define("followRange", 16D);
        builder.pop();
    }
}