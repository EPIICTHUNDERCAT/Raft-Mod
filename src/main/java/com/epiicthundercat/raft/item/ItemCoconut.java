package com.epiicthundercat.raft.item;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemCoconut extends ItemBucketMilk {
	public ItemCoconut(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		addToItems(this);
		this.setCreativeTab(RCreativeTab.RTabs);
	}
	private void addToItems(Item item) {

		RItems.items.add(item);

	}


	/**
	 * Called when the player finishes using this Item (E.g. finishes eating.).
	 * Not called when the player stops using the Item before the action is
	 * complete.
	 */
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			--stack.stackSize;
		}

		if (!worldIn.isRemote) {
			for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
				entityLiving.addPotionEffect(new PotionEffect(potioneffect));
			}
		}

		if (entityplayer != null) {
			entityplayer.addStat(StatList.getObjectUseStats(this));
		}

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			if (stack.stackSize <= 0) {
				return new ItemStack(RItems.coconut_shell);
			}

			if (entityplayer != null) {
				entityplayer.inventory.addItemStackToInventory(new ItemStack(RItems.coconut_shell));
			}
		}

		return stack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack stack) {
		return 16;
	}

	/**
	 * returns the action that specifies what animation to play when the items
	 * is being used
	 */
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.DRINK;
	}

	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		playerIn.setActiveHand(hand);
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}
	
}