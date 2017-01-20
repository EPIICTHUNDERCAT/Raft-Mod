package com.epiicthundercat.raft.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SeparatorRecipe {
	public final ItemStack inputStack;
	public final ItemStack outputOneStack;
	public final ItemStack outputTwoStack;
	public final ItemStack outputThreeStack;
	public final ItemStack outputFourStack;
	public final ItemStack outputFiveStack;
	public final int outputTwoChance;
	public final int outputThreeChance;
	public final int outputFourChance;
	public final int outputFiveChance;
	// public final int outputTwoChance;

	public SeparatorRecipe(ItemStack inputStack, ItemStack outputOneStack, ItemStack outputTwoStack,
			int outputTwoChance, ItemStack outputThreeStack, int outputThreeChance, ItemStack outputFourStack,
			int outputFourChance, ItemStack outputFiveStack, int outputFiveChance) {
		this.inputStack = inputStack;
		this.outputOneStack = outputOneStack;
		this.outputTwoStack = outputTwoStack;
		this.outputTwoChance = outputTwoChance;
		this.outputThreeStack = outputThreeStack;
		this.outputThreeChance = outputThreeChance;
		this.outputFourStack = outputFourStack;
		this.outputFourChance = outputFourChance;
		this.outputFiveStack = outputFiveStack;
		this.outputFiveChance = outputFiveChance;
	}
}
