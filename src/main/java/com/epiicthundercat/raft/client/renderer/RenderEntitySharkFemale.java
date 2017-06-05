package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.EntitySharkFemaleModel;
import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderEntitySharkFemale extends RenderLiving<EntitySharkFemale>{

	private static final ResourceLocation SHARK_TEXTURE = new ResourceLocation(Reference.ID, "textures/entity/entitysharkfemale.png");
	
	
	private final EntitySharkFemaleModel femaleSharkModel;
	

	public RenderEntitySharkFemale(RenderManager renderManagerIn) {
		super(renderManagerIn, new EntitySharkFemaleModel(), 0.2F);
		femaleSharkModel = (EntitySharkFemaleModel) super.mainModel;
	}
	@Override
	public void doRender(EntitySharkFemale entity, double x, double y, double z, float entityYaw, float partialTicks) {
		
bindTexture(SHARK_TEXTURE);
		
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	@Override
	protected ResourceLocation getEntityTexture(EntitySharkFemale entity) {
		return SHARK_TEXTURE;
	}
	
}
