package net.minecraft.src;

public class GuiGameLoading extends GuiScreen {
	private String lineOne;
	private String lineTwo;
	private String lineThree;
	private String lineFour;
	private long counter;
	private long startTime = 1337L;

	public void initGui() {
	}

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		if(this.startTime == 1337L) {
			this.startTime = System.currentTimeMillis();
			this.counter = this.startTime;
		}

		this.drawGradientRect(0, 0, this.width, this.height, -7829368, -7829368);
		if(this.counter < this.startTime + 7000L) {
			this.counter = System.currentTimeMillis();
		}

		if(this.counter > this.startTime + 2000L) {
			this.lineOne = "Sound - loaded.";
		}

		if(this.counter > this.startTime + 3000L) {
			this.lineTwo = "Font - loaded.";
		}

		if(this.counter > this.startTime + 4000L) {
			this.lineThree = "Particles - loaded.";
		}

		if(this.counter > this.startTime + 5000L) {
			this.lineFour = "Textures - loaded.";
		}

		if(this.counter > this.startTime + 6000L) {
			this.mc.displayGuiScreen(new GuiMainMenu());
		}

		this.drawCenteredString(this.fontRenderer, this.lineOne, this.width / 2, 90, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.lineTwo, this.width / 2, 110, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.lineThree, this.width / 2, 130, 0xFFFFFF);
		this.drawCenteredString(this.fontRenderer, this.lineFour, this.width / 2, 150, 0xFFFFFF);
		super.drawScreen(mouseX, mouseY, renderPartialTick);
	}

	protected void keyTyped(char character, int key) {
		this.mc.displayGuiScreen(new GuiMainMenu());
	}
}
