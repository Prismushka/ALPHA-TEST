package net.minecraft.src;

import java.awt.Toolkit;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;

public class GuiFocusableTextField extends Gui {
	private final FontRenderer fontRenderer;
	private int cursorCounter;
	private final int xPos;
	private final int yPos;
	private final int width;
	private final int height;
	private String text;
	private int maxStringLength;
	private String allowedCharacters;
	public boolean isFocused;

	public GuiFocusableTextField(FontRenderer fontRenderer1, int i2, int i3, String string4) {
		this(fontRenderer1, i2, i3, 200, 20, string4);
	}

	public GuiFocusableTextField(FontRenderer fontRenderer1, int i2, int i3, int i4, int i5, String string6) {
		this.cursorCounter = 0;
		this.text = "";
		this.maxStringLength = 32;
		this.allowedCharacters = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_\'abcdefghijklmnopqrstuvwxyz{|}~\u2302\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb";
		this.isFocused = false;
		this.fontRenderer = fontRenderer1;
		this.xPos = i2;
		this.yPos = i3;
		this.width = i4;
		this.height = i5;
		this.setText(string6);
		this.setMaxStringLength(i4 * 16 / 100);
	}

	public GuiFocusableTextField(FontRenderer fontRenderer1, GuiButton guiButton2, String string3) {
		this(fontRenderer1, guiButton2.xPosition, guiButton2.yPosition, guiButton2.width, guiButton2.height, string3);
	}

	public void setText(String string1) {
		this.text = string1;
	}

	public String getText() {
		return this.text;
	}

	public void updateCursorCounter() {
		++this.cursorCounter;
	}

	public void textboxKeyTyped(char c1, int i2) {
		if(this.isFocused) {
			StringSelection stringSelection3;
			if(c1 == 3 && this.text != null && !this.text.isEmpty()) {
				try {
					stringSelection3 = new StringSelection(this.text);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection3, (ClipboardOwner)null);
				} catch (Exception exception7) {
				}
			}

			if(c1 == 22) {
				String string4 = GuiScreen.getClipboardString();
				if(string4 == null) {
					string4 = "";
				}

				int i5 = this.maxStringLength - this.text.length();
				if(i5 > string4.length() || this.maxStringLength == 0) {
					i5 = string4.length();
				}

				if(i5 > 0) {
					this.text = this.text + string4.substring(0, i5);
				}
			}

			if(i2 == 14 && this.text.length() > 0) {
				this.text = this.text.substring(0, this.text.length() - 1);
			}

			if(c1 == 24 && this.text != null && !this.text.isEmpty()) {
				try {
					stringSelection3 = new StringSelection(this.text);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection3, (ClipboardOwner)null);
				} catch (Exception exception6) {
				}

				this.text = "";
			}

			if(this.allowedCharacters.indexOf(c1) >= 0 && (this.text.length() < this.maxStringLength || this.maxStringLength == 0)) {
				this.text = this.text + c1;
			}
		}

	}

	public void setFocused(boolean z1) {
		if(z1 && !this.isFocused) {
			this.cursorCounter = 0;
		}

		this.isFocused = z1;
	}

	public void drawTextBox() {
		if(this.isFocused) {
			this.drawRect(this.xPos - 1, this.yPos - 1, this.xPos + this.width + 1, this.yPos + this.height + 1, -6250336);
			this.drawRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, 0xFF000000);
			this.drawString(this.fontRenderer, this.text + (this.cursorCounter / 6 % 2 == 0 ? "_" : ""), this.xPos + 4, this.yPos + (this.height - 8) / 2, 14737632);
		}

	}

	public void setMaxStringLength(int i1) {
		this.maxStringLength = i1;
	}

	public void setAllowedCharacters(String string1) {
		this.allowedCharacters = string1;
	}
}
