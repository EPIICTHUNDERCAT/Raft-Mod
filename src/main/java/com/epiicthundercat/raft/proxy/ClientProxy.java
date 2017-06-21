package com.epiicthundercat.raft.proxy;

import com.epiicthundercat.raft.client.renderer.RenderEel;
import com.epiicthundercat.raft.client.renderer.RenderEntitySharkFemale;
import com.epiicthundercat.raft.client.renderer.RenderFish;
import com.epiicthundercat.raft.client.renderer.RenderFloatingBarrel;
import com.epiicthundercat.raft.client.renderer.RenderHook;
import com.epiicthundercat.raft.client.renderer.RenderPlank;
import com.epiicthundercat.raft.client.renderer.RenderScrap;
import com.epiicthundercat.raft.client.renderer.RenderThatch;
import com.epiicthundercat.raft.entity.EntityHook;
import com.epiicthundercat.raft.entity.FloatBarrel;
import com.epiicthundercat.raft.entity.PlankEntity;
import com.epiicthundercat.raft.entity.ScrapEntity;
import com.epiicthundercat.raft.entity.ThatchEntity;
import com.epiicthundercat.raft.entity.monster.EntityEel;
import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;
import com.epiicthundercat.raft.entity.passive.EntityFish;
import com.epiicthundercat.raft.init.RBlocks;
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public void preInit(FMLPreInitializationEvent preEvent) {
		super.preInit(preEvent);
		// ModEntities.initModels();
		RBlocks.registerModels();
		REventHandler.registerItemRenderers();

	}

	public void init(FMLInitializationEvent event) {
		super.init(event);
		RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
		RenderManager rm = Minecraft.getMinecraft().getRenderManager();

		rm.entityRenderMap.put(EntitySharkFemale.class, new RenderEntitySharkFemale(rm));
		rm.entityRenderMap.put(EntityFish.class, new RenderFish(rm));
		rm.entityRenderMap.put(FloatBarrel.class, new RenderFloatingBarrel(rm));
		rm.entityRenderMap.put(PlankEntity.class, new RenderPlank(rm));
		rm.entityRenderMap.put(ScrapEntity.class, new RenderScrap(rm));
		rm.entityRenderMap.put(ThatchEntity.class, new RenderThatch(rm));
		rm.entityRenderMap.put(EntityEel.class, new RenderEel(rm));

		if (event.getSide().isClient()) {
			RBlocks.registerBlockColors();
			RBlocks.registerItemBlockColors();
		}
	}

	@Override
	public void registerRenders(FMLInitializationEvent event) {
		RItems.registerRender(event);
		RBlocks.registerRender(event);
	}
	@Override
	public void registerEntities(FMLPreInitializationEvent preEvent) {
		super.registerEntities(preEvent);
		RenderingRegistry.registerEntityRenderingHandler(EntityHook.class, new IRenderFactory<EntityHook>() {
            @Override public RenderHook createRenderFor (RenderManager manager) {
                return new RenderHook(manager, RItems.hook_part);
            }
        });
	}

}