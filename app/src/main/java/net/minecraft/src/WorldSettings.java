package net.minecraft.src;

public final class WorldSettings {
	public final long seed;
	public final WorldTheme theme;
	public final byte snow;
	public final byte type;
	public int version;
	public short cloudHeight;
	public int skyColor;
	public int fogColor;
	public int cloudColor;
	public byte skyBrightness;
	public int foliageColor;

	public WorldSettings(long j1) {
		this.seed = j1;
		this.theme = new WorldTheme();
		this.snow = 1;
		this.type = 0;
		this.version = 19132;
		this.cloudHeight = 120;
		this.skyColor = 8961023;
		this.fogColor = 12638463;
		this.cloudColor = 0xFFFFFF;
		this.skyBrightness = 15;
	}

	public WorldSettings(long j1, WorldTheme worldTheme3, byte b4, byte b5) {
		this.seed = j1;
		this.theme = worldTheme3 == null ? new WorldTheme() : worldTheme3;
		this.snow = b4;
		this.type = b5;
		this.version = 19132;
		this.cloudHeight = 120;
		this.skyColor = 8961023;
		this.fogColor = 12638463;
		this.cloudColor = 0xFFFFFF;
		this.skyBrightness = 15;
		if(b5 == 2) {
			this.cloudHeight = (short)(this.cloudHeight - 100);
		} else if(b5 == 3) {
			this.skyColor = 10079487;
			this.fogColor = 0xFFFFFF;
			this.cloudColor = 0xFFFFFF;
		} else if(b5 == 6) {
			this.skyColor = 10079487;
			this.fogColor = 11587839;
			this.cloudColor = 0xFFFFFF;
		}

	}

	public WorldSettings(NBTTagCompound nBTTagCompound1) {
		this(nBTTagCompound1.getLong("RandomSeed"), new WorldTheme(nBTTagCompound1.getCompoundTag("Features")), (byte)(nBTTagCompound1.getBoolean("SnowCovered") ? 2 : 0), nBTTagCompound1.getByte("Type"));
		this.version = nBTTagCompound1.getInteger("version");
		if(nBTTagCompound1.hasKey("Environment")) {
			NBTTagCompound nBTTagCompound2 = nBTTagCompound1.getCompoundTag("Environment");
			if(nBTTagCompound2.hasKey("CloudColor")) {
				this.cloudColor = nBTTagCompound2.getInteger("CloudColor");
			}

			if(nBTTagCompound2.hasKey("SkyColor")) {
				this.skyColor = nBTTagCompound2.getInteger("SkyColor");
			}

			if(nBTTagCompound2.hasKey("FogColor")) {
				this.fogColor = nBTTagCompound2.getInteger("FogColor");
			}

			if(nBTTagCompound2.hasKey("SkyBrightness")) {
				this.skyBrightness = nBTTagCompound2.getByte("SkyBrightness");
				if(this.skyBrightness < 0) {
					this.skyBrightness = 0;
				}

				if(this.skyBrightness > 16) {
					this.skyBrightness = (byte)(this.skyBrightness * 15 / 100);
				}
			}

			if(nBTTagCompound2.hasKey("CloudHeight")) {
				this.cloudHeight = nBTTagCompound2.getShort("CloudHeight");
			}

			if(nBTTagCompound2.hasKey("FoliageColor")) {
				this.foliageColor = nBTTagCompound2.getInteger("FoliageColor");
			}
		}

	}

	public WorldSettings(World world1) {
		this.seed = world1.randomSeed;
		this.theme = new WorldTheme();
		this.snow = (byte)(world1.snowCovered ? 2 : 0);
		this.type = 0;
	}

	public void updateTagCompound(NBTTagCompound nBTTagCompound1) {
		nBTTagCompound1.setLong("RandomSeed", this.seed);
		nBTTagCompound1.setByte("Type", this.type);
		nBTTagCompound1.setCompoundTag("Features", this.theme.getNBTTagCompound());
		nBTTagCompound1.setBoolean("SnowCovered", this.snow == 2);
		NBTTagCompound nBTTagCompound2 = new NBTTagCompound();
		nBTTagCompound2.setInteger("CloudColor", this.cloudColor);
		nBTTagCompound2.setInteger("SkyColor", this.skyColor);
		nBTTagCompound2.setInteger("FogColor", this.fogColor);
		nBTTagCompound2.setByte("SkyBrightness", this.skyBrightness);
		nBTTagCompound2.setShort("CloudHeight", this.cloudHeight);
		nBTTagCompound2.setInteger("FoliageColor", this.foliageColor);
		nBTTagCompound1.setCompoundTag("Environment", nBTTagCompound2);
		nBTTagCompound1.setInteger("version", this.version);
	}

