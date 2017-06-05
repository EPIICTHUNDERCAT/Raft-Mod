package com.epiicthundercat.raft.block;

import java.util.Random;

import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.rafttileentitity.TileBurner;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class BlockBurner extends RBlock {
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
	public static final PropertyBool BURNING = PropertyBool.create("burning");

	public BlockBurner(String name, Material material) {
		super(name, material);
		setTickRandomly(true);
		setUnlocalizedName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, Integer.valueOf(0)).withProperty(BURNING,
				Boolean.valueOf(false)));

		setLightOpacity(0);
		this.fullBlock = false;
	}

	public AxisAlignedBB func_185496_a(IBlockState state, IBlockAccess source, BlockPos pos) {
		return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
	}

	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return NULL_AABB;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileBurner();
	}

	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	/*
	 * public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos,
	 * EnumFacing side) { if ((side != EnumFacing.UP) ||
	 * (worldIn.getBlockState(pos.down()).getBlock() != RBlocks.burner)) {
	 * return false; } return super.canPlaceBlockOnSide(worldIn, pos, side); }
	 * 
	 * public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState
	 * state) { if (worldIn.getBlockState(pos.down()).getBlock() !=
	 * RBlocks.burner) { return false; } return true; }
	 */
	public void observedNeighborChange(IBlockState observerState, World world, BlockPos observerPos, Block changedBlock,
			BlockPos changedBlockPos) {
		// checkAndDropBlock(world, observerPos, observerState);
		super.observedNeighborChange(observerState, world, observerPos, changedBlock, changedBlockPos);
	}

	/*
	 * protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState
	 * state) { if (!canBlockStay(worldIn, pos, state)) {
	 * dropBlockAsItem(worldIn, pos, state, 0); worldIn.setBlockState(pos,
	 * Blocks.AIR.getDefaultState(), 3); } }
	 */
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileBurner();
	}

	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if ((tileentity instanceof TileBurner)) {
			dropInventoryItems(worldIn, pos, ((TileBurner) tileentity).inventory());
			worldIn.updateComparatorOutputLevel(pos, this);
		}
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	private static void dropInventoryItems(World worldIn, BlockPos pos, IItemHandler inventory) {
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack itemstack = inventory.getStackInSlot(i);
			if (itemstack.getCount() > 0) {
				InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemstack);
			}
		}
	}

	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player,
			boolean willHarvest) {
		if (willHarvest) {
			return true;
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		super.onBlockHarvested(worldIn, pos, state, player);
		if (!player.capabilities.isCreativeMode) {
			dropBlockAsItem(worldIn, pos, state, 0);
		}
	}

	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(AGE, meta >> 1).withProperty(BURNING,
				Boolean.valueOf((meta & 1) > 0));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = state.getValue(AGE) << 1;
		return ((Boolean) state.getValue(BURNING)).booleanValue() ? meta | 1 : meta;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { AGE, BURNING });
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World worldIn, BlockPos pos, Random rand) {
		// randomly throw up some particles so it looks like the flesh is
		// bubbling
		super.randomDisplayTick(state, worldIn, pos, rand);

		if (state.getValue(BURNING) == true) {
			if (rand.nextInt(24) == 0) {
				worldIn.playSound((double) ((float) pos.getX() + 0.5F), (double) ((float) pos.getY() + 0.5F),
						(double) ((float) pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS,
						1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
			}

			worldIn.spawnParticle(EnumParticleTypes.FLAME,
					(double) ((float) pos.getX() + 0.75F - (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getY() + 0.25F + (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getZ() + 0.75F - (rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D,
					new int[] { Block.getStateId(state) });
			worldIn.spawnParticle(EnumParticleTypes.FLAME,
					(double) ((float) pos.getX() + 0.75F - (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getY() + 0.25F + (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getZ() + 0.75F - (rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D,
					new int[] { Block.getStateId(state) });
			worldIn.spawnParticle(EnumParticleTypes.FLAME,
					(double) ((float) pos.getX() + 0.75F - (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getY() + 0.25F + (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getZ() + 0.75F - (rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D,
					new int[] { Block.getStateId(state) });

			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
					(double) ((float) pos.getX() + 0.75F - (rand.nextFloat() / 2.0F)),
					(double) ((float) pos.getY() + 0.9F),
					(double) ((float) pos.getZ() + 0.75F - (rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D,
					new int[] { Block.getStateId(state) });

			if (rand.nextInt(2) == 0) {
				worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
						(double) ((float) pos.getX() + 0.75F - (rand.nextFloat() / 2.0F)),
						(double) ((float) pos.getY() + 0.9F),
						(double) ((float) pos.getZ() + 0.75F - (rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D,
						new int[] { Block.getStateId(state) });
			}
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean hasSoundPlayed = false;
		if (playerIn.getHeldItem(hand) != null) {
			Item item1 = playerIn.getHeldItem(hand).getItem();
			int age = ((Integer) state.getValue(AGE)).intValue();

			if (age == 0) {
				if (state.getValue(BURNING) == false) {
					if (!worldIn.isRainingAt(pos)) {
						if (item1 == Items.STICK) {
							if (worldIn.rand.nextInt(12) == 0) {
								worldIn.setBlockState(pos,
										RBlocks.burner.getDefaultState().withProperty(BURNING, true));
								// playerIn.addStat(TANAchievements.burner_song);
							}

							if (item1 == Items.STICK) {
								playerIn.getHeldItem(hand).setCount(playerIn.getHeldItem(hand).getCount() - 1);
							}

							return true;
						}

						if (item1 == Items.FLINT_AND_STEEL) {
							worldIn.playSound(playerIn, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS,
									1.0F, worldIn.rand.nextFloat() * 0.4F + 0.8F);
							worldIn.setBlockState(pos, RBlocks.burner.getDefaultState().withProperty(BURNING, true));
							// playerIn.addStat(TANAchievements.burner_song);

							if (item1 == Items.FLINT_AND_STEEL) {
								playerIn.getHeldItem(hand).damageItem(1, playerIn);
							}

							return true;
						}
					}
				}
			}
		}
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if ((!(tileEntity instanceof TileBurner)) || (playerIn.isSneaking())) {
			return false;
		}
		TileBurner te = (TileBurner) tileEntity;
		ItemStack item = playerIn.getHeldItem(hand);
		for (int i = 0; i < te.inventory().getSlots(); i++) {
			if (!item.isEmpty()) {
				if (te.inventory().getStackInSlot(i).isEmpty()) {
					if (te.inventory().insertItem(i, new ItemStack(item.getItem()), false).isEmpty()) {
						playerIn.getHeldItem(hand).shrink(1);
						worldIn.playSound((EntityPlayer) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
								SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.BLOCKS, 0.5F,
								worldIn.rand.nextFloat() * 0.1F + 0.9F);
					}
					return true;
				}
			} else if (!te.inventory().getStackInSlot(i).isEmpty()) {
				playerIn.inventory.addItemStackToInventory(te.inventory().getStackInSlot(i));
				te.inventory().extractItem(i, 1, false);
				if (!hasSoundPlayed) {
					worldIn.playSound((EntityPlayer) null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
							SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 0.5F,
							worldIn.rand.nextFloat() * 0.1F + 0.9F);
					hasSoundPlayed = true;
				}
			}
		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, side, hitX, hitY, hitZ);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
		if (state.getValue(BURNING) == true) {
			if (entity instanceof EntityLivingBase) {
				entity.setFire(1);
			}
		}
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		if (state.getValue(BURNING) == true) {
			return 15;
		} else {
			return 0;
		}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		// checkAndDropBlock(worldIn, pos, state);
		int age = ((Integer) state.getValue(AGE)).intValue();

		if (state.getValue(BURNING) == true) {
			if (worldIn.isRainingAt(pos)) {
				worldIn.setBlockState(pos, state.withProperty(BURNING, false).withProperty(AGE, 15), 2);
				worldIn.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS,
						0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
				for (int i = 0; i < 8; ++i) {
					worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
							(double) ((float) pos.getX() + 0.75F - (rand.nextFloat() / 2.0F)),
							(double) ((float) pos.getY() + 0.9F),
							(double) ((float) pos.getZ() + 0.75F - (rand.nextFloat() / 2.0F)), 0.0D, 0.0D, 0.0D,
							new int[] { Block.getStateId(state) });
				}
			}
			if (age < 7) {
				if (rand.nextInt(8) == 0) {
					worldIn.setBlockState(pos, state.withProperty(AGE, Integer.valueOf(age + 1)), 2);
				}
			}
			if (age == 7) {
				if (rand.nextInt(8) == 0) {
					worldIn.setBlockState(pos, state.withProperty(BURNING, false), 2);
				}
			}
		}
	}

}
