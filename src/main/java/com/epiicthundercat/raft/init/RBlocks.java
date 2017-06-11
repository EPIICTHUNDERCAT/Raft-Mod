package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.block.*;

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

	public static Block burner = new BlockBurner("burner", Material.ANVIL);
	public static Block palm_sapling = new BlockPalmSapling("palm_sapling");
	public static Block palm_leaves = new BlockPalmLeaves("palm_leaves");
	public static Block palm_log = new BlockPalmLog("palm_log");
	public static Block palm_planks = new BlockPalmWood("palm_planks");

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
