package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class CaveCreepEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double> minLiftedDistance;
    public CaveCreepEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("CaveCreepEntity");
        minLiftedDistance = builder.define("minLiftDistance", 3D);
        builder.pop();
    }
}
