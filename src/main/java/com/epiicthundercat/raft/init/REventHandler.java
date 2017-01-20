package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.init.barrel.BarrelLoot;

import net.minecraft.item.ItemStack;

public class REventHandler {
	/*
	 * EllPeck's Treasure Code!
	 */
	public static final List<BarrelLoot> scrap_loot = new ArrayList<BarrelLoot>();
	public static final List<BarrelLoot> barrel_loot = new ArrayList<BarrelLoot>();
	public static final List<BarrelLoot> thatch_loot = new ArrayList<BarrelLoot>();
	public static final List<BarrelLoot> plank_loot = new ArrayList<BarrelLoot>();

	public static void addBarrelLoot(ItemStack stack, int chance, int minAmount, int maxAmount) {
		barrel_loot.add(new BarrelLoot(stack, chance, minAmount, maxAmount));
	}

	public static void addThatchLoot(ItemStack stack, int chance, int minAmount, int maxAmount) {
		thatch_loot.add(new BarrelLoot(stack, chance, minAmount, maxAmount));
	}

	public static void addScrapLoot(ItemStack stack, int chance, int minAmount, int maxAmount) {
		scrap_loot.add(new BarrelLoot(stack, chance, minAmount, maxAmount));
	}

	public static void addPlankLoot(ItemStack stack, int chance, int minAmount, int maxAmount) {
		plank_loot.add(new BarrelLoot(stack, chance, minAmount, maxAmount));
	}
}
