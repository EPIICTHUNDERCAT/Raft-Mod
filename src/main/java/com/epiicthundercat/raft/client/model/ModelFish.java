package com.epiicthundercat.raft.client.model;

import com.epiicthundercat.raft.entity.passive.EntityFish;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelFish extends ModelBase {
	public ModelRenderer MainBody;
	public ModelRenderer FinLeft1;
	public ModelRenderer Head;
	public ModelRenderer TailFin;
	public ModelRenderer FinRight2;
	public ModelRenderer TopFin;

	public ModelFish() {
		 this.textureWidth = 32;
	        this.textureHeight = 32;
	        this.TailFin = new ModelRenderer(this, 0, 3);
	        this.TailFin.setRotationPoint(0.0F, 0.3F, 5.9F);
	        this.TailFin.addBox(0.0F, 0.0F, 0.0F, 0, 7, 7, 0.0F);
	        this.setRotationAngles(TailFin, 0.8491026810952413F, 0.0F, 0.0F);
	        this.Head = new ModelRenderer(this, 19, 9);
	        this.Head.setRotationPoint(0.0F, 0.3F, 0.0F);
	        this.Head.addBox(-1.0F, -2.0F, -3.0F, 2, 3, 3, 0.0F);
	        this.TopFin = new ModelRenderer(this, 0, 0);
	        this.TopFin.setRotationPoint(-0.3F, 0.0F, 4.3F);
	        this.TopFin.addBox(-0.2F, -3.0F, -0.6F, 1, 3, 3, 0.0F);
	        this.setRotationAngles(TopFin, 0.8491026810952413F, 0.0F, 0.0F);
	        this.MainBody = new ModelRenderer(this, 6, 19);
	        this.MainBody.setRotationPoint(0.0F, 21.3F, -4.0F);
	        this.MainBody.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 8, 0.0F);
	        this.FinRight2 = new ModelRenderer(this, 14, 0);
	        this.FinRight2.mirror = true;
	        this.FinRight2.setRotationPoint(-1.1F, 0.0F, 1.7F);
	        this.FinRight2.addBox(-1.3F, -1.5F, -0.5F, 1, 3, 5, 0.0F);
	        this.setRotationAngles(FinRight2, 0.0F, -0.03490658503988659F, 0.0F);
	        this.FinLeft1 = new ModelRenderer(this, 14, 0);
	        this.FinLeft1.mirror = true;
	        this.FinLeft1.setRotationPoint(1.5F, 0.3F, 1.7F);
	        this.FinLeft1.addBox(-0.1F, -1.8F, -0.5F, 1, 3, 5, 0.0F);
	        this.setRotationAngles(FinLeft1, 0.0F, 0.03490658503988659F, 0.0F);
	        this.MainBody.addChild(this.TailFin);
	        this.MainBody.addChild(this.Head);
	        this.MainBody.addChild(this.TopFin);
	        this.MainBody.addChild(this.FinRight2);
	        this.MainBody.addChild(this.FinLeft1);
	}

	@Override
	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw,
			float rotationPitch, float scale) {

		this.MainBody.render(scale);

	}

	public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entityIn) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entityIn);

		EntityFish fish = (EntityFish) entityIn;
		//f = entityIn.ticksExisted;
	//	f1 = 0.5f;
		float globalSpeed = 1.0f;
		float globalDegree = 1.0f;
		float globalHeight = 1.0f;

		swing(TailFin, 1f * globalSpeed, 0.5f * globalDegree, 0f, 0f, f, f1);
		swing(FinLeft1, 0.9f * globalSpeed, 0.3f * globalDegree, 0.5f, 0.5f, f, f1);
		swing(FinRight2, 0.9f * globalSpeed, -0.3f * globalDegree, 0.5f, -0.5f, f, f1);
		//swing(MainBody, 0.5f * globalSpeed, 0.2f * globalDegree, -2f, 0f, f, f1);
		swing(Head, 0.6f * globalSpeed, 0.5f * globalDegree, 2f, 0f, f, f1);
		swing(TopFin, 0.8f * globalSpeed, 0.5f * globalDegree, 2f, 0f, f, f1);

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