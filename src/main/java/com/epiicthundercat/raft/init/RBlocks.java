package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.block.BlockBurner;
import com.epiicthundercat.raft.block.BlockPalmLeaves;
import com.epiicthundercat.raft.block.BlockPalmLog;
import com.epiicthundercat.raft.block.BlockPalmSapling;
import com.epiicthundercat.raft.block.BlockPalmWood;
import com.epiicthundercat.raft.block.BlockPalmWoodSlab;
import com.epiicthundercat.raft.block.RBlock;
import com.epiicthundercat.raft.block.orecompounds.BlockAluminumCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockCopperCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockGoldCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockIronCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockLeadCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockNickelCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockSilverCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockTinCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockTungstenCompound;
import com.epiicthundercat.raft.block.orecompounds.BlockUraniumCompound;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RBlocks {

	public static List<Block> blocks = new ArrayList();

	public static Block burner = new BlockBurner("burner", Material.ANVIL, 0);
	public static Block palm_sapling = new BlockPalmSapling("palm_sapling");
	public static Block palm_leaves = new BlockPalmLeaves("palm_leaves");
	public static Block palm_log = new BlockPalmLog("palm_log");
	public static Block palm_planks = new BlockPalmWood("palm_planks");
	public static Block palm_slab = new BlockPalmWoodSlab("palm_slab", Material.WOOD);
	public static Block wet_cobble = new RBlock("wet_cobble", Material.ROCK, 3.0f);
	
	
	//Ore BLocks
	public static Block aluminum_compound_ore = new BlockAluminumCompound("aluminum_compound_ore", Material.ROCK, 0.5f);
	public static Block copper_compound_ore = new BlockCopperCompound("copper_compound_ore", Material.ROCK, 0.5f);
	public static Block gold_compound_ore = new BlockGoldCompound("gold_compound_ore", Material.ROCK, 0.5f);
	public static Block iron_compound_ore = new BlockIronCompound("iron_compound_ore", Material.ROCK, 0.5f);
	public static Block lead_compound_ore = new BlockLeadCompound("lead_compound_ore", Material.ROCK, 0.5f);
	public static Block nickel_compound_ore = new BlockNickelCompound("nickel_compound_ore", Material.ROCK, 0.5f);
	public static Block silver_compound_ore = new BlockSilverCompound("silver_compound_ore", Material.ROCK, 0.5f);
	public static Block tin_compound_ore = new BlockTinCompound("tin_compound_ore", Material.ROCK, 0.5f);
	public static Block tungsten_compound_ore = new BlockTungstenCompound("tungsten_compound_ore", Material.ROCK, 0.5f);
	public static Block uranium_compound_ore = new BlockUraniumCompound("uranium_compound_ore", Material.ROCK, 0.5f);
	
	
	public static List<Block> blockList() {
		return blocks;
	}

	@SideOnly(Side.CLIENT)
	public static void registerModels() {

		ModelLoader.setCustomStateMapper(palm_leaves,
				new StateMap.Builder().ignore(BlockLeaves.DECAYABLE, BlockLeaves.CHECK_DECAY).build());
	
		ModelLoader.setCustomStateMapper(palm_planks, new StateMap.Builder().ignore(BlockPalmWood.DOUBLE).build());
		ModelLoader.setCustomStateMapper(palm_log, new StateMap.Builder().ignore(BlockPalmLog.VARIANT).build());
	}


	@SideOnly(Side.CLIENT)
	public static void registerBlockColors()
	{
		final Minecraft mc = FMLClientHandler.instance().getClient();
		final BlockColors colors = mc.getBlockColors();

		colors.registerBlockColorHandler(new IBlockColor()
		{
			@Override
			public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int tintIndex)
			{
				return world != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(world, pos) : 6726755;
			}
		}, new Block[] {palm_leaves});
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemBlockColors()
	{
		final Minecraft mc = FMLClientHandler.instance().getClient();
		final BlockColors blockColors = mc.getBlockColors();
		final ItemColors colors = mc.getItemColors();

		colors.registerItemColorHandler((stack, tintIndex) ->
		{
			IBlockState state = ((ItemBlock)stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());

			return blockColors.colorMultiplier(state, null, null, tintIndex);
		}, new Block[] {palm_leaves});
	}
	public static void register(FMLPreInitializationEvent preEvent) {
		for (Block block : blockList()) {
			ItemBlock iBlock = new ItemBlock(block);
			GameRegistry.register(block);
			GameRegistry.register(iBlock, block.getRegistryName());
		}
	}

	public static void registerRender(FMLInitializationEvent event) {
		for (Block block : blockList()) {
			Item item = new Item().getItemFromBlock(block);
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
			renderItem.getItemModelMesher().register(item, 0,
					new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));
		}
	}
}
