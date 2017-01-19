package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.client.gui.RGuiHandler;
import com.epiicthundercat.raft.entity.ModEntities;
import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;
import com.epiicthundercat.raft.init.barrel.BarrelLootAdd;
import com.epiicthundercat.raft.rafttileentitity.TileEntityRegistry;
import com.epiicthundercat.raft.rafttileentitity.TileEntitySeperator;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void preInit(FMLPreInitializationEvent preEvent) {
		register(preEvent);
		TileEntityRegistry.registerTile();
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
		  GameRegistry.registerTileEntity(TileEntitySeperator.class, "TileEntitySeperator");
		MinecraftForge.EVENT_BUS.register(new REventHandler());
		MinecraftForge.EVENT_BUS.register(new RGuiHandler());

	}

	public void registerRenders(FMLInitializationEvent event) {

	}

	public void registerRender(FMLInitializationEvent event) {
	}
	public void registerEntities(FMLPreInitializationEvent preEvent) {
		}
}
