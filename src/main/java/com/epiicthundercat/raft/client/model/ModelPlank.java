package com.epiicthundercat.raft.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelPlank extends ModelBase {
    public ModelRenderer Plank;

    public ModelPlank() {
        this.textureWidth = 96;
        this.textureHeight = 16;

        this.Plank = new ModelRenderer(this, 0, 7);
        this.Plank.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Plank.addBox(0.0F, 0.0F, 0.0F, 37, 1, 8);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
      
        this.Plank.render(scale);
        
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
}
