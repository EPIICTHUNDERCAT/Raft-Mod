package com.epiicthundercat.raft.entity;

import java.awt.Color;

import com.epiicthundercat.raft.Raft;
import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.entity.monster.EntityEel;
import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;
import com.epiicthundercat.raft.entity.passive.EntityFish;

import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	public static void init() {
		int femalesharkegg = new Color(254, 85, 176).getRGB();

		// Every entity in our mod has an ID (local to this mod)

		// FemaleShark
		EntityRegistry.registerModEntity(getEntityResource("FemaleShark"), EntitySharkFemale.class, "FemaleShark", 0,
				Raft.instance, 80, 3, false, 0, femalesharkegg);
		// barrel
		EntityRegistry.registerModEntity(getEntityResource("Barrel"), FloatBarrel.class, "Barrel", 1, Raft.instance, 80,
				3, false);
		// thatch
		EntityRegistry.registerModEntity(getEntityResource("Thatch"), ThatchEntity.class, "Thatch", 2, Raft.instance,
				80, 3, false);
		// scrap
		EntityRegistry.registerModEntity(getEntityResource("Scrap"), ScrapEntity.class, "Scrap", 3, Raft.instance, 80,
				3, false);
		// plank
		EntityRegistry.registerModEntity(getEntityResource("Plank"), PlankEntity.class, "Plank", 4, Raft.instance, 80,
				3, false);
		// Fish
		EntityRegistry.registerModEntity(getEntityResource("Fish"), EntityFish.class, "Fish", 5, Raft.instance, 80, 3,
				false, 547853, femalesharkegg);
		// Eel
		EntityRegistry.registerModEntity(getEntityResource("Eel"), EntityEel.class, "Eel", 6, Raft.instance, 80, 3,
				false, 547853, femalesharkegg);
		/*
		 * We want our mob to spawn in Plains and ice plains biomes. If you
		 * don't add this then it will not spawn automatically but you can of
		 * course still make it spawn manually
		 */

		// Female Shark
		EntityRegistry.addSpawn(EntitySharkFemale.class, 1, 1, 3, EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN,
				Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER);
		// Fish
		EntityRegistry.addSpawn(EntityFish.class, 2, 3, 8, EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN,
				Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER);

		// Eel
		EntityRegistry.addSpawn(EntityEel.class, 2, 3, 8, EnumCreatureType.WATER_CREATURE, Biomes.DEEP_OCEAN,
				Biomes.OCEAN, Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER);

		/*
		 * Mob Placement
		 */

		// Female Shark
		EntitySpawnPlacementRegistry.setPlacementType(EntitySharkFemale.class, SpawnPlacementType.IN_WATER);
		// Fish
		EntitySpawnPlacementRegistry.setPlacementType(EntityFish.class, SpawnPlacementType.IN_WATER);
		// Eel
		EntitySpawnPlacementRegistry.setPlacementType(EntityEel.class, SpawnPlacementType.IN_WATER);

		/*
		 * This is the loot table for our mob
		 */

		// Female Shark
		LootTableList.register(EntitySharkFemale.LOOT_FEMALESHARK);
		// FISH
		LootTableList.register(EntityFish.LOOT_FISH);
		// FISH
		LootTableList.register(EntityEel.LOOT_EEL);

	}

	private static ResourceLocation getEntityResource(String entityName) {
		return new ResourceLocation(Reference.ID, entityName);
	}
}
