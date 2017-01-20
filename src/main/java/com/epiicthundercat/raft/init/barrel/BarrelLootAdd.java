package com.epiicthundercat.raft.init.barrel;
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
		
		REventHandler.addBarrelLoot(new ItemStack(Items.POTATO), 1, 0, 2);
		
		REventHandler.addBarrelLoot(new ItemStack(Items.WHEAT_SEEDS), 1, 0, 2);
		
		
		REventHandler.addPlankLoot(new ItemStack(RItems.plank), 100, 1, 1);
		REventHandler.addScrapLoot(new ItemStack(RItems.scrap), 100, 1, 1);
		REventHandler.addThatchLoot(new ItemStack(RItems.thatch), 100, 1, 1);
		
	}
}
