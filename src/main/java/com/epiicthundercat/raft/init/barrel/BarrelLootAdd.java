package com.epiicthundercat.raft.init.barrel;
/*
 * EllPeck's Treasure Code!
 */
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.item.ItemStack;

public class BarrelLootAdd {
	public static void init() {

		REventHandler.addBarrelLoot(new ItemStack(RItems.scrap), 2, 0, 5);

		REventHandler.addBarrelLoot(new ItemStack(RItems.plank), 2, 0, 5);

		REventHandler.addBarrelLoot(new ItemStack(RItems.thatch), 2, 0, 5);
		//REventHandler.addBarrelLoot(new ItemStack(RItems.scrap), 2, 0, 5);
	}
}
