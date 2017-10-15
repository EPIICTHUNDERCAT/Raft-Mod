package com.epiicthundercat.raft;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler {
	public static Configuration CONFIG;
	private static String DEF_CAT = "Options";
	private static String MOBS = "Mobs";
	@SubscribeEvent
	public void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equals(Reference.ID)) {
			init();
		}
	}

	public static void init() {
		if (CONFIG == null) {
			CONFIG = new Configuration(new File(Reference.CONFIG_FILE));
			MinecraftForge.EVENT_BUS.register(new ConfigHandler());
		}
		Reference.Seagull_Spawn_Rate = CONFIG.getInt("SeagullSpawnRate", MOBS, 100,0,100,
				"Spawn Rates 0-100");
		Reference.Seagull_Spawn_MIN_Amount = CONFIG.getInt("SharkSpawnRate", MOBS, 5,0,100,
				"Spawn Rates 0-100");
		Reference.Seagull_Spawn_MAX_Amount = CONFIG.getInt("SharkSpawnRate", MOBS, 9,0,100,
				"Spawn Rates 0-100");
		
		
		Reference.Fish_Spawn_Rate = CONFIG.getInt("FishSpawnRate", MOBS, 100,0,100,
				"Spawn Rates 0-100");
		Reference.Fish_Spawn_MIN_Amount = CONFIG.getInt("SharkSpawnRate", MOBS, 4,0,100,
				"Spawn Rates 0-100");
		Reference.Fish_Spawn_MAX_Amount = CONFIG.getInt("SharkSpawnRate", MOBS, 8,0,100,
				"Spawn Rates 0-100");
		
		
		Reference.Eel_Spawn_Rate = CONFIG.getInt("EelSpawnRate", MOBS, 70,0,100,
				"Spawn Rates 0-100");
		Reference.Eel_Spawn_MIN_Amount = CONFIG.getInt("EelSpawnMin", MOBS, 4,0,100,
				"Spawn Rates 0-100");
		Reference.Eel_Spawn_MAX_Amount = CONFIG.getInt("EelSpawnMax", MOBS, 4,0,100,
				"Spawn Rates 0-100");
		
	
		
		Reference.Shark_Spawn_Rate = CONFIG.getInt("SharkSpawnRate", MOBS, 70,0,100,
				"Spawn Rates 0-100");
		Reference.Shark_Spawn_MIN_Amount = CONFIG.getInt("SharkSpawnMin", MOBS, 1,0,100,
				"Spawn Rates 0-100");
		Reference.Shark_Spawn_MAX_Amount = CONFIG.getInt("SharkSpawnMax", MOBS, 2,0,100,
				"Spawn Rates 0-100");
		
		Reference.Coconut_Drink_Yield = CONFIG.getInt("Yield per coconut drink", DEF_CAT, 10, 0, 20, "Drink Amount- 20 is full");
		Reference.Coconut_Hydration_Yield = CONFIG.getFloat("Amount of Hydration Per Drink", DEF_CAT, 6.7f, 0, 20, "Hydration Amount- 20 is full");
		
		Reference.Tin_Can_Drink_Yield = CONFIG.getInt("Yield per Tin Can drink", DEF_CAT, 10, 0, 20, "Tin Can Drink Amount- 20 is full");
		Reference.Tin_Can_Hydration_Yield = CONFIG.getFloat("Amount of Hydration Per Drink", DEF_CAT, 6.7f, 0, 20, "Tin Can Hydration Amount- 20 is full");
		
		Reference.Air_On_Scuba = CONFIG.getInt("", DEF_CAT, 900, 0, 9999, "Amount of Air on the Scuba Plastic Bottle ");
		Reference.Damage_If_Scuba_Air_Runs_Out = CONFIG.getInt("", DEF_CAT, 2, 0, 200, "Damage when drowning after scuba gear runs out!");
		
		Reference.Is_Scrap_Placeable = CONFIG.getBoolean("Is Scrap Placeable", DEF_CAT, true, "True for yes, false for no");
		Reference.Is_Thatch_Placeable = CONFIG.getBoolean("Is Thatch Placeable", DEF_CAT, true, "True for yes, false for no");
		Reference.Is_Plank_Placeable = CONFIG.getBoolean("Is Plank Placeable", DEF_CAT, true, "True for yes, false for no");
		Reference.Is_Barrel_Placeable = CONFIG.getBoolean("Is Barrel Placeable", DEF_CAT, true, "True for yes, false for no");
		

		
		
		if (CONFIG.hasChanged()) {
			CONFIG.save();
		}
	}
}
