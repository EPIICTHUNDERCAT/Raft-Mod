package com.epiicthundercat.raft.registry.recipe;

import java.util.Collection;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDicSepRecipes implements IRecipeSeparator{
//private final ItemStack output;
private final String oreDictSource;
public final ItemStack results;
public final ItemStack outputTwo;
public final ItemStack outputThree;
public final ItemStack outputFour;
public final ItemStack outputFive;
public final int outputTwoChance;
public final int outputThreeChance;
public final int outputFourChance;
public final int outputFiveChance;


public OreDicSepRecipes(String oreDictionaryID, ItemStack results, ItemStack outputTwo, int outputTwoChance, ItemStack outputThree, int outputThreeChance, ItemStack outputFour, int outputFourChance, ItemStack outputFive, int outputFiveChance) {
	this.oreDictSource = oreDictionaryID;
	this.results = results;
	this.outputTwo = outputTwo;
	this.outputTwoChance = outputTwoChance;
	this.outputThree = outputThree;
	this.outputThreeChance = outputThreeChance;
	this.outputFour = outputFour;
	this.outputFourChance = outputFourChance;
	this.outputFive = outputFive;
	this.outputFiveChance = outputFiveChance;
}

@Override
public ItemStack getOutput() {
	return this.results.copy();
}

@Override
public boolean isValidInput(ItemStack input) {
	final List<ItemStack> validInputs = OreDictionary.getOres(this.oreDictSource);
	for (int i = 0; i < validInputs.size(); i++)
		if (validInputs.get(i).getMetadata() == OreDictionary.WILDCARD_VALUE) {
			// do not compare metadata values
			if (validInputs.get(i).getItem() == input.getItem())
				return true;
		} else if (ItemStack.areItemsEqual(validInputs.get(i), input))
			return true;
	return false;
}

@Override
public Collection<ItemStack> getValidInputs() {
	return OreDictionary.getOres(this.oreDictSource);
}

}
