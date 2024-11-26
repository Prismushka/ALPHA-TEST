package net.minecraft.src;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.minecraft.client.Minecraft;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ThreadDownloadResources extends Thread {
	public File resourcesFolder;
	private Minecraft mc;
	private boolean closing = false;

	public ThreadDownloadResources(File var1, Minecraft var2) {
		this.mc = var2;
		this.setName("Resource download thread");
		this.setDaemon(true);
		this.resourcesFolder = new File(var1, "plus_resources/");
		if(!this.resourcesFolder.exists() && !this.resourcesFolder.mkdirs()) {
			throw new RuntimeException("The working directory could not be created: " + this.resourcesFolder);
		}
	}

	public void run() {
		try {
			this.mc.resourcesToDownload = 0;
			this.mc.resourcesDownloaded = 0;
			URL var13 = new URL("https://androdome.com/ResAlpha/");
			DocumentBuilderFactory var2 = DocumentBuilderFactory.newInstance();
			DocumentBuilder var3 = var2.newDocumentBuilder();
			Document var4 = var3.parse(var13.openStream());
			NodeList var5 = var4.getElementsByTagName("Contents");

			int var6;
			for(var6 = 0; var6 < var5.getLength(); ++var6) {
				Node var7 = var5.item(var6);
				if(var7.getNodeType() == 1) {
					++this.mc.resourcesToDownload;
				}
			}

			for(var6 = 0; var6 < 2; ++var6) {
				for(int i14 = 0; i14 < var5.getLength(); ++i14) {
					Node var8 = var5.item(i14);
					if(var8.getNodeType() == 1) {
						if(var6 == 0) {
							++this.mc.resourcesDownloaded;
						}

						Element var9 = (Element)var8;
						String var10 = ((Element)var9.getElementsByTagName("Key").item(0)).getChildNodes().item(0).getNodeValue();
						long var11 = Long.parseLong(((Element)var9.getElementsByTagName("Size").item(0)).getChildNodes().item(0).getNodeValue());
						if(var11 > 0L) {
							this.downloadAndInstallResource(var13, var10, var11, var6);
							if(this.closing) {
								return;
							}
						}
					}
				}
			}
		} catch (Exception exception13) {
			this.loadResource(this.resourcesFolder, "");
			exception13.printStackTrace();
		}

	}

	public void reloadResources() {
		this.loadResource(this.resourcesFolder, "");
	}

	private void loadResource(File var1, String var2) {
		File[] var3 = var1.listFiles();

		for(int var4 = 0; var4 < var3.length; ++var4) {
			if(var3[var4].isDirectory()) {
				this.loadResource(var3[var4], var2 + var3[var4].getName() + "/");
			} else {
				try {
					this.mc.installResource(var2 + var3[var4].getName(), var3[var4]);
				} catch (Exception exception6) {
					System.out.println("Failed to add " + var2 + var3[var4].getName());
				}
			}
		}

	}

	private void downloadAndInstallResource(URL var1, String var2, long var3, int var5) {
		try {
			int var10 = var2.indexOf("/");
			String var7 = var2.substring(0, var10);
			if(!var7.equals("sound") && !var7.equals("newsound")) {
				if(var5 != 1) {
					return;
				}
			} else if(var5 != 0) {
				return;
			}

			File soundFolder = new File(this.resourcesFolder, var2);
			if(!soundFolder.exists() || soundFolder.length() != var3) {
				soundFolder.getParentFile().mkdirs();
				String var9 = var2.replaceAll(" ", "%20");
				this.downloadResource(new URL(var1, var9), soundFolder, var3);
				if(this.closing) {
					return;
				}
			}

			this.mc.installResource(var2, soundFolder);
		} catch (Exception exception10) {
			exception10.printStackTrace();
		}

	}

	private void downloadResource(URL var1, File var2, long var3) throws IOException {
		byte[] var5 = new byte[4096];
		DataInputStream var6 = new DataInputStream(var1.openStream());
		DataOutputStream var7 = new DataOutputStream(new FileOutputStream(var2));

		int var9;
		while((var9 = var6.read(var5)) >= 0) {
			var7.write(var5, 0, var9);
			if(this.closing) {
				return;
			}
		}

		var6.close();
		var7.close();
	}

	public void closeMinecraft() {
		this.closing = true;
	}
}
