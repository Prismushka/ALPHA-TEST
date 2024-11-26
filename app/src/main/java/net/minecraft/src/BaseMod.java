package net.minecraft.src;

import java.util.List;
import java.util.Map;

public abstract class BaseMod {
	public void AddRecipes(CraftingManager craftingManager1) {
	}

	public void RegisterBlocks(List list1) {
	}

	public void AddRenderer(Map map1) {
	}

	public void AddEntityID() {
	}

	public int AddSmelting(int i1) {
		return -1;
	}

	public int AddFuel(int i1) {
		return 0;
	}
}
