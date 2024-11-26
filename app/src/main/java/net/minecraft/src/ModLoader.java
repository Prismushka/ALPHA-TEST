package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ModLoader {
	private static List mods = new ArrayList();

	private static void addMod(String string0) {
		try {
			Class class1 = ModLoader.class.getClassLoader().loadClass(string0);
			if(class1.getSuperclass() != BaseMod.class) {
				return;
			}

			if(mods.add((BaseMod)class1.newInstance())) {
				System.out.println("Mod Loaded: " + string0);
			}
		} catch (Exception exception2) {
			exception2.printStackTrace();
		}

	}

	public static void Init() {
		System.out.println("Mod Init");
	}

	public static void AddAllRecipes(CraftingManager craftingManager0) {
		for(int i1 = 0; i1 < mods.size(); ++i1) {
			((BaseMod)mods.get(i1)).AddRecipes(craftingManager0);
		}

	}

	public static void RegisterAllBlocks(List list0) {
		for(int i1 = 0; i1 < mods.size(); ++i1) {
			((BaseMod)mods.get(i1)).RegisterBlocks(list0);
		}

	}

	public static void AddAllRenderers(Map map0) {
		for(int i1 = 0; i1 < mods.size(); ++i1) {
			((BaseMod)mods.get(i1)).AddRenderer(map0);
		}

	}

	public static void AddAllEntityIDs() {
		for(int i0 = 0; i0 < mods.size(); ++i0) {
			((BaseMod)mods.get(i0)).AddEntityID();
		}

	}

	public static int AddAllSmelting(int i0) {
		int i1 = -1;

		for(int i2 = 0; i2 < mods.size() && i1 == -1; ++i2) {
			i1 = ((BaseMod)mods.get(i2)).AddSmelting(i0);
		}

		return i1;
	}

	public static int AddAllFuel(int i0) {
		int i1 = 0;

		for(int i2 = 0; i2 < mods.size() && i1 == 0; ++i2) {
			i1 = ((BaseMod)mods.get(i2)).AddFuel(i0);
		}

		return i1;
	}

	static {
		try {
			File file0 = new File(ModLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			String string1;
			if(file0.isFile() && file0.getName().endsWith(".jar")) {
				JarInputStream jarInputStream5 = new JarInputStream(new FileInputStream(file0));
				JarEntry jarEntry6 = null;

				while(true) {
					jarEntry6 = jarInputStream5.getNextJarEntry();
					if(jarEntry6 == null) {
						break;
					}

					string1 = jarEntry6.getName();
					if(!jarEntry6.isDirectory() && string1.startsWith("mod_") && string1.endsWith(".class")) {
						addMod(string1.substring(0, string1.length() - 6));
					}
				}
			} else if(file0.isDirectory()) {
				File[] file2 = file0.listFiles();
				if(file2 != null) {
					for(int i3 = 0; i3 < file2.length; ++i3) {
						string1 = file2[i3].getName();
						if(file2[i3].isFile() && string1.startsWith("mod_") && string1.endsWith(".class")) {
							addMod(string1.substring(0, string1.length() - 6));
						}
					}
				}
			}
		} catch (Exception exception4) {
			exception4.printStackTrace();
		}

	}
}
