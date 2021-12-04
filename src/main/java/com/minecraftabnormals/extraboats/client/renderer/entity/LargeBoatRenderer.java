package com.minecraftabnormals.extraboats.client.renderer.entity;

import com.minecraftabnormals.extraboats.client.renderer.entity.model.LargeBoatModel;
import com.minecraftabnormals.extraboats.common.entity.item.boat.LargeBoatEntity;
import com.minecraftabnormals.extraboats.core.ExtraBoats;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LargeBoatRenderer extends EntityRenderer<LargeBoatEntity> {
	private static final String[] LARGE_BOAT_TEXTURES = new String[]{
			"textures/entity/large_boat/oak.png",
			"textures/entity/large_boat/spruce.png",
			"textures/entity/large_boat/birch.png",
			"textures/entity/large_boat/jungle.png",
			"textures/entity/large_boat/acacia.png",
			"textures/entity/large_boat/dark_oak.png",

			"textures/entity/large_boat/cherry.png",
			"textures/entity/large_boat/dead.png",
			"textures/entity/large_boat/fir.png",
			"textures/entity/large_boat/hellbark.png",
			"textures/entity/large_boat/jacaranda.png",
			"textures/entity/large_boat/magic.png",
			"textures/entity/large_boat/mahogany.png",
			"textures/entity/large_boat/palm.png",
			"textures/entity/large_boat/redwood.png",
			"textures/entity/large_boat/umbran.png",
			"textures/entity/large_boat/willow.png",

			"textures/entity/large_boat/driftwood.png",
			"textures/entity/large_boat/river.png",

			"textures/entity/large_boat/bamboo.png",

			"textures/entity/large_boat/poise.png",

			"textures/entity/large_boat/wisteria.png",
			"textures/entity/large_boat/env_willow.png",
			"textures/entity/large_boat/env_cherry.png",

			"textures/entity/large_boat/rosewood.png",
			"textures/entity/large_boat/morado.png",
			"textures/entity/large_boat/aspen.png",
			"textures/entity/large_boat/kousa.png",
			"textures/entity/large_boat/yucca.png",
			"textures/entity/large_boat/grimwood.png",

			"textures/entity/large_boat/maple.png",

			"textures/entity/large_boat/red_mushroom.png",
			"textures/entity/large_boat/brown_mushroom.png",
			"textures/entity/large_boat/glowshroom.png",

			"textures/entity/large_boat/sakura.png",

			"textures/entity/large_boat/crimson.png",
			"textures/entity/large_boat/warped.png"};

	protected final LargeBoatModel modelLargeBoat = new LargeBoatModel();

	public LargeBoatRenderer(EntityRendererManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.8F;
	}

	public void render(LargeBoatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.0D, 0.375D, 0.0D);
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		float f = (float) entityIn.getHurtTime() - partialTicks;
		float f1 = entityIn.getDamage() - partialTicks;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		if (f > 0.0F) {
			matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * (float) entityIn.getHurtDir()));
		}

		float f2 = entityIn.getBubbleAngle(partialTicks);
		if (!MathHelper.equal(f2, 0.0F)) {
			matrixStackIn.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.getBubbleAngle(partialTicks), true));
		}

		matrixStackIn.scale(-1.05F, -1.05F, 1.05F);
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		this.modelLargeBoat.setupAnim(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.modelLargeBoat.renderType(this.getTextureLocation(entityIn)));
		this.modelLargeBoat.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		if (!entityIn.isUnderWater()) {
			IVertexBuilder ivertexbuilder1 = bufferIn.getBuffer(RenderType.waterMask());
			this.modelLargeBoat.waterPatch().render(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.NO_OVERLAY);
		}
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(LargeBoatEntity entity) {
		return new ResourceLocation(ExtraBoats.MOD_ID, LARGE_BOAT_TEXTURES[entity.getModBoatType().ordinal()]);
	}
}