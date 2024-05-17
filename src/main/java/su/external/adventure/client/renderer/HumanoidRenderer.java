package su.external.adventure.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import su.external.adventure.Adventure;

public class HumanoidRenderer extends MobRenderer<Mob, HumanoidModel<Mob>> {
    protected ResourceLocation textureLocation;
    public HumanoidRenderer(EntityRendererProvider.Context context, String texture) {
        super(context, new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this));
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
        this.textureLocation = Adventure.id("textures/entities/humanoid/" + texture);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob p_114482_) {
        return textureLocation;
    }
}