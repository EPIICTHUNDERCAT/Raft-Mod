package com.epiicthundercat.raft.registry.recipe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.oredict.OreDictionary;

public class RecipeSeparator {
	private static final RecipeSeparator SEPARATING_BASE = new RecipeSeparator();
	private Map<ItemStack, ItemStack> sepaList = Maps.<ItemStack, ItemStack>newHashMap();
	private Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	private Map<Item, List<StackWithChance>> multiList = Maps.<Item, List<StackWithChance>>newHashMap();
	
	
	
	public static RecipeSeparator instance() {
		return SEPARATING_BASE;
	}
	
	public RecipeSeparator() {
	}

	public void addSeparatingRecipe(Block input, ItemStack stack, float experience) {
		this.addSeparatingRecipe(Item.getItemFromBlock(input), stack, experience);
	}

	public void addSeparatingRecipe(Item input, ItemStack stack, float experience) {
		this.addSeparatingRecipe(new ItemStack(input, 1, 0), stack, experience);
	}

	public void addSeparatingRecipe(ItemStack input, ItemStack stack, float experience) {
		if (getSeparatingResult(input) !=null) {
			net.minecraftforge.fml.common.FMLLog
					.info("Ignored separating recipe with conflicting input: " + input + " = " + stack);
			return;
		}
		this.sepaList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}
	
	public void addMultiSeparatingRecipe(Item input, List<StackWithChance> output) {
		if (multiList.get(input) != null) {
			FMLLog.info("Ignored separating recipe with conflicting input: " + input + " = multiple outputs");
			return;
		}
		multiList.put(input, output); 
	
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
	
	@Nullable
	public List<StackWithChance> getMultiSeparatingResult(Item stack) {
		List<StackWithChance> result = multiList.get(stack);
		if(result != null) return result;
		else {
		
			return null;
		}
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