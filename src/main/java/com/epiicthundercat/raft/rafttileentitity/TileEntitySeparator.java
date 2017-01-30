package com.epiicthundercat.raft.rafttileentitity;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.init.RecipeHandler;
import com.epiicthundercat.raft.registry.SeparatorRecipeRegistry;
import com.epiicthundercat.raft.registry.recipe.IRecipeSeparator;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntitySeparator extends TileEntity implements ITickable, ISidedInventory {
	private int cookTime;
	private int totalCookTime;
	//slot amount-8
	private ItemStack[] separatorItemStacks = new ItemStack[8];

	@Override
	public void update() {
		 this.seperate();
	}

	@Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagList inventory = new NBTTagList();
        for (byte slot = 0; slot < separatorItemStacks.length; slot++) {
            if (this.separatorItemStacks[slot] != null) {
                NBTTagCompound itemTag = new NBTTagCompound();
                separatorItemStacks[slot].writeToNBT(itemTag);
                itemTag.setByte("Slot", slot);
                inventory.appendTag(itemTag);
            }
        }
        nbt.setTag("Items", inventory);
        return super.writeToNBT(nbt);
    }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList inventory = (NBTTagList) nbt.getTag("Items");
		for (int i = 0; i < inventory.tagCount(); i++) {
			NBTTagCompound itemTag = inventory.getCompoundTagAt(i);
			int j = itemTag.getByte("Slot");
			separatorItemStacks[j] = ItemStack.loadItemStackFromNBT(itemTag);
		}
		super.readFromNBT(nbt);
	}

	@Override
	public String getName() {

		return RBlocks.separator.getUnlocalizedName();
	}

	@Override
	public boolean hasCustomName() {

		return true;
	}

	@Override
	public int getSizeInventory() {
		return separatorItemStacks.length;
	}

	@Nullable
	@Override
	public ItemStack getStackInSlot(int index) {
		return separatorItemStacks[index];
	}

	@Nullable
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(separatorItemStacks, index, count);
	}

	@Nullable
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(separatorItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(separatorItemStacks[index])
				&& ItemStack.areItemStackTagsEqual(stack, separatorItemStacks[index]);
		separatorItemStacks[index] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
			stack.stackSize = this.getInventoryStackLimit();
		}

		if (index == 0 && !flag) {
			this.markDirty();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.pos) != this ? false
				: player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D,
						(double) this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// TODO Auto-generated method stub

	}
/*
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return true;
	}*/
	private IRecipeSeparator getSeparatorRecipe(ItemStack item) {
		if(item == null) return null;
		return SeparatorRecipeRegistry.getInstance().getRecipeForInputItem(item);
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
		for (int i = 0; i < this.separatorItemStacks.length; ++i) {
			this.separatorItemStacks[i] = null;
		}

	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		// TODO Auto-generated method stub
		return false;
	}
	
	  private boolean canSeperate()
	    {
	        if (this.separatorItemStacks[0] == null)
	        {
	            return false;
	        }
	        else
	        {/*
	            ItemStack itemstack = RecipeHandler.instance().getSeparatorResult(this.separatorItemStacks[0]);
	            if (itemstack == null) return false;
	            if (this.separatorItemStacks[2] == null) return true;
	            if (!this.separatorItemStacks[2].isItemEqual(itemstack)) return false;
	            if (this.separatorItemStacks[3] == null) return true;
	            if (!this.separatorItemStacks[3].isItemEqual(itemstack)) return false;
	            if (this.separatorItemStacks[4] == null) return true;
	            if (!this.separatorItemStacks[4].isItemEqual(itemstack)) return false;
	            if (this.separatorItemStacks[5] == null) return true;
	            if (!this.separatorItemStacks[5].isItemEqual(itemstack)) return false;
	            if (this.separatorItemStacks[6] == null) return true;
	            if (!this.separatorItemStacks[6].isItemEqual(itemstack)) return false;
	            if (this.separatorItemStacks[7] == null) return true;
	            if (!this.separatorItemStacks[7].isItemEqual(itemstack)) return false;
	            int result = separatorItemStacks[2].stackSize + itemstack.stackSize;

	            return result <= getInventoryStackLimit() && result <= this.separatorItemStacks[2].getMaxStackSize(); //Forge BugFix: Make it respect stack sizes properly.
	        }*/
	        	return true;
	        	}
	    }
	  public void seperate()
	    {
	        if (this.canSeperate())
	        {
	            ItemStack itemstack = RecipeHandler.instance().getSeparatorResult(this.separatorItemStacks[0]);

	            if (this.separatorItemStacks[2] == null)
	            {
	                //this.separatorItemStacks[2] = itemstack.copy();
	            }
	            else if (this.separatorItemStacks[2].getItem() == itemstack.getItem())
	            {
	                this.separatorItemStacks[2].stackSize += itemstack.stackSize; // Forge BugFix: Results may have multiple items
	            }

	            --this.separatorItemStacks[0].stackSize;

	            if (this.separatorItemStacks[0].stackSize <= 0)
	            {
	                this.separatorItemStacks[0] = null;
	            }
	        }
	    }
	  @Override
		public boolean isItemValidForSlot(final int slot, final ItemStack item) {
		  return true;
			/*switch(slot){
			case 0:
				return SeparatorRecipeRegistry.getInstance().getRecipeForInputItem(item) != null;
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
				return true;
			default:
				return false;
			}*/
		}
}