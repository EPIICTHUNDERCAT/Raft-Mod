package com.epiicthundercat.raft.item;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class RItemArmor extends ItemArmor{
	public static ArmorMaterial PLASTIC_SCUBA = EnumHelper.addArmorMaterial("PLASTIC_SCUBA", "raft:plastic",
			150, new int[] { 3, 6, 8, 3 }, 30, SoundEvents.ITEM_BUCKET_FILL, 5.0F);
	
	public RItemArmor(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
		super(material, renderIndex, armorType);
		this.setRegistryName(name.toLowerCase());
		this.setUnlocalizedName(name.toLowerCase());
		this.setCreativeTab(RCreativeTab.RTabs);
		addToItems(this);
	}

	private void addToItems(Item item) {
		RItems.items.add(item);

	}
	   @Override
	    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
	         
	   }
}
