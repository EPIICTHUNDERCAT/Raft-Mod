package com.epiicthundercat.raft.registry.recipe;
import java.util.Collection;
import java.util.Collections;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RegularSeparatorRecipes implements IRecipeSeparator{

	public final ItemStack input;
	public final ItemStack output;
	
	public final ItemStack outputTwo;
	public final ItemStack outputThree;
	public final ItemStack outputFour;
	public final ItemStack outputFive;
	public final int outputTwoChance;
	public final int outputThreeChance;
	public final int outputFourChance;
	public final int outputFiveChance;

	
	public RegularSeparatorRecipes(ItemStack input, ItemStack output, ItemStack outputTwo, int outputTwoChance, ItemStack outputThree, int outputThreeChance, ItemStack outputFour, int outputFourChance, ItemStack outputFive, int outputFiveChance) {
		this.input = input;
		this.output = output;
		this.outputTwo = outputTwo;
		this.outputTwoChance = outputTwoChance;
		this.outputThree = outputThree;
		this.outputThreeChance = outputThreeChance;
		this.outputFour = outputFour;
		this.outputFourChance = outputFourChance;
		this.outputFive = outputFive;
		this.outputFiveChance = outputFiveChance;
		if (input == null)
			throw new NullPointerException(this.getClass().getName() + ": cannot have null input item");
		if (output == null)
			throw new NullPointerException(this.getClass().getName() + ": cannot have null output item");
	}

	
	@Override
	public ItemStack getOutput() {
		return this.output.copy();
	}

	
	@Override
	public boolean isValidInput(ItemStack input) {
		if ((input != null) && (this.input.getItemDamage() == OreDictionary.WILDCARD_VALUE))
			return this.input.getItem() == input.getItem();
		return ItemStack.areItemsEqual(this.input, input);
	}

	
	@Override
	public Collection<ItemStack> getValidInputs() {
		return Collections.singletonList(this.input);
	}

}
