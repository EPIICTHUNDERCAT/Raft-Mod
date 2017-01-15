package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.entity.ModEntities;
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;
import com.epiicthundercat.raft.init.barrel.BarrelLootAdd;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent preEvent) {
		register(preEvent);
		//NGConfig.config(preEvent);

	}

	public void init(FMLInitializationEvent event) {
		registerRenders(event);
		ModEntities.init();
		BarrelLootAdd.init();
	
	}

	private void register(FMLPreInitializationEvent preEvent) {
		RItems.register(preEvent);
		//Blocks.register(preEvent);
		MinecraftForge.EVENT_BUS.register(new REventHandler());
		

	}

	public void registerRenders(FMLInitializationEvent event) {

	}

	public void registerRender(FMLInitializationEvent event) {
	}
	public void registerEntities(FMLPreInitializationEvent preEvent) {
		}
}
