package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class WorldTheme {
	public byte defaultBlock;
	public byte defaultFluid;
	public byte defaultStillFluid;
	public byte topBlock;
	public byte fillerBlock;
	public byte sandTopBlock;
	public byte sandFillerBlock;
	public byte gravelTopBlock;
	public byte gravelFillerBlock;
	public byte stoneTopBlock;
	public byte stoneFillerBlock;
	public double sandNoise;
	public double gravelNoise;
	public int stoneNoise;
	public short waterLevel;
	public short beachLevel;
	public int carver;
	public int treesPerChunk;
	public int bigTree;
	public byte flowersPerChunk;
	public int cactiPerChunk;
	public byte mushroomsPerChunk;
	public int reedsPerChunk;
	public int clayPerChunk;
	Minecraft mc;

	public WorldTheme() {
		this(0);
	}

	public WorldTheme(int i1) {
		this.defaultBlock = (byte)Block.stone.blockID;
		this.defaultFluid = (byte)Block.waterMoving.blockID;
		this.defaultStillFluid = (byte)Block.waterStill.blockID;
		this.topBlock = (byte)Block.grass.blockID;
		this.fillerBlock = (byte)Block.dirt.blockID;
		this.sandTopBlock = (byte)Block.sand.blockID;
		this.sandFillerBlock = (byte)Block.sand.blockID;
		this.gravelTopBlock = 0;
		this.gravelFillerBlock = (byte)Block.gravel.blockID;
		this.stoneTopBlock = 0;
		this.stoneFillerBlock = (byte)Block.stone.blockID;
		this.sandNoise = 0.0D;
		this.gravelNoise = 3.0D;
		this.stoneNoise = 0;
		this.waterLevel = 64;
		this.beachLevel = 64;
		this.carver = 4;
		this.treesPerChunk = 0;
		this.bigTree = 10;
		this.flowersPerChunk = 2;
		this.cactiPerChunk = 1;
		this.mushroomsPerChunk = 1;
		this.reedsPerChunk = 10;
		this.clayPerChunk = 10;
		if(i1 > 0) {
			if(i1 == 1) {
				this.defaultFluid = (byte)Block.lavaMoving.blockID;
				this.defaultStillFluid = (byte)Block.lavaStill.blockID;
				this.topBlock = (byte)Block.dirt.blockID;
				this.sandTopBlock = (byte)Block.grass.blockID;
			} else if(i1 == 2) {
				this.sandNoise = -0.3D;
			} else if(i1 == 3) {
				this.treesPerChunk = 20;
			}
		}

	}

	public WorldTheme(NBTTagCompound nBTTagCompound1) {
		this.defaultBlock = (byte)Block.stone.blockID;
		this.defaultFluid = (byte)Block.waterMoving.blockID;
		this.defaultStillFluid = (byte)Block.waterStill.blockID;
		this.topBlock = (byte)Block.grass.blockID;
		this.fillerBlock = (byte)Block.dirt.blockID;
		this.sandTopBlock = (byte)Block.sand.blockID;
		this.sandFillerBlock = (byte)Block.sand.blockID;
		this.gravelTopBlock = 0;
		this.gravelFillerBlock = (byte)Block.gravel.blockID;
		this.stoneTopBlock = 0;
		this.stoneFillerBlock = (byte)Block.stone.blockID;
		this.sandNoise = 0.0D;
		this.gravelNoise = 3.0D;
		this.stoneNoise = 0;
		this.waterLevel = 64;
		this.beachLevel = 64;
		this.carver = 4;
		this.treesPerChunk = 0;
		this.bigTree = 10;
		this.flowersPerChunk = 2;
		this.cactiPerChunk = 1;
		this.mushroomsPerChunk = 1;
		this.reedsPerChunk = 10;
		this.clayPerChunk = 10;
		if(nBTTagCompound1.hasKey("StoneType")) {
			this.defaultBlock = nBTTagCompound1.getByte("StoneType");
		}

		if(nBTTagCompound1.hasKey("WaterType")) {
			this.defaultFluid = nBTTagCompound1.getByte("WaterType");
		}

		if(nBTTagCompound1.hasKey("StillWaterType")) {
			this.defaultStillFluid = nBTTagCompound1.getByte("StillWaterType");
		} else {
			this.defaultStillFluid = (byte)(this.defaultFluid + 1);
		}

		if(nBTTagCompound1.hasKey("GroundTopType")) {
			this.topBlock = nBTTagCompound1.getByte("GroundTopType");
		}

		if(nBTTagCompound1.hasKey("GroundInnerType")) {
			this.fillerBlock = nBTTagCompound1.getByte("GroundInnerType");
		}

		if(nBTTagCompound1.hasKey("SandTopType")) {
			this.sandTopBlock = nBTTagCompound1.getByte("SandTopType");
		}

		if(nBTTagCompound1.hasKey("SandInnerType")) {
			this.sandFillerBlock = nBTTagCompound1.getByte("SandInnerType");
		}

		if(nBTTagCompound1.hasKey("GravelTopType")) {
			this.gravelTopBlock = nBTTagCompound1.getByte("GravelTopType");
		}

		if(nBTTagCompound1.hasKey("GravelInnerType")) {
			this.gravelFillerBlock = nBTTagCompound1.getByte("GravelInnerType");
		}

		if(nBTTagCompound1.hasKey("RockTopType")) {
			this.stoneTopBlock = nBTTagCompound1.getByte("RockTopType");
		} else {
			this.stoneTopBlock = 0;
		}

		if(nBTTagCompound1.hasKey("RockInnerType")) {
			this.stoneFillerBlock = nBTTagCompound1.getByte("RockInnerType");
		} else {
			this.stoneFillerBlock = this.defaultBlock;
		}

		if(nBTTagCompound1.hasKey("SandBound")) {
			this.sandNoise = nBTTagCompound1.getDouble("SandBound");
		}

		if(nBTTagCompound1.hasKey("GravelBound")) {
			this.gravelNoise = nBTTagCompound1.getDouble("GravelBound");
		}

		if(nBTTagCompound1.hasKey("RockBound")) {
			this.stoneNoise = nBTTagCompound1.getInteger("RockBound");
		}

		if(nBTTagCompound1.hasKey("WaterHeight")) {
			this.waterLevel = nBTTagCompound1.getShort("WaterHeight");
		}

		if(nBTTagCompound1.hasKey("BeachHeight")) {
			this.beachLevel = nBTTagCompound1.getShort("BeachHeight");
		} else {
			this.beachLevel = this.waterLevel;
		}

		if(nBTTagCompound1.hasKey("Carver")) {
			this.carver = nBTTagCompound1.getInteger("Carver");
		}

		if(nBTTagCompound1.hasKey("Trees")) {
			this.treesPerChunk = nBTTagCompound1.getInteger("Trees");
		}

		if(nBTTagCompound1.hasKey("LargeTree")) {
			this.bigTree = nBTTagCompound1.getInteger("LargeTree");
		}

		if(nBTTagCompound1.hasKey("Flowers")) {
			this.flowersPerChunk = nBTTagCompound1.getByte("Flowers");
		}

		if(nBTTagCompound1.hasKey("Cacti")) {
			this.cactiPerChunk = nBTTagCompound1.getInteger("Cacti");
		}

		if(nBTTagCompound1.hasKey("Mushrooms")) {
			this.mushroomsPerChunk = nBTTagCompound1.getByte("Mushrooms");
		}

		if(nBTTagCompound1.hasKey("Reeds")) {
			this.reedsPerChunk = nBTTagCompound1.getInteger("Reeds");
		}

		if(nBTTagCompound1.hasKey("Clay")) {
			this.clayPerChunk = nBTTagCompound1.getInteger("Clay");
		}

	}

	public NBTTagCompound getNBTTagCompound() {
		NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
		nBTTagCompound1.setByte("StoneType", this.defaultBlock);
		nBTTagCompound1.setByte("WaterType", this.defaultFluid);
		nBTTagCompound1.setByte("StillWaterType", this.defaultStillFluid);
		nBTTagCompound1.setByte("GroundTopType", this.topBlock);
		nBTTagCompound1.setByte("GroundInnerType", this.fillerBlock);
		nBTTagCompound1.setByte("SandTopType", this.sandTopBlock);
		nBTTagCompound1.setByte("SandInnerType", this.sandFillerBlock);
		nBTTagCompound1.setByte("GravelTopType", this.gravelTopBlock);
		nBTTagCompound1.setByte("GravelInnerType", this.gravelFillerBlock);
		nBTTagCompound1.setByte("RockTopType", this.stoneTopBlock);
		nBTTagCompound1.setByte("RockInnerType", this.stoneFillerBlock);
		nBTTagCompound1.setDouble("SandBound", this.sandNoise);
		nBTTagCompound1.setDouble("GravelBound", this.gravelNoise);
		nBTTagCompound1.setInteger("RockBound", this.stoneNoise);
		nBTTagCompound1.setShort("WaterHeight", this.waterLevel);
		nBTTagCompound1.setShort("BeachHeight", this.beachLevel);
		nBTTagCompound1.setInteger("Carver", this.carver);
		nBTTagCompound1.setInteger("Trees", this.treesPerChunk);
		nBTTagCompound1.setInteger("LargeTree", this.bigTree);
		nBTTagCompound1.setByte("Flowers", this.flowersPerChunk);
		nBTTagCompound1.setInteger("Cacti", this.cactiPerChunk);
		nBTTagCompound1.setByte("Mushrooms", this.mushroomsPerChunk);
		nBTTagCompound1.setInteger("Reeds", this.reedsPerChunk);
		nBTTagCompound1.setInteger("Clay", this.clayPerChunk);
		return nBTTagCompound1;
	}

	public void setTheme(String string1) {
		if(string1.equalsIgnoreCase("OFF")) {
			this.mc.creativeMode = false;
		} else if(string1.equalsIgnoreCase("ON")) {
			this.mc.creativeMode = true;
		}

	}
}
