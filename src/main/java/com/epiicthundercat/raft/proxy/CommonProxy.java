package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.entity.ModEntities;
import com.epiicthundercat.raft.init.RItems;

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
	
	}

	private void register(FMLPreInitializationEvent preEvent) {
		RItems.register(preEvent);
		//Blocks.register(preEvent);
		//MinecraftForge.EVENT_BUS.register(new EventHandler());
		

	}

	public void registerRenders(FMLInitializationEvent event) {

	}

	public void registerRender(FMLInitializationEvent event) {
	}
	public void registerEntities(FMLPreInitializationEvent preEvent) {
		}
}
