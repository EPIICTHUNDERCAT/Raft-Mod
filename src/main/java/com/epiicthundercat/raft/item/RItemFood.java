package com.epiicthundercat.raft.item;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class RItemFood extends ItemFood{
	public RItemFood(String name, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setRegistryName(name.toLowerCase());
		this.setUnlocalizedName(name.toLowerCase());
		this.setCreativeTab(RCreativeTab.RTabs);
		addToItems(this);
	}

	private void addToItems(Item item) {

	RItems.items.add(item);
		
		
	}
}
