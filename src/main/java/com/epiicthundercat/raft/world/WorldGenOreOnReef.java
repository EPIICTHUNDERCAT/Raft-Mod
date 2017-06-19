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
	private WorldGenerator genOreCoralReef;
	

	public static int oreClusterRarity = 9;

	public WorldGenOreOnReef() {
		genOreCoralReef = new WorldGenOreReef();
		

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
			BlockPos position = new BlockPos(x + 8, y, z + 8);
			Biome biome = world.getBiome(position);
			if ((BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN)) || BiomeDictionary.hasType(biome, WATER)
					|| BiomeDictionary.hasType(biome, WET)) {
				genOreCoralReef.generate(world, rand, position);
				

			}
		}
	}

	public class WorldGenOreReef extends WorldGenerator {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			int r = MathHelper.clamp(rand.nextInt(8), 8, 8);
			for (int x = -r; x <= r; x++)
				for (int z = -r; z <= r; z++) {
					BlockPos pos = getTopSolidOrLiquidBlock(worldIn,
							new BlockPos(position.getX() + x, position.getY(), position.getZ() + z));
					if ((x * x) + (z * z) > r * r || rand.nextInt(r) < r / 2 || pos.getY() > worldIn.getSeaLevel() - 5)
						continue;

					Block block = worldIn.getBlockState(pos).getBlock();
					if (block == Blocks.GRAVEL || block == Blocks.DIRT || block == Blocks.SAND
							|| block == CoralReef.reef.getDefaultState()) {
						if (worldIn.setBlockState(pos, CoralReef.reef.getDefaultState())) {
						}

						
						IBlockState variant = RBlocks.copper_compound_ore.getDefaultState();
						IBlockState variant1 = RBlocks.aluminum_compound_ore.getDefaultState();
						IBlockState variant2 = CoralReef.coral.getDefaultState().withProperty(BlockCoral.VARIANTS,
								rand.nextInt(6));
						IBlockState variant3 = RBlocks.tin_compound_ore.getDefaultState();
						IBlockState variant4 = RBlocks.iron_compound_ore.getDefaultState();
						IBlockState variant5 = RBlocks.gold_compound_ore.getDefaultState();
						IBlockState variant6 = RBlocks.tungsten_compound_ore.getDefaultState();
						IBlockState variant7 = RBlocks.lead_compound_ore.getDefaultState();
						IBlockState variant8 = RBlocks.silver_compound_ore.getDefaultState();
						IBlockState variant9 = RBlocks.uranium_compound_ore.getDefaultState();
						IBlockState variant10 = RBlocks.nickel_compound_ore.getDefaultState();
						
						for (int i = 1; i <= rand.nextInt(4); i++) {
							Random random = new Random();
							switch (random.nextInt(11)) {
							case 0:

								worldIn.setBlockState(pos.up(), variant1);
								break;
							case 1:

								worldIn.setBlockState(pos.up(), variant);
								break;
							case 2:

								worldIn.setBlockState(pos.up(i), variant2);
								break;
							case 3:

								worldIn.setBlockState(pos.up(), variant3);
								break;
							case 4:

								worldIn.setBlockState(pos.up(), variant4);
								break;
							case 5:

								worldIn.setBlockState(pos.up(), variant5);
								break;
							case 6:

								worldIn.setBlockState(pos.up(), variant6);
								break;
							case 7:

								worldIn.setBlockState(pos.up(), variant7);
								break;
							case 8:

								worldIn.setBlockState(pos.up(), variant8);
								break;
							case 9:

								worldIn.setBlockState(pos.up(), variant9);
								break;
							case 10:

								worldIn.setBlockState(pos.up(), variant10);
								break;
							

							}

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
}