package net.minecraft.src;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;

public class GuiSelectWorld extends GuiScreen {
	protected GuiScreen parentScreen;
	protected String screenTitle = "Select world";
	private boolean selected = false;
	private int currentPage;
	private static final int maxPages = 20;
	private File mcDir = Minecraft.getMinecraftDir();
	private LogoEffectRandomizer[][] logoEffects;
	private float updateCounter = 0.0F;
	private float updateBackground = 0.0F;

	public GuiSelectWorld(GuiScreen guiScreen1) {
		this.parentScreen = guiScreen1;
		if(guiScreen1 instanceof GuiSelectWorld) {
			this.currentPage = ((GuiSelectWorld)guiScreen1).currentPage;
		}

	}

	public void updateScreen() {
		this.updateCounter += 0.01F;
		if(this.logoEffects != null) {
			for(int i1 = 0; i1 < this.logoEffects.length; ++i1) {
				for(int i2 = 0; i2 < this.logoEffects[i1].length; ++i2) {
					this.logoEffects[i1][i2].updateLogoEffects();
				}
			}
		}

	}

	public void initGui() {
		this.controlList.clear();
		this.initPage();
		this.initButtons();
	}

	public void initPage() {
	//	Predicate object0;
		//this.controlList = (List)this.controlList.stream().filter((object0)).collect(Collectors.toList());

		for(int i1 = 0; i1 < 5; ++i1) {
			int i2 = i1 + this.currentPage * 5;
			NBTTagCompound nBTTagCompound3 = World.getLevelData(this.mcDir, "World" + (i2 + 1));
			if(nBTTagCompound3 == null) {
				this.controlList.add(new GuiButton(i2, this.width / 2 - 100, this.height / 6 + 24 * i1, "Empty " + (i2 + 1)));
			} else {
				String string4 = nBTTagCompound3.getString("DisplayName");
				if(string4.isEmpty()) {
					string4 = "World " + (i2 + 1);
				}

				long j5 = nBTTagCompound3.getLong("SizeOnDisk");
				string4 = string4 + " (" + (float)(j5 / 1024L * 100L / 1024L) / 100.0F + " MB)";
				this.controlList.add(new GuiButton(i2, this.width / 2 - 100, this.height / 6 + 24 * i1, string4));
			}
		}

	}

	protected String getSaveName(int i1) {
		File file2 = Minecraft.getMinecraftDir();
		NBTTagCompound nBTTagCompound3 = World.getLevelData(file2, "World" + i1);
		if(nBTTagCompound3 == null) {
			return null;
		} else {
			String string4 = nBTTagCompound3.getString("DisplayName");
			return string4.isEmpty() ? "World" + i1 : string4;
		}
	}

	public void initButtons() {
		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 132, "Delete world"));
		this.controlList.add(new GuiButton(101, this.width / 2 - 100, this.height / 6 + 168, "Cancel"));
		this.controlList.add(new GuiButton(102, this.width / 2 - 140, this.height / 6 + 80, 20, 20, "<"));
		this.controlList.add(new GuiButton(103, this.width / 2 + 120, this.height / 6 + 80, 20, 20, ">"));
	}

	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id < 100) {
				this.selectWorld(button.id + 1);
			} else if(button.id == 100) {
				this.mc.displayGuiScreen(new GuiDeleteWorld(this));
			} else if(button.id == 101) {
				this.mc.displayGuiScreen(this.parentScreen);
			} else if(button.id == 102 && this.currentPage > 0) {
				--this.currentPage;
				this.initPage();
			} else if(button.id == 103 && this.currentPage < 19) {
				++this.currentPage;
				this.initPage();
			} else if(button.id == 104) {
				this.selectWorld(-1);
			}
		}

	}

	public void selectWorld(int i1) {
		this.mc.displayGuiScreen((GuiScreen)null);
		if(!this.selected) {
			this.selected = true;
			this.mc.playerController = new PlayerControllerSP(this.mc);
			this.mc.startWorld("World" + i1);
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		this.updateBackground += 0.001F;
		Calendar calendar9 = Calendar.getInstance();
		calendar9.setTime(new Date());
		
		if(this.mc.options.renderBackgrounds == 0){
			this.drawDefaultBackground();
		}
			
		if(this.mc.options.renderBackgrounds == 1){
			this.drawWorldBackground(this.updateBackground);
		}
		
		if(this.mc.options.renderBackgrounds == 2){
			this.drawGradientRect(0, 0, this.width, this.height, 1610941696, -1607454624);
		}
		
		if(this.mc.options.renderBackgrounds == 3){
			this.drawDefaultBackground();
		}
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 18, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.currentPage + 1 + " / " + 20, this.width / 2, 29, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, renderPartialTick);
		
		if(calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 12) {
			this.drawSnowyBackground(this.updateBackground);
		}

	}
}
