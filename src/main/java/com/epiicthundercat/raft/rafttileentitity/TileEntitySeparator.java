package com.epiicthundercat.raft.rafttileentitity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntitySeparator extends TileEntity implements ITickable, IInventory {
	private int cookTime;
	private int totalCookTime;
	//private NonNullList<ItemStack> separatorInv = NonNullList.<ItemStack>func_191197_a(2, ItemStack.field_190927_a);
	
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	    {
	        return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	    {
	        super.readFromNBT(nbt);
	      /*  NBTTagList inventory = (NBTTagList) nbt.getTag("Items");
			for(int i = 0; i < inventory.tagCount(); i++) {
					NBTTagCompound itemTag = inventory.getCompoundTagAt(i);
					ItemStack stack = new ItemStack(itemTag);
					pulverizerInv.set(itemTag.getByte("Slot"), stack);
				}
	this.cookTime = nbt.getInteger("CookTime");
	this.totalCookTime = nbt.getInteger("TotalCookTime");
	storage.readFromNBT(nbt);*/
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean hasCustomName() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ItemStack getStackInSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItemStack decrStackSize(int index, int count) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ItemStack removeStackFromSlot(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}