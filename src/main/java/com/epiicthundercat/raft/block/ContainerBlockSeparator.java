package com.epiicthundercat.raft.block;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBlockSeparator extends Container {
	IInventory tileSeperator;
	private int cookTime;
	private int totalCookTime;

	public ContainerBlockSeparator(InventoryPlayer playerInv, IInventory SepInv) {
		super();
		// InputSlot
		this.tileSeperator = SepInv;
		addSlotToContainer(new Slot(SepInv, 0, 8, 10));
		// FuelSlot
		addSlotToContainer(new SlotFurnaceFuel(SepInv, 1, 61, 42));
		// OutputSlots
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 2, 117, 9));
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 3, 117, 30));
		// Topfirstof2
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 4, 140, 9));
		// BottomFirstof2
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 5, 140, 30));
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 6, 117, 50));
		// bucketoutput
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 7, 8, 56));
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {

		return true;
	}
	
	 public void addListener(IContainerListener listener)
	    {
	        super.addListener(listener);
	        listener.sendAllWindowProperties(this, this.tileSeperator);
	    }
	 
	 
	 public void detectAndSendChanges()
	    {
	        super.detectAndSendChanges();

	        for (int i = 0; i < this.listeners.size(); ++i)
	        {
	            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

	            if (this.cookTime != this.tileSeperator.getField(0))
	            {
	                icontainerlistener.sendProgressBarUpdate(this, 0, this.tileSeperator.getField(0));
	            }

	            if (this.totalCookTime != this.tileSeperator.getField(1))
	            {
	                icontainerlistener.sendProgressBarUpdate(this, 1, this.tileSeperator.getField(1));
	            }
	        }

	        this.cookTime = this.tileSeperator.getField(0);
	        this.totalCookTime = this.tileSeperator.getField(1);
	    }

	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (index != 1 && index != 0) {
				if (FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (TileEntityFurnace.isItemFuel(itemstack1)) {
					if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
						return null;
					}
				} else if (index >= 3 && index < 30) {
					if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
						return null;
					}
				} else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(playerIn, itemstack1);
		}

		return itemstack;
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileSeperator.setField(id, data);
	}

}
