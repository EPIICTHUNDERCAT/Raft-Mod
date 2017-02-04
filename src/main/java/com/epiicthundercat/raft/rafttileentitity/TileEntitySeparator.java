package com.epiicthundercat.raft.rafttileentitity;

import com.epiicthundercat.raft.registry.recipe.RecipeSeparator;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class TileEntitySeparator extends TileEntity implements ITickable {

	private IItemHandlerModifiable INVENTORY;
	private ISidedInventory INVENTORY_NONWRAPPED;

	private boolean IS_COOKING = false;

	public ItemStack CURRENT_STACK = null;
	private boolean maintainLight = false;
	private final String TAG_SLOT = "Slot";
	private final String TAG_NAME = "Separator";
	private final String TAG_INVENTORY = "Inventory";

	private final String TAG_ISCOOKING = "IsCooking";

	private final String TAG_CURRENT_STACK = "CurrentStack";

	public TileEntitySeparator() {
		super();
		INVENTORY_NONWRAPPED = new SeparatorInventory(this);

	}

	public IItemHandlerModifiable getInventory() {
		return INVENTORY;
	}

	public ISidedInventory getInventoryNonWrapped() {
		return INVENTORY_NONWRAPPED;
	}

	public boolean maintainLight() {
		return maintainLight;
	}

	public void setLighting(boolean isLit) {
		maintainLight = isLit;
	}

	public ItemStack getInputStack() {
		return INVENTORY.getStackInSlot(0);
	}

	public void setInputStack(ItemStack stack) {
		INVENTORY.setStackInSlot(0, stack);
	}

	public void decrInputStack(int amount) {
		if (getInputStack() != null && amount > 0) {
			int stackSize = getInputStack().stackSize;
			int newStackSize = stackSize - amount;
			if (newStackSize <= 0) {
				INVENTORY.setStackInSlot(0, null);
				return;
			}
			ItemStack newStack = getInputStack().copy();
			newStack.stackSize -= amount;
			setInputStack(newStack);
			markDirty();
		}
	}

	public ItemStack getOutputStack() {
		return INVENTORY.getStackInSlot(2);
	}

	public void setOutputStack(ItemStack stack) {
		INVENTORY.setStackInSlot(2, stack);
		INVENTORY.setStackInSlot(3, stack);
		INVENTORY.setStackInSlot(4, stack);
		INVENTORY.setStackInSlot(5, stack);
		INVENTORY.setStackInSlot(6, stack);
	}

	public void appendOutputStack(int amount) {
		if (getOutputStack() != null && amount > 0) {
			ItemStack newStack = getOutputStack().copy();
			newStack.stackSize += amount;
			setOutputStack(newStack);
		}
	}

	public ItemStack getSecondaryStack() {
		return INVENTORY.getStackInSlot(2);
	}

	public void setSecondaryStack(ItemStack stack) {
		INVENTORY.setStackInSlot(2, stack);
	}

	public void appendSecondaryStack(int amount) {
		if (getSecondaryStack() != null && amount > 0) {
			ItemStack newStack = getSecondaryStack().copy();
			newStack.stackSize += amount;
			setSecondaryStack(newStack);
		}
	}
	public ItemStack getThirdStack() {
		return INVENTORY.getStackInSlot(3);
	}

	public void setThirdStack(ItemStack stack) {
		INVENTORY.setStackInSlot(3, stack);
	}

	public void appendThirdStack(int amount) {
		if (getSecondaryStack() != null && amount > 0) {
			ItemStack newStack = getThirdStack().copy();
			newStack.stackSize += amount;
			setThirdStack(newStack);
		}
	}
	public ItemStack getFourthStack() {
		return INVENTORY.getStackInSlot(4);
	}

	public void setFourthStack(ItemStack stack) {
		INVENTORY.setStackInSlot(4, stack);
	}

	public void appendFourthStack(int amount) {
		if (getSecondaryStack() != null && amount > 0) {
			ItemStack newStack = getFourthStack().copy();
			newStack.stackSize += amount;
			setFourthStack(newStack);
		}
	}
	public ItemStack getFifthStack() {
		return INVENTORY.getStackInSlot(5);
	}

	public void setFifthStack(ItemStack stack) {
		INVENTORY.setStackInSlot(5, stack);
	}

	public void appendFifthStack(int amount) {
		if (getSecondaryStack() != null && amount > 0) {
			ItemStack newStack = getFifthStack().copy();
			newStack.stackSize += amount;
			setFifthStack(newStack);
		}
	}
	public ItemStack getSixthStack() {
		return INVENTORY.getStackInSlot(6);
	}

	public void setSixthStack(ItemStack stack) {
		INVENTORY.setStackInSlot(6, stack);
	}

	public void appendSixthStack(int amount) {
		if (getSecondaryStack() != null && amount > 0) {
			ItemStack newStack = getSixthStack().copy();
			newStack.stackSize += amount;
			setSixthStack(newStack);
		}
	}
	public int getEnergyRequired(ItemStack stack) {
		return RecipeSeparator.getInstance().getEnergyRequired(stack);
	}

	public boolean isSeparating() {
		return IS_COOKING;
	}

	public void setSeparating(boolean isSeparating) {
		IS_COOKING = isSeparating;
		markDirty();
	}

	public void setCurrentStack(ItemStack stack) {
		decrInputStack(RecipeSeparator.getInstance().getInputCount(stack));
		CURRENT_STACK = stack;
	}

	public ItemStack getCurrentStack() {
		return CURRENT_STACK;
	}

	

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			RecipeSeparator recipes = RecipeSeparator.getInstance();
			if (!isSeparating()) {
				if (canSeparate()) {
					//setLighting(true);
					setSeparating(true);
					setCurrentStack(getInputStack());

				}
			} else {
				if (CURRENT_STACK == null) {

					setSeparating(false);
					setCurrentStack(null);

					update();
					return;
				}

			}
			update();
		}
	}

	public boolean canSeparate() {
		if (getInputStack() == null) {
			return false;
		}
		RecipeSeparator recipes = RecipeSeparator.getInstance();
		if (!recipes.isInputRegistered(getInputStack())) {
			return false; // failsafe..this shouldn't be possible
		}
		int inputCount = recipes.getInputCount(getInputStack());
		int inputSlotCount = getInputStack().stackSize;
		if (inputSlotCount < inputCount) {
			return false;
		}
		if (getOutputStack() != null) { // output slot contains items
			if (!getOutputStack().isItemEqual(recipes.getOutputFromInput(getInputStack()))) { // output
																								// of
																								// input
																								// item
																								// isn't
																								// same
																								// as
																								// items
																								// already
																								// in
																								// output
																								// slot
				return false;
			}
			int outputCount = recipes.getOutputCount(getInputStack());
			int maxOutputCount = recipes.getOutputFromInput(getInputStack()).getMaxStackSize();
			int outputSlotCount = getOutputStack().stackSize;
			if (outputSlotCount + outputCount > maxOutputCount) {
				return false;
			}
		}
		if (getSecondaryStack() != null) {
			if (recipes.hasSecondOutput(getInputStack())) {
				if (!getSecondaryStack().isItemEqual(recipes.getSecondOutputFromInput(getInputStack()))) {
					return false;
				}
				int secondCount = recipes.getSecondOutputCount(getInputStack());
				int maxSecondCount = recipes.getSecondOutputFromInput(getInputStack()).getMaxStackSize();
				int secondSlotCount = getSecondaryStack().stackSize;
				if (secondCount + secondSlotCount > maxSecondCount) {
					return false;
				}
			}
		}
		if (getThirdStack() != null) {
			if (recipes.hasThirdOutput(getInputStack())) {
				if (!getThirdStack().isItemEqual(recipes.getThirdOutputFromInput(getInputStack()))) {
					return false;
				}
				int thirdCount = recipes.getThirdOutputCount(getInputStack());
				int maxThirdCount = recipes.getThirdOutputFromInput(getInputStack()).getMaxStackSize();
				int thirdSlotCount = getThirdStack().stackSize;
				if (thirdCount + thirdSlotCount > maxThirdCount) {
					return false;
				}
			}
		}
		if (getFourthStack() != null) {
			if (recipes.hasFourthOutput(getInputStack())) {
				if (!getFourthStack().isItemEqual(recipes.getFourthOutputFromInput(getInputStack()))) {
					return false;
				}
				int fourthCount = recipes.getFourthOutputCount(getInputStack());
				int maxFourthCount = recipes.getFourthOutputFromInput(getInputStack()).getMaxStackSize();
				int fourthSlotCount = getFourthStack().stackSize;
				if (fourthCount + fourthSlotCount > maxFourthCount) {
					return false;
				}
			}
		}
		if (getFifthStack() != null) {
			if (recipes.hasFifthOutput(getInputStack())) {
				if (!getFifthStack().isItemEqual(recipes.getFifthOutputFromInput(getInputStack()))) {
					return false;
				}
				int fifthCount = recipes.getFifthOutputCount(getInputStack());
				int maxFifthCount = recipes.getFifthOutputFromInput(getInputStack()).getMaxStackSize();
				int fifthSlotCount = getFifthStack().stackSize;
				if (fifthCount + fifthSlotCount > maxFifthCount) {
					return false;
				}
			}
		}
		return true;
	}

	public void separateItem() {
		if (getCurrentStack() != null) {
			RecipeSeparator recipes = RecipeSeparator.getInstance();
			ItemStack outputStack = recipes.getOutputFromInput(getCurrentStack());
			if (getOutputStack() == null) {
				setOutputStack(outputStack.copy());
			} else if (getOutputStack() != null && getOutputStack().getItem() == outputStack.getItem()) {
				appendOutputStack(recipes.getOutputCount(getCurrentStack()));
			}
			if (recipes.hasSecondOutput(getInputStack())) {
				double chance = recipes.getSecondOutputChance(getInputStack());
				double r = getWorld().rand.nextDouble();
				if (r <= chance) {
					ItemStack secondaryStack = recipes.getSecondOutputFromInput(getInputStack());
					if (getSecondaryStack() == null) {
						setSecondaryStack(secondaryStack.copy());
					} else if (getSecondaryStack().getItem() == secondaryStack.getItem()) {
						appendSecondaryStack(recipes.getSecondOutputCount(getInputStack()));
					}
				}
			}
			if (recipes.hasThirdOutput(getInputStack())) {
				double chance = recipes.getThirdOutputChance(getInputStack());
				double r = getWorld().rand.nextDouble();
				if (r <= chance) {
					ItemStack thirdStack = recipes.getThirdOutputFromInput(getInputStack());
					if (getThirdStack() == null) {
						setThirdStack(thirdStack.copy());
					} else if (getThirdStack().getItem() == thirdStack.getItem()) {
						appendThirdStack(recipes.getThirdOutputCount(getInputStack()));
					}
				}
			}
			if (recipes.hasFourthOutput(getInputStack())) {
				double chance = recipes.getFourthOutputChance(getInputStack());
				double r = getWorld().rand.nextDouble();
				if (r <= chance) {
					ItemStack fourthStack = recipes.getFourthOutputFromInput(getInputStack());
					if (getFourthStack() == null) {
						setFourthStack(fourthStack.copy());
					} else if (getFourthStack().getItem() == fourthStack.getItem()) {
						appendFourthStack(recipes.getFourthOutputCount(getInputStack()));
					}
				}
			}
			if (recipes.hasFifthOutput(getInputStack())) {
				double chance = recipes.getFifthOutputChance(getInputStack());
				double r = getWorld().rand.nextDouble();
				if (r <= chance) {
					ItemStack FifthStack = recipes.getFifthOutputFromInput(getInputStack());
					if (getFifthStack() == null) {
						setFifthStack(FifthStack.copy());
					} else if (getFifthStack().getItem() == FifthStack.getItem()) {
						appendFifthStack(recipes.getFifthOutputCount(getInputStack()));
					}
				}
			}
			setSeparating(false);
			setCurrentStack(null);

		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagCompound nbt = compound.getCompoundTag(TAG_NAME);

		IS_COOKING = nbt.getBoolean(TAG_ISCOOKING);

		CURRENT_STACK = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag(TAG_CURRENT_STACK));
		NBTTagList tagList = nbt.getTagList(TAG_INVENTORY, 10);
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound slotNBT = tagList.getCompoundTagAt(i);
			if (slotNBT != null) {
				INVENTORY.setStackInSlot(slotNBT.getInteger(TAG_SLOT), ItemStack.loadItemStackFromNBT(slotNBT));
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagList nbtList = new NBTTagList();
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setBoolean(TAG_ISCOOKING, IS_COOKING);

		if (CURRENT_STACK != null) {
			nbt.setTag(TAG_CURRENT_STACK, CURRENT_STACK.writeToNBT(new NBTTagCompound()));
		}
		for (int i = 0; i < INVENTORY.getSlots(); i++) {
			if (INVENTORY.getStackInSlot(i) != null) {
				NBTTagCompound slotNBT = new NBTTagCompound();
				slotNBT.setInteger(TAG_SLOT, i);
				INVENTORY.getStackInSlot(i).writeToNBT(slotNBT);
				nbtList.appendTag(slotNBT);
			}
		}
		nbt.setTag(TAG_INVENTORY, nbtList);
		compound.setTag(TAG_NAME, nbt);
		return super.writeToNBT(compound);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			// return
			// CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(INVENTORY);
			return (T) INVENTORY;
		}
		return super.getCapability(capability, facing);
	}

	public static class SeparatorInventory implements ISidedInventory {
		private ItemStack[] inventory = new ItemStack[3];
		TileEntitySeparator te;

		public SeparatorInventory(TileEntitySeparator tileIn) {
			te = tileIn;
		}

		@Override
		public int getSizeInventory() {
			return inventory.length;
		}

		@Override
		public ItemStack getStackInSlot(int index) {
			return inventory[index];
		}

		@Override
		public ItemStack decrStackSize(int index, int count) {
			ItemStack stack = ItemStackHelper.getAndSplit(inventory, index, count);
			markDirty();
			return stack;
		}

		@Override
		public ItemStack removeStackFromSlot(int index) {
			ItemStack stack = ItemStackHelper.getAndRemove(inventory, index);
			markDirty();
			return stack;
		}

		@Override
		public void setInventorySlotContents(int index, ItemStack stack) {
			boolean flag = stack != null && stack.isItemEqual(inventory[index])
					&& ItemStack.areItemStackTagsEqual(stack, inventory[index]);
			inventory[index] = stack;
			if (stack != null && stack.stackSize > getInventoryStackLimit()) {
				stack.stackSize = getInventoryStackLimit();
			}
			markDirty();
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public boolean isUseableByPlayer(EntityPlayer player) {
			return true;
		}

		@Override
		public void openInventory(EntityPlayer player) {
		}

		@Override
		public void closeInventory(EntityPlayer player) {
		}

		@Override
		public boolean isItemValidForSlot(int index, ItemStack stack) {
			RecipeSeparator recipes = RecipeSeparator.getInstance();
			if (index == 0) {
				if (te.getInputStack() == null && te.getOutputStack() == null && te.getSecondaryStack() == null
						&& te.getCurrentStack() == null) { // completely empty
															// and not
															// processing
					return recipes.isInputRegistered(stack);
				} else if (te.getCurrentStack() != null) { // processing
					if (te.getInputStack() == null && te.getOutputStack() == null && te.getSecondaryStack() == null) { // all
																														// slots
																														// empty
						return recipes.isInputRegistered(stack) && recipes.compareStacks(te.getCurrentStack(), stack);
					} else if (te.getInputStack() != null && te.getOutputStack() == null
							&& te.getSecondaryStack() == null) { // only input
																	// has items
						return recipes.isInputRegistered(stack) && recipes.compareStacks(te.getCurrentStack(), stack)
								&& recipes.compareStacks(stack, te.getInputStack());
					} else if (te.getInputStack() == null && te.getOutputStack() == null
							&& te.getSecondaryStack() != null) { // only
																	// secondary
																	// has items
						if (recipes.isInputRegistered(stack) && !recipes.hasSecondOutput(stack)) { // if
																									// this
																									// input
																									// has
																									// no
																									// 2nd
																									// output,
																									// then
																									// let
																									// it
																									// process
							return true;
						} else {
							return recipes.isInputRegistered(stack) && recipes
									.compareStacks(recipes.getSecondOutputFromInput(stack), te.getSecondaryStack());
						}
					} else if (te.getInputStack() == null && te.getOutputStack() != null
							&& te.getSecondaryStack() == null) { // only output
																	// has items
						return recipes.compareStacks(stack, recipes.getInputFromOutput(te.getSecondaryStack()));
					}
				} else { // not processing
					if (te.getInputStack() != null && te.getOutputStack() == null && te.getSecondaryStack() == null) { // only
																														// input
																														// has
																														// items
						return recipes.isInputRegistered(stack) && recipes.compareStacks(stack, te.getInputStack());
					} else if (te.getInputStack() == null && te.getOutputStack() != null
							&& te.getSecondaryStack() == null) { // only output
																	// has items
						return recipes.compareStacks(recipes.getOutputFromInput(stack), te.getOutputStack());
					} else if (te.getInputStack() == null && te.getOutputStack() == null
							&& te.getSecondaryStack() != null) { // only
																	// secondary
																	// has items
						if (recipes.isInputRegistered(stack) && !recipes.hasSecondOutput(stack)) { // if
																									// this
																									// input
																									// has
																									// no
																									// 2nd
																									// output,
																									// then
																									// let
																									// it
																									// process
							return true;
						} else {
							return recipes.isInputRegistered(stack) && recipes
									.compareStacks(recipes.getSecondOutputFromInput(stack), te.getSecondaryStack());
						}
					} else if (te.getInputStack() != null && te.getOutputStack() == null
							&& te.getSecondaryStack() != null) {
						return recipes.getSecondOutputFromInput(te.getInputStack()) == null || recipes.compareStacks(
								recipes.getSecondOutputFromInput(te.getInputStack()), te.getSecondaryStack());
					} else if (te.getInputStack() == null && te.getOutputStack() != null
							&& te.getSecondaryStack() != null) {
						return recipes.isInputRegistered(stack)
								&& recipes.compareStacks(stack, recipes.getInputFromOutput(te.getOutputStack()));
					} else if (te.getInputStack() != null && te.getOutputStack() != null
							&& te.getSecondaryStack() != null) {
						return recipes.compareStacks(stack, te.getInputStack());
					}
				}
			}
			if (index == 1) {
				return RecipeSeparator.getInstance().isOutputRegistered(stack);
			}
			if (index == 2) {
				return RecipeSeparator.getInstance().isSecondOutputRegistered(stack);
			}
			return false;
		}

		@Override
		public int getField(int id) {
			return 0;
		}

		@Override
		public void setField(int id, int value) {
		}

		@Override
		public int getFieldCount() {
			return 0;
		}

		@Override
		public void clear() {
			inventory = new ItemStack[3];
			markDirty();
		}

		@Override
		public String getName() {
			return "compressor";
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public ITextComponent getDisplayName() {
			return new TextComponentString(I18n.format(getName()));
		}

		@Override
		public int[] getSlotsForFace(EnumFacing side) {
			return new int[] { 0, 1, 2 };
		}

		@Override
		public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
			return index == 0 && RecipeSeparator.getInstance().isInputRegistered(itemStackIn);
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
			return index > 0;
		}

		@Override
		public void markDirty() {
			if (te != null) {
				te.markDirty();
			}
		}
	}

}