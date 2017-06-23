package com.epiicthundercat.raft.item;

import java.util.List;

import com.epiicthundercat.raft.entity.EntityHook;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHook extends RItemSword {
public EntityPlayer player;
	private int uses;
	private int itemSlot;
	private boolean isSelected;

	public ItemHook(String name, ToolMaterial material) {
		super(name, material);
		this.setMaxStackSize(1);
		this.setMaxDamage(1000);

	}
@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (stack.getTagCompound().hasKey("wandCooldown")) {
			if (stack.getTagCompound().getInteger("wandCooldown") == 0) {
				if (stack.getTagCompound().hasKey("firing")) {
					stack.getTagCompound().setInteger("firing", stack.getTagCompound().getInteger("firing") + 1);
				} else {
					stack.getTagCompound().setInteger("firing", 1);
				}
				stack.getTagCompound().setInteger("wandCooldown", 10);
			}
		} else {
			if (stack.getTagCompound().hasKey("firing")) {
				stack.getTagCompound().setInteger("firing", stack.getTagCompound().getInteger("firing") + 1);
			} else {
				stack.getTagCompound().setInteger("firing", 1);
			}
			stack.getTagCompound().setInteger("wandCooldown", 10);
		}

		return false;

	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("wandCooldown")) {
				if (stack.getTagCompound().getInteger("wandCooldown") > 0) {
					stack.getTagCompound().setInteger("wandCooldown",
							stack.getTagCompound().getInteger("wandCooldown") - 1);
				}

				if (stack.getTagCompound().hasKey("firing")) {
					if (stack.getTagCompound().getInteger("firing") > 0) {
						stack.getTagCompound().setInteger("firing", stack.getTagCompound().getInteger("firing") - 1);
						for (int i = 0; i < 6; ++i) {
							if ((i % 6) == 0) {
								if (!world.isRemote) {
									world.playSound((EntityPlayer) null, entity.posX, entity.posY, entity.posZ,
											SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.NEUTRAL, 1.5F, 10.0F);
									EntityHook leaf = new EntityHook(world, (EntityLivingBase) entity);
									leaf.setHeadingFromThrower(entity, entity.rotationPitch, entity.rotationYaw, -2.0F,
											1.0F, -2.0F);
									entity.getEntityWorld().spawnEntity(leaf);
									
									stack.damageItem(1, (EntityPlayer) entity);
									if (stack.getItemDamage() == stack.getMaxDamage()) {

										entity.replaceItemInInventory(itemSlot, stack.EMPTY);
									}
								}
							}
						}
					}
				}

			}

		} else {
			stack.setTagCompound(new NBTTagCompound());
		}
	}
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		player.getActiveItemStack().getTagCompound().setBoolean("inUse", true);
	}
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		entityLiving.getActiveItemStack().getTagCompound().setBoolean("inUse", false);
		return stack;
	}
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		return !state.getBlock().equals(Blocks.TALLGRASS) || !state.equals(Blocks.GRASS) ? 0.0F : 1.0F;
	}
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos,
			EntityLivingBase entityLiving) {
		return !world.getBlockState(pos).getBlock().equals(Blocks.TALLGRASS)
				|| !world.getBlockState(pos).equals(Blocks.GRASS);
	}
	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged || !newStack.isItemEqual(oldStack);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

		tooltip.add(String.format("Item Used To Hook Floating Trash"));

		super.addInformation(stack, playerIn, tooltip, advanced);
	}

}
