package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.client.renderer.RenderEntitySharkFemale;
import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent preEvent) {
		super.preInit(preEvent);
		//ModEntities.initModels();
	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		
		
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();
		rm.entityRenderMap.put(EntitySharkFemale.class, new RenderEntitySharkFemale(rm));
		
		
		  

	}
	
	
	@Override
	public void registerRenders(FMLInitializationEvent event) {
		RItems.registerRender(event);
		//NGBlocks.registerRender(event);
	}
	/*
	@Override
	public void registerEntities(FMLPreInitializationEvent preEvent) {
		super.registerEntities(preEvent);

		RenderingRegistry.registerEntityRenderingHandler(EntityWitchProjectile.class,
				new IRenderFactory<EntityWitchProjectile>() {
					@Override
					public RenderWitchProjectile createRenderFor(RenderManager manager) {
						return new RenderWitchProjectile(manager, TMItems.witch_projectile);
					}
				});

		RenderingRegistry.registerEntityRenderingHandler(EntityBatPee.class,
				new IRenderFactory<EntityBatPee>() {
					@Override
					public RenderBatPee createRenderFor(RenderManager manager) {
						return new RenderBatPee(manager, TMItems.bat_pee);
					}
				});
	}
	*/
}