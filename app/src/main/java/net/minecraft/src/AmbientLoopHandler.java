package net.minecraft.src;

import java.util.Random;

public class AmbientLoopHandler {
	World world;
	EntityPlayer player;
	public int leaves;
	public int waterSurface;
	public int exposedOre;
	public int fallingWater;
	public int wind;
	public int rain;
	public int ambienceWait = 0;
	Random random = new Random();
	int birds = 45;
	int chimes = 60;
	int ocean = 60;
	int waterfall = 5;
	int killCount = 0;
	long timeOfDay = 0L;

	public AmbientLoopHandler(World world1, EntityPlayer entityPlayer2) {
		this.world = world1;
		this.player = entityPlayer2;
	}

	public void killLoops() {
		this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.birds screaming loop", 0.001F, 1.0F);
		this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.cricketsloop", 0.001F, 1.0F);
		this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.ocean", 0.001F, 1.0F);
		this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.underwater", 0.001F, 1.0F);
		this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.cave chimes", 0.001F, 1.0F);
		this.killCount = 1;
	}

	public void update() {
		if(this.world.isAmbienceEnabled()) {
			this.timeOfDay = this.world.worldTime;
			if(this.ambienceWait == 1) {
				this.world.playAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.birds screaming loop", 0.001F, 1.0F);
				this.world.playAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.cricketsloop", 0.001F, 1.0F);
				this.world.playAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.underwater", 0.001F, 1.0F);
				this.world.playAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.ocean", 0.001F, 1.0F);
				this.world.playAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.cave chimes", 0.001F, 1.0F);
			}

			++this.ambienceWait;
			if(this.killCount > 0) {
				--this.killCount;
			} else {
				byte b1 = 0;
				if(this.player.isInsideOfMaterial(Material.water)) {
					b1 = 1;
				}

				this.leaves = 0;
				this.waterSurface = 0;
				this.exposedOre = 0;
				this.fallingWater = 0;

				try {
					for(int i2 = -25; i2 < 25; ++i2) {
						for(int i3 = -15; i3 < 15; ++i3) {
							for(int i4 = -25; i4 < 25; ++i4) {
								int i5 = this.world.getBlockId(i2 + (int)this.player.posX, i3 + (int)this.player.posY, i4 + (int)this.player.posZ);
								if(i5 == Block.leaves.blockID) {
									++this.leaves;
								}

								if(i5 == Block.waterStill.blockID && this.world.getBlockId(i2 + (int)this.player.posX, i3 + (int)this.player.posY + 1, i4 + (int)this.player.posZ) == 0) {
									++this.waterSurface;
								}
							}
						}
					}
				} catch (Exception exception6) {
					exception6.printStackTrace();
				}

				this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.birds screaming loop", (float)this.leaves / 5000.0F * (1.001F - (float)b1) * ((float)(24000L - this.timeOfDay % 24000L) / 14000.0F - 0.7F), 1.0F);
				this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.cricketsloop", (float)this.leaves / 5000.0F * (1.001F - (float)b1) * ((float)(this.timeOfDay % 24000L) / 14000.0F), 1.0F);
				this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.ocean", (float)(this.waterSurface - 200) / 1000.0F * (1.001F - (float)b1), 1.0F);
				this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.underwater", (float)this.waterSurface / 1000.0F * ((float)b1 + 0.001F), 1.0F);
				this.world.updateAmbience(this.player.posX, this.player.posY, this.player.posZ, "loops.cave chimes", (float)(this.exposedOre - 20) / 50.0F, 1.0F);
			}
		}

	}
}
