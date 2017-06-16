package com.epiicthundercat.raft.client.model;

import com.epiicthundercat.raft.entity.FloatBarrel;
import com.epiicthundercat.raft.entity.passive.EntityFish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelBarrel extends ModelBase  {
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
		
		
		  GlStateManager.pushMatrix();
	        GlStateManager.translate(-0.4f, 0.0f, -0.4f);
	      this.BarrelCenter.render(scale);
	
		this.BarrelTop.render(scale);
	           GlStateManager.popMatrix();
		
	}

	public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public int getV()
	{
		return 45;
	}
	  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entityIn) {
			super.setRotationAngles(f, f1, f2, f3, f4, f5, entityIn);

			FloatBarrel fish = (FloatBarrel) entityIn;
			f = entityIn.ticksExisted;
			f1 = 0.5f;
			float globalSpeed = 1.0f;
			float globalDegree = 1.0f;
			float globalHeight = 1.0f;
swing(BarrelCenter, 1, 1, 1, 1, f, f1);
		
		}

		public void swing(ModelRenderer box, float speed, float degree, float offset, float weight, float f, float f1) {
			box.rotateAngleY = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
		}

		public void flap(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float f,
				float f1) {

			box.rotateAngleZ = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
		}

		public void bob(ModelRenderer box, float speed, float degree, float offset, float weight, float f, float f1) {
			box.rotationPointY = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
		}

		public void walk(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float f,
				float f1) {

			box.rotateAngleX = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
		}
}
