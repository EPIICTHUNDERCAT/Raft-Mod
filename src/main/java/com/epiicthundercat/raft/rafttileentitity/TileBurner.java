package com.epiicthundercat.raft.rafttileentitity;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.block.BlockBurner;
import com.epiicthundercat.raft.init.RBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBurner extends TileEntity implements ITickable {
	public int cookTimeRemaining = 300;
	private static final int totalTime = 300;
	
	
	
	
	public ItemStackHandler items = new ItemStackHandler(3) {
		protected int getStackLimit(int slot, ItemStack stack) {
			return 1;
		}

		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			 if ((stack != null) && ((stack.getItem() instanceof ItemFood)) && 
				        (FurnaceRecipes.instance().getSmeltingResult(stack) != null) && 
				        ((FurnaceRecipes.instance().getSmeltingResult(stack).getItem() instanceof ItemFood))) {
				        return super.insertItem(slot, stack, simulate);
			}
			return stack;
		}

		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			TileBurner.this.markDirty();

			IBlockState state = TileBurner.this.world.getBlockState(TileBurner.this.pos);
			TileBurner.this.world.notifyBlockUpdate(TileBurner.this.pos, state, state, 3);
		}
	};

	public SPacketUpdateTileEntity func_189518_D_() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}

	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readFromNBT(packet.getNbtCompound());

		IBlockState state = this.world.getBlockState(this.pos);
		this.world.notifyBlockUpdate(this.pos, state, state, 3);
	}

	private boolean isCooking() {
		IBlockState down = this.world.getBlockState(this.pos.down());
		if ((down.getBlock() == RBlocks.burner)
				&& (((Boolean) down.getValue(BlockBurner.BURNING)).booleanValue())) {
			return true;
		}
		return false;
	}
@Override
	public void update() {
		if (!this.world.isRemote) {
			if ((isCooking()) && (canCookAnything())) {
				this.cookTimeRemaining--;
				if (this.cookTimeRemaining <= 0) {
					 for (int i = 0; i < inventory().getSlots(); i++)
			          {
			            ItemStack stack = this.items.getStackInSlot(i);
			            if (!getCookingResult(stack).isEmpty()) {
			              this.items.setStackInSlot(i, getCookingResult(stack));
			            }
			          }
					this.cookTimeRemaining = 300;
				}
				markDirty();
			} else if (this.cookTimeRemaining < 300) {
				this.cookTimeRemaining++;
				markDirty();
			}
		}
	}

	public static ItemStack getCookingResult(ItemStack stack) {
		return FurnaceRecipes.instance().getSmeltingResult(stack).copy();
	}

	private boolean canCookAnything() {
		for (int i = 0; i < inventory().getSlots(); i++) {
			if (!FurnaceRecipes.instance().getSmeltingResult(this.items.getStackInSlot(i)).copy().isEmpty()) {
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			 return (T) this.items;
		}
		return (T) super.getCapability(capability, facing);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setTag("Items", this.items.serializeNBT());
		compound.setInteger("CookTime", (short) this.cookTimeRemaining);
		return compound;
	}
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.items.deserializeNBT(compound.getCompoundTag("Items"));
		this.cookTimeRemaining = compound.getInteger("CookTime");
	}
	
	public IItemHandler inventory() {
		return this.items;
	}
}
