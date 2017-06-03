package com.epiicthundercat.raft.rafttileentitity;

import java.util.List;

import com.epiicthundercat.raft.registry.recipe.RecipeSeparator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import toughasnails.handler.PacketHandler;
import toughasnails.init.ModBlocks;
import toughasnails.init.ModItems;

public class TileEntitySeparator extends RTileEntityInvBase implements ITickable, ISidedInventory {

	// public EnergyStorage storage = new EnergyStorage(50000);
	private ItemStack[] pulverizerInv = List.<ItemStack>withSize(6, ItemStack.EMPTY);
	public int cookTime;
	public int totalCookTime;
	

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList inventory = (NBTTagList) nbt.getTag("Items");
		for (int i = 0; i < inventory.tagCount(); i++) {
			NBTTagCompound itemTag = inventory.getCompoundTagAt(i);
			ItemStack stack = new ItemStack(itemTag);
			pulverizerInv.set(itemTag.getByte("Slot"), stack);
		}
		this.cookTime = nbt.getInteger("CookTime");
		this.totalCookTime = nbt.getInteger("TotalCookTime");
		storage.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList inventory = new NBTTagList();
		for (byte slot = 0; slot < pulverizerInv.size(); slot++) {
			ItemStack stack = pulverizerInv.get(slot);
			if (stack != null) {
				NBTTagCompound itemTag = new NBTTagCompound();
				stack.writeToNBT(itemTag);
				itemTag.setByte("Slot", slot);
				inventory.appendTag(itemTag);
			}
		}
		nbt.setTag("Items", inventory);
		nbt.setInteger("cookTime", (short) this.cookTime);
		nbt.setInteger("TotalCookTime", (short) this.totalCookTime);

	}

	public boolean canInteractWith(EntityPlayer playerIn) {
		return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
	}

	private net.minecraftforge.items.IItemHandler itemHandler;

	protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
		return new net.minecraftforge.items.wrapper.InvWrapper(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	@javax.annotation.Nullable
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability,
			@javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
		if (capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) (itemHandler == null ? (itemHandler = createUnSidedHandler()) : itemHandler);
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability,
			@javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
		return capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
				|| super.hasCapability(capability, facing);
	}

	@Override
	public int getSizeInventory() {
		return this.pulverizerInv.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.pulverizerInv.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.pulverizerInv, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.pulverizerInv, index);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return this.cookTime;
		case 1:
			return this.totalCookTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			this.cookTime = value;
			break;
		case 1:
			this.totalCookTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	@Override
	public void clear() {
		this.pulverizerInv.clear();
	}

	@Override
	public String getName() {
		return ModBlocks.Pulverizer.getUnlocalizedName();
	}

	@Override
	public boolean canInsertItem(int index, net.minecraft.item.ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, net.minecraft.item.ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN && index == 1) {
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}
		return true;
	}

	public boolean canPulverize() {
		if (((ItemStack) this.pulverizerInv.get(0)) == null) {
			return false;
		} else {

			ItemStack itemstack = RecipeSeparator.instance()
					.getPulverizingResult((ItemStack) this.pulverizerInv.get(0));

			if (itemstack == null) {
				return false;
			} else {
				ItemStack itemstack1 = (ItemStack) this.pulverizerInv.get(1);
				if (itemstack1 == null)
					return true;
				if (!itemstack1.isItemEqual(itemstack))
					return false;
				int result = itemstack1.getMaxStackSize() + itemstack.getMaxStackSize();
				return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}

	@Override
	public void update() {
		if (!worldObj.isRemote) {
			PacketHandler.INSTANCE.sendToAll(new MessageTEUpdate(this));
		}

		boolean flag1 = false;
		if (!this.worldObj.isRemote) {
			ItemStack itemstack = (ItemStack) this.pulverizerInv.get(1);
			if (storage.getEnergyStored() > energyUsage
					|| itemstack != null && ((ItemStack) this.pulverizerInv.get(0)) != null) {
				if (this.canPulverize()) {
					if (storage.getEnergyStored() > energyUsage) {
						flag1 = true;

						if (itemstack != null) {
							Item item = itemstack.getItem();
							--itemstack.stackSize;

							if (itemstack == null) {
								ItemStack item1 = item.getContainerItem(itemstack);
								this.pulverizerInv.set(1, item1);
							}
						}
					}TileEntityFurnace
				}

				if (storage.getEnergyStored() > energyUsage && this.canPulverize()) {
					++this.cookTime;

					if (this.cookTime == this.totalCookTime) {
						this.cookTime = 0;
						this.totalCookTime = this.getCookTime((ItemStack) this.pulverizerInv.get(0));
						this.PulverizeItem();
						flag1 = true;
					}
				} else {
					this.cookTime = 0;
				}
			} else if (!(storage.getEnergyStored() > energyUsage) && this.cookTime > 0) {
				this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
			}
		}

		if (flag1) {
			this.markDirty();
		}
	}

	public void PulverizeItem() {
		if (this.canPulverize()) {
			ItemStack itemstack = (ItemStack) this.pulverizerInv.get(0);
			ItemStack itemstack1 = RecipeSeparator.instance().getPulverizingResult(itemstack);
			ItemStack itemstack2 = (ItemStack) this.pulverizerInv.get(1);
			if (itemstack1 != null) {

				if (itemstack2 == null) {
					this.pulverizerInv.set(1, itemstack1.copy());
				} else if (itemstack2.getItem() == itemstack1.getItem()) {
					itemstack2.getMaxStackSize();
				}
				--itemstack.stackSize;
			}
		}

	}

	@Override
	public void setInventorySlotContents(int index, net.minecraft.item.ItemStack stack) {
		ItemStack itemstack = (ItemStack) this.pulverizerInv.get(index);
		boolean flag = stack != null && stack.isItemEqual(itemstack)
				&& ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.pulverizerInv.set(index, stack);

		if (stack.getMaxStackSize() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}

		if (index == 0 && !flag) {
			this.totalCookTime = this.getCookTime(stack);
			this.cookTime = 0;
			this.markDirty();
		}
	}

	public int getCookTime(ItemStack stack) {

		return 100;
	}

}

/*
 * private static final int[] SLOTS_TOP = new int[] { 0 }; private static final
 * int[] SLOTS_BOTTOM = new int[] { 2, 1 }; private static final int[]
 * SLOTS_SIDES = new int[] { 1 };
 * 
 * private ItemStack[] slot = new ItemStack[8];
 * 
 * private net.minecraftforge.items.IItemHandler itemHandler;
 * 
 * protected net.minecraftforge.items.IItemHandler createUnSidedHandler() {
 * return new net.minecraftforge.items.wrapper.InvWrapper(this); }
 * 
 * @SuppressWarnings("unchecked")
 * 
 * @Override
 * 
 * @javax.annotation.Nullable public <T> T
 * getCapability(net.minecraftforge.common.capabilities.Capability<T>
 * capability,
 * 
 * @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) { if
 * (capability ==
 * net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
 * return (T) (itemHandler == null ? (itemHandler = createUnSidedHandler()) :
 * itemHandler); return super.getCapability(capability, facing); }
 * 
 * @Override public boolean
 * hasCapability(net.minecraftforge.common.capabilities.Capability<?>
 * capability,
 * 
 * @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) { return
 * capability ==
 * net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ||
 * super.hasCapability(capability, facing); }
 * 
 * @Override public int getSizeInventory() { // TODO Auto-generated method stub
 * return 0; }
 * 
 * @Override public ItemStack getStackInSlot(int index) { // TODO Auto-generated
 * method stub return null; }
 * 
 * @Override public ItemStack decrStackSize(int index, int count) { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public ItemStack removeStackFromSlot(int index) { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public void setInventorySlotContents(int index, ItemStack stack) {
 * // TODO Auto-generated method stub
 * 
 * }
 * 
 * @Override public int getInventoryStackLimit() { // TODO Auto-generated method
 * stub return 0; }
 * 
 * @Override public boolean isUseableByPlayer(EntityPlayer player) { // TODO
 * Auto-generated method stub return false; }
 * 
 * @Override public void openInventory(EntityPlayer player) { // TODO
 * Auto-generated method stub
 * 
 * }
 * 
 * @Override public void closeInventory(EntityPlayer player) { // TODO
 * Auto-generated method stub
 * 
 * }
 * 
 * @Override public boolean isItemValidForSlot(int index, ItemStack stack) { //
 * TODO Auto-generated method stub return false; }
 * 
 * @Override public int getField(int id) { // TODO Auto-generated method stub
 * return 0; }
 * 
 * @Override public void setField(int id, int value) { // TODO Auto-generated
 * method stub
 * 
 * }
 * 
 * @Override public int getFieldCount() { // TODO Auto-generated method stub
 * return 0; }
 * 
 * @Override public void clear() { // TODO Auto-generated method stub
 * 
 * }
 * 
 * @Override public String getName() { // TODO Auto-generated method stub return
 * null; }
 * 
 * @Override public boolean hasCustomName() { // TODO Auto-generated method stub
 * return false; }
 * 
 * @Override public int[] getSlotsForFace(EnumFacing side) { // TODO
 * Auto-generated method stub return null; }
 * 
 * @Override public boolean canInsertItem(int index, ItemStack itemStackIn,
 * EnumFacing direction) { // TODO Auto-generated method stub return false; }
 * 
 * @Override public boolean canExtractItem(int index, ItemStack stack,
 * EnumFacing direction) { // TODO Auto-generated method stub return false; }
 * 
 * @Override public void update() { // TODO Auto-generated method stub
 * 
 * } }
 */
