package net.minecraft.src;

public class GuiDumpItemIcons extends GuiScreen {
	private GuiScreen parentScreen;
	protected String screenTitle = "Dump item icons";
	private int maxResolution = 5;
	private int selectedResolution = 2;
	private GuiButton buttonResolution;

	public GuiDumpItemIcons(GuiScreen guiScreen1) {
		this.parentScreen = guiScreen1;
	}

	public void initGui() {
		this.controlList.clear();
		this.controlList.add(this.buttonResolution = new GuiButton(0, this.width / 2 - 100, this.height / 4 - 10 + 50 + 16, "32x32"));
		this.controlList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, "Dump"));
		this.controlList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
		this.dumpOptions();
	}

	private void dumpOptions() {
		this.maxResolution = 0;

		int i1;
		for(i1 = 16; i1 + i1 / 8 < this.mc.displayHeight; i1 += 16) {
			++this.maxResolution;
		}

		if(this.maxResolution < 1) {
			this.maxResolution = 1;
		}

		i1 = 16 * this.selectedResolution;
		this.buttonResolution.displayString = i1 + "\u00d7" + i1;
	}

	protected void actionPerformed(GuiButton button) {
		boolean z2 = GuiScreen.isShiftKeyDown();
		if(button.id == 2) {
			this.mc.displayGuiScreen(this.parentScreen);
		} else if(button.id == 1) {
			int i3 = 16 * this.selectedResolution;
		} else if(button.id == 0) {
			this.selectedResolution += z2 ? -1 : 1;
			if(this.selectedResolution < 1 || this.selectedResolution > this.maxResolution) {
				this.selectedResolution = z2 ? this.maxResolution : 1;
			}
		}

		this.dumpOptions();
	}

	protected void keyTyped(char character, int key) {
		if(key == 1) {
			this.mc.displayGuiScreen(this.parentScreen);
		}

	}

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		this.drawDefaultBackground();
		this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, renderPartialTick);
	}
}
