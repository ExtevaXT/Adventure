package su.external.adventure.client.renderer;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import su.external.adventure.client.model.OgreModel;
import su.external.adventure.entity.boss.OgreEntity;

public class OgreRenderer extends GeoEntityRenderer<OgreEntity> {
    public OgreRenderer(EntityRendererProvider.Context context) {
        super(context, new OgreModel());
    }
}