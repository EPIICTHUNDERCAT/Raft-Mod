package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.entity.FloatBarrel;
import com.epiicthundercat.raft.item.ItemBarrel;
import com.epiicthundercat.raft.item.ItemPlank;
import com.epiicthundercat.raft.item.ItemScrap;
import com.epiicthundercat.raft.item.ItemThatch;
import com.epiicthundercat.raft.item.RItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RItems {
	public static List<Item> items = new ArrayList();
	
	public static Item hook = new RItem("hook");
	public static Item thatch = new ItemThatch("thatch");
	public static Item rope = new RItem("rope");
	public static Item plank = new ItemPlank("plank");
	public static Item barrel = new ItemBarrel("barrel", FloatBarrel.Type.OAK);
	public static Item spruce_barrel = new ItemBarrel("spruce_barrel", FloatBarrel.Type.SPRUCE);
	public static Item birch_barrel = new ItemBarrel("birch_barrel", FloatBarrel.Type.BIRCH);
	public static Item dark_oak_barrel = new ItemBarrel("dark_oak_barrel", FloatBarrel.Type.DARK_OAK);
	public static Item acacia_barrel = new ItemBarrel("acacia_barrel", FloatBarrel.Type.ACACIA);
	public static Item jungle_barrel = new ItemBarrel("jungle_barrel", FloatBarrel.Type.JUNGLE);
	public static Item spear = new RItem("spear");
	public static Item tin_can = new RItem("tin_can");
	public static Item scrap = new ItemScrap("scrap");
	public static Item dirt_piece = new RItem("dirt_piece");
	public static Item sand_piece = new RItem("sand_piece");
	public static Item gravel_piece = new RItem("gravel_piece");
	public static Item compressed_scraps = new RItem("compressed_scraps");
	public static Item double_compressed_scraps = new RItem("double_compressed_scraps");
	public static Item iron_compound = new RItem("iron_compound");
	public static Item tin_compound = new RItem("tin_compound");
	public static Item copper_compound = new RItem("copper_compound");
	public static Item silver_compound = new RItem("silver_compound");
	public static Item gold_compound = new RItem("gold_compound");
	public static Item lead_compound = new RItem("lead_compound");
	public static Item aluminium_compound = new RItem("aluminium_compound");
	public static Item ardite_compound = new RItem("ardite_compound");
	public static Item cobalt_compound = new RItem("steel_compound");
	public static Item steel_compound = new RItem("cobalt_compound");
	public static Item nickel_compound = new RItem("nickel_compound");
	public static Item lead_ingot = new RItem("lead_ingot");
	public static Item copper_ingot = new RItem("copper_ingot");
	public static Item aluminium_ingot = new RItem("aluminium_ingot");
	public static Item silver_ingot = new RItem("silver_ingot");
	public static Item tin_ingot = new RItem("tin_ingot");
	public static Item steel_ingot = new RItem("steel_ingot");
	public static Item nickel_ingot = new RItem("nickel_ingot");
	public static Item ardite_ingot = new RItem("ardite_ingot");
	public static Item cobalt_ingot = new RItem("cobalt_ingot");
	public static Item plastic = new RItem("plastic");
	//public static Item ardite_ingot = new RItem("ardite_ingot");
	//public static Item ardite_ingot = new RItem("ardite_ingot");
	//public static Item ardite_ingot = new RItem("ardite_ingot");
	
	private static List<Item> getItems() {
		return items;
	}
	
	public static void register(FMLPreInitializationEvent preEvent) {
		for (Item item : getItems()) {
			GameRegistry.register(item);
		}
	}

	public static void registerRender(FMLInitializationEvent event) {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		for (Item item : getItems()) {
			renderItem.getItemModelMesher().register(item, 0,
					new ModelResourceLocation(item.getRegistryName().toString(), "inventory"));
		}
	}
	
	
}
