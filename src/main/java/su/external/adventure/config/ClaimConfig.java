package su.external.adventure.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;

public class ClaimConfig {
    public final ForgeConfigSpec.ConfigValue<Double> defaultModifier;
    public final ForgeConfigSpec.ConfigValue<Integer> defaultPrice;
    public ClaimConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Claim");
        defaultModifier = builder.define("defaultModifier", 1.5D);
        defaultPrice = builder.define("defaultPrice", 10000);
        builder.pop();
    }
}
