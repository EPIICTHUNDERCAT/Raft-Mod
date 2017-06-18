package com.epiicthundercat.raft.world;

import static net.minecraftforge.common.BiomeDictionary.Type.WATER;
import static net.minecraftforge.common.BiomeDictionary.Type.WET;

import java.util.Random;

import com.epiicthundercat.raft.init.RBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.IWorldGenerator;
import primetoxinz.coralreef.BlockCoral;
import primetoxinz.coralreef.CoralReef;

public class WorldGenOreOnReef implements IWorldGenerator {
	private WorldGenerator genCoralReef;
	//private WorldGenerator genSmallReef;
	 public static int oreClusterRarity = 8;
	public WorldGenOreOnReef() {
		genCoralReef = new WorldGenOreReef();
		//genSmallReef = new WorldGenSmallOreReef();
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			this.runGenerator(world, rand, chunkX, chunkZ, 0, world.getSeaLevel() - 4);
		}
	}

	private void runGenerator(World world, Random rand, int chunk_X, int chunk_Z, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight)
			throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		if (rand.nextInt(oreClusterRarity) == 0) {
			int x = chunk_X * 16 + rand.nextInt(16);
			int z = chunk_Z * 16 + rand.nextInt(16);
			int y = MathHelper.clamp(getTopSolidOrLiquidBlock(world, new BlockPos(x, 64, z)).getY(), minHeight,
					maxHeight);
			BlockPos position = new BlockPos(x, y, z);
			Biome biome = world.getBiome(position);
			if ((BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN))
					|| BiomeDictionary.hasType(biome, WATER) || BiomeDictionary.hasType(biome, WET)) {
				genCoralReef.generate(world, rand, position);
			}
		}
	}

	public class WorldGenOreReef extends WorldGenerator {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			int r = MathHelper.clamp(rand.nextInt(16), 8, 16);
			for (int x = -r; x <= r; x++)
				for (int z = -r; z <= r; z++) {
					BlockPos pos = getTopSolidOrLiquidBlock(worldIn,
							new BlockPos(position.getX() + x, position.getY(), position.getZ() + z));
					if ((x * x) + (z * z) > r * r || rand.nextInt(r) < r / 2 || pos.getY() > worldIn.getSeaLevel() - 5)
						continue;
										
					Block block = worldIn.getBlockState(pos).getBlock();
					if (block == Blocks.GRAVEL || block == Blocks.DIRT || block == Blocks.SAND || block == CoralReef.reef.getDefaultState()) {
						if (worldIn.setBlockState(pos, CoralReef.reef.getDefaultState())) {
							}
							 
						IBlockState variant2 = CoralReef.coral.getDefaultState().withProperty(BlockCoral.VARIANTS, rand.nextInt(6));
						 
						IBlockState variant = RBlocks.palm_log.getDefaultState();
						IBlockState variant3 = Blocks.GOLD_BLOCK.getDefaultState();

						IBlockState variant1 = Blocks.DIAMOND_BLOCK.getDefaultState();
							for (int i = 1; i <= rand.nextInt(4); i++) {
								Random random = new Random();
								switch (random.nextInt(4)) {
								case 0:
									//System.out.println("SpawningB");
									worldIn.setBlockState(pos.up(), variant1);
									break;
								case 1:
									//System.out.println("SpawningT");
									worldIn.setBlockState(pos.up(), variant);
									break;
								case 2:
									//System.out.println("SpawningS");
									worldIn.setBlockState(pos.up(i), variant2);
									break;
								case 3:
									//System.out.println("SpawningP");
									worldIn.setBlockState(pos.up(), variant3);
									break;
							
								
								}
									
									
									
								
						//	}

						}
					}
				}
			return true;

		}
	}

	public static BlockPos getTopSolidOrLiquidBlock(World world, BlockPos pos) {
		Chunk chunk = world.getChunkFromBlockCoords(pos);
		BlockPos blockpos;
		BlockPos blockpos1;

		for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos
				.getY() >= 0; blockpos = blockpos1) {
			blockpos1 = blockpos.down();
			IBlockState state = chunk.getBlockState(blockpos1);
			if (state.getMaterial().isSolid() && !state.getMaterial().isLiquid()
					&& !state.getBlock().isLeaves(state, world, blockpos1)
					&& !state.getBlock().isFoliage(world, blockpos1)) {
				break;
			}
		}
		return blockpos.down();
	}

	/*public class WorldGenSmallOreReef extends WorldGenerator {
		private final IBlockState block;
		private final int startRadius;

		public WorldGenSmallOreReef() {
			super(false);
			this.block = CoralReef.reef.getDefaultState().withProperty(BlockReef.VARIANTS, 1);
			this.startRadius = 2;
		}

		public boolean generate(World worldIn, Random rand, BlockPos position) {
			if (rand.nextInt(4) != 0)
				return true;
			while (true) {
				label0: {
					if (position.getY() > 3) {
						if (worldIn.isAirBlock(position.down())) {
							break label0;
						}

						Block block = worldIn.getBlockState(position.down()).getBlock();

						if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.STONE) {
							break label0;
						}
					}

					if (position.getY() <= 3) {
						return false;
					}

					int i1 = this.startRadius;

					for (int i = 0; i1 >= 0 && i < 3; ++i) {
						int j = i1 + rand.nextInt(2);
						int k = i1 + rand.nextInt(2);
						int l = i1 + rand.nextInt(2);
						float f = (float) (j + k + l) * 0.333F + 0.5F;

						for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-j, -k, -l),
								position.add(j, k, l))) {
							if (blockpos.distanceSq(position) <= (double) (f * f)) {
								worldIn.setBlockState(blockpos, this.block, 4);
							}
						}

						position = position.add(-(i1 + 1) + rand.nextInt(2 + i1 * 2), 0 - rand.nextInt(2),
								-(i1 + 1) + rand.nextInt(2 + i1 * 2));
					}

					return true;
				}
				position = position.down();
			}
		}
	}*/
}