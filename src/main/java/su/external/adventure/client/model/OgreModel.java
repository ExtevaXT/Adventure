package su.external.adventure.client.model;

import mod.azure.azurelib.model.DefaultedEntityGeoModel;
import net.minecraft.resources.ResourceLocation;
import su.external.adventure.Adventure;
import su.external.adventure.entity.boss.OgreEntity;

public class OgreModel extends DefaultedEntityGeoModel<OgreEntity> {
    public OgreModel() {
        super(Adventure.id("ogre"));
    }
    @Override
    public ResourceLocation getTextureResource(OgreEntity entity) {
        return Adventure.id("textures/entities/ogre/ogre_" + (entity.isEnraged() ? "angry" : entity.ogreType) + ".png");
    }
}
