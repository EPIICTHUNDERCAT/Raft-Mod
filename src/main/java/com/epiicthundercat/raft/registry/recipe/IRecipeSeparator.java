package com.epiicthundercat.raft.registry.recipe;

/**
 * CODE BELONGS TO P455w0rd ! Creator or P455w0rds Things and WCT and many other mods!
 * 
 * 
 * 
 */
import java.util.List;

import net.minecraft.item.ItemStack;

public interface IRecipeSeparator {

	List<ItemStack> getInputList();

	List<ItemStack> getOutputList();

	List<ItemStack> getSecondaryOutputList();

	List<ItemStack> getThirdOutputList();

	List<ItemStack> getFourthOutputList();

	List<ItemStack> getFifthOutputList();

	boolean hasSecondOutput(ItemStack stack);

	boolean hasThirdOutput(ItemStack stack);

	boolean hasFourthOutput(ItemStack stack);

	boolean hasFifthOutput(ItemStack stack);

	int getEnergyRequired(ItemStack stack);

	double getSecondOutputChance(ItemStack stack);

	double getThirdOutputChance(ItemStack stack);

	double getFourthOutputChance(ItemStack stack);

	double getFifthOutputChance(ItemStack stack);

	ItemStack getInput(ItemStack stack);

	ItemStack getInputFromOutput(ItemStack stack);

	ItemStack getOutputFromInput(ItemStack stack);

	ItemStack getSecondOutputFromInput(ItemStack stack);

	ItemStack getThirdOutputFromInput(ItemStack stack);

	ItemStack getFourthOutputFromInput(ItemStack stack);

	ItemStack getFifthOutputFromInput(ItemStack stack);

	boolean hasEnergyRegistered(ItemStack stack);

	boolean isInputRegistered(ItemStack stack);

	boolean isOutputRegistered(ItemStack stack);
}
