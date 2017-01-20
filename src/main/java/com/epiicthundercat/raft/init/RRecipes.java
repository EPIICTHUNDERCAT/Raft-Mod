package com.epiicthundercat.raft.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class RRecipes {
	
	
	
	
	public static void register(FMLPreInitializationEvent preEvent) {
		RecipeHandler.addSeparatorSeperateRecipe(Items.WATER_BUCKET, new ItemStack(RItems.dirt_piece), new ItemStack(RItems.sand_piece), 25, new ItemStack(RItems.gravel_piece), 10, null, 0, null, 0);
		RecipeHandler.addSeparatorSeperateRecipe(RItems.scrap, new ItemStack(RItems.aluminium_compound), new ItemStack(RItems.copper_compound), 10, null, 0, null, 0, null, 0);
	}
}
