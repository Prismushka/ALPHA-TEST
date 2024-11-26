package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class ItemTooltip {
	public static ItemTooltip[] items = new ItemTooltip[32000];
	public static ItemTooltip[][] fromMetadata = new ItemTooltip[32000][16];
	public List lines;
	public List colors;
	public Pair gradient;
	public static final Pair DEFAULT_GRAD = new Pair(-1073741824, -1073741824);
	public static final Pair SPECIAL_GRAD = new Pair(-1072689120, -1070386330);
	public static final Pair INVALID_GRAD = new Pair(-1065479662, -1065479662);
	public static final Pair LEGENDARY_GRAD = new Pair(-1071640544, -1067043994);
	public static final int INVALID_COL = 16733525;
	public static final int DESCRIPTION_COL = 11184810;
	public static final int INFO_COL = 11184742;
	public static final int UNCOMMON_COL = 16777045;
	public static final int RARE_COL = 5636095;
	public static final int LEGENDARY_COL = 16733695;
	private boolean parsedString;
	private String nextText;

	public ItemTooltip addLine(String string1, int i2) {
		this.lines.add(string1);
		this.colors.add(i2);
		return this;
	}

	public ItemTooltip(Pair pair1, Object... object2) {
		this.lines = new ArrayList();
		this.colors = new ArrayList();
		this.gradient = pair1;
		Object[] object3 = object2;
		int i4 = object2.length;

		for(int i5 = 0; i5 < i4; ++i5) {
			Object object6 = object3[i5];
			if(Item.class.isAssignableFrom(object6.getClass())) {
				Object[] object7 = ((Item)object6).getDynamicTooltip();
				int i8 = object7.length;

				for(int i9 = 0; i9 < i8; ++i9) {
					Object object10 = object7[i9];
					this.parseLine(object10);
				}
			} else {
				this.parseLine(object6);
			}
		}

		if(this.parsedString) {
			this.lines.add(this.nextText);
			this.colors.add(0xFFFFFF);
		}

	}

	public ItemTooltip copy() {
		return new ItemTooltip(this.gradient, new Object[]{new ArrayList(this.lines), new ArrayList(this.colors)});
	}

	public ItemTooltip(Object... object1) {
		this(DEFAULT_GRAD, object1);
	}

	public static ItemTooltip invalid(String string0, Object... object1) {
		return new ItemTooltip(INVALID_GRAD, new Object[]{string0, 16733525, object1});
	}

	public static ItemTooltip legendary(String string0, Object... object1) {
		return new ItemTooltip(LEGENDARY_GRAD, new Object[]{string0, 0xFFFFFF, object1});
	}

	public int size() {
		return this.lines.size();
	}

	private void parseLine(Object object1) {
		if(object1 instanceof String) {
			if(this.parsedString) {
				this.lines.add(this.nextText);
				this.colors.add(0xFFFFFF);
			}

			this.nextText = (String)object1;
			this.parsedString = true;
		} else if(object1 instanceof Integer) {
			if(this.parsedString) {
				this.lines.add(this.nextText);
				this.colors.add(((Integer)object1).intValue());
			}

			this.parsedString = false;
		}

	}

	static {
		items[1] = new ItemTooltip(new Object[]{"Stone"});
		items[2] = new ItemTooltip(new Object[]{"Grass"});
		items[3] = new ItemTooltip(new Object[]{"Dirt"});
		items[4] = new ItemTooltip(new Object[]{"Cobblestone"});
		items[5] = new ItemTooltip(new Object[]{"Wooden Planks"});
		items[6] = new ItemTooltip(new Object[]{"Sapling"});
		items[7] = invalid("Bedrock", new Object[0]);
		items[8] = invalid("Flowing Water", new Object[0]);
		items[9] = invalid("Still Water", new Object[0]);
		items[10] = invalid("Flowing Lava", new Object[0]);
		items[11] = invalid("Still Lava", new Object[0]);
		items[12] = new ItemTooltip(new Object[]{"Sand"});
		items[13] = new ItemTooltip(new Object[]{"Gravel"});
		items[14] = new ItemTooltip(new Object[]{"Gold Ore"});
		items[15] = new ItemTooltip(new Object[]{"Iron Ore"});
		items[16] = new ItemTooltip(new Object[]{"Coal Ore"});
		items[17] = new ItemTooltip(new Object[]{"Log"});
		items[18] = new ItemTooltip(new Object[]{"Leaves"});
		items[19] = new ItemTooltip(new Object[]{"Sponge"});
		items[20] = new ItemTooltip(new Object[]{"Glass"});
		items[21] = new ItemTooltip(new Object[]{"Red Cloth"});
		items[22] = new ItemTooltip(new Object[]{"Orange Cloth"});
		items[23] = new ItemTooltip(new Object[]{"Yellow Cloth"});
		items[24] = new ItemTooltip(new Object[]{"Chartreuse Cloth"});
		items[25] = new ItemTooltip(new Object[]{"Green Cloth"});
		items[26] = new ItemTooltip(new Object[]{"Spring Green Cloth"});
		items[27] = new ItemTooltip(new Object[]{"Cyan Cloth"});
		items[28] = new ItemTooltip(new Object[]{"Capri Cloth"});
		items[29] = new ItemTooltip(new Object[]{"Ultramarine Cloth"});
		items[30] = new ItemTooltip(new Object[]{"Violet Cloth"});
		items[31] = new ItemTooltip(new Object[]{"Purple Cloth"});
		items[32] = new ItemTooltip(new Object[]{"Magenta Cloth"});
		items[33] = new ItemTooltip(new Object[]{"Pink Cloth"});
		items[34] = new ItemTooltip(new Object[]{"Dark Gray Cloth"});
		items[35] = new ItemTooltip(new Object[]{"White Cloth"});
		items[36] = new ItemTooltip(new Object[]{"Gray Cloth"});
		items[37] = new ItemTooltip(new Object[]{"Yellow Flower"});
		items[38] = new ItemTooltip(new Object[]{"Red Flower"});
		items[39] = new ItemTooltip(new Object[]{"Brown Mushroom"});
		items[40] = new ItemTooltip(new Object[]{"Red Mushroom"});
		items[41] = new ItemTooltip(new Object[]{"Block of Gold"});
		items[42] = new ItemTooltip(new Object[]{"Block of Iron"});
		items[43] = invalid("Double Stone Slab", new Object[0]);
		items[44] = new ItemTooltip(new Object[]{"Stone Slab"});
		items[45] = new ItemTooltip(new Object[]{"Bricks"});
		items[46] = new ItemTooltip(new Object[]{"TNT"});
		items[47] = new ItemTooltip(new Object[]{"Bookshelf"});
		items[48] = new ItemTooltip(new Object[]{"Mossy Cobblestone"});
		items[49] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Obsidian"});
		items[50] = new ItemTooltip(new Object[]{"Torch"});
		items[51] = invalid("Fire", new Object[0]);
		items[52] = invalid("Mob Spawner", new Object[0]);
		items[53] = new ItemTooltip(new Object[]{"Wooden Stairs"});
		items[54] = new ItemTooltip(new Object[]{"Chest"});
		items[55] = invalid("Redstone Dust (Block)", new Object[0]);
		items[56] = new ItemTooltip(new Object[]{"Diamond Ore"});
		items[57] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Block of Diamond"});
		items[58] = new ItemTooltip(new Object[]{"Crafting Table"});
		items[59] = invalid("Crops", new Object[0]);
		items[60] = invalid("Farmland", new Object[0]);
		items[61] = new ItemTooltip(new Object[]{"Furnace"});
		items[62] = invalid("Lit Furnace", new Object[0]);
		items[63] = invalid("Sign (Block)", new Object[0]);
		items[64] = invalid("Wooden Door (Block)", new Object[0]);
		items[65] = new ItemTooltip(new Object[]{"Ladder"});
		items[66] = new ItemTooltip(new Object[]{"Minecart Track"});
		items[67] = new ItemTooltip(new Object[]{"Cobblestone Stairs"});
		items[68] = invalid("Wall Sign (Block)", new Object[0]);
		items[69] = new ItemTooltip(new Object[]{"Lever"});
		items[70] = new ItemTooltip(new Object[]{"Stone Pressure Plate"});
		items[71] = invalid("Iron Door (Block)", new Object[0]);
		items[72] = new ItemTooltip(new Object[]{"Wooden Pressure Plate"});
		items[73] = new ItemTooltip(new Object[]{"Redstone Ore"});
		items[74] = invalid("Glowing Redstone Ore", new Object[0]);
		items[75] = invalid("Redstone Torch (Off)", new Object[0]);
		items[76] = new ItemTooltip(new Object[]{"Redstone Torch"});
		items[77] = new ItemTooltip(new Object[]{"Stone Button"});
		items[78] = new ItemTooltip(new Object[]{"Snow"});
		items[79] = new ItemTooltip(new Object[]{"Ice"});
		items[80] = new ItemTooltip(new Object[]{"Snow Block"});
		items[81] = new ItemTooltip(new Object[]{"Cactus"});
		items[82] = new ItemTooltip(new Object[]{"Clay Block"});
		items[83] = invalid("Sugar Cane (Block)", new Object[0]);
		items[84] = new ItemTooltip(new Object[]{"Jukebox"});
		items[85] = new ItemTooltip(new Object[]{"Fence"});
		items[86] = new ItemTooltip(new Object[]{"Orange Flower"});
		items[87] = new ItemTooltip(new Object[]{"Chartreuse Flower"});
		items[88] = new ItemTooltip(new Object[]{"Green Flower"});
		items[89] = new ItemTooltip(new Object[]{"Spring Green Flower"});
		items[90] = new ItemTooltip(new Object[]{"Cyan Flower"});
		items[91] = new ItemTooltip(new Object[]{"Capri Flower"});
		items[92] = new ItemTooltip(new Object[]{"Ultramarine Flower"});
		items[93] = new ItemTooltip(new Object[]{"Violet Flower"});
		items[94] = new ItemTooltip(new Object[]{"Purple Flower"});
		items[95] = new ItemTooltip(new Object[]{"Magenta Flower"});
		items[96] = new ItemTooltip(new Object[]{"Pink Flower"});
		items[256] = new ItemTooltip(new Object[]{"Iron Shovel", Item.shovel});
		items[257] = new ItemTooltip(new Object[]{"Iron Pickaxe", Item.pickaxeSteel});
		items[258] = new ItemTooltip(new Object[]{"Iron Axe", Item.axeSteel});
		items[259] = new ItemTooltip(new Object[]{"Flint and Steel"});
		items[260] = new ItemTooltip(new Object[]{"Apple", Item.appleRed});
		items[261] = new ItemTooltip(new Object[]{"Bow"});
		items[262] = new ItemTooltip(new Object[]{"Arrow"});
		items[263] = new ItemTooltip(new Object[]{"Coal"});
		items[264] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond"});
		items[265] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Ingot"});
		items[266] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Gold Ingot"});
		items[267] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Sword", "+3 Damage", 11184810, Item.swordSteel});
		items[268] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Wooden Sword", "+1 Damage", 11184810, Item.swordWood});
		items[269] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Wooden Shovel", Item.shovelWood});
		items[270] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Wooden Pickaxe", Item.pickaxeWood});
		items[271] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Wooden Axe", Item.axeWood});
		items[272] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Stone Sword", "+2 Damage", 11184810, Item.swordStone});
		items[273] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Stone Shovel", Item.shovelStone});
		items[274] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Stone Pickaxe", Item.pickaxeStone});
		items[275] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Stone Axe", Item.axeStone});
		items[276] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Sword", "+5 Damage", 11184810, Item.swordDiamond});
		items[277] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Shovel", Item.shovelDiamond});
		items[278] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Pickaxe", Item.pickaxeDiamond});
		items[279] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Axe", Item.axeDiamond});
		items[280] = new ItemTooltip(new Object[]{"Stick"});
		items[281] = new ItemTooltip(new Object[]{"Bowl"});
		items[282] = new ItemTooltip(new Object[]{"Mushroom Stew", Item.bowlSoup});
		items[283] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Sword", "+4 Damage", 11184810, Item.swordGold});
		items[284] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Shovel", Item.shovelGold});
		items[285] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Pickaxe", Item.pickaxeGold});
		items[286] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Axe", Item.axeGold});
		items[287] = new ItemTooltip(new Object[]{"String"});
		items[288] = new ItemTooltip(new Object[]{"Feather"});
		items[289] = new ItemTooltip(new Object[]{"Gunpowder"});
		items[290] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Wooden Hoe", Item.hoeWood});
		items[291] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Stone Hoe", Item.hoeStone});
		items[292] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Hoe", Item.hoeSteel});
		items[293] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Hoe", Item.hoeDiamond});
		items[294] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Hoe", Item.hoeGold});
		items[295] = new ItemTooltip(new Object[]{"Seeds"});
		items[296] = new ItemTooltip(new Object[]{"Wheat"});
		items[297] = new ItemTooltip(new Object[]{"Bread", Item.bread});
		items[298] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Leather Cap", "+1 Armor", 11184810, Item.helmetLeather});
		items[299] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Leather Tunic", "+1 Armor", 11184810, Item.plateLeather});
		items[300] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Leather Pants", "+1 Armor", 11184810, Item.legsLeather});
		items[301] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Leather Boots", "+1 Armor", 11184810, Item.bootsLeather});
		items[302] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Chainmail Helmet", "How did you get this?", 11184810, Item.helmetChain});
		items[303] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Chainmail Chestplate", "How did you get this?", 11184810, Item.plateChain});
		items[304] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Chainmail Leggings", "How did you get this?", 11184810, Item.legsChain});
		items[305] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Chainmail Boots", "How did you get this?", 11184810, Item.bootsChain});
		items[306] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Helmet", "+2 Armor", 11184810, Item.helmetSteel});
		items[307] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Chestplate", "+2 Armor", 11184810, Item.plateSteel});
		items[308] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Leggings", "+2 Armor", 11184810, Item.legsSteel});
		items[309] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Iron Boots", "+2 Armor", 11184810, Item.bootsSteel});
		items[310] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Helmet", "+4 Armor", 11184810, Item.helmetDiamond});
		items[311] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Chestplate", "+4 Armor", 11184810, Item.plateDiamond});
		items[312] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Leggings", "+4 Armor", 11184810, Item.legsDiamond});
		items[313] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Diamond Boots", "+4 Armor", 11184810, Item.bootsDiamond});
		items[314] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Helmet", "+3 Armor", 11184810, Item.helmetGold});
		items[315] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Chestplate", "+3 Armor", 11184810, Item.plateGold});
		items[316] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Leggings", "+3 Armor", 11184810, Item.legsGold});
		items[317] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Golden Boots", "+3 Armor", 11184810, Item.bootsGold});
		items[318] = new ItemTooltip(new Object[]{"Flint"});
		items[319] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Raw Porkchop", Item.porkRaw});
		items[320] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Cooked Porkchop", Item.porkCooked});
		items[321] = new ItemTooltip(new Object[]{"Painting"});
		items[322] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Golden Apple", Item.appleGold});
		items[323] = new ItemTooltip(new Object[]{"Sign"});
		items[324] = new ItemTooltip(new Object[]{"Wooden Door"});
		items[325] = new ItemTooltip(new Object[]{"Bucket"});
		items[326] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Water Bucket"});
		items[327] = new ItemTooltip(SPECIAL_GRAD, new Object[]{"Lava Bucket"});
		items[328] = new ItemTooltip(new Object[]{"Minecart"});
		items[329] = new ItemTooltip(new Object[]{"Saddle"});
		items[330] = new ItemTooltip(new Object[]{"Iron Door"});
		items[331] = new ItemTooltip(new Object[]{"Redstone Dust"});
		items[332] = new ItemTooltip(new Object[]{"Snowball"});
		items[333] = new ItemTooltip(new Object[]{"Boat"});
		items[334] = new ItemTooltip(new Object[]{"Leather"});
		items[335] = new ItemTooltip(new Object[]{"Milk"});
		items[336] = new ItemTooltip(new Object[]{"Brick"});
		items[337] = new ItemTooltip(new Object[]{"Clay Ball"});
		items[338] = new ItemTooltip(new Object[]{"Sugar Cane"});
		items[339] = new ItemTooltip(new Object[]{"Paper"});
		items[340] = new ItemTooltip(new Object[]{"Book"});
		items[341] = new ItemTooltip(new Object[]{"Slime Ball"});
		items[342] = new ItemTooltip(new Object[]{"Minecart with Chest"});
		items[343] = new ItemTooltip(new Object[]{"Minecart with Furnace"});
		items[344] = new ItemTooltip(new Object[]{"Egg"});
		items[345] = new ItemTooltip(new Object[]{"Compass"});
		items[346] = new ItemTooltip(new Object[]{"Fishing Rod"});
		items[347] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Eraser Tool", "Instantly breaks blocks!", 11184810, Item.EraserTool});
		items[2256] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Music Disc", "C418 - 13", 11184810, Item.record13});
		items[2257] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Music Disc", "C418 - Cat", 11184810, Item.recordCat});
		items[2258] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Music Disc", "Markus Alexei - Magnet", 11184810, Item.recordMagnet});
		items[2259] = new ItemTooltip(LEGENDARY_GRAD, new Object[]{"Music Disc", "C418 - Stal", 11184810, Item.recordStal});
	}
}
