package com.epiicthundercat.raft.init.barrel;
import com.epiicthundercat.raft.init.RBlocks;
/*
 * EllPeck's Treasure Code! ALL CREDITS GO TO HIM
 */
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BarrelLootAdd {
	public static void init() {

		REventHandler.addBarrelLoot(new ItemStack(RItems.scrap), 3, 0, 5);

		REventHandler.addBarrelLoot(new ItemStack(RItems.plank), 2, 0, 5);

		REventHandler.addBarrelLoot(new ItemStack(RItems.thatch), 2, 0, 5);
		
		
		REventHandler.addBarrelLoot(new ItemStack(Items.POTATO), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(RBlocks.palm_sapling), 1, 0, 1);
		//REventHandler.addBarrelLoot(new ItemStack(RItems.coconut), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(RItems.dirt_piece), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(RItems.sand_piece), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(RItems.gravel_piece), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(Items.WHEAT_SEEDS), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(Items.BEETROOT), 1, 0, 1);
		REventHandler.addBarrelLoot(new ItemStack(Items.BEETROOT_SEEDS), 1, 0, 1);
		
		
		REventHandler.addPlankLoot(new ItemStack(RItems.plank), 1, 0, 0);
		REventHandler.addScrapLoot(new ItemStack(RItems.scrap), 1, 0, 0);
		REventHandler.addThatchLoot(new ItemStack(RItems.thatch), 1, 0, 0);
		
	}
}
