package com.github.epiicthundercat.raft.item;

import com.github.epiicthundercat.raft.creativetab.RCreativeTab;
import com.github.epiicthundercat.raft.init.RItems;

import net.minecraft.item.Item;

public class RItem extends Item {
	
	
	public RItem(String name) {
		
		setRegistryName(name.toLowerCase());
		setUnlocalizedName(name.toLowerCase());
		setCreativeTab(RCreativeTab.RTabs);
		addToItems(this);
	}

	private void addToItems(Item item) {

		RItems.items.add(item);

	}
}
