package com.epiicthundercat.raft.init;

import net.minecraft.item.ItemStack;

public class SeparatorRecipe {
	  public final ItemStack inputStack;
	    public final ItemStack outputOneStack;
	    public final ItemStack outputTwoStack;
	    public final int outputTwoChance;

	    public SeparatorRecipe(ItemStack inputStack, ItemStack outputOneStack, ItemStack outputTwoStack, int outputTwoChance) {
	        this.inputStack = inputStack;
	        this.outputOneStack = outputOneStack;
	        this.outputTwoStack = outputTwoStack;
	        this.outputTwoChance = outputTwoChance;
	    }
}
