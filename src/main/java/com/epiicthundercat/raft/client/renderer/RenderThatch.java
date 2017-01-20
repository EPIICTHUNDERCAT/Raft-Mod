package com.epiicthundercat.raft.client.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelThatch;
import com.epiicthundercat.raft.entity.ThatchEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderThatch extends Render<ThatchEntity> {
	private static final ResourceLocation THATCH_TEXTURES = new ResourceLocation(Reference.ID,
			"textures/entity/thatch.png");
	private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);
	private static final Matrix4f MATRIX = new Matrix4f();
	public static final Quaternion CURRENT = new Quaternion();
	private int lastV = 0;
	private ModelThatch thatch;

	public RenderThatch(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.thatch = new ModelThatch();
		this.lastV = this.thatch.getV();
	}

	@Override
	public void doRender(ThatchEntity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		if (lastV != thatch.getV()) {
			this.thatch = new ModelThatch();
			this.lastV = thatch.getV();
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(THATCH_TEXTURES);
		GlStateManager.pushMatrix();

		GlStateManager.translate((float) x, (float) y + 0.25F, (float) z);

		this.thatch.render(entity, 0, 0, 0, 0, 0, 0.0625F);

		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void setupRotation(ThatchEntity p_188311_1_, float p_188311_2_, float p_188311_3_) {

	}

	public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_) {

		GlStateManager.translate(p_188309_1_ + -0.4f, p_188309_3_ + 0.1f, p_188309_5_ + -0.4f);

	}

	private static Matrix4f toMatrix(Quaternion quat) {

		return null;

	}

	private static Quaternion lerp(Quaternion start, Quaternion end, float alpha) {
		return null;

	}

	@Override
	protected ResourceLocation getEntityTexture(ThatchEntity entity) {
		// TODO Auto-generated method stub
		return THATCH_TEXTURES;
	}
}