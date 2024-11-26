package net.minecraft.src;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiIngame extends Gui {
	private static RenderItem itemRenderer = new RenderItem();
	private List chatMessageList = new ArrayList();
	private Random rand = new Random();
	private Minecraft mc;
	public String testMessage = null;
	public String hoveredUsername = null;
	private int updateCounter = 0;
	private String recordPlaying = "";
	private int recordPlayingUpFor = 0;
	public float damageGuiPartialTime;
	float prevVignetteBrightness = 1.0F;

	public GuiIngame(Minecraft minecraft) {
		this.mc = minecraft;
	}

	public void renderGameOverlay(float renderPartialTick, boolean hasScreen, int width, int height) {
		ScaledResolution scaledResolution5 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		int i6 = scaledResolution5.getScaledWidth();
		int i7 = scaledResolution5.getScaledHeight();
		FontRenderer fontRenderer8 = this.mc.fontRenderer;
		this.mc.entityRenderer.setupOverlayRendering();
		GL11.glEnable(GL11.GL_BLEND);
		if(this.mc.options.fancyGraphics) {
			this.renderVignette(this.mc.thePlayer.getBrightness(renderPartialTick), i6, i7);
		}

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/gui.png"));
		InventoryPlayer inventoryPlayer9 = this.mc.thePlayer.inventory;
		this.zLevel = -90.0F;
		this.drawTexturedModalRect(i6 / 2 - 91, i7 - 22, 0, 0, 182, 22);
		this.drawTexturedModalRect(i6 / 2 - 91 - 1 + inventoryPlayer9.currentItem * 20, i7 - 22 - 1, 0, 22, 24, 22);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/icons.png"));
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR);
		this.drawTexturedModalRect(i6 / 2 - 7, i7 / 2 - 7, 0, 0, 16, 16);
		GL11.glDisable(GL11.GL_BLEND);
		boolean z10 = this.mc.thePlayer.heartsLife / 3 % 2 == 1;
		if(this.mc.thePlayer.heartsLife < 10) {
			z10 = false;
		}

		int i11 = this.mc.thePlayer.health;
		int i12 = this.mc.thePlayer.prevHealth;
		this.rand.setSeed((long)(this.updateCounter * 312871));
		int i14 = 0;
		int i13;
		int i15;
		byte b19;
		if(this.mc.playerController.shouldDrawHUD()) {
			i13 = this.mc.thePlayer.getPlayerArmorValue();

			int i18;
			for(i14 = 0; i14 < 10; ++i14) {
				i15 = i7 - 32;
				if(i13 > 0) {
					i18 = i6 / 2 + 91 - i14 * 8 - 9;
					if(i14 * 2 + 1 < i13) {
						this.drawTexturedModalRect(i18, i15, 34, 9, 9, 9);
					}

					if(i14 * 2 + 1 == i13) {
						this.drawTexturedModalRect(i18, i15, 25, 9, 9, 9);
					}

					if(i14 * 2 + 1 > i13) {
						this.drawTexturedModalRect(i18, i15, 16, 9, 9, 9);
					}
				}

				b19 = 0;
				if(z10) {
					b19 = 1;
				}

				int i20 = i6 / 2 - 91 + i14 * 8;
				if(i11 <= 4) {
					i15 += this.rand.nextInt(2);
				}

				this.drawTexturedModalRect(i20, i15, 16 + b19 * 9, 0, 9, 9);
				if(z10) {
					if(i14 * 2 + 1 < i12) {
						this.drawTexturedModalRect(i20, i15, 70, 0, 9, 9);
					}

					if(i14 * 2 + 1 == i12) {
						this.drawTexturedModalRect(i20, i15, 79, 0, 9, 9);
					}
				}

				if(i14 * 2 + 1 < i11) {
					this.drawTexturedModalRect(i20, i15, 52, 0, 9, 9);
				}

				if(i14 * 2 + 1 == i11) {
					this.drawTexturedModalRect(i20, i15, 61, 0, 9, 9);
				}
			}

			if(this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
				i14 = (int)Math.ceil((double)(this.mc.thePlayer.air - 2) * 10.0D / 300.0D);
				i15 = (int)Math.ceil((double)this.mc.thePlayer.air * 10.0D / 300.0D) - i14;

				for(i18 = 0; i18 < i14 + i15; ++i18) {
					if(i18 < i14) {
						this.drawTexturedModalRect(i6 / 2 - 91 + i18 * 8, i7 - 32 - 9, 16, 18, 9, 9);
					} else {
						this.drawTexturedModalRect(i6 / 2 - 91 + i18 * 8, i7 - 32 - 9, 25, 18, 9, 9);
					}
				}
			}
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();

		for(i13 = 0; i13 < 9; ++i13) {
			i14 = i6 / 2 - 90 + i13 * 20 + 2;
			i15 = i7 - 16 - 3;
			this.renderInventorySlot(i13, i14, i15, renderPartialTick);
		}

		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		if(Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			fontRenderer8.drawStringWithShadow("Minecraft Alpha+ 1.0.12 (Build 5)", 2, 2, 0xFFFFFF);
			fontRenderer8.drawStringWithShadow(this.mc.debug, 2, 22, 0xFFFFFF);
			fontRenderer8.drawStringWithShadow(this.mc.debugInfoRenders(), 2, 42, 0xFFFFFF);
			fontRenderer8.drawStringWithShadow(this.mc.getEntityDebug(), 2, 52, 0xFFFFFF);
			fontRenderer8.drawStringWithShadow(this.mc.debugInfoEntities(), 2, 62, 0xFFFFFF);
			fontRenderer8.drawStringWithShadow(this.mc.debugInfoPoz(), 2, 82, 0xFFFFFF);
		} else {
			fontRenderer8.drawStringWithShadow("Minecraft Alpha+ 1.0.12", 2, 2, 0xFFFFFF);
		}

		if(this.recordPlayingUpFor > 0) {
			float f28 = (float)this.recordPlayingUpFor - renderPartialTick;
			i14 = (int)(f28 * 256.0F / 20.0F);
			if(i14 > 255) {
				i14 = 255;
			}

			if(i14 > 0) {
				GL11.glPushMatrix();
				GL11.glTranslatef((float)(i6 / 2), (float)(i7 - 48), 0.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				i15 = Color.HSBtoRGB(f28 / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
				fontRenderer8.drawString(this.recordPlaying, -fontRenderer8.getStringWidth(this.recordPlaying) / 2, -4, i15 + (i14 << 24));
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glPopMatrix();
			}
		}

		b19 = 10;
		boolean z29 = false;
		if(this.mc.currentScreen instanceof GuiChat) {
			b19 = 20;
			z29 = true;
		}

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, (float)(i7 - 48), 0.0F);

		int i24;
		for(i15 = 0; i15 < this.chatMessageList.size() && i15 < b19; ++i15) {
			if(((ChatLine)this.chatMessageList.get(i15)).updateCounter < 200 || z29) {
				double d22 = (double)((ChatLine)this.chatMessageList.get(i15)).updateCounter / 200.0D;
				d22 = 1.0D - d22;
				d22 *= 10.0D;
				if(d22 < 0.0D) {
					d22 = 0.0D;
				}

				if(d22 > 1.0D) {
					d22 = 1.0D;
				}

				d22 *= d22;
				i24 = (int)(255.0D * d22);
				if(z29) {
					i24 = 255;
				}

				if(i24 > 0) {
					byte b25 = 2;
					int i26 = -i15 * 9;
					String string27 = ((ChatLine)this.chatMessageList.get(i15)).message;
					this.drawRect(b25, i26 - 1, b25 + 320, i26 + 8, i24 / 2 << 24);
					GL11.glEnable(GL11.GL_BLEND);
					fontRenderer8.drawStringWithShadow(string27, b25, i26, 0xFFFFFF + (i24 << 24));
				}
			}
		}

		GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		this.hoveredUsername = null;
		if(Keyboard.isKeyDown(Keyboard.KEY_TAB) && this.mc.isMultiplayerWorld() && this.mc.getSendQueue() != null) {
			ArrayList arrayList30 = this.mc.getSendQueue().players;
			this.drawGradientRect(i14 / 2 - 120, i15 / 2 + -90, i14 / 2 + 120, i15 / 2 + 60, -1878719232, -1070583712);
			String string23 = "Connected players:";
			fontRenderer8.drawString(string23, i14 - fontRenderer8.getStringWidth(string23) / 2, i15 - 64 - 12, 0xFFFFFF);

			for(i24 = 0; i24 < arrayList30.size(); ++i24) {
				int i21 = i14 + i24 % 2 * 120 - 120;
				int i31 = i15 - 64 + (i24 / 2 << 3);
				if(hasScreen && width >= i21 && height >= i31 && width < i21 + 120 && height < i31 + 8) {
					this.hoveredUsername = ((PlayerScore)arrayList30.get(i24)).playerName;
					fontRenderer8.drawString(((PlayerScore)arrayList30.get(i24)).playerName + " : " + ((PlayerScore)arrayList30.get(i24)).playerScore + " pts", i21 + 2, i31, 0xFFFFFF);
				} else {
					fontRenderer8.drawString(((PlayerScore)arrayList30.get(i24)).playerName + " : " + ((PlayerScore)arrayList30.get(i24)).playerScore + " pts", i21, i31, 15658734);
				}
			}
		}

	}

	private void renderVignette(float brightness, int width, int height) {
		brightness = 1.0F - brightness;
		if(brightness < 0.0F) {
			brightness = 0.0F;
		}

		if(brightness > 1.0F) {
			brightness = 1.0F;
		}

		this.prevVignetteBrightness = (float)((double)this.prevVignetteBrightness + (double)(brightness - this.prevVignetteBrightness) * 0.01D);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glBlendFunc(GL11.GL_ZERO, GL11.GL_ONE_MINUS_SRC_COLOR);
		GL11.glColor4f(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/misc/vignette.png"));
		Tessellator tessellator4 = Tessellator.instance;
		tessellator4.startDrawingQuads();
		tessellator4.addVertexWithUV(0.0D, (double)height, -90.0D, 0.0D, 1.0D);
		tessellator4.addVertexWithUV((double)width, (double)height, -90.0D, 1.0D, 1.0D);
		tessellator4.addVertexWithUV((double)width, 0.0D, -90.0D, 1.0D, 0.0D);
		tessellator4.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
		tessellator4.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	private void renderInventorySlot(int i1, int i2, int i3, float renderPartialTick) {
		ItemStack itemStack5 = this.mc.thePlayer.inventory.mainInventory[i1];
		if(itemStack5 != null) {
			float f6 = (float)itemStack5.animationsToGo - renderPartialTick;
			if(f6 > 0.0F) {
				GL11.glPushMatrix();
				float f7 = 1.0F + f6 / 5.0F;
				GL11.glTranslatef((float)(i2 + 8), (float)(i3 + 12), 0.0F);
				GL11.glScalef(1.0F / f7, (f7 + 1.0F) / 2.0F, 1.0F);
				GL11.glTranslatef((float)(-(i2 + 8)), (float)(-(i3 + 12)), 0.0F);
			}

			itemRenderer.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, itemStack5, i2, i3);
			if(f6 > 0.0F) {
				GL11.glPopMatrix();
			}

			itemRenderer.renderItemOverlayIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, itemStack5, i2, i3);
		}

	}

	public void updateTick() {
		if(this.recordPlayingUpFor > 0) {
			--this.recordPlayingUpFor;
		}

		++this.updateCounter;

		for(int i1 = 0; i1 < this.chatMessageList.size(); ++i1) {
			++((ChatLine)this.chatMessageList.get(i1)).updateCounter;
		}

	}

	public void addChatMessage(String message) {
		while(this.mc.fontRenderer.getStringWidth(message) > 320) {
			int i2;
			for(i2 = 1; i2 < message.length() && this.mc.fontRenderer.getStringWidth(message.substring(0, i2 + 1)) <= 320; ++i2) {
			}

			this.addChatMessage(message.substring(0, i2));
			message = message.substring(i2);
		}

		this.chatMessageList.add(0, new ChatLine(message));

		while(this.chatMessageList.size() > 50) {
			this.chatMessageList.remove(this.chatMessageList.size() - 1);
		}

	}

	public void setRecordPlayingMessage(String record) {
		this.recordPlaying = "Now playing: " + record;
		this.recordPlayingUpFor = 60;
	}
}
