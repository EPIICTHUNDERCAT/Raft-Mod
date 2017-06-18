package com.epiicthundercat.raft.block;

import java.util.List;
import java.util.Random;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.init.RItems;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPalmLeaves extends BlockLeaves {
	public BlockPalmLeaves(String name) {
		super();

		addToBlocks(this);
		this.setRegistryName(name.toLowerCase());
		this.setUnlocalizedName(name.toLowerCase());
		this.setCreativeTab(RCreativeTab.RTabs);
		this.setHarvestLevel("axe", 0);

		this.setDefaultState(blockState.getBaseState().withProperty(DECAYABLE, Boolean.valueOf(true))
				.withProperty(CHECK_DECAY, Boolean.valueOf(true)));
		this.leavesFancy = true;
	}

	private void addToBlocks(Block block) {
		RBlocks.blocks.add(block);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { DECAYABLE, CHECK_DECAY });
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY,
				Boolean.valueOf((meta & 8) > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;

		if (!state.getValue(DECAYABLE).booleanValue()) {
			meta |= 4;
		}

		if (state.getValue(CHECK_DECAY).booleanValue()) {
			meta |= 8;
		}

		return meta;
	}

	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void setGraphicsLevel(boolean fancy) {
		super.setGraphicsLevel(true);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(RBlocks.palm_sapling);

	}

	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
	
		if (worldIn.rand.nextInt(chance) == 0.9) {

			spawnAsEntity(worldIn, pos, new ItemStack(RItems.coconut));
		}
		spawnAsEntity(worldIn, pos, new ItemStack(RItems.thatch));

	}
	int chance = 2;
	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity tile,
			ItemStack stack) {
		
		if (!world.isRemote && stack.getItem() == Items.SHEARS) {
			player.addStat(StatList.getBlockStats(this));
			spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this)));
		} else if (!world.isRemote && stack.getItem() == RItems.hook && world.rand.nextInt(chance) == 1) {
			spawnAsEntity(world, pos, new ItemStack(RItems.coconut));
		} else
			super.harvestBlock(world, player, pos, state, tile, stack);
	}
	

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return Lists.newArrayList(new ItemStack(this));
	}
}
