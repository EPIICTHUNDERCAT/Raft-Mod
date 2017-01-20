package com.epiicthundercat.raft.world;

import java.util.Random;

import com.epiicthundercat.raft.init.RBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

public class WorldGenPalmTree extends WorldGenAbstractTree {

	public static final WorldGenPalmTree TREE_GEN = new WorldGenPalmTree(true);
	public static final WorldGenPalmTree NATURAL_GEN = new WorldGenPalmTree(false);

	private final boolean doBlockNotify;

	private int minTreeHeight;
	private int maxTreeHeight;

	public WorldGenPalmTree(boolean doBlockNotify) {
		this(doBlockNotify, 17, 20);
	}

	public WorldGenPalmTree(boolean doBlockNotify, int minTreeHeight, int maxTreeHeight) {
		super(doBlockNotify);
		this.doBlockNotify = doBlockNotify;
		this.minTreeHeight = minTreeHeight;
		this.maxTreeHeight = maxTreeHeight;
	}

	private boolean isGeneratableTree(World world, BlockPos pos, int treeHeight) {
		MutableBlockPos blockpos = new MutableBlockPos();

		for (int y = pos.getY(); y <= pos.getY() + 1 + treeHeight; ++y) {
			if (y >= 0 && y < 256) {
				int checkedRange = y == pos.getY() ? 0 : y >= pos.getY() + 1 + treeHeight - 2 ? 2 : 1;

				for (int x = pos.getX() - checkedRange; x <= pos.getX() + checkedRange; ++x) {
					for (int z = pos.getZ() - checkedRange; z <= pos.getZ() + checkedRange; ++z) {
						blockpos.setPos(x, y, z);

						if (!isReplaceable(world, blockpos)) {
							return false;
						}
					}
				}
			} else {
				return false;
			}
		}

		return true;
	}

	private void setTree(World world, Random random, BlockPos pos, Biome biome, int treeHeight) {
		MutableBlockPos blockpos = new MutableBlockPos(pos);

		blockpos.move(EnumFacing.DOWN);

		for (int woodHeight = 0; woodHeight < treeHeight; ++woodHeight) {
			blockpos.move(EnumFacing.UP);

			IBlockState state = world.getBlockState(blockpos);
			Block block = state.getBlock();

			if (block.isAir(state, world, blockpos) || block.isLeaves(state, world, blockpos)
					|| state.getMaterial() == Material.VINE) {

				if (random.nextInt(10) == 0) {
					byte count = 0;
					MutableBlockPos pos1 = new MutableBlockPos();

					for (int x = pos.getX() - 2; count < 3 && x <= pos.getX() + 2; ++x) {
						for (int z = pos.getZ() - 2; count < 3 && z <= pos.getZ() + 2; ++z) {
							pos1.setPos(x, pos.getY() - 1, z);

							state = world.getBlockState(pos1);
							block = state.getBlock();

							if (block.canSustainPlant(state, world, pos1, EnumFacing.UP, Blocks.BROWN_MUSHROOM)) {
								block = random.nextInt(30) == 0 ? Blocks.RED_MUSHROOM : Blocks.BROWN_MUSHROOM;
								block.onPlantGrow(state, world, pos1, pos1.up());

								if (random.nextInt(6) == 0 && world.getBlockState(pos1).getBlock() == block) {
									++count;
								}
							}
						}
					}
				}
			}
		}
	}

	private void setLeaves(World world, Random random, BlockPos pos, Biome biome, int treeHeight) {
		int leavesHeight = 12;

		if (treeHeight - leavesHeight >= leavesHeight - 3) {
			leavesHeight += 2;
		}

		MutableBlockPos blockpos = new MutableBlockPos();

		for (int y = pos.getY() - leavesHeight + treeHeight; y <= pos.getY() + treeHeight; ++y) {
			int leaveNum = y - (pos.getY() + treeHeight);
			int leaveRange = 1 - leaveNum / (leavesHeight / 2 - 1);

			for (int x = pos.getX() - leaveRange; x <= pos.getX() + leaveRange; ++x) {
				for (int z = pos.getZ() - leaveRange; z <= pos.getZ() + leaveRange; ++z) {
					if (Math.abs(x - pos.getX()) != leaveRange || Math.abs(z - pos.getZ()) != leaveRange
							|| random.nextInt(2) != 0 && leaveNum != 0) {
						blockpos.setPos(x, y, z);

						IBlockState state = world.getBlockState(blockpos);
						Block block = state.getBlock();

						if ((block.isAir(state, world, blockpos) || block.isLeaves(state, world, blockpos)
								|| state.getMaterial() == Material.VINE) && random.nextInt(12) != 0) {
							setBlockAndNotifyAdequately(world, blockpos, RBlocks.palm_leaves.getDefaultState());
						}
					}
				}
			}
		}
	}

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {
		Biome biome = world.getBiome(pos);
		int i = 0;

		if (biome.isHighHumidity()) {
			i = random.nextInt(3);
		} else if (biome.getFloatTemperature(pos) >= 1.0F) {
			i = -(random.nextInt(2) + 3);
		}

		int treeHeight = Math.min(random.nextInt(4) + minTreeHeight + i, maxTreeHeight + i);

		if (treeHeight != 0 && pos.getY() > 0 && pos.getY() + treeHeight + 1 <= 256
				&& isGeneratableTree(world, pos, treeHeight)) {
			BlockPos down = pos.down();
			IBlockState state = world.getBlockState(down);
			Block block = state.getBlock();

			if (block.canSustainPlant(state, world, down, EnumFacing.UP, (IPlantable) RBlocks.palm_sapling)
					&& pos.getY() < 256 - treeHeight - 1) {
				block.onPlantGrow(state, world, down, pos);

				setLeaves(world, random, pos, biome, treeHeight);
				setTree(world, random, pos, biome, treeHeight);

				return true;
			}
		}

		return false;
	}

}
