package com.epiicthundercat.raft.rafttileentitity;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class RenderTileEntitySeparator extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z,
			float partialTicks, int destroyStage) {
		if (te instanceof TileEntitySeparator){
			TileEntitySeparator teb = (TileEntitySeparator)te;
		
		}
	}
}
