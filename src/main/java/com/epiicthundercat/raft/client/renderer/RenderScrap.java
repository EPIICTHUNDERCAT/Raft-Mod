package com.epiicthundercat.raft.client.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelScrap;
import com.epiicthundercat.raft.entity.ScrapEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderScrap extends Render<ScrapEntity> {
	private static final ResourceLocation SCRAP_TEXTURES = new ResourceLocation(Reference.ID,
			"textures/entity/scrap.png");
	private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);
	private static final Matrix4f MATRIX = new Matrix4f();
	public static final Quaternion CURRENT = new Quaternion();
	private int lastV = 0;
	private ModelScrap scrap;

	public RenderScrap(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.scrap = new ModelScrap();
		this.lastV = this.scrap.getV();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(ScrapEntity entity) {
		return SCRAP_TEXTURES;
	}

	@Override
	public void doRender(ScrapEntity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		if (lastV != scrap.getV()) {
			this.scrap = new ModelScrap();
			this.lastV = scrap.getV();
		}
		Minecraft.getMinecraft().getTextureManager().bindTexture(SCRAP_TEXTURES);
		GlStateManager.pushMatrix();

		GlStateManager.translate((float) x, (float) y + 0.25F, (float) z);

		this.scrap.render(entity, 0, 0, 0, 0, 0, 0.0625F);

		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void setupRotation(ScrapEntity p_188311_1_, float p_188311_2_, float p_188311_3_) {

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
}