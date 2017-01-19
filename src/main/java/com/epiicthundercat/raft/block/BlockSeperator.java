package com.epiicthundercat.raft.block;

import java.util.Random;

import com.epiicthundercat.raft.Raft;
import com.epiicthundercat.raft.VariablesSeperator;
import com.epiicthundercat.raft.client.gui.RGuiHandler;
import com.epiicthundercat.raft.rafttileentitity.TileEntitySeperator;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class BlockSeperator extends RBlock implements ITileEntityProvider {
	public static Object instance;
	//public static int GUIID = 2;
	public static IItemHandler inherited;
	int a1 = 0;
	int a2 = 0;
	int a3 = 0;
	int a4 = 0;
	int a5 = 0;
	int a6 = 0;
	boolean red = false;

	public BlockSeperator() {
		super("Seperator", Material.ANVIL);

	
	}

	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySeperator();
	}

	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntity tileentity = world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, (TileEntitySeperator) tileentity);
		world.removeTileEntity(pos);
		super.breakBlock(world, pos, state);
	}

	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int eventID, int eventParam) {
		super.eventReceived(state, worldIn, pos, eventID, eventParam);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if ((tileentity instanceof TileEntitySeperator)) {
			return Container.calcRedstoneFromInventory((TileEntitySeperator) tileentity);
		}
		return 0;
	}

	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
		world.scheduleUpdate(new BlockPos(i, j, k), this, tickRate(world));
	}

	public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return this.red ? 15 : 0;
	}

	public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return this.red ? 15 : 0;
	}

	public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
		if (VariablesSeperator.Active > 0) {
			VariablesSeperator.Process += 1;
		}
		if (VariablesSeperator.Bucket == 1) {
			TileEntity inv = world.getTileEntity(new BlockPos(i, j, k));
			if ((inv != null) && ((inv instanceof TileEntitySeperator))) {
				((TileEntitySeperator) inv).setInventorySlotContents(1,
						new ItemStack(Items.BUCKET, 1));
			}
		}
		if (VariablesSeperator.Bucket == 1) {
			TileEntity inv = world.getTileEntity(new BlockPos(i, j, k));
			if ((inv != null) && ((inv instanceof TileEntitySeperator))) {
				((TileEntitySeperator) inv).decrStackSize(0, 1);
			}
		}
		if (VariablesSeperator.Bucket == 1) {
			VariablesSeperator.Bucket = 2;
		}
		if (VariablesSeperator.Bucket == 2) {
			VariablesSeperator.Bucket = 0;
		}
		if (VariablesSeperator.Fuelin == 1) {
			TileEntity inv = world.getTileEntity(new BlockPos(i, j, k));
			if ((inv != null) && ((inv instanceof TileEntitySeperator))) {
				((TileEntitySeperator) inv).decrStackSize(2, 1);
			}
		}
		if (VariablesSeperator.Fuelin == 1) {
			VariablesSeperator.Fuel += 1000;
		}
		if (VariablesSeperator.Fuelin == 2) {
			VariablesSeperator.Fuelin = 0;
		}
		if (VariablesSeperator.Fuelin == 1) {
			VariablesSeperator.Fuelin = 2;
		}
		if (VariablesSeperator.Process == 102) {
			VariablesSeperator.Active = 0;
		}
		if (VariablesSeperator.Process == 102) {
			VariablesSeperator.Process = 0;
		}
		if (VariablesSeperator.Process == 102) {
			VariablesSeperator.Active = 0;
		}
		if (VariablesSeperator.Process == 102) {
			VariablesSeperator.Process = 0;
		}
		
		if ((VariablesSeperator.Process < 101) && (VariablesSeperator.Process > 0)) {
			VariablesSeperator.Fuel -= 10;
		}
		world.scheduleUpdate(new BlockPos(i, j, k), this, tickRate(world));
	}

	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random) {
		EntityPlayer entity = Minecraft.getMinecraft().thePlayer;
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		World par1World = world;
		int par2 = i;
		int par3 = j;
		int par4 = k;
		Random par5Random = random;
		for (int la = 0; la < 8; la++) {
			double d0 = par2 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.3999999985098839D;
			double d1 = par3 + 0.7F + (par5Random.nextFloat() - 0.5F) * 0.3999999985098839D + 0.5D;
			double d2 = par4 + 0.5F + (par5Random.nextFloat() - 0.5F) * 0.3999999985098839D;
			double d3 = 0.2199999988079071D;
			double d4 = 0.27000001072883606D;
			par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d4, d1 + d3, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer entity, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();
		if ((entity instanceof EntityPlayer)) {
			entity.openGui(Raft.instance, RGuiHandler.GUIID2, world, i, j, k);
		}
		return true;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.SOLID;
	}

	public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
	}

	public int tickRate(World world) {
		return 10;
	}

	public int quantityDropped(Random par1Random) {
		return 1;
	}
}
