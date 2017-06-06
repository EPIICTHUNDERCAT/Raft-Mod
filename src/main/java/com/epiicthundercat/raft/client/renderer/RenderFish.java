package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelFish;
import com.epiicthundercat.raft.entity.passive.EntityFish;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderFish extends RenderLiving<EntityFish>{
	 private static final ResourceLocation NEMO = new ResourceLocation(Reference.ID, "textures/entity/fish/nemo.png");
	    private static final ResourceLocation FISH = new ResourceLocation(Reference.ID, "textures/entity/fish/fish.png");
	    private static final ResourceLocation SALMON = new ResourceLocation(Reference.ID, "textures/entity/fish/salmon.png");
	//private static final ResourceLocation SIAMESE_OCELOT_TEXTURES = new ResourceLocation(Reference.ID, "textures/entity/fish/siamese.png");
	
	    private final ModelFish ModelFish;
		

		public RenderFish(RenderManager renderManagerIn) {
			super(renderManagerIn, new ModelFish(), 0.2F);
			ModelFish = (ModelFish) super.mainModel;
		}
	
	
	
	
	
	
	    protected ResourceLocation getEntityTexture(EntityFish entity)
	    {
	        switch (entity.getFishType())
	        {
	        default:
	            case 0:
	                //return NEMO;
	            case 1:
	                return FISH;
	            case 2:
	                return SALMON;
	            case 3:
	                return NEMO;
	        }
	    }
}
