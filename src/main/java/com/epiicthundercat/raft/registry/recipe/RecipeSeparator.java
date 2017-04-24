package com.epiicthundercat.raft.registry.recipe;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;

public class RecipeSeparator {
	private static final List<RecipeSeparator> REGISTRY = Lists.<RecipeSeparator>newArrayList();

	private ItemStack input;
	private ItemStack output;
	private ItemStack output2;
	private ItemStack output3;
	private ItemStack output4;
	private ItemStack output5;
	private double chance;
	private double chanceTwo;
	private double chanceThree;
	private double chanceFour;
	
	

	public RecipeSeparator(ItemStack in, ItemStack out, @Nullable ItemStack out2, double chance2, @Nullable ItemStack out3, double chance3, @Nullable ItemStack out4, double chance4, @Nullable ItemStack out5, double chance5 ) {
		if (!isRecipeRegistered(this)) {
			input = in;
			output = out;
			
			if (out != null) {
				output2 = out2;
				chance = chance2;
				output3 = out3;
				chanceTwo = chance3;
				output4 = out4;
				chanceThree = chance4;
				output5 = out5;
				chanceFour = chance5;
						
				
			}
		}
	}

	public static List<RecipeSeparator> getRecipes() {
		return REGISTRY;
	}

	public static void addRecipe(RecipeSeparator recipe) {
		if (!isRecipeRegistered(recipe)) {
			getRecipes().add(recipe);
		}
	}

	public static void addRecipe(ItemStack in, ItemStack out, @Nullable ItemStack out2, double chance2, @Nullable ItemStack out3, double chance3, @Nullable ItemStack out4, double chance4, @Nullable ItemStack out5, double chance5 ) {
		RecipeSeparator recipe = new RecipeSeparator(in, out, out2, chance2, out3, chance3, out4, chance4, out5, chance5);
		if (!isRecipeRegistered(recipe)) {
			getRecipes().add(new RecipeSeparator(in, out, out2, chance2, out3, chance3, out4, chance4, out5, chance5));
		}
	}

	public static boolean isRecipeRegistered(RecipeSeparator recipe) {
		ItemStack currentInput = recipe.getInput();
		ItemStack currentOutput = recipe.getOutput();
		for (RecipeSeparator recipe2 : getRecipes()) {
			if (compareStacks(currentInput, recipe2.getInput()) && compareStacks(currentOutput, recipe2.getOutput())) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidInput(ItemStack stack) {
		for (ItemStack currentStack : getInputList()) {
			if (compareStacks(stack, currentStack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidOutput(ItemStack stack) {
		for (ItemStack currentStack : getOutputList()) {
			if (compareStacks(stack, currentStack)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidSecondaryOutput(ItemStack stack) {
		for (ItemStack currentStack : getSecondaryOutputList()) {
			if (compareStacks(stack, currentStack)) {
				return true;
			}
		}
		return false;
	}

	public static RecipeSeparator getRecipeFromInput(ItemStack stack) {
		for (RecipeSeparator recipe : getRecipes()) {
			if (compareStacks(stack, recipe.getInput())) {
				return recipe;
			}
		}
		return null;
	}

	public static RecipeSeparator getRecipeFromOutput(ItemStack stack) {
		for (RecipeSeparator recipe : getRecipes()) {
			if (compareStacks(stack, recipe.getOutput())) {
				return recipe;
			}
		}
		return null;
	}

	public static List<ItemStack> getInputList() {
		List<ItemStack> list = Lists.<ItemStack>newArrayList();
		for (RecipeSeparator recipe : getRecipes()) {
			list.add(recipe.getInput());
		}
		return list;
	}

	public static List<ItemStack> getOutputList() {
		List<ItemStack> list = Lists.<ItemStack>newArrayList();
		for (RecipeSeparator recipe : getRecipes()) {
			list.add(recipe.getOutput());
		}
		return list;
	}

	public static List<ItemStack> getSecondaryOutputList() {
		List<ItemStack> list = Lists.<ItemStack>newArrayList();
		for (RecipeSeparator recipe : getRecipes()) {
			list.add(recipe.getSecondOutput());
		}
		return list;
	}

	public static boolean compareStacks(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack2 == null) {
			return stack1 == stack2;
		}
		if (stack1.getItem() != stack2.getItem()) {
			return false;
		}
		if (stack1.getItemDamage() != stack2.getItemDamage()) {
			return false;
		}
		if (!ItemStack.areItemStackTagsEqual(stack1, stack2)) {
			return false;
		}
		return true;
	}

	
	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput() {
		return output;
	}

	public ItemStack getSecondOutput() {
		return output2;
	}

	
	
	public double getChance() {
		return chance;
	}

	
	public boolean hasSecondOutput() {
		return output2 != null;
	}

	
	
	
	public double getSecondOutputChance() {
		return chance;
	}

	
	public int getInputCount() {
		return getInput().stackSize;
	}

	
	public int getOutputCount() {
		return getOutput().stackSize;
	}

	
	public int getSecondOutputCount() {
		return hasSecondOutput() ? getSecondOutput().stackSize : -1;
	}

}

