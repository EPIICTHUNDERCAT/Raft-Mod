package com.epiicthundercat.raft.client.model;

import com.epiicthundercat.raft.entity.ThatchEntity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelThatch extends ModelBase {
    public ModelRenderer Thatch;

    public ModelThatch() {
    	   this.textureWidth = 64;
           this.textureHeight = 32;

           this.Thatch = new ModelRenderer(this, 0, 0);
           this.Thatch.setRotationPoint(0.0F, 0.0F, 0.0F);
           this.Thatch.addBox(0.0F, 0.0F, 0.0F, 21, 0, 21);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
     GlStateManager.pushMatrix();
     GlStateManager.translate(-0.5f, 0.0f, -0.5f);
        this.Thatch.render(scale);
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

  		ThatchEntity fish = (ThatchEntity) entityIn;
  		f = entityIn.ticksExisted;
  		f1 = 0.5f;
  		float globalSpeed = 1.0f;
  		float globalDegree = 1.0f;
  		float globalHeight = 1.0f;
bob(Thatch, 2, 2, 0, 0, f, f1);
  	
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
