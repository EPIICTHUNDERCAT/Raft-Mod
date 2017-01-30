package com.epiicthundercat.raft.registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.registry.recipe.IRecipeSeparator;
import com.epiicthundercat.raft.registry.recipe.OreDicSepRecipes;
import com.epiicthundercat.raft.registry.recipe.RegularSeparatorRecipes;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;

public class SeparatorRecipeRegistry {
	private final List<IRecipeSeparator> recipes = new ArrayList<>();

	private final Map<ItemLookupReference, IRecipeSeparator> recipeByInputCache = new HashMap<>();
	private final Map<ItemLookupReference, List<IRecipeSeparator>> recipeByOutputCache = new HashMap<>();

	private static final Lock initLock = new ReentrantLock();
	private static SeparatorRecipeRegistry instance = null;

	/**
	 * Gets a singleton instance of SeperatorRecipeRegistry
	 * 
	 * @return A global instance of SeperatorRecipeRegistry
	 */
	public static SeparatorRecipeRegistry getInstance() {
		if (instance == null) {
			initLock.lock();
			try {
				if (instance == null)
					// thread-safe singleton instantiation
					instance = new SeparatorRecipeRegistry();
			} finally {
				initLock.unlock();
			}
		}
		return instance;
	}


	public static void addNewSeparatorRecipe(final String oreDictionaryName, final ItemStack outputOne, final ItemStack outputTwo,
			int outputTwoChance, final ItemStack outputThree, int outputThreeChance, final ItemStack outputFour,
			int outputFourChance, final ItemStack outputFive, int outputFiveChance) { // preferred method
		getInstance().addRecipe(new OreDicSepRecipes(oreDictionaryName, outputOne, outputTwo, outputTwoChance, outputThree,
				outputThreeChance, outputFour, outputFourChance, outputFive, outputFiveChance));
	}

	public static void addNewSeparatorRecipe(final ItemStack input, final ItemStack outputOne, final ItemStack outputTwo,
			int outputTwoChance, final ItemStack outputThree, int outputThreeChance, final ItemStack outputFour,
			int outputFourChance, final ItemStack outputFive, int outputFiveChance) {
		if ((input == null) || (outputOne == null) && (outputTwo == null) && (outputThree == null) && (outputFour == null) && (outputFive == null))
			FMLLog.severe("%s: %s: Crusher recipe not registered because of null input or output. \n %s",
				Reference.ID, SeparatorRecipeRegistry.class,
				Arrays.toString(Thread.currentThread().getStackTrace()).replace(", ", "\n").replace("[", "").replace("]", "")
				);
		getInstance().addRecipe(new RegularSeparatorRecipes(input, outputOne, outputTwo, outputTwoChance, outputThree,
				outputThreeChance, outputFour, outputFourChance, outputFive, outputFiveChance));
	}


	public static void addNewSeparatorRecipe(final Item input, final ItemStack outputOne, final ItemStack outputTwo,
			int outputTwoChance, final ItemStack outputThree, int outputThreeChance, final ItemStack outputFour,
			int outputFourChance, final ItemStack outputFive, int outputFiveChance) {
		getInstance().addRecipe(new IRecipeSeparator() {

			@Override
			public ItemStack getOutput() {
				return outputOne;
			}

			@Override
			public boolean isValidInput(ItemStack in) {
				return input.equals(in.getItem());
			}

			@Override
			public Collection<ItemStack> getValidInputs() {
				return Collections.singletonList(new ItemStack(input));
			}
		});
	}
	
	
	public static void addNewSeparatorRecipe(final Block input, final ItemStack outputOne, final ItemStack outputTwo,
			int outputTwoChance, final ItemStack outputThree, int outputThreeChance, final ItemStack outputFour,
			int outputFourChance, final ItemStack outputFive, int outputFiveChance) {
		getInstance().addRecipe(new IRecipeSeparator() {

			
			@Override
			public ItemStack getOutput() {
				return outputOne;
			}

			@Override
			public boolean isValidInput(ItemStack in) {
				return input.equals(Block.getBlockFromItem(in.getItem()));
			}

			@Override
			public Collection<ItemStack> getValidInputs() {
				return Collections.singletonList(new ItemStack(input));
			}
		});
	}


