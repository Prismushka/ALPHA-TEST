package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerControllerCreative extends PlayerController {
	public PlayerControllerCreative(Minecraft minecraft1) {
		super(minecraft1);
		this.isInTestMode = true;
	}

	public boolean shouldDrawHUD() {
		return false;
	}

	public void onWorldChange(World world) {
		super.onWorldChange(world);
	}

	public void onUpdate() {
	}
}
