package com.epiicthundercat.raft.client.model;

import com.epiicthundercat.raft.entity.monster.EntityEel;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEel extends ModelBase {
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer body1;
	public ModelRenderer body2;
	public ModelRenderer body3;
	public ModelRenderer body4;
	public ModelRenderer body5;
	public ModelRenderer body6;
	public ModelRenderer body7;
	public ModelRenderer body8;
	public ModelRenderer tailEnd3rdB4;
	public ModelRenderer tailEnd2ndBefore;
	public ModelRenderer tailEnd;

	public ModelEel() {
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.body2 = new ModelRenderer(this, 0, 0);
		this.body2.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body2.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body4 = new ModelRenderer(this, 0, 0);
		this.body4.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body4.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body8 = new ModelRenderer(this, 0, 0);
		this.body8.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body8.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.head = new ModelRenderer(this, 16, 0);
		this.head.setRotationPoint(0.0F, 22.0F, -14.0F);
		this.head.addBox(-1.0F, -1.5F, -4.0F, 2, 3, 4, 0.0F);
		this.tailEnd = new ModelRenderer(this, 44, -2);
		this.tailEnd.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.tailEnd.addBox(0.0F, -2.0F, 0.0F, 0, 4, 5, 0.0F);
		this.tailEnd2ndBefore = new ModelRenderer(this, 52, 4);
		this.tailEnd2ndBefore.setRotationPoint(-0.5F, 0.0F, 4.0F);
		this.tailEnd2ndBefore.addBox(-0.5F, -2.0F, 0.0F, 1, 4, 4, 0.0F);
		this.body5 = new ModelRenderer(this, 0, 0);
		this.body5.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body5.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body6 = new ModelRenderer(this, 0, 0);
		this.body6.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body6.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body = new ModelRenderer(this, 0, 0);
		this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.body.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.tailEnd3rdB4 = new ModelRenderer(this, 30, 0);
		this.tailEnd3rdB4.setRotationPoint(0.4F, 0.0F, 4.0F);
		this.tailEnd3rdB4.addBox(-1.5F, -2.0F, 0.0F, 2, 4, 4, 0.0F);
		this.body1 = new ModelRenderer(this, 0, 0);
		this.body1.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body1.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body7 = new ModelRenderer(this, 0, 0);
		this.body7.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body7.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body3 = new ModelRenderer(this, 0, 0);
		this.body3.setRotationPoint(0.0F, 0.0F, 4.0F);
		this.body3.addBox(-1.5F, -2.0F, 0.0F, 3, 4, 4, 0.0F);
		this.body1.addChild(this.body2);
		this.body3.addChild(this.body4);
		this.body7.addChild(this.body8);
		this.tailEnd2ndBefore.addChild(this.tailEnd);
		this.tailEnd3rdB4.addChild(this.tailEnd2ndBefore);
		this.body4.addChild(this.body5);
		this.body5.addChild(this.body6);
		this.head.addChild(this.body);
		this.body8.addChild(this.tailEnd3rdB4);
		this.body.addChild(this.body1);
		this.body6.addChild(this.body7);
		this.body2.addChild(this.body3);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.head.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entityIn) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entityIn);

		EntityEel eel = (EntityEel) entityIn;

		
		//f = entityIn.ticksExisted;
		//f1 = 0.5f;
		float globalSpeed = 2.0f;
		float globalDegree = 1.0f;
		float globalHeight = 1.0f;

		swing(head, 0.4f * globalSpeed, -0.3f * globalDegree, 1, 0, f, f1);
		swing(body, 0.4f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body1, 0.4f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body2, 0.4f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body3, 0.4f * globalSpeed, -0.3f * globalDegree, 1, 0, f, f1);
		swing(body4, 0.3f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body5, 0.3f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body6, 0.3f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body7, 0.2f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(body8, 0.4f * globalSpeed, -0.3f * globalDegree, 1, 0, f, f1);
		swing(tailEnd3rdB4, 0.2f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(tailEnd2ndBefore, 0.2f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
		swing(tailEnd, 0.2f * globalSpeed, 0.2f * globalDegree, 1, 0, f, f1);
	
		
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