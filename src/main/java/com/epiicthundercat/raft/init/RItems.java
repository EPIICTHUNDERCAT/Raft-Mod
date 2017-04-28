package com.epiicthundercat.raft.init;

import java.util.ArrayList;
import java.util.List;

import com.epiicthundercat.raft.entity.FloatBarrel;
import com.epiicthundercat.raft.item.ItemBarrel;
import com.epiicthundercat.raft.item.ItemCoconut;
import com.epiicthundercat.raft.item.ItemHook;
import com.epiicthundercat.raft.item.ItemPlank;
import com.epiicthundercat.raft.item.ItemScrap;
import com.epiicthundercat.raft.item.ItemThatch;
import com.epiicthundercat.raft.item.ItemTinCan;
import com.epiicthundercat.raft.item.ItemTinPotion;
import com.epiicthundercat.raft.item.RItem;
import com.epiicthundercat.raft.item.RItemFood;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RItems {
	public static List<Item> items = new ArrayList();

	public static Item hook = new ItemHook("hook");
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
	public static Item tin_can = new ItemTinCan("tin_can");
	public static Item scrap = new ItemScrap("scrap");
	public static Item dirt_piece = new RItem("dirt_piece");
	public static Item sand_piece = new RItem("sand_piece");
	public static Item gravel_piece = new RItem("gravel_piece");
	public static Item compressed_scraps = new RItem("compressed_scraps");
	public static Item double_compressed_scraps = new RItem("double_compressed_scraps");

	// IRON
	public static Item iron_compound = new RItem("iron_compound");
	public static Item iron_ore_material = new RItem("iron_ore_material");

	// TIN
	public static Item tin_compound = new RItem("tin_compound");
	public static Item tin_ingot = new RItem("tin_ingot");
	public static Item tin_ore_material = new RItem("tin_ore_material");

	// COPPER
	public static Item copper_compound = new RItem("copper_compound");
	public static Item copper_ingot = new RItem("copper_ingot");
	public static Item copper_ore_material = new RItem("copper_ore_material");

	// SILVER
	public static Item silver_compound = new RItem("silver_compound");
	public static Item silver_ingot = new RItem("silver_ingot");
	public static Item silver_ore_material = new RItem("silver_ore_material");

	// GOLD
	public static Item gold_ore_material = new RItem("gold_ore_material");
	public static Item gold_compound = new RItem("gold_compound");

	// LEAD
	public static Item lead_compound = new RItem("lead_compound");
	public static Item lead_ore_material = new RItem("lead_ore_material");
	public static Item lead_ingot = new RItem("lead_ingot");

	// TUNGSTEN
	public static Item tungsten_ingot = new RItem("tungsten_ingot");
	public static Item tungsten_compound = new RItem("tungsten_compound");
	public static Item tungsten_ore_material = new RItem("tungsten_ore_material");

	// ALUMINUM
	public static Item aluminum_compound = new RItem("aluminum_compound");
	public static Item aluminum_ingot = new RItem("aluminum_ingot");
	public static Item aluminum_ore_material = new RItem("aluminum_ore_material");

	// ARDITE
	public static Item ardite_compound = new RItem("ardite_compound");
	public static Item ardite_ingot = new RItem("ardite_ingot");
	public static Item ardite_ore_material = new RItem("ardite_ore_material");

	// COBALT
	public static Item cobalt_compound = new RItem("cobalt_compound");
	public static Item cobalt_ingot = new RItem("cobalt_ingot");
	public static Item cobalt_ore_material = new RItem("cobalt_ore_material");

	// STEEL
	public static Item steel_compound = new RItem("steel_compound");
	public static Item steel_ingot = new RItem("steel_ingot");
	public static Item steel_ore_material = new RItem("steel_ore_material");

	// NICKEL
	public static Item nickel_compound = new RItem("nickel_compound");
	public static Item nickel_ingot = new RItem("nickel_ingot");
	public static Item nickel_ore_material = new RItem("nickel_ore_material");

	// URANIUM
	public static Item uranium_ingot = new RItem("uranium_ingot");
	public static Item uranium_compound = new RItem("uranium_compound");
	public static Item uranium_ore_material = new RItem("uranium_ore_material");

	public static Item plastic = new RItem("plastic");
	public static Item coconut = new ItemCoconut("coconut");
	public static Item coconut_shell = new RItemFood("coconut_shell", 5, 5, false);
	public static Item cooked_beet = new RItemFood("cooked_beet", 10, 10, false);
	public static Item nail = new RItem("nail");
	public static Item sulfur = new RItem("sulfur");
	public static Item tin_can_potion = new ItemTinPotion("filled_tin_can");
	public static Item tin_can_dirty = new RItem("tin_can_dirty");
	public static Item raw_shark_meat = new RItemFood("raw_shark_meat", 3, 1, true);
	public static Item cooked_shark_meat = new RItemFood("cooked_shark_meat", 10, 10, true);
	public static Item stones = new RItem("stones");

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
