package com.epiicthundercat.raft.item;

import java.util.List;

import com.epiicthundercat.raft.entity.EntityHook;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHook extends RItem {

	
	private int uses;

	public ItemHook(String name) {
		super(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(10);
		
	}

	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.getTagCompound().hasKey("wandCooldown")) {
				if (stack.getTagCompound().getInteger("wandCooldown") == 0) {
					if (stack.getTagCompound().hasKey("firing")) {
						stack.getTagCompound().setInteger("firing", stack.getTagCompound().getInteger("firing") + 1);
					} else {
						stack.getTagCompound().setInteger("firing", 1);
					}
					stack.getTagCompound().setInteger("wandCooldown", 10);
					return false;
				}
			} else {
				if (stack.getTagCompound().hasKey("firing")) {
					stack.getTagCompound().setInteger("firing", stack.getTagCompound().getInteger("firing") + 1);
				} else {
					stack.getTagCompound().setInteger("firing", 1);
				}
				stack.getTagCompound().setInteger("wandCooldown", 10);
				return false;
			}
		}
		return true;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		return EnumActionResult.PASS;

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
											SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.NEUTRAL, 1.5F, 10.0F);
									EntityHook lightning = new EntityHook(world,
											(EntityLivingBase) entity);
									lightning.setHeadingFromThrower(entity, entity.rotationPitch, entity.rotationYaw,
											0.0F, 1.0F, 0.0F);
									System.out.println("spawner");
									entity.getEntityWorld().spawnEntity(lightning);
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

	

	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		return slotChanged;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

		tooltip.add(String.format("Item Used To Hook Floating Trash"));

		super.addInformation(stack, playerIn, tooltip, advanced);
	}

}
