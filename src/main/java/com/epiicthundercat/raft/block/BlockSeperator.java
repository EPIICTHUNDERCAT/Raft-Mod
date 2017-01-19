package com.epiicthundercat.raft.block;

import com.epiicthundercat.raft.rafttileentitity.TileEntitySeperator;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSeperator extends BlockContainer {

	public BlockSeperator(Material materialIn) {
		super(materialIn);
		
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
	
		return new TileEntitySeperator();
	}
	
}
