package com.epiicthundercat.raft.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
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
     
        this.Thatch.render(scale);
        
    }

    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
