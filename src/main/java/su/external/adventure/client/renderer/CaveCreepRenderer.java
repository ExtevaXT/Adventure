package su.external.adventure.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import su.external.adventure.Adventure;
import su.external.adventure.client.model.CaveCreepModel;
import su.external.adventure.entity.crawler.CaveCreepEntity;

public class CaveCreepRenderer extends GeoEntityRenderer<CaveCreepEntity> {
public CaveCreepRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CaveCreepModel());
        this.shadowRadius = 0.3f;
        }

@Override
public ResourceLocation getTextureLocation(CaveCreepEntity instance) {
        return new ResourceLocation(Adventure.MOD_ID, "textures/entities/cave_creep.png");
        }

@Override
public RenderType getRenderType(CaveCreepEntity animatable, float partialTicks, PoseStack stack,
        MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
        ResourceLocation textureLocation) {
        stack.scale(0.8F, 0.8F, 0.8F);
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
        }
        }