package net.minecraft.src;

public class ItemEraser extends ItemTool {
	private static Block[] blocksEffectiveAgainst = new Block[]{Block.stone, Block.dirt, Block.grass, Block.cobblestone, Block.planks, Block.sapling, Block.bedrock, Block.sand, Block.gravel, Block.oreGold, Block.oreIron, Block.oreCoal, Block.wood, Block.leaves, Block.sponge, Block.glass, Block.clothRed, Block.clothOrange, Block.clothYellow, Block.clothChartreuse, Block.clothGreen, Block.clothSpringGreen, Block.clothCyan, Block.clothCapri, Block.clothUltramarine, Block.clothViolet, Block.clothPurple, Block.clothMagenta, Block.clothRose, Block.clothDarkGray, Block.cloth, Block.clothGray, Block.plantYellow, Block.plantRed, Block.mushroomBrown, Block.mushroomRed, Block.blockGold, Block.blockSteel, Block.stairDouble, Block.stairSingle, Block.brick, Block.tnt, Block.bookshelf, Block.cobblestoneMossy, Block.obsidian, Block.torch, Block.fire, Block.mobSpawner, Block.stairCompactWood, Block.chest, Block.redstoneWire, Block.oreDiamond, Block.blockDiamond, Block.workbench, Block.crops, Block.tilledField, Block.stoneOvenIdle, Block.stoneOvenActive, Block.signStanding, Block.doorWood, Block.ladder, Block.minecartTrack, Block.stairCompactStone, Block.signWall, Block.lever, Block.pressurePlateStone, Block.doorSteel, Block.pressurePlateWood, Block.oreRedstone, Block.oreRedstoneGlowing, Block.torchRedstoneIdle, Block.torchRedstoneActive, Block.button, Block.snow, Block.ice, Block.blockSnow, Block.cactus, Block.blockClay, Block.reed, Block.jukebox, Block.fence, Block.plantOrange, Block.plantChartreuse, Block.plantGreen, Block.plantSpringGreen, Block.plantCyan, Block.plantCapri, Block.plantUltramarine, Block.plantViolet, Block.plantPurple, Block.plantMagenta, Block.plantRose, Block.bedrock};
	private int harvestLevel;

	public ItemEraser(int i1, int i2) {
		super(i1, 2, i2, blocksEffectiveAgainst);
		this.harvestLevel = i2;
	}

	public boolean canHarvestBlock(Block block) {
		return block == Block.obsidian ? this.harvestLevel == 100000 : (block != Block.blockDiamond && block != Block.oreDiamond ? (block != Block.blockGold && block != Block.oreGold ? (block != Block.blockSteel && block != Block.oreIron ? (block != Block.oreRedstone && block != Block.oreRedstoneGlowing ? (block.material == Material.rock ? true : block.material == Material.iron) : this.harvestLevel >= 2) : this.harvestLevel >= 1) : this.harvestLevel >= 2) : this.harvestLevel >= 100000);
	}
}
