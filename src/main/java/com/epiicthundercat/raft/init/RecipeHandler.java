package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipeHandler {
	private static final RecipeHandler INSTANCE = new RecipeHandler();
	public static List<SeparatorRecipe> separatorRecipes = new ArrayList<SeparatorRecipe>();
	private final Map<ItemStack, ItemStack> seperatorList = Maps.<ItemStack, ItemStack>newHashMap();

	public static void addSeparatorRecipe(ItemStack input, ItemStack outputOne, ItemStack outputTwo,
			int outputTwoChance, ItemStack outputThree, int outputThreeChance, ItemStack outputFour,
			int outputFourChance, ItemStack outputFive, int outputFiveChance) {
		separatorRecipes.add(new SeparatorRecipe(input, outputOne, outputTwo, outputTwoChance, outputThree,
				outputThreeChance, outputFour, outputFourChance, outputFive, outputFiveChance));
	}

	public static void addSeparatorSeperateRecipe(Item input, ItemStack outputOne, ItemStack outputTwo,
			int outputTwoChance, ItemStack outputThree, int outputThreeChance, ItemStack outputFour,
			int outputFourChance, ItemStack outputFive, int outputFiveChance) {
		addSeparatorRecipe(new ItemStack(input), outputOne, outputTwo, outputTwoChance, outputThree, outputThreeChance, outputFour, outputFourChance, outputFive, outputFiveChance);
	}

	@Nullable
	public ItemStack getSeparatorResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.seperatorList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}

		return null;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public static RecipeHandler instance() {
		return INSTANCE;
	}
}
