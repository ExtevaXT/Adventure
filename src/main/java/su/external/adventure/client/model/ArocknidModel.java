package su.external.adventure.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import su.external.adventure.Adventure;
import su.external.adventure.entity.crawler.ArocknidEntity;

public class ArocknidModel extends AnimatedGeoModel<ArocknidEntity> {

    @Override
    public ResourceLocation getModelLocation(ArocknidEntity entity) {
        return Adventure.id("geo/entity/arocknid.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ArocknidEntity entity) {
        return Adventure.id("textures/entity/arocknid.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ArocknidEntity entity) {
        return Adventure.id("animations/entity/arocknid.animation.json");
    }
}
