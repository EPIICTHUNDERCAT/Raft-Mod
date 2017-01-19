package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeHandler {

    public static List<SeparatorRecipe> separatorRecipes = new ArrayList<SeparatorRecipe>();

    public static void addSeparatorRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo, int outputTwoChance) {
    	separatorRecipes.add(new SeparatorRecipe(input, outputOne, outputTwo, outputTwoChance));
    }
	
	
}
