package com.epiicthundercat.raft.item;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class RItemSword extends ItemSword {
	public RItemSword(String name, ToolMaterial material) {
		super(material);
		setRegistryName(name.toLowerCase());
		setUnlocalizedName(name.toLowerCase());
		addToItems(this);
		this.setCreativeTab(RCreativeTab.RTabs);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		if (target instanceof EntityLivingBase) {
			if (stack.getItem() == RItems.shark_tooth_sword) {
				((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 200, 2, false, false));
			} else if (stack.getItem() == RItems.spear) {
				((EntityLivingBase) target).addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 10, 2, false, false));
			}
		}
		return true;
	}

	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
		if (toRepair.getItem() == RItems.shark_tooth_sword) {
			return repair.getItem() == RItems.shark_tooth;
		} else if (toRepair.getItem() == RItems.spear) {
			return repair.getItem() == Items.FLINT;
		}

		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return (TextFormatting.BLUE + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name"))
				.trim();
	}

	private void addToItems(Item item) {

		RItems.items.add(item);

	}
}
