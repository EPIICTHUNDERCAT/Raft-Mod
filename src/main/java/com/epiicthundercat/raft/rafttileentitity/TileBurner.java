package com.epiicthundercat.raft.rafttileentitity;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.block.BlockBurner;

import net.minecraft.block.Block;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileBurner extends TileEntity implements ITickable {
	public int cookTimeRemaining;
	public int totalTime = 80; // change to the value you want later
	BlockBurner burner;
	public ItemStackHandler items = new ItemStackHandler(3) {
		protected int getStackLimit(int slot, ItemStack stack) {
			return 1;
		}

		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if ((stack != null) && ((stack.getItem() instanceof ItemFood))
					&& (FurnaceRecipes.instance().getSmeltingResult(stack) != null)
					&& ((FurnaceRecipes.instance().getSmeltingResult(stack).getItem() instanceof ItemFood))) {
				return super.insertItem(slot, stack, simulate);
			}
			return stack;
		}

		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			TileBurner.this.markDirty();

			IBlockState state = TileBurner.this.world.getBlockState(TileBurner.this.pos);
			TileBurner.this.world.notifyBlockUpdate(TileBurner.this.pos, state, state, 3);
			TileBurner.this.markDirty();
		}
	};

	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		writeToNBT(tag);
		return new SPacketUpdateTileEntity(this.pos, 0, tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		writeToNBT(nbtTagCompound);
		return nbtTagCompound;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onDataPacket(NetworkManager networkManager, SPacketUpdateTileEntity s35PacketUpdateTileEntity) {
		readFromNBT(s35PacketUpdateTileEntity.getNbtCompound());
		world.markBlockRangeForRenderUpdate(this.pos, this.pos);
		this.world.notifyBlockUpdate(this.pos, world.getBlockState(this.pos), world.getBlockState(this.pos), 3);
	}

	@Override
	public void update() {

		if (!this.world.isRemote) {
			/*
			 * if (this.burner.BURNING == null) { if (canCookAnything()) {
			 * return; } } else if (this.burner.BURNING != null) {
			 */
			if (canCookAnything()) {
				this.cookTimeRemaining++;
				if (this.cookTimeRemaining >= totalTime) {
					this.cookTimeRemaining = 0;
					for (int i = 0; i < inventory().getSlots(); i++) {
						ItemStack stack = this.items.getStackInSlot(i);
						ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack).copy();
						this.items.setStackInSlot(i, result);
					}
				}
			} else if (this.cookTimeRemaining > 0) {
				this.cookTimeRemaining = 0;
			}
			// }

		}
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
		return this.getCapability(capability, facing) != null;
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
		compound.setInteger("TotalTime", (short) this.totalTime);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if (compound.hasKey("Items")) {
			this.items.deserializeNBT((NBTTagCompound) compound.getTag("Items"));
		}
		this.cookTimeRemaining = compound.getInteger("CookTime");
		this.totalTime = compound.getInteger("TotalTime");
	}

	public IItemHandler inventory() {
		return this.items;
	}
}
