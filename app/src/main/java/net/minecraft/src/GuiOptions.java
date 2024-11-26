package net.minecraft.src;

import java.util.Calendar;
import java.util.Date;

public class GuiOptions extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Options";
	private GameSettings options;
	private float updateBackground = 0.0F;

	public GuiOptions(GuiScreen guiScreen1, GameSettings gameSettings2) {
		this.parentScreen = guiScreen1;
		this.options = gameSettings2;
		
	}

	public void initGui() {
		for(int i1 = 0; i1 < this.options.numberOfOptions; ++i1) {
			int i2 = this.options.isSlider(i1);
			if(i2 == 0) {
				this.controlList.add(new GuiSmallButton(i1, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), this.options.getOptionDisplayString(i1)));
			} else {
				this.controlList.add(new GuiSlider(i1, this.width / 2 - 155 + i1 % 2 * 160, this.height / 6 + 24 * (i1 >> 1), i1, this.options.getOptionDisplayString(i1), this.options.getOptionFloatValue(i1)));
			}
		}

		this.controlList.add(new GuiButton(100, this.width / 2 - 100, this.height / 6 + 144, "Controls"));
		this.controlList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, "Done"));
	}

	protected void actionPerformed(GuiButton button) {
		if(button.enabled) {
			if(button.id < 100) {
				this.options.setOptionValue(button.id, 1);
				button.displayString = this.options.getOptionDisplayString(button.id);
			}

			if(button.id == 100) {
				this.mc.displayGuiScreen(new GuiControls(this, this.options));
			}

			if(button.id == 200) {
				this.mc.displayGuiScreen(this.parentScreen);
			}
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
		
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, renderPartialTick);
		
		if(calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 1 || calendar9.get(2) + 1 == 12) {
			this.drawSnowyBackground(this.updateBackground);
		}

	}
}
