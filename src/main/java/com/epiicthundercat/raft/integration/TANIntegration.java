package com.epiicthundercat.raft.integration;

import com.epiicthundercat.raft.ConfigHandler;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
					thirstHandler.addStats(ConfigHandler.raft.Coconut_Drink_Yield, ConfigHandler.raft.Coconut_Hydration_Yield);
				} else if (stack.getItem().equals(RItems.tin_can_potion)) {
					if (PotionUtils.getFullEffectsFromItem(stack).isEmpty()) {
						thirstHandler.addStats(ConfigHandler.raft.Tin_Can_Drink_Yield, ConfigHandler.raft.Tin_Can_Hydration_Yield);
					}

				}

				if (zeroStack)
					stack.setCount(0);
			}
		}
	}


	}

