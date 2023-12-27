package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public class HumanoidEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double> armorProbability, coinMultiplier;
    public final ForgeConfigSpec.ConfigValue<List<String>> metals;
    public HumanoidEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("HumanoidEntity");
        metals = builder.comment("Base metals for entities").define("metals",
                Arrays.asList("copper", "bronze", "bismuth_bronze", "black_bronze", "wrought_iron", "steel", "black_steel", "red_steel", "blue_steel"));
        armorProbability = builder.define("armorProbability", 10D);
        coinMultiplier = builder.define("coinMultiplier", 1D);
        builder.pop();
    }
}