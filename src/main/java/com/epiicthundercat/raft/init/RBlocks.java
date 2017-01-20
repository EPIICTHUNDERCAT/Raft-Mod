package com.epiicthundercat.raft.init;
import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.block.BlockPalmLeaves;
import com.epiicthundercat.raft.block.BlockPalmSapling;
import com.epiicthundercat.raft.block.BlockSeparator;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
public class RBlocks {

	public static List<Block> blocks = new ArrayList();
	
	public static Block separator = new BlockSeparator("separator", Material.ANVIL);
	public static Block palm_sapling = new BlockPalmSapling("palm_sapling");
	public static Block palm_leaves = new BlockPalmLeaves("palm_leaves");
	
	public static List<Block> blockList() {
		return blocks;		
	}
	
	public static void register(FMLPreInitializationEvent preEvent) {
		for (Block block : blockList()){
			ItemBlock iBlock = new ItemBlock(block);
			GameRegistry.register(block);
		    GameRegistry.register(iBlock, block.getRegistryName());
		}
	}
	
	public static void registerRender(FMLInitializationEvent event) {
		for (Block block : blockList()){
		Item item = new Item().getItemFromBlock(block);
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
    	renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(block.getRegistryName().toString(), "inventory"));
		}
	}
}

