package com.epiicthundercat.raft.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class RRecipes {

	public static void register(FMLPreInitializationEvent preEvent) {
		RecipeHandler.addSeparatorSeperateRecipe(Items.WATER_BUCKET, new ItemStack(RItems.dirt_piece),
				new ItemStack(RItems.sand_piece), 25, new ItemStack(RItems.gravel_piece), 10, null, 0, null, 0);
		RecipeHandler.addSeparatorSeperateRecipe(RItems.scrap, new ItemStack(RItems.aluminum_compound),
				new ItemStack(RItems.copper_compound), 10, null, 0, null, 0, null, 0);

		// Rope
		GameRegistry.addShapelessRecipe(new ItemStack(RItems.rope), new ItemStack(RItems.thatch),
				new ItemStack(RItems.thatch), new ItemStack(RItems.thatch));
		// Tin Can
		GameRegistry.addShapedRecipe(new ItemStack(RItems.tin_can), "   ", "S  S", " S ", 'S',
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

		// Dirty Filled Tin > Filled Tin Can
		GameRegistry.addSmelting(new ItemStack(RItems.tin_can_dirty), new ItemStack(RItems.tin_can_potion), 1);

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
