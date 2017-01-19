package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelPlank;
import com.epiicthundercat.raft.entity.PlankEntity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPlank extends Render<PlankEntity> {
	private static final ResourceLocation PLANK_TEXTURES = new ResourceLocation(Reference.ID,
			"textures/entity/plank.png");
	/** instance of ModelPlank for rendering */
	protected ModelBase ModelPlank = new ModelPlank();

	public RenderPlank(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(PlankEntity entity) {
		return PLANK_TEXTURES;
	}

}