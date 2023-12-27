package su.external.adventure.client.model;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import su.external.adventure.Adventure;
import su.external.adventure.entity.crawler.CaveCreepEntity;

public class CaveCreepModel extends AnimatedGeoModel<CaveCreepEntity> {

    @Override
    public ResourceLocation getModelLocation(CaveCreepEntity entity) {
        return Adventure.id("geo/entity/cave_creep.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(CaveCreepEntity entity) {
        return Adventure.id("textures/entity/cave_creep.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(CaveCreepEntity entity) {
        return Adventure.id("animations/entity/cave_creep.animation.json");
    }
}
