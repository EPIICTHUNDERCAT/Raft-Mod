package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.ConfigHandler;
import com.epiicthundercat.raft.entity.ModEntities;
import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;
import com.epiicthundercat.raft.init.RRecipes;
import com.epiicthundercat.raft.init.barrel.BarrelLootAdd;
import com.epiicthundercat.raft.integration.TANIntegration;
import com.epiicthundercat.raft.rafttileentitity.TileEntityRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	

	public void preInit(FMLPreInitializationEvent preEvent) {
		register(preEvent);
		TileEntityRegistry.registerTile();
		 ConfigHandler.init();
		//NGConfig.config(preEvent);

	}

	public void init(FMLInitializationEvent event) {
		registerRenders(event);
		ModEntities.init();
		BarrelLootAdd.init();
		
	}

	private void register(FMLPreInitializationEvent preEvent) {
		RItems.register(preEvent);
		
		RBlocks.register(preEvent);
		RRecipes.register(preEvent);
		registerEntities(preEvent);
		 //GameRegistry.registerWorldGenerator(new WorldGenOreOnReef(),1);
		MinecraftForge.EVENT_BUS.register(new REventHandler());
		if (Loader.isModLoaded("toughasnails")) {
			MinecraftForge.EVENT_BUS.register(new TANIntegration());
			
		}
	//	NetworkRegistry.INSTANCE.registerGuiHandler(Raft.instance, new RGuiHandler());
		
	

	}

	public void registerRenders(FMLInitializationEvent event) {

	}

	public void registerRender(FMLInitializationEvent event) {
	}
	public void registerEntities(FMLPreInitializationEvent preEvent) {
	}
}
