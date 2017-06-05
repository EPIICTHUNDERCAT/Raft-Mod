package com.epiicthundercat.raft.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class RGuiHandler{} //implements IGuiHandler {

	
/*	public static final int GUI_SEPERATOR = 0;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (ID == GUI_SEPERATOR) {
			if (te instanceof TileEntitySeparator) {
				return new ContainerBlockSeparator(player.inventory, (TileEntitySeparator) te);
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		TileEntity te = world.getTileEntity(pos);
		if (ID == GUI_SEPERATOR) {
			if (te instanceof TileEntitySeparator) {
				TileEntitySeparator containerTileEntity = (TileEntitySeparator) te;
				return new SeparatorGui(containerTileEntity,
						new ContainerBlockSeparator(player.inventory, containerTileEntity), te, player.inventory, containerTileEntity);
			}
		}
		return null;
	}
	
}*/
