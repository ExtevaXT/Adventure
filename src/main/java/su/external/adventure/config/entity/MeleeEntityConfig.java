package su.external.adventure.config.entity;

import net.minecraftforge.common.ForgeConfigSpec;

public class MeleeEntityConfig {
    public final ForgeConfigSpec.ConfigValue<Double> speedModifier;
    public final ForgeConfigSpec.BooleanValue followingTargetEvenIfNotSeen;
    public MeleeEntityConfig(ForgeConfigSpec.Builder builder) {
        builder.push("MeleeEntity");
        speedModifier = builder.comment("Melee Goal SpeedModifier").define("speedModifier", 1D);
        followingTargetEvenIfNotSeen = builder.comment("Melee Goal FollowingTargetEvenIfNotSeen").define("followingTargetEvenIfNotSeen", false);
        builder.pop();
    }
}