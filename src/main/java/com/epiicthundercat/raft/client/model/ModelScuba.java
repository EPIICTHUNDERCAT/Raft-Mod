package com.epiicthundercat.raft.client.model;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * ModelPlayer - Either Mojang or a mod author Created using Tabula 6.0.0
 */
@SideOnly(Side.CLIENT)
public class ModelScuba extends ModelExtendedArmor {
	public ModelRenderer plasticBottle;
	public ModelRenderer cap;
	public ModelRenderer strap1;
	public ModelRenderer strap3;
	public ModelRenderer strap2;

	public ModelScuba() {
		this.textureWidth = 32;
		this.textureHeight = 32;
		this.strap2 = new ModelRenderer(this, 0, 8);
		this.strap2.setRotationPoint(8.0F, 1.0F, 3.0F);
		this.strap2.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
		this.strap3 = new ModelRenderer(this, 0, 18);
		this.strap3.setRotationPoint(0.0F, 1.0F, 11.0F);
		this.strap3.addBox(0.0F, 0.0F, 0.0F, 9, 1, 1, 0.0F);
		this.plasticBottle = new ModelRenderer(this, 0, 0);
		this.plasticBottle.setRotationPoint(-4.5F, -3.0F, -6.9F);
		this.plasticBottle.addBox(0.0F, 0.0F, 0.0F, 9, 3, 3, 0.0F);
		this.strap1 = new ModelRenderer(this, 0, 8);
		this.strap1.setRotationPoint(0.0F, 1.0F, 3.0F);
		this.strap1.addBox(0.0F, 0.0F, 0.0F, 1, 1, 8, 0.0F);
		this.cap = new ModelRenderer(this, 0, 8);
		this.cap.setRotationPoint(8.5F, 0.5F, 0.5F);
		this.cap.addBox(0.0F, 0.0F, 0.0F, 1, 2, 2, 0.0F);
		this.plasticBottle.addChild(this.strap2);
		this.plasticBottle.addChild(this.strap3);
		this.plasticBottle.addChild(this.strap1);
		this.plasticBottle.addChild(this.cap);
	}

	/*@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		//this.plasticBottle.render(f5);
		//this.syncModel(entity, f5);
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

	@Override
	public void addModelParts() {
		  this.bipedHead.showModel = false;
	        this.bipedHeadwear = this.plasticBottle;	
	}
}