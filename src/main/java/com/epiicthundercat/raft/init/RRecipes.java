package com.epiicthundercat.raft.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RRecipes {

	public static void register(FMLPreInitializationEvent preEvent) {

		GameRegistry.registerFuelHandler(new RFuelHandler());

		/**
		 * RECIPES
		 */
		


		// Plank > Planks

		// Rope
		GameRegistry.addShapelessRecipe(new ItemStack(RItems.rope), new ItemStack(RItems.thatch),
				new ItemStack(RItems.thatch), new ItemStack(RItems.thatch));
		// Tin Can
		GameRegistry.addShapedRecipe(new ItemStack(RItems.tin_can), "S S", " S ", "   ", 'S',
				new ItemStack(RItems.scrap));
		// Spear
		GameRegistry.addShapedRecipe(new ItemStack(RItems.spear), " F", "S ", 'F', new ItemStack(Items.FLINT), 'S',
				new ItemStack(Items.STICK));
		// Compressed Scraps
		GameRegistry.addShapedRecipe(new ItemStack(RItems.compressed_scraps), "SSS", "SSS", "SSS", 'S',
				new ItemStack(RItems.scrap));
		// DoubleCompressed Scraps
		GameRegistry.addShapedRecipe(new ItemStack(RItems.double_compressed_scraps), "SSS", "SSS", "SSS", 'S',
				new ItemStack(RItems.compressed_scraps));
		// Hook
		GameRegistry.addShapedRecipe(new ItemStack(RItems.hook), " S", "R ", 'S', new ItemStack(RItems.scrap), 'R',
				new ItemStack(RItems.rope));
		// Dirt
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.DIRT), "DD", "DD", 'D', new ItemStack(RItems.dirt_piece));
		// Sand
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.SAND), "DD", "DD", 'D', new ItemStack(RItems.sand_piece));
		// Gravel
		GameRegistry.addShapedRecipe(new ItemStack(Blocks.GRAVEL), "DD", "DD", 'D', new ItemStack(RItems.gravel_piece));
	/*	// Separator
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(RBlocks.separator),
				new Object[] { "CTC", "CRC", "CLC", 'L', "logWood", 'C', new ItemStack(RItems.double_compressed_scraps),
						'R', new ItemStack(RItems.rope), 'T', new ItemStack(RItems.tin_can_potion) }));
		*/ // Wood>planks
		GameRegistry.addShapelessRecipe(new ItemStack(RBlocks.palm_planks, 4), new ItemStack(RBlocks.palm_log));

		// Dirty Filled Tin > Filled Tin Can
		GameRegistry.addSmelting(new ItemStack(RItems.tin_can_dirty), new ItemStack(RItems.tin_can_potion), 1);

		GameRegistry.addSmelting(RBlocks.palm_log, new ItemStack(Items.COAL, 1, 1), 10);
		GameRegistry.addSmelting(new ItemStack(Items.BEETROOT), new ItemStack(RItems.cooked_beet), 10);
		GameRegistry.addSmelting(new ItemStack(RItems.raw_shark_meat), new ItemStack(RItems.cooked_shark_meat), 20);

		// INGOTS
		// aluminum
		GameRegistry.addSmelting(new ItemStack(RItems.aluminum_ore_material), new ItemStack(RItems.aluminum_ingot), 50);
		// aluminum ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.aluminum_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.aluminum_compound), 'S', new ItemStack(RItems.stones));

		// tin
		GameRegistry.addSmelting(new ItemStack(RItems.tin_ore_material), new ItemStack(RItems.tin_ingot), 50);
		// tin ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.tin_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.tin_compound), 'S', new ItemStack(RItems.stones));

		// lead
		GameRegistry.addSmelting(new ItemStack(RItems.lead_ore_material), new ItemStack(RItems.lead_ingot), 50);
		// lead ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.lead_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.lead_compound), 'S', new ItemStack(RItems.stones));

		// silver
		GameRegistry.addSmelting(new ItemStack(RItems.silver_ore_material), new ItemStack(RItems.silver_ingot), 50);
		// silver ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.silver_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.silver_compound), 'S', new ItemStack(RItems.stones));

		// copper
		GameRegistry.addSmelting(new ItemStack(RItems.copper_ore_material), new ItemStack(RItems.copper_ingot), 50);
		// copper ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.copper_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.copper_compound), 'S', new ItemStack(RItems.stones));

		// tungsten
		GameRegistry.addSmelting(new ItemStack(RItems.tungsten_ore_material), new ItemStack(RItems.tungsten_ingot), 50);
		// tungsten ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.tungsten_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.tungsten_compound), 'S', new ItemStack(RItems.stones));

		// ardite
		GameRegistry.addSmelting(new ItemStack(RItems.ardite_ore_material), new ItemStack(RItems.ardite_ingot), 50);
		// ardite ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.ardite_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.ardite_compound), 'S', new ItemStack(RItems.stones));

		// cobalt
		GameRegistry.addSmelting(new ItemStack(RItems.cobalt_ore_material), new ItemStack(RItems.cobalt_ingot), 50);
		// cobalt ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.cobalt_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.cobalt_compound), 'S', new ItemStack(RItems.stones));

		// uranium
		GameRegistry.addSmelting(new ItemStack(RItems.uranium_ore_material), new ItemStack(RItems.uranium_ingot), 50);
		// uranium ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.uranium_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.uranium_compound), 'S', new ItemStack(RItems.stones));

		// nickel
		GameRegistry.addSmelting(new ItemStack(RItems.nickel_ore_material), new ItemStack(RItems.nickel_ingot), 50);
		// nickel ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.nickel_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.nickel_compound), 'S', new ItemStack(RItems.stones));

		// iron
		GameRegistry.addSmelting(new ItemStack(RItems.iron_ore_material), new ItemStack(Items.IRON_INGOT), 50);
		// iron ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.iron_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.iron_compound), 'S', new ItemStack(RItems.stones));

		// gold
		GameRegistry.addSmelting(new ItemStack(RItems.gold_ore_material), new ItemStack(Items.GOLD_INGOT), 50);
		// gold ore material
		GameRegistry.addShapedRecipe(new ItemStack(RItems.gold_ore_material), "DS", "SD", 'D',
				new ItemStack(RItems.gold_compound), 'S', new ItemStack(RItems.stones));

		OreDictionary.registerOre("ingotLead", new ItemStack(RItems.lead_ingot));
		OreDictionary.registerOre("ingotCopper", new ItemStack(RItems.copper_ingot));
		OreDictionary.registerOre("ingotSilver", new ItemStack(RItems.silver_ingot));
		OreDictionary.registerOre("ingotAluminum", new ItemStack(RItems.aluminum_ingot));
		OreDictionary.registerOre("ingotTin", new ItemStack(RItems.tin_ingot));
		OreDictionary.registerOre("ingotSteel", new ItemStack(RItems.steel_ingot));
		OreDictionary.registerOre("ingotNickel", new ItemStack(RItems.nickel_ingot));
		OreDictionary.registerOre("ingotArdite", new ItemStack(RItems.ardite_ingot));
		OreDictionary.registerOre("ingotCobalt", new ItemStack(RItems.cobalt_ingot));
		OreDictionary.registerOre("ingotTungsten", new ItemStack(RItems.tungsten_ingot));

		OreDictionary.registerOre("plastic", new ItemStack(RItems.plastic));
		OreDictionary.registerOre("ingotPlastic", new ItemStack(RItems.plastic));
		OreDictionary.registerOre("ingotRubber", new ItemStack(RItems.plastic));
		OreDictionary.registerOre("rubber", new ItemStack(RItems.plastic));
		OreDictionary.registerOre("waterBottle", new ItemStack(Items.POTIONITEM));
		OreDictionary.registerOre("waterBottle", new ItemStack(RItems.tin_can_potion));
		OreDictionary.registerOre("logWood", new ItemStack(RBlocks.palm_log, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("treeLeaves", new ItemStack(RBlocks.palm_leaves, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("treeSapling", new ItemStack(RBlocks.palm_sapling, 1, OreDictionary.WILDCARD_VALUE));

		OreDictionary.registerOre("plankWood", new ItemStack(RBlocks.palm_planks, 1, OreDictionary.WILDCARD_VALUE));

		// OreDictionary.registerOre("slabWood", new
		// ItemStack(RBlocks.palm_slab, 1, OreDictionary.WILDCARD_VALUE));

		// OreDictionary.registerOre("stairWood", new
		// ItemStack(RBlocks.palm_stairs, 1, OreDictionary.WILDCARD_VALUE));

		// OreDictionary.registerOre("fenceWood", new
		// ItemStack(RBlocks.palm_fence, 1, OreDictionary.WILDCARD_VALUE));

		// OreDictionary.registerOre("fenceGateWood", new
		// ItemStack(RBlocks.palm_fence_gate,, 1,
		// OreDictionary.WILDCARD_VALUE));

	}
}
