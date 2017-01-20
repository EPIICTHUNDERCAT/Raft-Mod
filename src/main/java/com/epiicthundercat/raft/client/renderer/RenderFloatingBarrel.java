package com.epiicthundercat.raft.client.renderer;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.ReadableVector;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelBarrel;
import com.epiicthundercat.raft.entity.FloatBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderFloatingBarrel extends Render<FloatBarrel> {
	private static final ResourceLocation[] BARREL_TEXTURES = new ResourceLocation[] {
			new ResourceLocation(Reference.ID, "textures/entity/barrel/barrel_oak.png"),
			new ResourceLocation(Reference.ID, "textures/entity/barrel/barrel_spruce.png"),
			new ResourceLocation(Reference.ID, "textures/entity/barrel/barrel_birch.png"),
			new ResourceLocation(Reference.ID, "textures/entity/barrel/barrel_jungle.png"),
			new ResourceLocation(Reference.ID, "textures/entity/barrel/barrel_acacia.png"),
			new ResourceLocation(Reference.ID, "textures/entity/barrel/barrel_darkoak.png") };
	private static final FloatBuffer BUF_FLOAT_16 = BufferUtils.createFloatBuffer(16);
	private static final Matrix4f MATRIX = new Matrix4f();
	public static final Quaternion CURRENT = new Quaternion();

	private int lastV = 0;
	private ModelBarrel barrel;

	public RenderFloatingBarrel(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.barrel = new ModelBarrel();
		this.lastV = this.barrel.getV();
	}

	@Override
	public void doRender(FloatBarrel entity, double x, double y, double z, float p_76986_8_, float partialTicks) {
		if (lastV != barrel.getV()) {
			this.barrel = new ModelBarrel();
			this.lastV = barrel.getV();
		}
Minecraft.getMinecraft().getTextureManager().bindTexture(BARREL_TEXTURES[entity.getBarrelType().ordinal()]);
		GlStateManager.pushMatrix();

		
		GlStateManager.translate((float) x, (float) y + 0.25F, (float) z);

		
		this.barrel.render(entity, 0, 0, 0, 0, 0, 0.0625F);

		GlStateManager.popMatrix();

		super.doRender(entity, x, y, z, p_76986_8_, partialTicks);
	}

	public void setupRotation(FloatBarrel p_188311_1_, float p_188311_2_, float p_188311_3_) {
	
	}

	public void setupTranslation(double p_188309_1_, double p_188309_3_, double p_188309_5_) {

		GlStateManager.translate(p_188309_1_ + -0.4f, p_188309_3_ + 0.1f, p_188309_5_ + -0.4f);

	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(FloatBarrel entity) {
		return BARREL_TEXTURES[entity.getBarrelType().ordinal()];
	}

	

	private static Matrix4f toMatrix(Quaternion quat) {
	
return null;
		
	}

	private static Quaternion lerp(Quaternion start, Quaternion end, float alpha) {
		return null;
	
	}
}