package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.client.renderer.RenderEntitySharkFemale;
import com.epiicthundercat.raft.client.renderer.RenderFish;
import com.epiicthundercat.raft.client.renderer.RenderFloatingBarrel;
import com.epiicthundercat.raft.client.renderer.RenderPlank;
import com.epiicthundercat.raft.client.renderer.RenderScrap;
import com.epiicthundercat.raft.client.renderer.RenderThatch;
import com.epiicthundercat.raft.entity.FloatBarrel;
import com.epiicthundercat.raft.entity.PlankEntity;
import com.epiicthundercat.raft.entity.ScrapEntity;
import com.epiicthundercat.raft.entity.ThatchEntity;
import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;
import com.epiicthundercat.raft.entity.passive.EntityFish;
import com.epiicthundercat.raft.init.RBlocks;
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
		rm.entityRenderMap.put(EntityFish.class, new RenderFish(rm));
		rm.entityRenderMap.put(FloatBarrel.class, new RenderFloatingBarrel(rm));
		rm.entityRenderMap.put(PlankEntity.class, new RenderPlank(rm));
		rm.entityRenderMap.put(ScrapEntity.class, new RenderScrap(rm));
		rm.entityRenderMap.put(ThatchEntity.class, new RenderThatch(rm));
		//rm.entityRenderMap.put(FloatBarrel.class, new RenderFloatingBarrel(rm));
		
		  

	}
	
	
	@Override
	public void registerRenders(FMLInitializationEvent event) {
		RItems.registerRender(event);
		RBlocks.registerRender(event);
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