package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.entity.FloatBarrel;
import com.epiicthundercat.raft.item.ItemBarrel;
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
	public static Item thatch = new RItem("thatch");
	public static Item rope = new RItem("rope");
	public static Item plank = new RItem("plank");
	public static Item barrel = new ItemBarrel("barrel", FloatBarrel.Type.OAK);
	public static Item spruce_barrel = new ItemBarrel("spruce_barrel", FloatBarrel.Type.SPRUCE);
	public static Item birch_barrel = new ItemBarrel("birch_barrel", FloatBarrel.Type.BIRCH);
	public static Item dark_oak_barrel = new ItemBarrel("dark_oak_barrel", FloatBarrel.Type.DARK_OAK);
	public static Item acacia_barrel = new ItemBarrel("acacia_barrel", FloatBarrel.Type.ACACIA);
	public static Item jungle_barrel = new ItemBarrel("jungle_barrel", FloatBarrel.Type.JUNGLE);
	public static Item spear = new RItem("spear");
	public static Item tin_can = new RItem("tin_can");
	public static Item scrap = new RItem("scrap");
	
	
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
