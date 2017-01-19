package com.epiicthundercat.raft.client.gui;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.block.ContainerBlockSeparator;
import com.epiicthundercat.raft.rafttileentitity.TileEntitySeparator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SeparatorGui extends GuiContainer {
	public static final ResourceLocation seperatortexture = new ResourceLocation(Reference.ID,
			"textures/gui/Separator.png");
	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	TileEntitySeparator seperator;

	public SeparatorGui(TileEntitySeparator tileEntity, ContainerBlockSeparator container, TileEntity te) {
		super(container);
		        xSize = WIDTH;
		        ySize = HEIGHT;
		        seperator= (TileEntitySeparator) tileEntity;
		}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(seperatortexture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
}
