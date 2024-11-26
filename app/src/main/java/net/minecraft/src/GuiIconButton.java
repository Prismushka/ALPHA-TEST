package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiIconButton extends GuiSmallButton {
	public int itemID;

	public GuiIconButton(int i1, int i2, int i3, String string4) {
		super(i1, i2, i3, string4);
	}

	public void drawButton(Minecraft minecraft, int mouseX, int mouseY) {
		if(this.visible) {
			FontRenderer fontRenderer4 = minecraft.fontRenderer;
			RenderEngine renderEngine5 = minecraft.renderEngine;
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, renderEngine5.getTexture("/gui/gui.png"));
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			boolean z6 = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
			int i7 = this.getHoverState(z6);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i7 * 20, this.width / 2, this.height);
			this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i7 * 20, this.width / 2, this.height);
			this.mouseDragged(minecraft, mouseX, mouseY);
			boolean z8 = this.itemID > 0 && this.itemID < Item.itemsList.length && Item.itemsList[this.itemID] != null;
			int i9 = fontRenderer4.getStringWidth(this.displayString);
			int i10 = this.xPosition + (this.width - i9) / 2;
			if(z8) {
				i10 -= 7;
			}

			int i11 = this.yPosition + (this.height - 8) / 2;
			if(!this.enabled) {
				this.drawString(fontRenderer4, this.displayString, i10, i11, -6250336);
			} else if(z6) {
				this.drawString(fontRenderer4, this.displayString, i10, i11, 16777120);
			} else {
				this.drawString(fontRenderer4, this.displayString, i10, i11, 14737632);
			}

			if(z8) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glPushMatrix();
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				RenderHelper.enableStandardItemLighting();
				GL11.glPopMatrix();
				(new RenderItem()).renderItemIntoGUI(fontRenderer4, renderEngine5, new ItemStack(this.itemID), this.xPosition + (this.width + i9 - 14) / 2, i11 - 4);
				RenderHelper.disableStandardItemLighting();
				GL11.glDisable(GL12.GL_RESCALE_NORMAL);
			}
		}

	}
}
