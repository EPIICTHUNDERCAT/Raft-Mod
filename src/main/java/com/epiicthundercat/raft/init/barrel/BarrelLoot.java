package com.epiicthundercat.raft.init.barrel;
/*
 * EllPeck's Treasure Code!
 */
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;

public class BarrelLoot extends WeightedRandom.Item{
	public final ItemStack returnItem;
    public final int minAmount;
    public final int maxAmount;

    public BarrelLoot(ItemStack returnItem, int chance, int minAmount, int maxAmount){
        super(chance);
        this.returnItem = returnItem;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }
}
