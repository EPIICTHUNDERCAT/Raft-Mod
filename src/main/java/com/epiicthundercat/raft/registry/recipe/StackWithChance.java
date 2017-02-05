package com.epiicthundercat.raft.registry.recipe;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class StackWithChance {
	
	private ItemStack stack;
	private int chance;
	
	public StackWithChance(ItemStack stack_, int chance_){
		stack = stack_;
		chance = chance_;
		System.out.println("Constructing StackWithChance with stack " + stack_.toString() + " and chance " + chance_);
	}
	
	public StackWithChance(Item stack_, int chance_){
		this(new ItemStack(stack_, 1, 0), chance_);
		
	}
	
	public StackWithChance(Item stack_){
		this(new ItemStack(stack_, 1, 0), 1);
	}
	
	public ItemStack getStack(){
		return stack;
	}
	
	public int getChance(){
		return chance;
	}
	
	
}
