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
		this.textureHeight = 16;

		this.MainBody = new ModelRenderer(this, 0, 9);
		this.MainBody.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.MainBody.addBox(0.0F, -2.0F, -2.4F, 8, 4, 3);
		this.FinLeft1 = new ModelRenderer(this, 19, 8);
		this.FinLeft1.setRotationPoint(1.9F, 0.3F, 0.7F);
		this.FinLeft1.addBox(-0.2F, -1.8F, -0.3F, 5, 3, 1);
		this.FinLeft1.mirror = true;
		this.setRotationAngles(this.FinLeft1, 0.0F, -0.04241150198859518F, 0.0F);
		this.MainBody.addChild(this.FinLeft1);
		this.Head = new ModelRenderer(this, 0, 0);
		this.Head.setRotationPoint(0.0F, 0.3F, -0.7F);
		this.Head.addBox(-3.0F, -2.0F, -1.0F, 3, 3, 2);
		this.MainBody.addChild(this.Head);
		this.TailFin = new ModelRenderer(this, 10, 0);
		this.TailFin.setRotationPoint(5.7F, 0.0F, -0.8F);
		this.TailFin.addBox(0.0F, 0.0F, 0.0F, 7, 7, 0);
		this.setRotationAngles(this.TailFin, 0.0F, 0.0F, -0.8491027077268521F);
		this.MainBody.addChild(this.TailFin);
		this.FinRight2 = new ModelRenderer(this, 19, 8);
		this.FinRight2.setRotationPoint(1.9F, 0.3F, -2.3F);
		this.FinRight2.addBox(-0.2F, -1.8F, -0.8F, 5, 3, 1);
		this.setRotationAngles(this.FinRight2, 0.0F, 0.04241150198859518F, 0.0F);
		this.MainBody.addChild(this.FinRight2);
		this.TopFin = new ModelRenderer(this, 0, 5);
		this.TopFin.setRotationPoint(4.0F, 0.0F, -0.7F);
		this.TopFin.addBox(-0.2F, -3.0F, -0.6F, 3, 3, 1);
		this.setRotationAngles(this.TopFin, 0.0F, 0.0F, -0.8491027077268521F);
		this.MainBody.addChild(this.TopFin);
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

		float globalSpeed = 1.0f;
		float globalDegree = 1.0f;
		float globalHeight = 1.0f;

		swing(TailFin, 1f * globalSpeed, 0.5f * globalDegree, 0f, 0f, f, f1);
		swing(FinLeft1, 0.8f * globalSpeed, -0.3f * globalDegree, 1f, -0.9f, f, f1);
		swing(FinRight2, 0.8f * globalSpeed, 0.3f * globalDegree, 1f, 0.9f, f, f1);
		swing(MainBody, 0.8f * globalSpeed, 0.5f * globalDegree, -2f, 0f, f, f1);
		swing(Head, 0.8f * globalSpeed, 0.5f * globalDegree, 2f, 0f, f, f1);
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