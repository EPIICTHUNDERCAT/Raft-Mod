package com.epiicthundercat.raft.client.model;

import com.epiicthundercat.raft.entity.passive.EntitySeagull;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class ModelSeagul extends ModelBase {
	public ModelRenderer mainBody;
	public ModelRenderer neckPartOne;
	public ModelRenderer legThighLeft;
	public ModelRenderer legThighRight;
	public ModelRenderer wingRightPartOne;
	public ModelRenderer wingLeftPartOne;
	public ModelRenderer backFeathers;
	public ModelRenderer neckPartTwo;
	public ModelRenderer neckToHead;
	public ModelRenderer head;
	public ModelRenderer beakPartOne;
	public ModelRenderer beakPartTwo;
	public ModelRenderer beakFront;
	public ModelRenderer legCalfLeft;
	public ModelRenderer footLeft;
	public ModelRenderer legCalfRight;
	public ModelRenderer footRight;
	public ModelRenderer wingRightPartTwo;
	public ModelRenderer wingLeftPartTwo;
	public ModelRenderer backFeathersTwo;

	public ModelSeagul() {
	        this.textureWidth = 64;
	        this.textureHeight = 64;
	        this.legCalfLeft = new ModelRenderer(this, 19, 48);
	        this.legCalfLeft.setRotationPoint(0.0F, 3.0F, 0.0F);
	        this.legCalfLeft.addBox(-0.7F, 0.0F, -0.7F, 1, 3, 1, 0.0F);
	        this.setRotateAngle(legCalfLeft, -0.31869712141416456F, 0.0F, 0.0F);
	        this.wingRightPartTwo = new ModelRenderer(this, 4, 23);
	        this.wingRightPartTwo.setRotationPoint(-0.2F, -0.2F, 3.8F);
	        this.wingRightPartTwo.addBox(-0.8F, -2.0F, 0.1F, 1, 3, 4, 0.0F);
	        this.setRotateAngle(wingRightPartTwo, 0.18203784098300857F, 0.0F, 0.0F);
	        this.backFeathers = new ModelRenderer(this, 0, 47);
	        this.backFeathers.setRotationPoint(0.6F, -1.7F, 5.8F);
	        this.backFeathers.addBox(-2.0F, -0.4F, 0.2F, 4, 1, 4, 0.0F);
	        this.setRotateAngle(backFeathers, -0.27314402793711257F, 0.0F, 0.0F);
	        this.mainBody = new ModelRenderer(this, 20, 29);
	        this.mainBody.setRotationPoint(0.0F, 14.8F, 0.0F);
	        this.mainBody.addBox(-2.0F, -2.0F, 0.0F, 5, 5, 6, 0.0F);
	        this.setRotateAngle(mainBody, -0.22759093446006054F, 0.0F, 0.0F);
	        this.neckPartTwo = new ModelRenderer(this, 26, 14);
	        this.neckPartTwo.setRotationPoint(-0.5F, 0.5F, -1.7F);
	        this.neckPartTwo.addBox(-1.0F, -1.0F, -2.0F, 3, 3, 3, 0.0F);
	        this.setRotateAngle(neckPartTwo, -0.5009094953223726F, 0.0F, 0.0F);
	        this.wingRightPartOne = new ModelRenderer(this, 3, 32);
	        this.wingRightPartOne.setRotationPoint(-2.0F, 0.0F, 1.2F);
	        this.wingRightPartOne.addBox(-1.0F, -2.2F, -1.0F, 1, 4, 5, 0.0F);
	        this.neckToHead = new ModelRenderer(this, 26, 8);
	        this.neckToHead.setRotationPoint(0.4F, 0.4F, -1.8F);
	        this.neckToHead.addBox(-0.9F, -0.8F, -3.2F, 2, 2, 4, 0.0F);
	        this.setRotateAngle(neckToHead, -0.7285004297824331F, 0.0F, 0.0F);
	        this.wingLeftPartTwo = new ModelRenderer(this, 48, 27);
	        this.wingLeftPartTwo.setRotationPoint(0.2F, -0.2F, 3.8F);
	        this.wingLeftPartTwo.addBox(0.8F, -2.0F, 0.1F, 1, 3, 4, 0.0F);
	        this.setRotateAngle(wingLeftPartTwo, 0.18203784098300857F, 0.0F, 0.0F);
	        this.beakFront = new ModelRenderer(this, 53, 6);
	        this.beakFront.setRotationPoint(0.0F, 2.8F, -0.1F);
	        this.beakFront.addBox(0.0F, 0.0F, 0.0F, 1, 1, 1, 0.0F);
	        this.setRotateAngle(beakFront, 0.4553564018453205F, 0.0F, 0.0F);
	        this.beakPartOne = new ModelRenderer(this, 41, 5);
	        this.beakPartOne.setRotationPoint(0.6F, 2.6F, 0.3F);
	        this.beakPartOne.addBox(-0.6F, 0.4F, -0.4F, 1, 2, 1, 0.0F);
	        this.setRotateAngle(beakPartOne, 0.31869712141416456F, 0.0F, 0.0F);
	        this.head = new ModelRenderer(this, 26, 1);
	        this.head.setRotationPoint(-0.4F, 0.2F, -4.0F);
	        this.head.addBox(-1.0F, -1.0F, -1.0F, 3, 4, 3, 0.0F);
	        this.setRotateAngle(head, 0.18203784098300857F, 0.0F, 0.0F);
	        this.backFeathersTwo = new ModelRenderer(this, 47, 51);
	        this.backFeathersTwo.setRotationPoint(-0.3F, 2.8F, 1.0F);
	        this.backFeathersTwo.addBox(-1.4F, -0.7F, 0.0F, 3, 1, 3, 0.0F);
	        this.setRotateAngle(backFeathersTwo, 0.36425021489121656F, 0.0F, 0.0F);
	        this.legCalfRight = new ModelRenderer(this, 33, 49);
	        this.legCalfRight.setRotationPoint(0.0F, 3.0F, 0.0F);
	        this.legCalfRight.addBox(-0.7F, 0.0F, -0.7F, 1, 3, 1, 0.0F);
	        this.setRotateAngle(legCalfRight, -0.31869712141416456F, 0.0F, 0.0F);
	        this.beakPartTwo = new ModelRenderer(this, 47, 4);
	        this.beakPartTwo.setRotationPoint(0.0F, 2.8F, 0.6F);
	        this.beakPartTwo.addBox(0.0F, 0.0F, 0.0F, 1, 3, 1, 0.0F);
	        this.wingLeftPartOne = new ModelRenderer(this, 47, 35);
	        this.wingLeftPartOne.setRotationPoint(2.0F, 0.0F, 1.2F);
	        this.wingLeftPartOne.addBox(1.0F, -2.2F, -1.0F, 1, 4, 5, 0.0F);
	        this.neckPartOne = new ModelRenderer(this, 24, 20);
	        this.neckPartOne.setRotationPoint(0.5F, -0.8F, 0.0F);
	        this.neckPartOne.addBox(-2.0F, -1.0F, -2.0F, 4, 4, 4, 0.0F);
	        this.setRotateAngle(neckPartOne, -0.31869712141416456F, 0.0F, 0.0F);
	        this.footLeft = new ModelRenderer(this, 15, 54);
	        this.footLeft.setRotationPoint(0.0F, 3.0F, 0.0F);
	        this.footLeft.addBox(-1.7F, 0.0F, -2.7F, 3, 0, 3, 0.0F);
	        this.setRotateAngle(footLeft, 0.31869712141416456F, 0.0F, 0.0F);
	        this.footRight = new ModelRenderer(this, 30, 54);
	        this.footRight.setRotationPoint(0.0F, 3.0F, 0.0F);
	        this.footRight.addBox(-1.7F, 0.0F, -2.7F, 3, 0, 3, 0.0F);
	        this.setRotateAngle(footRight, 0.31869712141416456F, 0.0F, 0.0F);
	        this.legThighLeft = new ModelRenderer(this, 19, 42);
	        this.legThighLeft.setRotationPoint(2.0F, 2.7F, 2.9F);
	        this.legThighLeft.addBox(-0.7F, 0.0F, -0.7F, 1, 3, 1, 0.0F);
	        this.setRotateAngle(legThighLeft, 0.22759093446006054F, 0.0F, 0.0F);
	        this.legThighRight = new ModelRenderer(this, 33, 43);
	        this.legThighRight.setRotationPoint(-0.7F, 2.7F, 2.9F);
	        this.legThighRight.addBox(-0.7F, 0.0F, -0.7F, 1, 3, 1, 0.0F);
	        this.setRotateAngle(legThighRight, 0.22759093446006054F, 0.0F, 0.0F);
	        this.legThighLeft.addChild(this.legCalfLeft);
	        this.wingRightPartOne.addChild(this.wingRightPartTwo);
	        this.mainBody.addChild(this.backFeathers);
	        this.neckPartOne.addChild(this.neckPartTwo);
	        this.mainBody.addChild(this.wingRightPartOne);
	        this.neckPartTwo.addChild(this.neckToHead);
	        this.wingLeftPartOne.addChild(this.wingLeftPartTwo);
	        this.beakPartTwo.addChild(this.beakFront);
	        this.head.addChild(this.beakPartOne);
	        this.neckToHead.addChild(this.head);
	        this.backFeathers.addChild(this.backFeathersTwo);
	        this.legThighRight.addChild(this.legCalfRight);
	        this.head.addChild(this.beakPartTwo);
	        this.mainBody.addChild(this.wingLeftPartOne);
	        this.mainBody.addChild(this.neckPartOne);
	        this.legCalfLeft.addChild(this.footLeft);
	        this.legCalfRight.addChild(this.footRight);
	        this.mainBody.addChild(this.legThighLeft);
	        this.mainBody.addChild(this.legThighRight);
	    }

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.mainBody.render(f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	  public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity)
	  {
	    super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
	    
	    EntitySeagull entityseagull = (EntitySeagull)entity;
	    if ((!entityseagull.getCanFly()) || (entity.onGround))
	    {
	      this.legThighLeft.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount);
	      this.footLeft.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount);
	      this.legCalfLeft.rotateAngleX = -0.0523599F;
	      
	      this.legThighRight.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
	      this.footRight.rotateAngleX = (MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount);
	      this.legCalfRight.rotateAngleX = -0.0523599F;
	      
	      this.head.rotateAngleX = 0.0174533F;
	    //  this.beakPartOne.rotateAngleX = -1.396263F;
	  //    this.beakPartTwo.rotateAngleX = -1.527421F;
	      
	      this.head.rotateAngleY = (netHeadYaw / 57.295776F);
	      this.wingLeftPartOne.rotateAngleY = -0.122173F;
	      this.wingLeftPartTwo.rotateAngleY = -0.122173F;
	      this.wingRightPartOne.rotateAngleY = 0.122173F;
	      this.wingRightPartTwo.rotateAngleY = 0.122173F;
	    }
	    else
	    {
	      this.legThighLeft.rotateAngleX = 0.9F;
	      this.footLeft.rotateAngleX = 0.9F;
	      this.legCalfLeft.rotateAngleX = 0.6F;
	      
	      this.legThighRight.rotateAngleX = 0.9F;
	      this.footRight.rotateAngleX = 0.9F;
	      this.legCalfRight.rotateAngleX = 0.6F;
	      
	      this.head.rotateAngleX = 0.8174533F;
	      this.beakPartOne.rotateAngleX = -0.596263F;
	      this.beakPartTwo.rotateAngleX = -0.727421F;
	      this.head.rotateAngleY = 0.0174533F;
	      
	      this.wingLeftPartOne.rotateAngleY = 0.4F;
	    this.wingLeftPartTwo.rotateAngleY = 0.4F;
	      this.wingRightPartOne.rotateAngleY = -0.4F;
	      this.wingRightPartTwo.rotateAngleY = -0.4F;
	    }
	  //  this.wingRightPartOne.rotateAngleZ = (ageInTicks + 0.2268928F);
	  //  this.wingLeftPartOne.rotateAngleZ = (-(ageInTicks + 0.2268928F));
	  //  this.wingRightPartTwo.rotateAngleZ = (ageInTicks + 0.1919862F);
	  //  this.wingLeftPartTwo.rotateAngleZ = (-(ageInTicks + 0.1919862F));
	    
	   this.beakPartTwo.rotateAngleY = this.head.rotateAngleY;
	    this.beakPartOne.rotateAngleY = this.head.rotateAngleY;
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