	public WorldSettings setEnvironment(int i1) {
		return i1 == 0 ? this.setEnvironment("Normal") : (i1 == 1 ? this.setEnvironment("Hell") : (i1 == 2 ? this.setEnvironment("Paradise") : (i1 == 3 ? this.setEnvironment("Woods") : this)));
	}

	public WorldSettings setEnvironment(String string1) {
		if(string1.equalsIgnoreCase("Normal")) {
			this.skyColor = 8961023;
			this.fogColor = 12638463;
			this.cloudColor = 0xFFFFFF;
		} else if(string1.equalsIgnoreCase("Classic")) {
			this.skyColor = 10079487;
			this.fogColor = 0xFFFFFF;
			this.cloudColor = 0xFFFFFF;
		} else if(string1.equalsIgnoreCase("Hell")) {
			this.cloudColor = 2164736;
			this.fogColor = 1049600;
			this.skyColor = 1049600;
			this.skyBrightness = 7;
			this.cloudHeight = 120;
		} else if(string1.equalsIgnoreCase("Paradise")) {
			this.skyColor = 13033215;
			this.fogColor = 13033215;
			this.cloudColor = 15658751;
			this.skyBrightness = 16;
			this.cloudHeight = 182;
		} else if(string1.equalsIgnoreCase("Woods")) {
			this.skyColor = 7699847;
			this.fogColor = 5069403;
			this.cloudColor = 5069403;
			this.skyBrightness = 12;
		} else if(string1.equalsIgnoreCase("Infinite")) {
			this.skyColor = 255;
			this.fogColor = 11587839;
			this.cloudColor = 0xFFFFFF;
		} else if(string1.equalsIgnoreCase("Mountainous")) {
			this.skyColor = 10079487;
			this.fogColor = 11587839;
			this.cloudColor = 0xFFFFFF;
		} else if(string1.equalsIgnoreCase("Sky")) {
			this.skyColor = 12632319;
			this.skyBrightness = 16;
			this.fogColor = 8421536;
			this.cloudHeight = 20;
		} else if(string1.equalsIgnoreCase("Rainforest")) {
			this.fogColor = 12638463;
			this.foliageColor = 2094168;
			this.skyColor = 6798591;
		} else if(string1.equalsIgnoreCase("Swampland")) {
			this.fogColor = 12638463;
			this.foliageColor = 9154376;
			this.skyColor = 8887551;
		} else if(string1.equalsIgnoreCase("Seasonal Forest")) {
			this.fogColor = 12638463;
			this.foliageColor = 5169201;
			this.skyColor = 6798591;
		} else if(string1.equalsIgnoreCase("Forest")) {
			this.fogColor = 12638463;
			this.foliageColor = 5159473;
			this.skyColor = 7646463;
		} else if(string1.equalsIgnoreCase("Savanna")) {
			this.fogColor = 12638463;
			this.foliageColor = 5169201;
			this.skyColor = 7646463;
		} else if(string1.equalsIgnoreCase("Shrubland")) {
			this.fogColor = 12638463;
			this.foliageColor = 5169201;
			this.skyColor = 7646463;
		} else if(string1.equalsIgnoreCase("Taiga")) {
			this.fogColor = 12638463;
			this.foliageColor = 8107825;
			this.skyColor = 9214719;
		} else if(string1.equalsIgnoreCase("Desert")) {
			this.fogColor = 12638463;
			this.foliageColor = 5169201;
			this.skyColor = 6798335;
		} else if(string1.equalsIgnoreCase("Plains")) {
			this.fogColor = 12638463;
			this.foliageColor = 5169201;
			this.skyColor = 6798591;
		} else if(string1.equalsIgnoreCase("Ice Desert")) {
			this.fogColor = 12638463;
			this.foliageColor = 12899129;
			this.skyColor = 10263039;
		} else if(string1.equalsIgnoreCase("Tundra")) {
			this.fogColor = 12638463;
			this.foliageColor = 12899129;
			this.skyColor = 9214719;
		}

		return this;
	}
}