	public void clearCache() {
		this.recipeByInputCache.clear();
		this.recipeByOutputCache.clear();
	}

	
	public void addRecipe(IRecipeSeparator crusherRecipe) {
		this.recipes.add(crusherRecipe);
	}

	
	public List<IRecipeSeparator> getRecipesForOutputItem(ItemStack output) {
		final ItemLookupReference ref = new ItemLookupReference(output);
		if (this.recipeByOutputCache.containsKey(ref)) {
			final List<IRecipeSeparator> recipeCache = this.recipeByOutputCache.get(ref);
			if (recipeCache.isEmpty())
				return null;
			return recipeCache;
		} else {
			// add recipe cache
			final List<IRecipeSeparator> recipeCache = new ArrayList<>();
			for (final IRecipeSeparator r : this.recipes)
				if (ItemStack.areItemsEqual(r.getOutput(), output))
					recipeCache.add(r);
			this.recipeByOutputCache.put(ref, recipeCache);
			if (recipeCache.isEmpty())
				return null;
			return recipeCache;
		}
	}

	
	public List<IRecipeSeparator> getRecipesForOutputItem(IBlockState output) {
		final ItemLookupReference ref = new ItemLookupReference(output);
		if (this.recipeByOutputCache.containsKey(ref)) {
			final List<IRecipeSeparator> recipeCache = this.recipeByOutputCache.get(ref);
			if (recipeCache.isEmpty())
				return null;
			return recipeCache;
		} else {
			// add recipe cache
			final List<IRecipeSeparator> recipeCache = new ArrayList<>();
			for (final IRecipeSeparator r : this.recipes)
				if (ref.equals(r.getOutput()))
					recipeCache.add(r);
			this.recipeByOutputCache.put(ref, recipeCache);
			if (recipeCache.isEmpty())
				return null;
			return recipeCache;
		}
	}

	
	public IRecipeSeparator getRecipeForInputItem(ItemStack input) {
		final ItemLookupReference ref = new ItemLookupReference(input);
		if (this.recipeByInputCache.containsKey(ref))
			return this.recipeByInputCache.get(ref);
		else {
			for (final IRecipeSeparator r : this.recipes)
				if (r.isValidInput(input)) {
					this.recipeByInputCache.put(ref, r);
					return r;
				}
			// no recipes, cache null result
			this.recipeByInputCache.put(ref, null);
			return null;
		}
	}

	public IRecipeSeparator getRecipeForInputItem(IBlockState input) {
		final ItemLookupReference ref = new ItemLookupReference(input);
		final ItemStack stack = new ItemStack(input.getBlock(), 1, ref.metaData);
		if (this.recipeByInputCache.containsKey(ref))
			return this.recipeByInputCache.get(ref);
		else {
			for (final IRecipeSeparator r : this.recipes)
				if (r.isValidInput(stack)) {
					this.recipeByInputCache.put(ref, r);
					return r;
				}
			// no recipes, cache null result
			this.recipeByInputCache.put(ref, null);
			return null;
		}
	}

	
	public Collection<IRecipeSeparator> getAllRecipes() {
		return Collections.unmodifiableList(this.recipes);
	}

	private static final class ItemLookupReference {
		final Item item;
		final int metaData;
		final int hashCache;

		public ItemLookupReference(ItemStack inputItem) {
			this.item = inputItem.getItem();
			this.metaData = inputItem.getMetadata();
			this.hashCache = this.item.getUnlocalizedName().hashCode() + (57 * this.metaData);
		}

		public ItemLookupReference(IBlockState inputBlock) {
			this.item = Item.getItemFromBlock(inputBlock.getBlock());
			this.metaData = inputBlock.getBlock().getMetaFromState(inputBlock);
			this.hashCache = inputBlock.getBlock().getUnlocalizedName().hashCode() + (57 * this.metaData);
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof ItemLookupReference) {
				final ItemLookupReference that = (ItemLookupReference) other;
				return (this.hashCache == that.hashCache) && (this.item == that.item)
						&& (this.metaData == that.metaData);
			} else if (other instanceof ItemStack) {
				final ItemStack that = (ItemStack) other;
				return (this.item == that.getItem()) && (this.metaData == that.getMetadata());
			} else
				return false;
		}

		@Override
		public int hashCode() {
			return this.hashCache;
		}
	}
}
