package com.epiicthundercat.raft.client.gui;

import com.epiicthundercat.raft.block.BlockSeperator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class RGuiHandler implements IGuiHandler{
	 public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	    {
	      if (id == GuiSeperatorContainer.GUIID) {
	        return new GuiSeperatorContainer(world, x, y, z, player);
	      }
	      return null;
	    }
	    
	    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	    {
	      if (id == GuiSeperatorContainer.GUIID) {
	        return new GuiSeperator(world, x, y, z, player);
	      }
	      return null;
	    }
}
