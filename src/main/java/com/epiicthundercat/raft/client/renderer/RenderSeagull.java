package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelSeagul;
import com.epiicthundercat.raft.entity.monster.EntityEel;
import com.epiicthundercat.raft.entity.passive.EntitySeagull;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSeagull extends RenderLiving<EntitySeagull>{

	private static final ResourceLocation GULL_TEXTURE = new ResourceLocation(Reference.ID, "textures/entity/seagull.png");
	
	
	private final ModelSeagul eelModel;
	

	public RenderSeagull(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelSeagul(), 0.2F);
		eelModel = (ModelSeagul) super.mainModel;
	}
	@Override
	public void doRender(EntitySeagull entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
bindTexture(GULL_TEXTURE);
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	@Override
	protected ResourceLocation getEntityTexture(EntitySeagull entity) {
		return GULL_TEXTURE;
	}
}