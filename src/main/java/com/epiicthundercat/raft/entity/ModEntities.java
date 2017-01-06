package com.epiicthundercat.raft.entity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.epiicthundercat.raft.Raft;
import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;

import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class ModEntities {
	public static void init() {
		int femalesharkegg = new Color(254, 85, 176).getRGB();

		// Every entity in our mod has an ID (local to this mod)

		// FemaleShark
		EntityRegistry.registerModEntity(EntitySharkFemale.class, "Female Shark", 1, Raft.instance, 80, 3, false, 0,
				femalesharkegg);

		/*
		 * We want our mob to spawn in Plains and ice plains biomes. If you
		 * don't add this then it will not spawn automatically but you can of
		 * course still make it spawn manually
		 */

		// Female Shark
		EntityRegistry.addSpawn(EntitySharkFemale.class, 2, 3, 8, EnumCreatureType.WATER_CREATURE, getWaterBiomeList());

		/*
		 * Mob Placement
		 */

		// Female Shark
		EntitySpawnPlacementRegistry.setPlacementType(EntitySharkFemale.class, SpawnPlacementType.ON_GROUND);

		/*
		 * This is the loot table for our mob
		 */

		// Female Shark
		// LootTableList.register(EntitySharkFemale.LOOT_FEMALESHARK);

		// BUG
		// LootTableList.register(TMBug.LOOT_BUG);

	}

	private static Biome[] getWaterBiomeList() {
		List<Biome> biomes = new ArrayList<Biome>();
		Iterator<Biome> biomeList = Biome.REGISTRY.iterator();
		while (biomeList.hasNext()) {
			Biome currentBiome = biomeList.next();
			List<SpawnListEntry> spawnList = currentBiome.getSpawnableList(EnumCreatureType.WATER_CREATURE);
			for (SpawnListEntry spawnEntry : spawnList) {
				if (spawnEntry.entityClass == EntitySquid.class) {
					biomes.add(currentBiome);
				}
			}
		}
		return biomes.toArray(new Biome[biomes.size()]);
	}

}
