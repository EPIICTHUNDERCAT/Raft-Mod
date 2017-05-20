package com.epiicthundercat.raft.integration;

import com.epiicthundercat.raft.init.RItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.thirst.ThirstHandler;

public class TANIntegration {
	
	 @SubscribeEvent
	    public void onItemUseFinish(LivingEntityUseItemEvent.Finish event)
	    {
	        if (event.getEntityLiving() instanceof EntityPlayer)
	        {
	            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
	            ItemStack stack = player.getHeldItem(player.getActiveHand());
	            ThirstHandler thirstHandler = (ThirstHandler)ThirstHelper.getThirstData(player);

	            if (thirstHandler.isThirsty())
	            {
	                // For some reason the stack size can be zero for water bottles, which breaks everything.
	                // As a workaround, we temporarily set it to 1
	                boolean zeroStack = false;

	                if (stack.stackSize <= 0)
	                {
	                    stack.stackSize = 1;
	                    zeroStack = true;
	                }

	                if (stack.getItem().equals(RItems.coconut))
	                {
	                    thirstHandler.addStats(10, 6.7F);
	                }
	                else if (stack.getItem().equals(RItems.tin_can_potion))
	                {
	                 
	                        //Still fill thirst for other potions, but less than water
	                        thirstHandler.addStats(8, 1.0F);
	                    
	                }

	                if (zeroStack) stack.stackSize = 0;
	            }
	        }
	    }
}
