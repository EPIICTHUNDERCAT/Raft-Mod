package com.epiicthundercat.raft.rafttileentitity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.block.BlockSeparator;
import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.registry.recipe.RecipeSeparator;
import com.epiicthundercat.raft.registry.recipe.StackWithChance;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySeparator extends TileEntity implements ITickable, ISidedInventory {
	private static final int[] SLOTS_TOP = new int[] { 0 };
	private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
	private static final int[] SLOTS_SIDES = new int[] { 1 };
	private int separatorBurnTime;
	private int currentItemBurnTime;
	private int cookTime;
	private int totalCookTime;

	private String separatorCustomName;
	// slot amount8
	private ItemStack[] slot = new ItemStack[8];

	@Override
	public void update() {
		boolean flag = isBurning();
		boolean flag1 = false;

		if (isBurning()) {
			--separatorBurnTime;
		}

		if (!worldObj.isRemote) {
			if (isBurning() || slot[1] != null && slot[0] != null) {
				if (!isBurning() && (canSeparate() || canMultiSeparate())) {
					separatorBurnTime = getItemBurnTime(slot[1]);
					currentItemBurnTime = separatorBurnTime;

					if (isBurning()) {
						flag1 = true;

						if (slot[1] != null) {
							--slot[1].stackSize;

							if (slot[1].stackSize == 0) {
								slot[1] = slot[1].getItem().getContainerItem(slot[1]);
							}
						}
					}
				}

				if (isBurning()) {
					++cookTime;

					if (cookTime == totalCookTime && (canSeparate() || canMultiSeparate())) {
						cookTime = 0;
						totalCookTime = getCookTime(slot[0]);
						separate();
						flag1 = true;
					}
				} else {
					cookTime = 0;
				}
			} else if (!isBurning() && cookTime > 0) {
				cookTime = MathHelper.clamp_int(cookTime - 2, 0, totalCookTime);
			}

			if (flag != isBurning()) {
				flag1 = true;
				BlockSeparator.setState(isBurning(), worldObj, pos);
			}
		}

		if (flag1) {
			markDirty();
		}
	}

	public int getCookTime(@Nullable ItemStack stack) {
		return 100;
	}

	public static int getItemBurnTime(ItemStack stack) {
		if (stack == null) {
			return 0;
		} else {
			Item item = stack.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.WOODEN_SLAB) {
					return 150;
				}

				if (block.getDefaultState().getMaterial() == Material.WOOD) {
					return 300;
				}

				if (block == Blocks.COAL_BLOCK) {
					return 16000;
				}
			}

			if (item instanceof ItemTool && "WOOD".equals(((ItemTool) item).getToolMaterialName()))
				return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword) item).getToolMaterialName()))
				return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe) item).getMaterialName()))
				return 200;
			if (item == Items.STICK)
				return 100;
			if (item == Items.COAL)
				return 1600;
			if (item == Items.LAVA_BUCKET)
				return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING))
				return 100;
			if (item == Items.BLAZE_ROD)
				return 2400;
			return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {

		super.writeToNBT(compound);
		compound.setInteger("BurnTime", separatorBurnTime);
		compound.setInteger("CookTime", cookTime);
		compound.setInteger("CookTimeTotal", totalCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < slot.length; ++i) {
			if (slot[i] != null) {
				NBTTagCompound nbttagcompound = new NBTTagCompound();
				nbttagcompound.setByte("Slot", (byte) i);
				slot[i].writeToNBT(nbttagcompound);
				nbttaglist.appendTag(nbttagcompound);
			}
		}

		compound.setTag("Items", nbttaglist);

		if (hasCustomName()) {
			compound.setString("CustomName", separatorCustomName);
		}

		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		NBTTagList nbttaglist = compound.getTagList("Items", 10);
		slot = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			int j = nbttagcompound.getByte("Slot");

			if (j >= 0 && j < slot.length) {
				slot[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
			}
		}

		separatorBurnTime = compound.getInteger("BurnTime");
		cookTime = compound.getInteger("CookTime");
		totalCookTime = compound.getInteger("CookTimeTotal");
		currentItemBurnTime = getItemBurnTime(slot[1]);

		if (compound.hasKey("CustomName", 8)) {
			separatorCustomName = compound.getString("CustomName");
		}
	}

	@Override
	public boolean hasCustomName() {
		return separatorCustomName != null && !separatorCustomName.isEmpty();
	}

	public void setCustomInventoryName(String p_145951_1_) {
		separatorCustomName = p_145951_1_;
	}

	public static void registerFixesSeparator(DataFixer fixer) {
		fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("Separator", new String[] { "Items" }));
	}

	@Override
	public String getName() {

		return RBlocks.separator.getUnlocalizedName();
	}

	public boolean isBurning() {
		return separatorBurnTime > 0;
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory) {
		return inventory.getField(0) > 0;
	}

	@Override
	public int getSizeInventory() {
		return slot.length;
	}

	@Nullable
	@Override
	public ItemStack getStackInSlot(int index) {
		return slot[index];
	}

	@Nullable
	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(slot, index, count);
	}

	@Nullable
	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(slot, index);

	}

	@Override
	public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
		boolean flag = stack != null && stack.isItemEqual(slot[index])
				&& ItemStack.areItemStackTagsEqual(stack, slot[index]);
		slot[index] = stack;

		if (stack != null && stack.stackSize > getInventoryStackLimit()) {
			stack.stackSize = getInventoryStackLimit();

		}

		if (index == 0 && !flag) {
			totalCookTime = getCookTime(stack);
			cookTime = 0;
			markDirty();

		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;

	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) != this ? false
				: player.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D,
						(double) pos.getZ() + 0.5D) <= 64.0D;

	}

	@Override
	public void openInventory(EntityPlayer player) {

	}

	@Override
	public void closeInventory(EntityPlayer player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return separatorBurnTime;
		case 1:
			return currentItemBurnTime;
		case 2:
			return cookTime;
		case 3:
			return totalCookTime;
		default:
			return 0;
		}

	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			separatorBurnTime = value;
			break;
		case 1:
			currentItemBurnTime = value;
			break;
		case 2:
			cookTime = value;
			break;
		case 3:
			totalCookTime = value;
		}

	}

	@Override
	public int getFieldCount() {

		return 4;

	}

	@Override
	public void clear() {
		for (int i = 0; i < slot.length; ++i) {
			slot[i] = null;

		}

	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {

		return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);

	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return isItemValidForSlot(index, itemStackIn);

	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (direction == EnumFacing.DOWN && index == 1) {
			Item item = stack.getItem();

			if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
				return false;
			}
		}

		return true;

	}

	private boolean canSeparate() {
		if (slot[0] == null) {
			return false;
		} else {
			ItemStack itemstack = RecipeSeparator.instance().getSeparatingResult(slot[0]);
			if (itemstack == null)
				return false;
			if (slot[2] == null)
				return true;
			if (!slot[2].isItemEqual(itemstack))
				return false;
			if (slot[3] == null)
				return true;
			if (!slot[3].isItemEqual(itemstack))
				return false;
			int result = slot[2].stackSize + itemstack.stackSize;
			return result <= getInventoryStackLimit() && result <= slot[2].getMaxStackSize();
		}

	}

	private boolean canMultiSeparate() {
		if (slot[0] == null)
			return false;
		else {
			if (allOutputSlotsEmpty())
				return true;

			if (!areOutputsFull(RecipeSeparator.instance().getMultiSeparatingResult(slot[0].getItem())))
				return true;
		}
		return false;

	}

	public boolean allOutputSlotsEmpty() {
		if (slot[2] == null && slot[3] == null && slot[4] == null && slot[5] == null && slot[6] == null) {
			// ("All output slots are empty");
			return true;
		}

		return false;
	}

	public boolean areOutputsFull(List<StackWithChance> list) {

		List<Boolean> slots = new ArrayList<Boolean>();
		boolean slot2 = false;
		boolean slot3 = false;
		boolean slot4 = false;
		boolean slot5 = false;
		boolean slot6 = false;
		slots.add(slot2);
		slots.add(slot3);
		slots.add(slot4);
		slots.add(slot5);
		slots.add(slot6);

		for (int k = list.size() - 1; k >= 0; k--) {
			if (list.get(k).getStack() != null && slot[k + 2] != null) {
				ItemStack stack = list.get(k).getStack();
				if (stack.stackSize + slot[k + 2].stackSize > slot[k + 2].getMaxStackSize()) {
					slots.set(k, true);
				}
			}

		}
		if (slots.get(0) == false && slots.get(1) == false && slots.get(2) == false && slots.get(3) == false
				&& slots.get(4) == false)
			return false;

		return true;
	}

	public void separate() {
		if (canSeparate()) {
			ItemStack itemstack = RecipeSeparator.instance()
					.getSeparatingResult(new ItemStack(slot[0].getItem(), 1, slot[0].getMetadata()));

			if (slot[2] == null) {
				slot[2] = itemstack.copy();
			} else if (slot[2].getItem() == itemstack.getItem()) {
				slot[2].stackSize += itemstack.stackSize;
			}
			if (slot[3] == null) {
				slot[3] = itemstack.copy();

			} else if (slot[3].getItem() == itemstack.getItem()) {
				slot[3].stackSize += itemstack.stackSize;
			}

			if (slot[0].getItem() == Item.getItemFromBlock(Blocks.SPONGE) && slot[0].getMetadata() == 1
					&& slot[1] != null && slot[1].getItem() == Items.BUCKET) {
				slot[7] = new ItemStack(Items.WATER_BUCKET);
			}

//			--slot[0].stackSize;

		//	if (slot[0].stackSize <= 0) {
			//	slot[0] = null;
			int s = slot[0].stackSize - 1;
			if(s > 0) --slot[0].stackSize;
			else if(s <= 0) slot[0] = null;
			//}
			markDirty();
			return;
		}
		else if (canMultiSeparate()) {

			List<StackWithChance> outputs = RecipeSeparator.instance().getMultiSeparatingResult(slot[0].getItem());
			if (outputs != null) {
				int k = outputs.size();
				StackWithChance item1 = null;
				StackWithChance item2 = null;
				StackWithChance item3 = null;
				StackWithChance item4 = null;
				StackWithChance item5 = null;

				if (k > 0)
					item1 = outputs.get(0);
				if (k > 1)
					item2 = outputs.get(1);
				if (k > 2)
					item3 = outputs.get(2);
				if (k > 3)
					item4 = outputs.get(3);
				if (k > 4)
					item5 = outputs.get(4);
				Random rand = worldObj.rand;
				if (item1 != null && rand.nextInt(item1.getChance()) == 0 && slot[2] != null) {
					this.slot[2] = new ItemStack(item1.getStack().getItem(),
							item1.getStack().stackSize + slot[2].stackSize, item1.getStack().getMetadata());
				} else if (item1 != null && rand.nextInt(item1.getChance()) == 0)
					slot[2] = item1.getStack();

				if (item2 != null && rand.nextInt(item2.getChance()) == 0 && slot[3] != null) {
					this.slot[3] = new ItemStack(item2.getStack().getItem(),
							item2.getStack().stackSize + slot[3].stackSize, item2.getStack().getMetadata());
				} else if (item2 != null && rand.nextInt(item2.getChance()) == 0)
					slot[3] = item2.getStack();

				if (item3 != null && rand.nextInt(item3.getChance()) == 0 && slot[4] != null) {
					this.slot[4] = new ItemStack(item3.getStack().getItem(),
							item3.getStack().stackSize + slot[4].stackSize, item3.getStack().getMetadata());
				} else if (item3 != null && rand.nextInt(item3.getChance()) == 0)
					slot[4] = item3.getStack();

				if (item4 != null && rand.nextInt(item4.getChance()) == 0 && slot[5] != null) {
					this.slot[5] = new ItemStack(item4.getStack().getItem(),
							item4.getStack().stackSize + slot[5].stackSize, item4.getStack().getMetadata());
				} else if (item4 != null && rand.nextInt(item4.getChance()) == 0)
					slot[5] = item4.getStack();

				if (item5 != null && rand.nextInt(item5.getChance()) == 0 && slot[6] != null) {
					this.slot[6] = new ItemStack(item5.getStack().getItem(),
							item5.getStack().stackSize + slot[6].stackSize, item5.getStack().getMetadata());
				} else if (item5 != null && rand.nextInt(item5.getChance()) == 0)
					slot[6] = item5.getStack();
				
			//	--slot[0].stackSize;
				int s = slot[0].stackSize - 1;
				if(s > 0) --slot[0].stackSize;
				else if(s <= 0) slot[0] = null;
				
				///if (slot[0].stackSize <= 0) {
					//slot[0] = null;
				//}
				
				
				markDirty();
				return;
			}

		}

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
}
