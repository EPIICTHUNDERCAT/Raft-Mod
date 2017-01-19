package com.epiicthundercat.raft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelScrap extends ModelBase {
    public ModelRenderer Scrap;

    public ModelScrap() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Scrap = new ModelRenderer(this, 0, 9);
        this.Scrap.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Scrap.addBox(-11.0F, 0.0F, -11.0F, 21, 0, 21);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
        
        this.Scrap.render(scale);
    
    }

    public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
