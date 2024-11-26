package net.minecraft.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Bootstrap {
	private final File nativesFolder = new File(this.getAppDir("ALPHA+"), "natives");
	private final List<String> classpathEntities = new LinkedList();

	public Bootstrap() {
		if (!this.nativesFolder.exists()) {
			this.nativesFolder.mkdirs();
		}

	}

	public static void main(String[] args) {
		Bootstrap newStarter = new Bootstrap();
		newStarter.makeClasspath();
		newStarter.checkWorkspace();
		newStarter.startMinecraft(args);
	}

	private void makeClasspath() {
		String[] split = System.getProperty("java.class.path").split(File.pathSeparator);
		String[] myLocation = split;
		int var3 = split.length;

		for (int var4 = 0; var4 < var3; ++var4) {
			String url = myLocation[var4];
			this.classpathEntities.add(Paths.get(url, new String[0]).toString());
		}

		File var6 = this.getMyLocation();
		if (!this.classpathEntities.contains(var6.getAbsolutePath())) {
			this.classpathEntities.add(var6.getAbsolutePath());
		}

	}

	private void checkWorkspace() {
		File[] nativeFiles = this.nativesFolder.listFiles();
		if (nativeFiles == null || nativeFiles.length == 0) {
			this.unpackNatives(this.getMyLocation());
		}

		if (!this.hasLibraryPathProvided()) {
			this.setLibraryPath(this.nativesFolder);
		}

	}

	private void startMinecraft(String... args) {
		File myLocation = this.getMyLocation();
		long manualMemory = 0L;
		Iterator javaBin = ManagementFactory.getRuntimeMXBean().getInputArguments().iterator();

		while (javaBin.hasNext()) {
			String java = (String) javaBin.next();
			if (java.toLowerCase().startsWith("-xmx")) {
				manualMemory = Runtime.getRuntime().maxMemory();
			}
		}

		if (myLocation.isDirectory()) {
			addDir(this.nativesFolder.getAbsolutePath());
			Minecraft.main(args);
		} else {
			File var15 = new File(System.getProperty("java.home"), "bin");
			File var16 = new File(var15, "java");
			int debugPort = (new Random()).nextInt(Short.MAX_VALUE) + 16383;
			StringBuilder sb = new StringBuilder();
			Iterator pb = this.classpathEntities.iterator();

			while (pb.hasNext()) {
				String e = (String) pb.next();
				sb.append(e);
				if (pb.hasNext()) {
					sb.append(File.pathSeparator);
				}
			}

			ProcessBuilder var17 = new ProcessBuilder(new String[0]);
			var17.directory(myLocation.getParentFile());
			var17.command(new String[] { var16.getAbsolutePath(), "-Xdebug",
					"-Xrunjdwp:transport=dt_socket,address=" + debugPort + ",server=y,suspend=n",
					"-Djava.library.path=" + System.getProperty("java.library.path"),
					"-Xmx" + (manualMemory == 0L ? 404750336L : manualMemory), "-cp", sb.toString(),
					"net.minecraft.client.Minecraft" });
			String[] var18 = args;
			int var11 = args.length;

			for (int var12 = 0; var12 < var11; ++var12) {
				String arg = var18[var12];
				var17.command().add(arg);
			}

			try {
				var17.inheritIO().start();
				System.exit(0);
			} catch (IOException var14) {
				var14.printStackTrace();
			}

		}
	}

	private void setLibraryPath(File libraryPath) {
		System.setProperty("java.library.path", libraryPath.getAbsolutePath());
	}

	private void unpackNatives(File source) {
		if (!this.hasLibraryPathProvided()) {
			if (source.isDirectory()) {
				if (this.classpathEntities.isEmpty()) {
					throw new RuntimeException("Classpath is empty, can\'t find any native package");
				} else {
					Iterator e1 = this.classpathEntities.iterator();

					while (e1.hasNext()) {
						String classpathEntity1 = (String) e1.next();
						String entities1 = classpathEntity1.toLowerCase();
						if (entities1.contains("platform") && entities1.endsWith(".jar")) {
							this.unpackNatives(new File(classpathEntity1));
						}
					}

				}
			} else {
				try {
					JarFile e = new JarFile(source, false);
					Throwable classpathEntity = null;

					try {
						Enumeration entities = e.entries();

						while (entities.hasMoreElements()) {
							JarEntry entry = (JarEntry) entities.nextElement();
							if (!entry.isDirectory() && entry.getName().indexOf(47) == -1
									&& this.isNativeFile(entry.getName())) {
								InputStream in = e.getInputStream(e.getEntry(entry.getName()));
								File f = new File(this.nativesFolder, entry.getName());
								Files.copy(in, f.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
							}
						}
					} catch (Throwable var16) {
						classpathEntity = var16;
						throw var16;
					} finally {
						if (e != null) {
							if (classpathEntity != null) {
								try {
									e.close();
								} catch (Throwable var15) {
									classpathEntity.addSuppressed(var15);
								}
							} else {
								e.close();
							}
						}

					}
				} catch (IOException var18) {
					var18.printStackTrace();
				}

			}
		}
	}

	private boolean hasLibraryPathProvided() {
		String libraryPath = System.getProperty("java.library.path");
		int libraryPathLength = libraryPath.split(System.getProperty("path.separator")).length;
		return libraryPathLength == 1;
	}

	private File getMyLocation() {
		try {
			return new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException var2) {
			throw new RuntimeException("Failed to get location of binary file", var2);
		}
	}

	private boolean isNativeFile(String entryName) {
		String osName = System.getProperty("os.name");
		String name = entryName.toLowerCase();
		return osName.startsWith("Win") ? name.endsWith(".dll")
				: (!osName.startsWith("Mac") && !osName.startsWith("Darwin") ? name.endsWith(".so")
						: name.endsWith(".jnilib") || name.endsWith(".dylib"));
	}

	public File getAppDir(String var0) {
		String var = System.getProperty("os.name").toLowerCase();
		String userHome = System.getProperty("user.home", ".");
		File var2;
		if (!var.contains("linux") && !var.contains("unix")) {
			if (var.contains("win")) {
				String var3 = System.getenv("APPDATA");
				if (var3 != null) {
					var2 = new File(var3, "." + var0 + '/');
				} else {
					var2 = new File(userHome, '.' + var0 + '/');
				}
			} else if (!var.contains("mac") && !var.contains("darwin")) {
				var2 = new File(userHome, var0 + '/');
			} else {
				var2 = new File(userHome, "Library/Application Support/" + var0);
			}
		} else {
			var2 = new File(userHome, '.' + var0 + '/');
		}

		if (!var2.exists() && !var2.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + var2);
		} else {
			return var2;
		}
	}

	public static void addDir(String s) {
		try {
			Field e = ClassLoader.class.getDeclaredField("usr_paths");
			e.setAccessible(true);
			String[] paths = (String[]) ((String[]) e.get((Object) null));
			String[] tmp = paths;
			int var4 = paths.length;

			for (int var5 = 0; var5 < var4; ++var5) {
				String path = tmp[var5];
				if (s.equals(path)) {
					return;
				}
			}

			tmp = new String[paths.length + 1];
			System.arraycopy(paths, 0, tmp, 0, paths.length);
			tmp[paths.length] = s;
			e.set((Object) null, tmp);
			System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + s);
		} catch (IllegalAccessException var7) {
			throw new RuntimeException("Failed to get permissions to set library path", var7);
		} catch (NoSuchFieldException var8) {
			(new RuntimeException("Failed to get field handle to set library path", var8)).printStackTrace();
			System.setProperty("org.lwjgl.librarypath", s);
			System.setProperty("net.java.games.input.librarypath", s);
		}

	}

	// public static File getMinecraftDir() {
	// 	if(minecraftDir == null) {
	// 		minecraftDir = getAppDir("ALPHA+");
	// 	}

	// 	return minecraftDir;
	// }

	static {
		Bootstrap newStarter = new Bootstrap();
		newStarter.makeClasspath();
	}
}
