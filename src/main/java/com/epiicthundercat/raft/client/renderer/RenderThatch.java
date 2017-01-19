package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelThatch;
import com.epiicthundercat.raft.entity.ThatchEntity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderThatch extends Render<ThatchEntity> {
	private static final ResourceLocation THATCH_TEXTURES = new ResourceLocation(Reference.ID,
			"textures/entity/thatch.png");
	/** instance of ModelPlank for rendering */
	protected ModelBase ModelThatch = new ModelThatch();

	public RenderThatch(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(ThatchEntity entity) {
		return THATCH_TEXTURES;
	}

}