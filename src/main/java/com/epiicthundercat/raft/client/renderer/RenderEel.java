package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelEel;
import com.epiicthundercat.raft.entity.monster.EntityEel;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEel extends RenderLiving<EntityEel>{

	private static final ResourceLocation EEL_TEXTURE = new ResourceLocation(Reference.ID, "textures/entity/eel.png");
	
	
	private final ModelEel eelModel;
	

	public RenderEel(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelEel(), 0.2F);
		eelModel = (ModelEel) super.mainModel;
	}
	@Override
	public void doRender(EntityEel entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
bindTexture(EEL_TEXTURE);
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	@Override
	protected ResourceLocation getEntityTexture(EntityEel entity) {
		return EEL_TEXTURE;
	}
	
}
