package com.epiicthundercat.raft;

import com.epiicthundercat.raft.init.SendMovePack;
import com.epiicthundercat.raft.proxy.CommonProxy;
import com.epiicthundercat.raft.world.WorldGenOreOnReef;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Reference.ID, name = Reference.NAME, version = Reference.VER, dependencies = Reference.DEPENDENCIES)
public class Raft {
	public static float windX = 0.07f;
	public static float windZ = -0.07f;
	@Instance(value = Reference.ID)
	public static Raft instance;
	public static SimpleNetworkWrapper network;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.SERVER_PROXY)
	public static CommonProxy proxy;
	

	@EventHandler
	private void preInit(FMLPreInitializationEvent preEvent) {
		network = NetworkRegistry.INSTANCE.newSimpleChannel("Raft");
		network.registerMessage(SendMovePack.class, SendMovePack.class, 0, Side.CLIENT);
		network.registerMessage(SendMovePack.class, SendMovePack.class, 0, Side.SERVER);
		proxy.preInit(preEvent);
		 GameRegistry.registerWorldGenerator(new WorldGenOreOnReef(),1);
	}

	@EventHandler
	private void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

}
