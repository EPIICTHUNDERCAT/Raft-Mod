package com.epiicthundercat.raft.integration;

import com.epiicthundercat.raft.init.RItems;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toughasnails.api.TANPotions;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class TANIntegration {

	@SubscribeEvent
	public void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack stack = player.getHeldItem(player.getActiveHand());
			ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);

			if (thirstHandler.isThirsty()) {
				// For some reason the stack size can be zero for water bottles,
				// which breaks everything.
				// As a workaround, we temporarily set it to 1
				boolean zeroStack = false;

				if (stack.getCount() <= 0) {
					stack.setCount(1);
					zeroStack = true;
				}

				if (stack.getItem().equals(RItems.coconut)) {
					thirstHandler.addStats(10, 6.7F);
				} else if (stack.getItem().equals(RItems.tin_can_potion)) {
					if (PotionUtils.getFullEffectsFromItem(stack).isEmpty()) {
						thirstHandler.addStats(8, 1.0F);
					}

				}

				if (zeroStack)
					stack.setCount(0);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerInteract(RightClickEmpty event) {

		if (event.getWorld().isRemote) {
			return;
		} // Not doing this on client side. (Seems to be fired on server side
			// only by default already.)
		if (event.getEntityPlayer() == null) {
			return;
		}

		if (event.getEntityPlayer().capabilities.isCreativeMode) {
			return;
		} // Not interfering with creative mode
		if (event.getHand() != EnumHand.MAIN_HAND) {
			return;
		} // Only for the main hand

		if (event.getEntityPlayer().isSneaking()) {
			return;
		} // Not interfering like this.

		// IBlockState block = event.getWorld().getBlockState(event.getPos());
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		RayTraceResult raytraceresult = event.getWorld().rayTraceBlocks(player.getPositionEyes(0), player.getLookVec(),
				true);

		/*
		 * if (block.getMaterial() == Material.WATER) { return; }
		 */
		if (raytraceresult == null) {
			return;
		} else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK) {
			return;
		} else {
			BlockPos blockpos = raytraceresult.getBlockPos();

				IBlockState iblockstate = event.getWorld().getBlockState(blockpos);
				Material material = iblockstate.getMaterial();
				if (event.getEntityLiving() instanceof EntityPlayer) {

					ThirstHandler thirstHandler = (ThirstHandler) ThirstHelper.getThirstData(player);

					if (thirstHandler.isThirsty()) {

						if (material == Material.WATER
								&& ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0) {

							thirstHandler.addStats(10, 6.7F);
							player.addPotionEffect(new PotionEffect(TANPotions.thirst, 600));
							player.playSound(SoundEvents.BLOCK_WATER_AMBIENT, 10, 10);
						}
					}

				}

			}
		}
	}

