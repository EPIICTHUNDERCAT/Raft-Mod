package com.epiicthundercat.raft.block;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class RBlock extends Block{
	public RBlock(String name, Material material, float hardness){
		super(material);
		this.setHardness(hardness);
		this.setRegistryName(name.toLowerCase());
		this.setUnlocalizedName(name.toLowerCase());
		this.setCreativeTab(RCreativeTab.RTabs);
		addToBlocks(this);
	}
	private void addToBlocks(Block block) {
		RBlocks.blocks.add(block);
	}
}
