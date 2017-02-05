package com.epiicthundercat.raft.registry.recipe;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.init.RItems;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeSeparator {
	private static final RecipeSeparator SEPARATING_BASE = new RecipeSeparator();
	private final Map<ItemStack, ItemStack> sepaList = Maps.<ItemStack, ItemStack>newHashMap();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	
	
	public static RecipeSeparator instance() {
		return SEPARATING_BASE;
	}
	
	public RecipeSeparator() {

		addSeparatingRecipeForItem(RItems.scrap, new ItemStack(RItems.aluminum_compound), new ItemStack(RItems.iron_compound), 900);

	}

	public void addSeparatingRecipeForBlock(Block input, ItemStack stack, ItemStack output2, float experience) {
		this.addSeparatingRecipeForItem(Item.getItemFromBlock(input), stack, output2, experience);
	}

	public void addSeparatingRecipeForItem(Item input, ItemStack stack, ItemStack output2, float experience) {
		this.addSeparatingRecipe(new ItemStack(input, 1, 32767), stack, output2, experience);
	}

	public void addSeparatingRecipe(ItemStack input, ItemStack stack, ItemStack output2, float experience) {
		if (getSeparatingResult(input) !=null) {
			net.minecraftforge.fml.common.FMLLog
					.info("Ignored separating recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.sepaList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	@Nullable
	public ItemStack getSeparatingResult(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.sepaList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return (ItemStack) entry.getValue();
			}
		}

		return null;
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem()
				&& (stack2.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getSeparatingList() {
		return this.sepaList;
	}

	public float getSeparatingExperience(ItemStack stack) {
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1)
			return ret;

		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack) entry.getKey())) {
				return ((Float) entry.getValue()).floatValue();
			}
		}

		return 0.0F;
	}

	public static ItemStack getOreDictItemWithMeta(String oreIdName, int num) {
		List<ItemStack> res = OreDictionary.getOres(oreIdName);
		if (res != null) {
			ItemStack[] res2 = res.toArray(new ItemStack[res.size()]);
			for (int i = 0; i < res.size();) {
				return new ItemStack(res2[i].getItem(), num, res2[i].getItemDamage());
			}
		}
		return null;
	}
}