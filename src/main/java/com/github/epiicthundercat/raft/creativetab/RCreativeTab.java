package com.github.epiicthundercat.raft.creativetab;

import com.github.epiicthundercat.raft.init.RItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RCreativeTab extends CreativeTabs{
	public RCreativeTab(int index, String label) {
		super(index, label);
	}

	public static final RCreativeTab RTabs = new RCreativeTab(CreativeTabs.getNextID(), "rtabs") {
		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			return RItems.hook;
		}
	};
	
	@Override
	public Item getTabIconItem() {
		return null;
	}
}