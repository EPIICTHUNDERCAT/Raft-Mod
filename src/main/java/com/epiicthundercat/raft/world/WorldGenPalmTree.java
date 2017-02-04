package com.epiicthundercat.raft.world;

import java.util.Random;

import com.epiicthundercat.raft.block.BlockPalmLeaves;
import com.epiicthundercat.raft.block.BlockPalmSapling;
import com.epiicthundercat.raft.init.RBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenPalmTree extends WorldGenAbstractTree {

	public static final WorldGenPalmTree TREE_GEN = new WorldGenPalmTree(true);
	public static final WorldGenPalmTree NATURAL_GEN = new WorldGenPalmTree(false);
	private static final IBlockState TRUNK = RBlocks.palm_log.getDefaultState();
	private static final IBlockState LEAF = RBlocks.palm_leaves.getDefaultState()
			.withProperty(BlockPalmLeaves.CHECK_DECAY, Boolean.valueOf(false));

	public WorldGenPalmTree(boolean doBlockNotify) {
		super(doBlockNotify);
	}

	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int i = rand.nextInt(3) + rand.nextInt(3) + 5;
		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
			for (int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
				int k = 1;

				if (j == position.getY()) {
					k = 0;
				}

				if (j >= position.getY() + 1 + i - 2) {
					k = 2;
				}

				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

				for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
					for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
						if (j >= 0 && j < 256) {
							if (!this.isReplaceable(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
								flag = false;
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			} else {
				BlockPos down = position.down();
				IBlockState state = worldIn.getBlockState(down);
				boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down,
						net.minecraft.util.EnumFacing.UP, ((BlockPalmSapling) RBlocks.palm_sapling));

				if (isSoil && position.getY() < worldIn.getHeight() - i - 1) {
					state.getBlock().onPlantGrow(state, worldIn, down, position);
					EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(rand);
					int k2 = i - rand.nextInt(4) - 1;
					int l2 = 3 - rand.nextInt(3);
					int i3 = position.getX();
					int j1 = position.getZ();
					int k1 = 0;

					for (int l1 = 0; l1 < i; ++l1) {
						int i2 = position.getY() + l1;

						if (l1 >= k2 && l2 > 0) {
							i3 += enumfacing.getFrontOffsetX();
							j1 += enumfacing.getFrontOffsetZ();
							--l2;
						}

						BlockPos blockpos = new BlockPos(i3, i2, j1);
						state = worldIn.getBlockState(blockpos);

						if (state.getBlock().isAir(state, worldIn, blockpos)
								|| state.getBlock().isLeaves(state, worldIn, blockpos)) {
							this.placeLogAt(worldIn, blockpos);
							k1 = i2;
						}
					}

					BlockPos blockpos2 = new BlockPos(i3, k1, j1);

					for (int j3 = -1; j3 <= 0; ++j3) {
						for (int i4 = -1; i4 <= 0; ++i4) {
							if (Math.abs(j3) != 3 || Math.abs(i4) != 3) {
								//this.placeLeafAt(worldIn, blockpos2.add(j3, 0, i4));
							}
						}
					}
					BlockPos blockpos3 = new BlockPos(i3, k1, j1);

					//for (int j7 = 2; j7 ) {
						//for (int i8 = -1; i8 < 3; ++i8) {
						//	if (Math.abs(j7) != 3 || Math.abs(i8) != 3) {
								this.placeLeafAt(worldIn, blockpos3.add(0, 1, 0));
								this.placeLeafAt(worldIn, blockpos3.add(0, 2, 0));
								this.placeLeafAt(worldIn, blockpos3.add(1, 1, 0));
								this.placeLeafAt(worldIn, blockpos3.add(0, 1, 1));
								this.placeLeafAt(worldIn, blockpos3.add(0, 1, 2));
								this.placeLeafAt(worldIn, blockpos3.add(2, 1, 0));
								this.placeLeafAt(worldIn, blockpos3.add(-1, 1, 0));
								this.placeLeafAt(worldIn, blockpos3.add(0, 1, -1));
								this.placeLeafAt(worldIn, blockpos3.add(-2, 1, 0));
								this.placeLeafAt(worldIn, blockpos3.add(0, 1, -2));
								this.placeLeafAt(worldIn, blockpos3.add(0, 0, 2));
								this.placeLeafAt(worldIn, blockpos3.add(2, 0, 0));
								this.placeLeafAt(worldIn, blockpos3.add(-2, 0, 0));
								this.placeLeafAt(worldIn, blockpos3.add(0, 0, -2));
								this.placeLeafAt(worldIn, blockpos3.add(0, 0, 3));
								this.placeLeafAt(worldIn, blockpos3.add(3, 0, 0));
								this.placeLeafAt(worldIn, blockpos3.add(-3, 0, 0));
								this.placeLeafAt(worldIn, blockpos3.add(0, 0, -3));
							//}
						//}
					//}

					
					
					EnumFacing enumfacing1 = EnumFacing.Plane.HORIZONTAL.random(rand);

					if (enumfacing1 != enumfacing) {
						int l3 = k2 - rand.nextInt(2) - 1;
						int k4 = 1 + rand.nextInt(3);
						k1 = 0;

						for (int l4 = l3; l4 < i && k4 > 0; --k4) {
							if (l4 >= 1) {
								int j2 = position.getY() + l4;
								i3 += enumfacing1.getFrontOffsetX();
								j1 += enumfacing1.getFrontOffsetZ();
								BlockPos blockpos1 = new BlockPos(i3, j2, j1);
								state = worldIn.getBlockState(blockpos1);

								if (state.getBlock().isAir(state, worldIn, blockpos1)
										|| state.getBlock().isLeaves(state, worldIn, blockpos1)) {
									//this.placeLogAt(worldIn, blockpos1);
									//k1 = j2;
								}
							}

							++l4;
						}

					/*	if (k1 > 0) {
							BlockPos blockpos3 = new BlockPos(i3, k1, j1);

							for (int i5 = -2; i5 <= 2; ++i5) {
								for (int k5 = -2; k5 <= 2; ++k5) {
									if (Math.abs(i5) != 2 || Math.abs(k5) != 2) {
										this.placeLeafAt(worldIn, blockpos3.add(i5, 0, k5));
									}
								}
							}

							blockpos3 = blockpos3.up();

							for (int j5 = -1; j5 <= 1; ++j5) {
								for (int l5 = -1; l5 <= 1; ++l5) {
									this.placeLeafAt(worldIn, blockpos3.add(j5, 0, l5));
								}
							}
						}*/
					}

					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private void placeLogAt(World worldIn, BlockPos pos) {
		this.setBlockAndNotifyAdequately(worldIn, pos, TRUNK);
	}

	private void placeLeafAt(World worldIn, BlockPos pos) {
		IBlockState state = worldIn.getBlockState(pos);

		if (state.getBlock().isAir(state, worldIn, pos) || state.getBlock().isLeaves(state, worldIn, pos)) {
			this.setBlockAndNotifyAdequately(worldIn, pos, LEAF);
		}
	}
	/*
	 * public static final WorldGenPalmTree TREE_GEN = new
	 * WorldGenPalmTree(true); public static final WorldGenPalmTree NATURAL_GEN
	 * = new WorldGenPalmTree(false);
	 * 
	 * private final boolean doBlockNotify;
	 * 
	 * private int minTreeHeight; private int maxTreeHeight;
	 * 
	 * public WorldGenPalmTree(boolean doBlockNotify) { this(doBlockNotify, 7,
	 * 10); }
	 * 
	 * public WorldGenPalmTree(boolean doBlockNotify, int minTreeHeight, int
	 * maxTreeHeight) { super(doBlockNotify); this.doBlockNotify =
	 * doBlockNotify; this.minTreeHeight = minTreeHeight; this.maxTreeHeight =
	 * maxTreeHeight; }
	 * 
	 * private boolean isGeneratableTree(World world, BlockPos pos, int
	 * treeHeight) { MutableBlockPos blockpos = new MutableBlockPos();
	 * 
	 * for (int y = pos.getY(); y <= pos.getY() + 1 + treeHeight; ++y) { if (y
	 * >= 0 && y < 256) { int checkedRange = y == pos.getY() ? 0 : y >=
	 * pos.getY() + 1 + treeHeight - 2 ? 2 : 1;
	 * 
	 * for (int x = pos.getX() - checkedRange; x <= pos.getX() + checkedRange;
	 * ++x) { for (int z = pos.getZ() - checkedRange; z <= pos.getZ() +
	 * checkedRange; ++z) { blockpos.setPos(x, y, z);
	 * 
	 * if (!isReplaceable(world, blockpos)) { return false; } } } } else {
	 * return false; } }
	 * 
	 * return true; }
	 * 
	 * private void setTree(World world, Random random, BlockPos pos, Biome
	 * biome, int treeHeight) {
	 * 
	 * MutableBlockPos blockpos = new MutableBlockPos(pos);
	 * 
	 * blockpos.move(EnumFacing.DOWN);
	 * 
	 * for (int woodHeight = 0; woodHeight < treeHeight; ++woodHeight) {
	 * blockpos.move(EnumFacing.UP);
	 * 
	 * IBlockState state = world.getBlockState(blockpos); Block block =
	 * state.getBlock();
	 * 
	 * if (block.isAir(state, world, blockpos) || block.isLeaves(state, world,
	 * blockpos) || state.getMaterial() == Material.LEAVES) {
	 * 
	 * IBlockState blockstate = RBlocks.palm_log.getDefaultState(); if
	 * (random.nextInt(40) == 0) { blockstate =
	 * blockstate.withProperty(BlockPalmLog.VARIANT,
	 * BlockPalmLog.EnumType.COCO); } setBlockAndNotifyAdequately(world,
	 * blockpos, blockstate);
	 * 
	 * 
	 * 
	 * if (random.nextInt(10) == 0) {
	 * 
	 * byte count = 0; MutableBlockPos pos1 = new MutableBlockPos();
	 * 
	 * for (int x = pos.getX() - 2; count < 3 && x <= pos.getX() + 2; ++x) { for
	 * (int z = pos.getZ() - 2; count < 3 && z <= pos.getZ() + 2; ++z) {
	 * pos1.setPos(x, pos.getY() - 1, z);
	 * 
	 * state = world.getBlockState(pos1); block = state.getBlock();
	 * 
	 * if (block.canSustainPlant(state, world, pos1, EnumFacing.UP,
	 * Blocks.BROWN_MUSHROOM)) { block = random.nextInt(30) == 0 ?
	 * Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM; block.onPlantGrow(state,
	 * world, pos1, pos1.up());
	 * 
	 * if (random.nextInt(6) == 0 && world.getBlockState(pos1).getBlock() ==
	 * block) { ++count; } } } } } } } }
	 * 
	 * private void setLeaves(World world, Random random, BlockPos pos, Biome
	 * biome, int treeHeight) { int leavesHeight = 2;
	 * 
	 * if (treeHeight - leavesHeight >= leavesHeight - 3) { leavesHeight += 2; }
	 * 
	 * MutableBlockPos blockpos = new MutableBlockPos();
	 * 
	 * for (int y = pos.getY() - leavesHeight + treeHeight; y <= pos.getY() +
	 * treeHeight; ++y) { int leaveNum = y - (pos.getY() + treeHeight); int
	 * leaveRange = 1 - leaveNum / (leavesHeight / 2 - 1);
	 * 
	 * for (int x = pos.getX() - leaveRange; x <= pos.getX() + leaveRange; ++x)
	 * { for (int z = pos.getZ() - leaveRange; z <= pos.getZ() + leaveRange;
	 * ++z) { if (Math.abs(x - pos.getX()) != leaveRange || Math.abs(z -
	 * pos.getZ()) != leaveRange || random.nextInt(2) != 0 && leaveNum != 0) {
	 * blockpos.setPos(x, y, z);
	 * 
	 * IBlockState state = world.getBlockState(blockpos); Block block =
	 * state.getBlock();
	 * 
	 * if ((block.isAir(state, world, blockpos) || block.isLeaves(state, world,
	 * blockpos) || state.getMaterial() == Material.LEAVES) &&
	 * random.nextInt(12) != 0) { setBlockAndNotifyAdequately(world, blockpos,
	 * RBlocks.palm_planks.getDefaultState()); } } } } } }
	 * 
	 * @Override public boolean generate(World world, Random random, BlockPos
	 * pos) { Biome biome = world.getBiome(pos); int i = 0;
	 * 
	 * if (biome.isHighHumidity()) { i = random.nextInt(3); } else if
	 * (biome.getFloatTemperature(pos) >= 1.0F) { i = -(random.nextInt(2) + 3);
	 * }
	 * 
	 * int treeHeight = Math.min(random.nextInt(4) + minTreeHeight + i,
	 * maxTreeHeight + i);
	 * 
	 * if (treeHeight != 0 && pos.getY() > 0 && pos.getY() + treeHeight + 1 <=
	 * 256 && isGeneratableTree(world, pos, treeHeight)) { BlockPos down =
	 * pos.down(); IBlockState state = world.getBlockState(down); Block block =
	 * state.getBlock();
	 * 
	 * if (block.canSustainPlant(state, world, down, EnumFacing.UP, (IPlantable)
	 * RBlocks.palm_sapling) && pos.getY() < 256 - treeHeight - 1) {
	 * block.onPlantGrow(state, world, down, pos);
	 * 
	 * setLeaves(world, random, pos, biome, treeHeight); setTree(world, random,
	 * pos, biome, treeHeight);
	 * 
	 * return true; } }
	 * 
	 * return false; }
	 */
}
