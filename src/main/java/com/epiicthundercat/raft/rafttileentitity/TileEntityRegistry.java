package com.epiicthundercat.raft.rafttileentitity;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityRegistry {
	
	
	
	
public static void registerTile(){
	GameRegistry.registerTileEntity(TileEntitySeperator.class, "Seperator");
}



}
