package com.epiicthundercat.raft.client.renderer;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelScrap;
import com.epiicthundercat.raft.entity.ScrapEntity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderScrap extends Render<ScrapEntity> {
	private static final ResourceLocation SCRAP_TEXTURES = new ResourceLocation(Reference.ID,
			"textures/entity/scrap.png");
	/** instance of ModelPlank for rendering */
	protected ModelBase ModelScrap = new ModelScrap();

	public RenderScrap(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called
	 * unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(ScrapEntity entity) {
		return SCRAP_TEXTURES;
	}

}