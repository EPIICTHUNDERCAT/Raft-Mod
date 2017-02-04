package com.epiicthundercat.raft.block;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.item.ItemStack;

public class BlockPalmWoodSlab extends BlockSlab{

	public BlockPalmWoodSlab(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getUnlocalizedName(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDouble() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IProperty<?> getVariantProperty() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		// TODO Auto-generated method stub
		return null;
	}

}
