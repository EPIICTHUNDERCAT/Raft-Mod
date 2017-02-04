package com.epiicthundercat.raft.registry.recipe;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

/**
 * CODE BELONGS TO P455w0rd ! Creator or P455w0rds Things and WCT and many other
 * mods!
 * 
 * 
 * 
 */

public class RecipeSeparator implements IRecipeSeparator {
	private static final RecipeSeparator INSTANCE = new RecipeSeparator();
	private final Map<ItemStack, ItemStack> INPUT_OUTPUT = Maps.newHashMap();
	private final Map<ItemStack, ItemStack> OUTPUT_INPUT = Maps.newHashMap();
	private final Map<ItemStack, ItemStack> INPUT_OUTPUT2 = Maps.newHashMap();
	private final Map<ItemStack, ItemStack> INPUT_OUTPUT3 = Maps.newHashMap();
	private final Map<ItemStack, ItemStack> INPUT_OUTPUT4 = Maps.newHashMap();
	private final Map<ItemStack, ItemStack> INPUT_OUTPUT5 = Maps.newHashMap();
	private final Map<ItemStack, Double> INPUT_OUTPUT2_CHANCE = Maps.newHashMap();
	private final Map<ItemStack, Double> INPUT_OUTPUT3_CHANCE = Maps.newHashMap();
	private final Map<ItemStack, Double> INPUT_OUTPUT4_CHANCE = Maps.newHashMap();
	private final Map<ItemStack, Double> INPUT_OUTPUT5_CHANCE = Maps.newHashMap();

	private final Map<ItemStack, Integer> INPUT_ENERGY = Maps.newHashMap();
	private ItemStack input;
	private ItemStack output;
	private ItemStack output2;
	private ItemStack output3;
	private ItemStack output4;
	private ItemStack output5;
	private ItemStack output6;
	private int energy;
	private double chance2;
	private double chance3;
	private double chance4;
	private double chance5;
	

	public RecipeSeparator() {
	}

