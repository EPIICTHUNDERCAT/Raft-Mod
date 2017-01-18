package com.epiicthundercat.raft.client.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.VariablesSeperator;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class GuiSeperator extends GuiContainer {
	int i = 0;
	int j = 0;
	int k = 0;
	EntityPlayer entity = null;

	public GuiSeperator(World world, int i, int j, int k, EntityPlayer entity) {
		super(null);
		this.i = i;
		this.j = j;
		this.k = k;
		this.entity = entity;
		this.xSize = 176;
		this.ySize = 166;
		
	}

	private static final ResourceLocation texture = new ResourceLocation(Reference.ID, "textures/gui/Seperator.png");

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		int posX = this.width / 2;
		int posY = this.height / 2;
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.renderEngine.bindTexture(texture);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

		this.zLevel = 100.0F;

		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.ID, "textures/gui/ArrowDown.png"));
		drawTexturedModalRect(this.guiLeft + 4, this.guiTop + 27, 0, 0, 256, 256);

		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.ID, "textures/gui/fire.png"));
		drawTexturedModalRect(this.guiLeft + 5, this.guiTop + 7, 0, 0, 256, 256);

		this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.ID, "textures/gui/ArrowSide.png"));
		drawTexturedModalRect(this.guiLeft + 48, this.guiTop + 19, 0, 0, 256, 256);
	}

	protected void mouseClicked(int par1, int par2, int par3) throws IOException {
		super.mouseClicked(par1, par2, par3);
	}

	public void updateScreen() {
		super.updateScreen();
		int posX = this.width / 2;
		int posY = this.height / 2;
	}

	protected void keyTyped(char par1, int par2) throws IOException {
		super.keyTyped(par1, par2);
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		int posX = this.width / 2;
		int posY = this.height / 2;
		this.fontRendererObj.drawString("Cheap Seperator", 61, 3, 16777215);
		this.fontRendererObj.drawString("" + VariablesSeperator.Fuel + "", 80, 67, 16777215);
		this.fontRendererObj.drawString("" + VariablesSeperator.Process + "", 31, 13, 0);
		this.fontRendererObj.drawString("%", 48, 13, 0);
	}

	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}

	public void initGui() {
		super.initGui();
		this.guiLeft = ((this.width - 176) / 2);
		this.guiTop = ((this.height - 166) / 2);
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		int posX = this.width / 2;
		int posY = this.height / 2;
	}

	protected void actionPerformed(GuiButton button) {
		MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
		World world = server.worldServers[0];
	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}
