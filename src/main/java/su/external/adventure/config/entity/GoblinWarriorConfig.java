package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class GoblinWarriorConfig {
    public final ForgeConfigSpec.ConfigValue<Double> coinMultiplier;
    public GoblinWarriorConfig(ForgeConfigSpec.Builder builder) {
        builder.push("GoblinWarrior");
        coinMultiplier = builder.define("coinMultiplier", 1.5D);
        builder.pop();
    }
}