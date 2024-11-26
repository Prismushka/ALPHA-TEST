package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiScreen extends Gui {
	protected Minecraft mc;
	public int width;
	public int height;
	protected List controlList = new ArrayList();
	public boolean allowUserInput = false;
	protected FontRenderer fontRenderer;
	private GuiButton selectedButton = null;

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		for(int i4 = 0; i4 < this.controlList.size(); ++i4) {
			GuiButton guiButton5 = (GuiButton)this.controlList.get(i4);
			guiButton5.drawButton(this.mc, mouseX, mouseY);
		}

	}

	protected void keyTyped(char character, int key) {
		if(key == 1) {
			this.mc.displayGuiScreen((GuiScreen)null);
			this.mc.setIngameFocus();
		}

	}

	public void drawTooltip(ItemTooltip itemTooltip1, int i2, int i3) {
		int i4 = 0;
		Iterator iterator5 = itemTooltip1.lines.iterator();

		int i7;
		while(iterator5.hasNext()) {
			String string6 = (String)iterator5.next();
			i7 = this.fontRenderer.getStringWidth(string6);
			if(i7 > i4) {
				i4 = i7;
			}
		}

		if(i2 + i4 + 48 > this.width) {
			i2 -= i4 + 24;
		}

		int i8 = itemTooltip1.size() * 9;
		this.drawGradientRect(i2 + 8, i3, i2 + i4 + 16, i3 + i8 + 8, ((Integer)itemTooltip1.gradient.item0).intValue(), ((Integer)itemTooltip1.gradient.item1).intValue());

		for(i7 = 0; i7 < itemTooltip1.size(); ++i7) {
			this.drawString(this.fontRenderer, (String)itemTooltip1.lines.get(i7), i2 + 12, i3 + 4 + i7 * 9, ((Integer)itemTooltip1.colors.get(i7)).intValue());
		}

	}

	public static String getClipboardString() {
		try {
			Transferable transferable0 = Toolkit.getDefaultToolkit().getSystemClipboard().getContents((Object)null);
			if(transferable0 != null && transferable0.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				String string1 = (String)transferable0.getTransferData(DataFlavor.stringFlavor);
				return string1;
			}
		} catch (Exception exception2) {
		}

		return null;
	}
	
	public void drawSnowyBackground(float f1) {
		this.drawSnowyBackgroundRight(f1);
		this.drawSnowyBackgroundLeft(f1);
	}

	public void drawSnowyBackgroundRight(float f1) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator2 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/snow_2.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f3 = 64.0F;
		float f4 = 256.0F;
		double d5 = (double)f1 * 1.5D;
		tessellator2.startDrawingQuads();
		tessellator2.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D + d5, (double)((float)this.height / f4) - (double)f1);
		tessellator2.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / f3) + 0.0D + d5, (double)((float)this.height / f4) - (double)f1);
		tessellator2.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / f3) + 0.0D + d5, (double)(0.0F - f1));
		tessellator2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D + d5, (double)(0.0F - f1));
		tessellator2.draw();
	}

	public void drawSnowyBackgroundLeft(float f1) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator2 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/snow_1.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f3 = 64.0F;
		float f4 = 256.0F;
		double d5 = (double)f1 * 1.5D;
		tessellator2.startDrawingQuads();
		tessellator2.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D - d5, (double)((float)this.height / f4) - (double)f1);
		tessellator2.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / f3) + (0.0D - d5), (double)((float)this.height / f4) - (double)f1);
		tessellator2.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / f3) + (0.0D - d5), (double)(0.0F - f1));
		tessellator2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D - d5, (double)(0.0F - f1));
		tessellator2.draw();
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		if(i3 == 0) {
			for(int i4 = 0; i4 < this.controlList.size(); ++i4) {
				GuiButton guiButton5 = (GuiButton)this.controlList.get(i4);
				if(guiButton5.mousePressed(this.mc, i1, i2)) {
					this.selectedButton = guiButton5;
					this.mc.sndManager.playSoundFX("random.click", 1.0F, 1.0F);
					this.actionPerformed(guiButton5);
				}
			}
		}

	}

	protected void mouseMovedOrUp(int i1, int i2, int i3) {
		if(this.selectedButton != null && i3 == 0) {
			this.selectedButton.mouseReleased(i1, i2);
			this.selectedButton = null;
		}

	}

	protected void actionPerformed(GuiButton button) {
	}

	public void setWorldAndResolution(Minecraft minecraft1, int i2, int i3) {
		this.mc = minecraft1;
		this.fontRenderer = minecraft1.fontRenderer;
		this.width = i2;
		this.height = i3;
		this.initGui();
	}

	public void initGui() {
	}

	public void handleInput() {
		while(Mouse.next()) {
			this.handleMouseInput();
		}

		while(Keyboard.next()) {
			this.handleKeyboardInput();
		}

	}

	public void handleMouseInput() {
		int i1;
		int i2;
		if(Mouse.getEventButtonState()) {
			i1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
			i2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			this.mouseClicked(i1, i2, Mouse.getEventButton());
		} else {
			i1 = Mouse.getEventX() * this.width / this.mc.displayWidth;
			i2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			this.mouseMovedOrUp(i1, i2, Mouse.getEventButton());
		}

	}

	public void handleKeyboardInput() {
		if(Keyboard.getEventKeyState()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_F11) {
				this.mc.toggleFullscreen();
				return;
			}

			this.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}

	}

	public void updateScreen() {
	}

	public void onGuiClosed() {
	}

	public void drawDefaultBackground() {
		this.drawWorldBackground(0.0F);
	}

	public void drawWorldBackground(float f1) {
		if(this.mc.theWorld != null) {
			this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		} else {
			this.drawBackground(f1);
		}

	}

	public void drawBackground(float f1) {
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		Tessellator tessellator2 = Tessellator.instance;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/dirt.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float f3 = 32.0F;
		tessellator2.startDrawingQuads();
		tessellator2.setColorOpaque_I(4210752);
		tessellator2.addVertexWithUV(0.0D, (double)this.height, 0.0D, 0.0D, (double)((float)this.height / f3 + (float)f1));
		tessellator2.addVertexWithUV((double)this.width, (double)this.height, 0.0D, (double)((float)this.width / f3), (double)((float)this.height / f3 + (float)f1));
		tessellator2.addVertexWithUV((double)this.width, 0.0D, 0.0D, (double)((float)this.width / f3), (double)(0 + f1));
		tessellator2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double)(0 + f1));
		tessellator2.draw();
	}

	public boolean doesGuiPauseGame() {
		return true;
	}

	public void deleteWorld(boolean z1, int i2) {
	}

	public static boolean isShiftKeyDown() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}
}
