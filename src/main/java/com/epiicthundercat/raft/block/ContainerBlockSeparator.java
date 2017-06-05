package com.epiicthundercat.raft.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBlockSeparator extends Container {

	private final IInventory tileSeparator;
	private int cookTime;
	private int totalCookTime;
	private int furnaceBurnTime;
	private int currentItemBurnTime;

	public ContainerBlockSeparator(InventoryPlayer playerInv, IInventory SepInv) {
		super();
		// InputSlot
		this.tileSeparator = SepInv;
		addSlotToContainer(new Slot(SepInv, 0, 8, 10));
		// FuelSlot
		addSlotToContainer(new SlotFurnaceFuel(SepInv, 1, 61, 33));
		// OutputSlots
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 2, 124, 12));
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 3, 124, 33));
		// Topfirstof2
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 4, 124, 54));
		// BottomFirstof2
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 5, 103, 33));
		addSlotToContainer(new SlotFurnaceOutput(playerInv.player, SepInv, 6, 145, 33));
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

	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileSeparator);
	}

	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		 for (int i = 0; i < this.listeners.size(); ++i)
	        {
	            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

	            if (this.cookTime != this.tileSeparator.getField(2))
	            {
	                icontainerlistener.sendProgressBarUpdate(this, 2, this.tileSeparator.getField(2));
	            }

	            if (this.furnaceBurnTime != this.tileSeparator.getField(0))
	            {
	                icontainerlistener.sendProgressBarUpdate(this, 0, this.tileSeparator.getField(0));
	            }

	            if (this.currentItemBurnTime != this.tileSeparator.getField(1))
	            {
	                icontainerlistener.sendProgressBarUpdate(this, 1, this.tileSeparator.getField(1));
	            }

	            if (this.totalCookTime != this.tileSeparator.getField(3))
	            {
	                icontainerlistener.sendProgressBarUpdate(this, 3, this.tileSeparator.getField(3));
	            }
	        }

	        this.cookTime = this.tileSeparator.getField(2);
	        this.furnaceBurnTime = this.tileSeparator.getField(0);
	        this.currentItemBurnTime = this.tileSeparator.getField(1);
	        this.totalCookTime = this.tileSeparator.getField(3);
	}

	

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.tileSeparator.setField(id, data);
	}

}
