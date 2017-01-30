package com.epiicthundercat.raft.registry.recipe;

import java.util.Collection;

import net.minecraft.item.ItemStack;

public interface IRecipeSeparator {

	
	
	
	

	public abstract ItemStack getOutput();

	
	public abstract boolean isValidInput(ItemStack input);


	public abstract Collection<ItemStack> getValidInputs();
}
