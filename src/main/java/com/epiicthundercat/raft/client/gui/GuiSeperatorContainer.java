package com.epiicthundercat.raft.client.gui;

import com.epiicthundercat.raft.VariablesSeperator;
import com.epiicthundercat.raft.block.BlockSeperator;
import com.epiicthundercat.raft.rafttileentitity.TileEntitySeperator;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiSeperatorContainer extends Container {
	public static Object instance;
	public static int GUIID = 2;
	World world = null;
	EntityPlayer entity = null;
	int i;
	int j;
	int k;

	public GuiSeperatorContainer(World world, int i, int j, int k, EntityPlayer player) {
		this.world = world;
		this.entity = player;
		this.i = i;
		this.j = j;
		this.k = k;

		TileEntity ent = world.getTileEntity(new BlockPos(i, j, k));
		if ((ent != null) && ((ent instanceof TileEntitySeperator))) {
			BlockSeperator.inherited = (IInventory) ent;
		} else {
			BlockSeperator.inherited = new InventoryBasic("", true, 9);
		}
		addSlotToContainer(new Slot(BlockSeperator.inherited, 0, 8, 10) {
			public boolean isItemValid(ItemStack stack) {
				return (stack != null) && (stack.getItem() == Items.WATER_BUCKET);
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					World world = server.worldServers[0];
					if (VariablesSeperator.Active == 0) {
						if (VariablesSeperator.Fuel > 999) {
							VariablesSeperator.Active = 1;

							VariablesSeperator.Bucket = 1;

						}
					}
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 1, 8, 56) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					WorldServer localWorldServer = server.worldServers[0];
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 2, 61, 42) {
			public boolean isItemValid(ItemStack stack) {
				return (stack != null) && (stack.getItem() == Items.COAL);
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					World world = server.worldServers[0];

					VariablesSeperator.Fuelin = 1;
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 3, 117, 9) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					WorldServer localWorldServer = server.worldServers[0];
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 4, 140, 9) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					WorldServer localWorldServer = server.worldServers[0];
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 5, 117, 30) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					WorldServer localWorldServer = server.worldServers[0];
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 6, 140, 30) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					WorldServer localWorldServer = server.worldServers[0];
				}
			}
		});
		addSlotToContainer(new Slot(BlockSeperator.inherited, 7, 140, 50) {
			public boolean isItemValid(ItemStack stack) {
				return false;
			}

			public void onSlotChanged() {
				super.onSlotChanged();
				if (getHasStack()) {
					EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
					int i = (int) entity.posX;
					int j = (int) entity.posY;
					int k = (int) entity.posZ;
					MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
					WorldServer localWorldServer = server.worldServers[0];
				}
			}
		});
		bindPlayerInventory(player.inventory);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
			}
		}
		for (i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);
		if ((slot != null) && (slot.getHasStack())) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (index < 9) {
				if (!mergeItemStack(itemstack1, 9, 36, true)) {
					return null;
				}
			} else if (!mergeItemStack(itemstack1, 0, 9, false)) {
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

	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
	}
}
