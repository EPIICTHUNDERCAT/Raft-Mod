package com.epiicthundercat.raft.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.IMultipassModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBarrel extends ModelBase implements IMultipassModel {
	//public ModelRenderer noWater;
	public ModelRenderer BarrelCenter;
	public ModelRenderer BarrelOutside;
	public ModelRenderer BarrelOutside3;
	public ModelRenderer BarrelOutside2;
	public ModelRenderer BarrelOutside22;
	public ModelRenderer BarrelTop;

	public ModelBarrel() {
		this.textureWidth = 92;
		this.textureHeight = 64;

		this.BarrelCenter = new ModelRenderer(this, 0, 36);
		this.BarrelCenter.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.BarrelCenter.addBox(0.0F, 0.0F, 0.0F, 11, 13, 11);
		this.BarrelOutside = new ModelRenderer(this, 42, 0);
		this.BarrelOutside.setRotationPoint(-0.5F, 1.0F, 0.5F);
		this.BarrelOutside.addBox(0.0F, 0.0F, 0.0F, 12, 11, 10);
		this.BarrelCenter.addChild(this.BarrelOutside);
		this.BarrelOutside3 = new ModelRenderer(this, 48, 21);
		this.BarrelOutside3.setRotationPoint(-0.6F, 0.4F, 0.5F);
		this.BarrelOutside3.addBox(0.0F, 0.0F, 0.0F, 13, 10, 9);
		this.BarrelOutside.addChild(this.BarrelOutside3);
		this.BarrelOutside2 = new ModelRenderer(this, 48, 41);
		this.BarrelOutside2.setRotationPoint(0.4F, 1.0F, -0.5F);
		this.BarrelOutside2.addBox(0.0F, 0.0F, 0.0F, 10, 11, 12);
		this.BarrelCenter.addChild(this.BarrelOutside2);
		this.BarrelOutside22 = new ModelRenderer(this, 0, 12);
		this.BarrelOutside22.setRotationPoint(0.5F, 0.4F, -0.5F);
		this.BarrelOutside22.addBox(0.0F, 0.0F, 0.0F, 9, 10, 13);
		this.BarrelOutside2.addChild(this.BarrelOutside22);
		this.BarrelTop = new ModelRenderer(this, 0, 0);
		this.BarrelTop.setRotationPoint(0.5F, -0.4F, 0.5F);
		this.BarrelTop.addBox(0.0F, 0.0F, 0.0F, 10, 1, 10);
		
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw,
			float rotationPitch, float scale) {
		
		this.BarrelCenter.render(scale);
	
		this.BarrelTop.render(scale);
		
	}

	public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void renderMultipass(Entity p_187054_1_, float p_187054_2_, float p_187054_3_, float p_187054_4_,
			float p_187054_5_, float p_187054_6_, float scale) {
	
	}
}
