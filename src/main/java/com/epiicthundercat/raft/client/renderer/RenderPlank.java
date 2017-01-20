package com.epiicthundercat.raft.client.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelPlank;
import com.epiicthundercat.raft.entity.PlankEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPlank extends Render<PlankEntity> {
	private static final ResourceLocation PLANK_TEXTURES = new ResourceLocation(Reference.ID,
			"textures/entity/plank.png");
	private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);
	private static final Matrix4f MATRIX = new Matrix4f();
	public static final Quaternion CURRENT = new Quaternion();

	private int lastV = 0;
	private ModelPlank plank;

	public RenderPlank(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.plank = new ModelPlank();
		this.lastV = this.plank.getV();
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(PlankEntity entity) {
		return PLANK_TEXTURES;
	}

	@Override
	public void doRender(PlankEntity entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		if (lastV != plank.getV()) {
			this.plank = new ModelPlank();
			this.lastV = plank.getV();
		}
Minecraft.getMinecraft().getTextureManager().bindTexture(PLANK_TEXTURES);
		GlStateManager.pushMatrix();

		
		GlStateManager.translate((float) x, (float) y + 0.25F, (float) z);

		
		this.plank.render(entity, 0, 0, 0, 0, 0, 0.0625F);

		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void setupRotation(PlankEntity p_188311_1_, float p_188311_2_, float p_188311_3_) {
	
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