package net.minecraft.src;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class GuiMainMenu extends GuiScreen {
	private static final Random rand = new Random();
	String[] logoBlockLayers = new String[]{"*   * * *   * *** *** *** *** *** ***", "** ** * **  * *   *   * * * * *    * ", "* * * * * * * **  *   **  *** **   * ", "*   * * *  ** *   *   * * * * *    * ", "*   * * *   * *** *** * * * * *    * "};
	String[] logoBlockLayers2 = new String[]{"*** *   *** * * ***   *  ", "* * *   * * * * * *   *  ", "*** *   *** *** *** *****", "* * *   *   * * * *   *  ", "* * *** *   * * * *   *  "};
	private LogoEffectRandomizer[][] logoEffects;
	private float updateCounter = 0.0F;
	private static final ItemTooltip TOOLTIP_MULTIPLAYER = new ItemTooltip(ItemTooltip.DEFAULT_GRAD, new Object[]{"Multiplayer in development!", "At the moment,", 11184810, "the multiplayer is in an early", 11184810, "stage of development,", 11184810, "so it is not available now.", 11184810});
	private float updateFloat;
	private String splashString = "";
	private String[] splashes = new String[]{""};
	private GuiButton multiplayer;
	private float updateBackground = 0.0F;

	public GuiMainMenu() {
		try {
			this.splashString = this.splashes[rand.nextInt(this.splashes.length)];
		} catch (Exception exception2) {
		}

	}

	public void updateScreen() {
		++this.updateCounter;
		if(this.logoEffects != null) {
			for(int i1 = 0; i1 < this.logoEffects.length; ++i1) {
				for(int i2 = 0; i2 < this.logoEffects[i1].length; ++i2) {
					this.logoEffects[i1][i2].updateLogoEffects();
				}
			}
		}

	}

	protected void keyTyped(char character, int key) {
	}

	public void initGui() {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(new Date());
		if(calendar1.get(2) + 1 == 11 && calendar1.get(5) == 9) {
			this.splashString = "Happy birthday, ez!";
		} else if(calendar1.get(2) + 1 == 6 && calendar1.get(5) == 1) {
			this.splashString = "Happy birthday, Notch!";
		} else if(calendar1.get(2) + 1 == 12 && calendar1.get(5) == 24) {
			this.splashString = "Merry X-mas!";
		} else if(calendar1.get(2) + 1 == 1 && calendar1.get(5) == 1) {
			this.splashString = "Happy new year!";
		}

		this.controlList.clear();
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48, "Singleplayer"));
		this.multiplayer = new GuiButton(2, this.width / 2 - 100, this.height / 4 + 72, "Multiplayer");
		this.controlList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96, "Tutorial Level"));
		this.controlList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Options"));
		this.controlList.add(this.multiplayer);
		this.multiplayer.enabled = false;
		((GuiButton)this.controlList.get(1)).enabled = true;
		if(this.mc.options.developerMode) {
			this.multiplayer.enabled = true;
			((GuiButton)this.controlList.get(1)).enabled = true;
		}

	}

	protected void actionPerformed(GuiButton button) {
		if(button.id == 0) {
			this.mc.displayGuiScreen(new GuiOptions(this, this.mc.options));
		}

		if(button.id == 1) {
			this.mc.displayGuiScreen(new GuiSelectWorld(this));
		}

		if(button.id == 2) {
			this.mc.displayGuiScreen(new GuiMultiplayer(this));
		}

		if(button.id == 3) {
			this.mc.playerController = new PlayerControllerSP(this.mc);
			this.mc.startWorld("WorldTutorial");
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		this.updateBackground += 0.001F;
		Calendar calendar9 = Calendar.getInstance();
		calendar9.setTime(new Date());
		
		if(this.mc.options.renderBackgrounds == 0){
			this.drawDefaultBackground();
			if(calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 12) {
				this.drawSnowyBackground(this.updateBackground);
			}
		}
			
		if(this.mc.options.renderBackgrounds == 1){
			this.drawWorldBackground(this.updateBackground);
			if(calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 12) {
				this.drawSnowyBackground(this.updateBackground);
			}
		}
		
		if(this.mc.options.renderBackgrounds == 2){
			this.drawGradientRect(0, 0, this.width, this.height, 1610941696, -1607454624);
			if(calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 12) {
				this.drawSnowyBackground(this.updateBackground);
			}
		}
		
		if(this.mc.options.renderBackgrounds == 3){
			this.drawDefaultBackground();
			if(calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 12) {
				this.drawSnowyBackground(this.updateBackground);
			}
		}
		
		Tessellator tessellator4 = Tessellator.instance;
		this.drawLogo(renderPartialTick);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/gui/logo.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		tessellator4.setColorOpaque_I(0xFFFFFF);
		GL11.glPushMatrix();
		this.drawLogo1(renderPartialTick);
		GL11.glTranslatef((float)(this.width / 2 + 90), 70.0F, 0.0F);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		float f5 = 1.8F - MathHelper.abs(MathHelper.sin((float)(System.currentTimeMillis() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
		f5 = f5 * 100.0F / (float)(this.fontRenderer.getStringWidth(this.splashString) + 32);
		GL11.glScalef(f5, f5, f5);
		this.drawCenteredString(this.fontRenderer, this.splashString, 0, -8, 16776960);
		GL11.glPopMatrix();
		String string6 = "";
		this.drawString(this.fontRenderer, string6, this.width - this.fontRenderer.getStringWidth(string6) - 2, this.height - 10, 0xFFFFFF);
		this.drawString(this.fontRenderer, "Minecraft Alpha+ 1.0.12", 2, 2, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, renderPartialTick);
		if(this.multiplayer.mouseHover(mouseX, mouseY)) {
			this.drawTooltip(TOOLTIP_MULTIPLAYER, mouseX, mouseY);
		}

	}

	private void drawLogo1(float f1) {
		int i2;
		if(this.logoEffects == null) {
			this.logoEffects = new LogoEffectRandomizer[this.logoBlockLayers2[0].length()][this.logoBlockLayers2.length];

			for(int i3 = 0; i3 < this.logoEffects.length; ++i3) {
				for(i2 = 0; i2 < this.logoEffects[i3].length; ++i2) {
					this.logoEffects[i3][i2] = new LogoEffectRandomizer(this, i3, i2);
				}
			}
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution scaledResolution14 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		i2 = 120 * scaledResolution14.scaleFactor;
		GLU.gluPerspective(70.0F, (float)this.mc.displayWidth / (float)i2, 0.05F, 100.0F);
		GL11.glViewport(0, this.mc.displayHeight - i2, this.mc.displayWidth, i2);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDepthMask(true);

		for(int i4 = 0; i4 < 3; ++i4) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.6F, 0.6F, -23.0F);
			if(i4 == 0) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glTranslatef(0.0F, -0.4F, 0.0F);
				GL11.glScalef(0.98F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			if(i4 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			}

			if(i4 == 2) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}

			GL11.glScalef(1.0F, -1.0F, 1.0F);
			GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(0.89F, 1.0F, 0.4F);
			GL11.glTranslatef((float)(-this.logoBlockLayers2[0].length()) * 0.5F, (float)(-this.logoBlockLayers2.length) * -1.1F, 0.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			if(i4 == 0) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/black.png"));
			}

			RenderBlocks renderBlocks5 = new RenderBlocks();

			for(int i6 = 0; i6 < this.logoBlockLayers2.length; ++i6) {
				for(int i7 = 0; i7 < this.logoBlockLayers2[i6].length(); ++i7) {
					char c8 = this.logoBlockLayers2[i6].charAt(i7);
					if(c8 != 32) {
						GL11.glPushMatrix();
						LogoEffectRandomizer logoEffectRandomizer9 = this.logoEffects[i7][i6];
						float f10 = (float)(logoEffectRandomizer9.prevHeight + (logoEffectRandomizer9.height - logoEffectRandomizer9.prevHeight) * (double)f1);
						float f11 = 1.0F;
						float f12 = 1.0F;
						float f13 = 0.0F;
						if(i4 == 0) {
							f11 = f10 * 0.04F + 1.0F;
							f12 = 1.0F / f11;
							f10 = 0.0F;
						}

						GL11.glTranslatef((float)i7, (float)i6, f10);
						GL11.glScalef(f11, f11, f11);
						GL11.glRotatef(f13, 0.0F, 1.0F, 0.0F);
						renderBlocks5.renderBlockAsItem(Block.planks, f12);
						GL11.glPopMatrix();
					}
				}
			}

			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	private void drawLogo(float renderPartialTick) {
		int i2;
		if(this.logoEffects == null) {
			this.logoEffects = new LogoEffectRandomizer[this.logoBlockLayers[0].length()][this.logoBlockLayers.length];

			for(int i3 = 0; i3 < this.logoEffects.length; ++i3) {
				for(i2 = 0; i2 < this.logoEffects[i3].length; ++i2) {
					this.logoEffects[i3][i2] = new LogoEffectRandomizer(this, i3, i2);
				}
			}
		}

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		ScaledResolution scaledResolution14 = new ScaledResolution(this.mc.displayWidth, this.mc.displayHeight);
		i2 = 120 * scaledResolution14.scaleFactor;
		GLU.gluPerspective(70.0F, (float)this.mc.displayWidth / (float)i2, 0.05F, 100.0F);
		GL11.glViewport(0, this.mc.displayHeight - i2, this.mc.displayWidth, i2);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPushMatrix();
		GL11.glLoadIdentity();
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDepthMask(true);

		for(int i4 = 0; i4 < 3; ++i4) {
			GL11.glPushMatrix();
			GL11.glTranslatef(0.4F, 0.6F, -12.0F);
			if(i4 == 0) {
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
				GL11.glTranslatef(0.0F, -0.4F, 0.0F);
				GL11.glScalef(0.98F, 1.0F, 1.0F);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			}

			if(i4 == 1) {
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
			}

			if(i4 == 2) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			}

			GL11.glScalef(1.0F, -1.0F, 1.0F);
			GL11.glRotatef(15.0F, 1.0F, 0.0F, 0.0F);
			GL11.glScalef(0.89F, 1.0F, 0.4F);
			GL11.glTranslatef((float)(-this.logoBlockLayers[0].length()) * 0.5F, (float)(-this.logoBlockLayers.length) * 0.5F, 0.0F);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/terrain.png"));
			if(i4 == 0) {
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.mc.renderEngine.getTexture("/title/black.png"));
			}

			RenderBlocks renderBlocks5 = new RenderBlocks();

			for(int i6 = 0; i6 < this.logoBlockLayers.length; ++i6) {
				for(int i7 = 0; i7 < this.logoBlockLayers[i6].length(); ++i7) {
					char c8 = this.logoBlockLayers[i6].charAt(i7);
					if(c8 != 32) {
						GL11.glPushMatrix();
						LogoEffectRandomizer logoEffectRandomizer9 = this.logoEffects[i7][i6];
						float f10 = (float)(logoEffectRandomizer9.prevHeight + (logoEffectRandomizer9.height - logoEffectRandomizer9.prevHeight) * (double)renderPartialTick);
						float f11 = 1.0F;
						float f12 = 1.0F;
						float f13 = 0.0F;
						if(i4 == 0) {
							f11 = f10 * 0.04F + 1.0F;
							f12 = 1.0F / f11;
							f10 = 0.0F;
						}

						GL11.glTranslatef((float)i7, (float)i6, f10);
						GL11.glScalef(f11, f11, f11);
						GL11.glRotatef(f13, 0.0F, 1.0F, 0.0F);
						renderBlocks5.renderBlockAsItem(Block.stone, f12);
						GL11.glPopMatrix();
					}
				}
			}

			GL11.glPopMatrix();
		}

		GL11.glDisable(GL11.GL_BLEND);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glPopMatrix();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glPopMatrix();
		GL11.glViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	static Random getRandom() {
		return rand;
	}
}