	// purely here for JEI support
	public RecipeSeparator(ItemStack in, ItemStack out, @Nullable ItemStack out2,  double chance2, @Nullable ItemStack out3, double chance3,  @Nullable ItemStack out4, double chance4,   @Nullable ItemStack out5, double chance5, int energyIn) {
		if (!isRecipeRegistered(in, out, energy)) {
			input = in;
			output = out;
			energy = energyIn;
			if (out != null) {
				output2 = out2;
				chance2 = chance3;
			}
		}
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
	public ItemStack getThirdOutput() {
		return output3;
	}
	public ItemStack getFourthOutput() {
		return output4;
	}
	public ItemStack getFifthOutput() {
		return output5;
	}
	

	public int getEnergyRequired() {
		return energy;
	}

	public double getOutChance2() {
		return chance2;
	}
	public double getOutChance3() {
		return chance3;
	}
	public double getOutChance4() {
		return chance4;
	}
	public double getOutChance5() {
		return chance5;
	}

	public List<RecipeSeparator> getRecipeList() {
		List<RecipeSeparator> recipeList = Lists.newArrayList();
		for (ItemStack input : INPUT_OUTPUT.keySet()) {
			if (hasSecondOutput(input)) {
				recipeList.add(new RecipeSeparator(input, getOutputFromInput(input), getSecondOutputFromInput(input),
						getSecondOutputChance(input), getThirdOutputFromInput(input), getThirdOutputChance(input), getFourthOutputFromInput(input), getFourthOutputChance(input), getFifthOutputFromInput(input), getFifthOutputChance(input), getEnergyRequired(input)));
			} else {
				recipeList.add(
						new RecipeSeparator(input, getOutputFromInput(input), null, 0.0D, null, 0.0D, null, 0.0D, null, 0.0D, getEnergyRequired(input)));
			}
		}
		return recipeList;
	}

	public static RecipeSeparator getInstance() {
		return INSTANCE;
	}

	public void addRecipe(ItemStack in, ItemStack out, @Nullable ItemStack out2,  double chance2, @Nullable ItemStack out3, double chance3,  @Nullable ItemStack out4, double chance4,   @Nullable ItemStack out5, double chance5, int energy) {
		if (!isRecipeRegistered(in, out, energy)) {
			INPUT_OUTPUT.put(in, out);
			OUTPUT_INPUT.put(out, in);
			INPUT_ENERGY.put(in, energy);
			if (out2 != null) {
				INPUT_OUTPUT2.put(in, out2);
				INPUT_OUTPUT2_CHANCE.put(in, chance2);
			}
			if (out3 != null){
				INPUT_OUTPUT3.put(in, out3);
				INPUT_OUTPUT3_CHANCE.put(in, chance3);
			}
			if (out4 != null){
				INPUT_OUTPUT4.put(in, out4);
				INPUT_OUTPUT4_CHANCE.put(in, chance4);
			}
			if (out5 != null){
				INPUT_OUTPUT5.put(in, out5);
				INPUT_OUTPUT5_CHANCE.put(in, chance5);
			}
		}
	}

	public boolean isRecipeRegistered(ItemStack in, ItemStack out, int energy) {
		return isInputRegistered(in) && isOutputRegistered(out) && hasEnergyRegistered(in)
				&& getEnergyRequired(in) == energy;
	}

	@Override
	public List<ItemStack> getInputList() {
		return Arrays.asList(INPUT_OUTPUT.keySet().toArray(new ItemStack[INPUT_OUTPUT.size()]));
	}

	@Override
	public List<ItemStack> getOutputList() {
		return Arrays.asList(INPUT_OUTPUT.values().toArray(new ItemStack[INPUT_OUTPUT.size()]));
	}

	@Override
	public List<ItemStack> getSecondaryOutputList() {
		return Arrays.asList(INPUT_OUTPUT2.values().toArray(new ItemStack[INPUT_OUTPUT2.size()]));
	}

	@Override
	public boolean isInputRegistered(ItemStack stack) {
		for (ItemStack registeredInput : INPUT_OUTPUT.keySet()) {
			if (compareStacks(registeredInput, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isOutputRegistered(ItemStack stack) {
		for (ItemStack registeredOutput : OUTPUT_INPUT.keySet()) {
			if (compareStacks(registeredOutput, stack)) {
				return true;
			}
		}
		return false;
	}

	public boolean isSecondOutputRegistered(ItemStack stack) {
		for (ItemStack registeredOutput : INPUT_OUTPUT2.values()) {
			if (compareStacks(registeredOutput, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasSecondOutput(ItemStack stack) {
		for (ItemStack registeredOutput2 : INPUT_OUTPUT2.keySet()) {
			if (compareStacks(registeredOutput2, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasEnergyRegistered(ItemStack stack) {
		for (ItemStack registeredEnergyStack : INPUT_ENERGY.keySet()) {
			if (compareStacks(registeredEnergyStack, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int getEnergyRequired(ItemStack stack) {
		if (isInputRegistered(stack) && hasEnergyRegistered(stack)) {
			for (ItemStack registeredEnergyStack : INPUT_ENERGY.keySet()) {
				if (compareStacks(registeredEnergyStack, stack)) {
					return INPUT_ENERGY.get(registeredEnergyStack);
				}
			}
		}
		return 20;
	}

	@Override
	public double getSecondOutputChance(ItemStack stack) {
		if (isInputRegistered(stack) && hasSecondOutput(stack)) {
			for (ItemStack registeredOutput2 : INPUT_OUTPUT2_CHANCE.keySet()) {
				if (compareStacks(registeredOutput2, stack)) {
					return INPUT_OUTPUT2_CHANCE.get(registeredOutput2);
				}
			}
		}
		return 0.0D;
	}

	@Override
	public ItemStack getInput(ItemStack stack) {
		if (isInputRegistered(stack)) {
			for (ItemStack registeredInput : INPUT_OUTPUT.keySet()) {
				if (compareStacks(registeredInput, stack)) {
					return registeredInput;
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getInputFromOutput(ItemStack stack) {
		if (isOutputRegistered(stack)) {
			for (ItemStack registeredOutput : OUTPUT_INPUT.keySet()) {
				if (compareStacks(registeredOutput, stack)) {
					return OUTPUT_INPUT.get(registeredOutput);
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getOutputFromInput(ItemStack stack) {
		if (isInputRegistered(stack)) {
			for (ItemStack registeredInput : INPUT_OUTPUT.keySet()) {
				if (compareStacks(registeredInput, stack)) {
					return INPUT_OUTPUT.get(registeredInput);
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getSecondOutputFromInput(ItemStack stack) {
		if (isInputRegistered(stack) && hasSecondOutput(stack)) {
			for (ItemStack registeredOutput2 : INPUT_OUTPUT2.keySet()) {
				if (compareStacks(registeredOutput2, stack)) {
					return INPUT_OUTPUT2.get(registeredOutput2);
				}
			}
		}
		return null;
	}

	public boolean compareStacks(ItemStack stack1, ItemStack stack2) {
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

	public int getInputCount(ItemStack stack) {
		return isInputRegistered(stack) ? getInput(stack).stackSize : -1;
	}

	public int getOutputCount(ItemStack stack) {
		return isInputRegistered(stack) ? getOutputFromInput(stack).stackSize : -1;
	}

	public int getSecondOutputCount(ItemStack stack) {
		return isInputRegistered(stack) && hasSecondOutput(stack) ? getSecondOutputFromInput(stack).stackSize : -1;
	}
	public int getThirdOutputCount(ItemStack stack) {
		return isInputRegistered(stack) && hasThirdOutput(stack) ? getThirdOutputFromInput(stack).stackSize : -1;
	}

	public int getFourthOutputCount(ItemStack stack) {
		return isInputRegistered(stack) && hasFourthOutput(stack) ? getFourthOutputFromInput(stack).stackSize : -1;
	}
	public int getFifthOutputCount(ItemStack stack) {
		return isInputRegistered(stack) && hasFifthOutput(stack) ? getFifthOutputFromInput(stack).stackSize : -1;
	}
	


	@Override
	public List<ItemStack> getThirdOutputList() {
		return Arrays.asList(INPUT_OUTPUT3.values().toArray(new ItemStack[INPUT_OUTPUT3.size()]));
	}

	@Override
	public List<ItemStack> getFourthOutputList() {
		return Arrays.asList(INPUT_OUTPUT4.values().toArray(new ItemStack[INPUT_OUTPUT4.size()]));
	}

	@Override
	public List<ItemStack> getFifthOutputList() {
		return Arrays.asList(INPUT_OUTPUT5.values().toArray(new ItemStack[INPUT_OUTPUT5.size()]));
	}

	@Override
	public boolean hasThirdOutput(ItemStack stack) {
		for (ItemStack registeredOutput3 : INPUT_OUTPUT3.keySet()) {
			if (compareStacks(registeredOutput3, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasFourthOutput(ItemStack stack) {
		for (ItemStack registeredOutput4 : INPUT_OUTPUT4.keySet()) {
			if (compareStacks(registeredOutput4, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasFifthOutput(ItemStack stack) {
		for (ItemStack registeredOutput5 : INPUT_OUTPUT5.keySet()) {
			if (compareStacks(registeredOutput5, stack)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public double getThirdOutputChance(ItemStack stack) {
		if (isInputRegistered(stack) && hasThirdOutput(stack)) {
			for (ItemStack registeredOutput3 : INPUT_OUTPUT3_CHANCE.keySet()) {
				if (compareStacks(registeredOutput3, stack)) {
					return INPUT_OUTPUT3_CHANCE.get(registeredOutput3);
				}
			}
		}
		return 0.0D;
	}

	@Override
	public double getFourthOutputChance(ItemStack stack) {
		if (isInputRegistered(stack) && hasFourthOutput(stack)) {
			for (ItemStack registeredOutput4 : INPUT_OUTPUT4_CHANCE.keySet()) {
				if (compareStacks(registeredOutput4, stack)) {
					return INPUT_OUTPUT4_CHANCE.get(registeredOutput4);
				}
			}
		}
		return 0.0D;
	}

	@Override
	public double getFifthOutputChance(ItemStack stack) {
		if (isInputRegistered(stack) && hasFifthOutput(stack)) {
			for (ItemStack registeredOutput5 : INPUT_OUTPUT5_CHANCE.keySet()) {
				if (compareStacks(registeredOutput5, stack)) {
					return INPUT_OUTPUT5_CHANCE.get(registeredOutput5);
				}
			}
		}
		return 0.0D;
	}

	@Override
	public ItemStack getThirdOutputFromInput(ItemStack stack) {
		if (isInputRegistered(stack) && hasThirdOutput(stack)) {
			for (ItemStack registeredOutput3 : INPUT_OUTPUT3.keySet()) {
				if (compareStacks(registeredOutput3, stack)) {
					return INPUT_OUTPUT3.get(registeredOutput3);
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getFourthOutputFromInput(ItemStack stack) {
		if (isInputRegistered(stack) && hasFourthOutput(stack)) {
			for (ItemStack registeredOutput4 : INPUT_OUTPUT4.keySet()) {
				if (compareStacks(registeredOutput4, stack)) {
					return INPUT_OUTPUT4.get(registeredOutput4);
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getFifthOutputFromInput(ItemStack stack) {
		if (isInputRegistered(stack) && hasFifthOutput(stack)) {
			for (ItemStack registeredOutput5 : INPUT_OUTPUT5.keySet()) {
				if (compareStacks(registeredOutput5, stack)) {
					return INPUT_OUTPUT5.get(registeredOutput5);
				}
			}
		}
		return null;
	}
}