package com.epiicthundercat.raft.rafttileentitity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class RenderTileBurner extends TileEntitySpecialRenderer<TileBurner> {

	public void renderTileEntityAt(TileBurner te, double x, double y, double z, float partialTicks, int destroyStage) {
		IBlockState state = te.getWorld().getBlockState(te.getPos());

		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.disableLighting();

		IItemHandler inv = (IItemHandler) te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		assert (inv != null);

		GlStateManager.pushMatrix();

		GlStateManager.translate(x + 0.5D, y - 0.2D, z + 0.5D);
		GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
		for (int i = 0; i < 3; i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getCount() > 0) {
				GlStateManager.pushMatrix();

				float zz = (i - 1.0F) * 0.1875F;

				GlStateManager.translate(0.0F, 0.0F, zz);

				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

				Minecraft mc = Minecraft.getMinecraft();
				mc.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				mc.getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);

				GlStateManager.popMatrix();
			}
		}
		GlStateManager.popMatrix();

		GlStateManager.enableLighting();
	}
}