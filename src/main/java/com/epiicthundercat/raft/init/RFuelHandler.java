package com.epiicthundercat.raft.init;

import com.epiicthundercat.raft.block.BlockPalmLeaves;
import com.epiicthundercat.raft.block.BlockPalmLog;
import com.epiicthundercat.raft.block.BlockPalmSapling;
import com.epiicthundercat.raft.block.BlockPalmWood;
import com.epiicthundercat.raft.block.BlockPalmWoodSlab;
import com.epiicthundercat.raft.block.BlockPalmWoodStairs;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class RFuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack fuel) {
		Block block = Block.getBlockFromItem(fuel.getItem());

		if (block instanceof BlockPalmLog || block instanceof BlockPalmWood || block instanceof BlockPalmWoodStairs) {
			return 300;
		} else if (block instanceof BlockPalmLeaves || block instanceof BlockPalmSapling) {
			return 100;
		} else if (block instanceof BlockPalmWoodSlab) {
			return 150;
		} else if (fuel.getItem() == RItems.plank) {
			return 100;
		} else

			return 0;
	}
}