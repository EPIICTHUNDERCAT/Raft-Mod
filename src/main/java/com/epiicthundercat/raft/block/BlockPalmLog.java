package com.epiicthundercat.raft.block;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPalmLog extends BlockLog {
	public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
	public BlockPalmLog(String name) {
		super();
		addToBlocks(this);
		this.setRegistryName(name.toLowerCase());
		this.setUnlocalizedName(name.toLowerCase());
		this.setHarvestLevel("axe", 0);
		this.setCreativeTab(RCreativeTab.RTabs);
		this.setDefaultState(blockState.getBaseState().withProperty(VARIANT, EnumType.NORMAL).withProperty(LOG_AXIS, EnumAxis.Y));
		
	}
	private void addToBlocks(Block block) {
		RBlocks.blocks.add(block);
	}
	

	

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT, LOG_AXIS});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState state = getDefaultState().withProperty(VARIANT, (meta & 3) % 4 == 1 ? EnumType.COCO : EnumType.NORMAL);

		switch (meta & 12)
		{
			case 0:
				state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Y);
				break;
			case 4:
				state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.X);
				break;
			case 8:
				state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.Z);
				break;
			default:
				state = state.withProperty(LOG_AXIS, BlockLog.EnumAxis.NONE);
		}

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int meta = 0;

		meta = meta | state.getValue(VARIANT).getMetadata();

		switch (state.getValue(LOG_AXIS))
		{
			case X:
				meta |= 4;
				break;
			case Z:
				meta |= 8;
				break;
			case NONE:
				meta |= 12;
				break;
			default:
		}

		return meta;
	}

	@Override
	public MapColor getMapColor(IBlockState state)
	{
		return BlockPlanks.EnumType.BIRCH.getMapColor();
	}

	@Override
	public int getLightValue(IBlockState state)
	{
		switch (state.getValue(VARIANT))
		{
			case COCO:
				return 5;
			default:
		}

		return super.getLightValue(state);
	}

	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		boolean ret = super.removedByPlayer(state, world, pos, player, willHarvest);

		if (ret && state.getValue(VARIANT) == EnumType.COCO)
		{
			if (player.swingingHand.MAIN_HAND != null)
			{
				spawnAsEntity(world, pos, new ItemStack(RItems.coconut));
			}
		}

		return ret;
	}

	public enum EnumType implements IStringSerializable
	{
		NORMAL(0, "normal"),
		COCO(1, "coco");

		private static final EnumType[] META_LOOKUP = new EnumType[values().length];

		private final int meta;
		private final String name;

		private EnumType(int meta, String name)
		{
			this.meta = meta;
			this.name = name;
		}

		public int getMetadata()
		{
			return meta;
		}

		@Override
		public String getName()
		{
			return name;
		}

		@Override
		public String toString()
		{
			return name;
		}

		public static EnumType byMetadata(int meta)
		{
			if (meta < 0 || meta >= META_LOOKUP.length)
			{
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		static
		{
			for (EnumType type : values())
			{
				META_LOOKUP[type.getMetadata()] = type;
			}
		}
	}
}
