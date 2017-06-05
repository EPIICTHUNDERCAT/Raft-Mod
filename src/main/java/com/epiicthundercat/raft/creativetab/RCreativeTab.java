package com.epiicthundercat.raft.creativetab;

import com.epiicthundercat.raft.init.RItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RCreativeTab extends CreativeTabs{
	public RCreativeTab(int index, String label) {
		super(index, label);
	}

	public static final RCreativeTab RTabs = new RCreativeTab(CreativeTabs.getNextID(), "rtabs") {
		@SideOnly(Side.CLIENT)
		public ItemStack getTabIconItem() {
			return new ItemStack(RItems.hook);
		}
	};
	
	@Override
	public ItemStack getTabIconItem() {
		return null;
	}
}