package com.epiicthundercat.raft.client.gui;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.block.ContainerBlockSeparator;
import com.epiicthundercat.raft.rafttileentitity.TileEntitySeparator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SeparatorGui extends GuiContainer {
	public static final ResourceLocation seperatortexture = new ResourceLocation(Reference.ID,
			"textures/gui/Separator.png");
	
	    private final IInventory tileSeparator;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 166;
	TileEntitySeparator seperator;

	
	
	
	public SeparatorGui(TileEntitySeparator tileEntity, ContainerBlockSeparator container, TileEntity te, InventoryPlayer playerInv, IInventory separatorInv){
		super(new ContainerBlockSeparator(playerInv, separatorInv));
		 
	        this.tileSeparator = separatorInv;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		 GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        this.mc.getTextureManager().bindTexture(seperatortexture);
	        int i = (this.width - this.xSize) / 2;
	        int j = (this.height - this.ySize) / 2;
	        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

	       /* if (TileEntitySeparator.isBurning(this.tileSeparator))
	        {
	            int k = this.getBurnLeftScaled(13);
	            this.drawTexturedModalRect(i + 62.2f, j + 35 + 28 - k, 176, 12 - k, 14, k + 1);
	        }
*/
	        int l = this.getCookProgressScaled(24);
	        this.drawTexturedModalRect(i + 58, j + 10, 176, 14, l + 1, 16);
	}
	
   
    private int getCookProgressScaled(int pixels)
    {
        int i = this.tileSeparator.getField(2);
        int j = this.tileSeparator.getField(3);
        return j != 0 && i != 0 ? i * pixels / j : 0;
    }

    private int getBurnLeftScaled(int pixels)
    {
        int i = this.tileSeparator.getField(1);

        if (i == 0)
        {
            i = 200;
        }

        return this.tileSeparator.getField(0) * pixels / i;
    }
}
