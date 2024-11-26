package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiInventoryCreative extends GuiScreen {
	private static RenderBlocks renderBlocks = new RenderBlocks();
	public boolean dragging = false;
	protected List inventorySlots = new ArrayList();
	protected int xSize = 176;
	protected int ySize = 166;

	public GuiInventoryCreative() {
		this.allowUserInput = true;
	}

	private int getBlockAtPosition(int i1, int i2) {
		for(int i3 = 0; i3 < Session.registeredBlocksList.size(); ++i3) {
			int i4 = this.width / 2 + i3 % 9 * 24 + -108 - 3;
			int i5 = this.height / 2 + i3 / 9 * 24 + -60 + 3;
			if(i1 >= i4 && i1 <= i4 + 24 && i2 >= i5 - 12 && i2 <= i5 + 12) {
				return i3;
			}
		}

		return -1;
	}

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		if(this.dragging) {
			this.mc.entityRenderer.itemRenderer.resetEquippedProgress2();
		}

		int i4;
		for(i4 = 0; i4 < this.inventorySlots.size(); ++i4) {
			SlotInventory slotInventory5 = (SlotInventory)this.inventorySlots.get(i4);
			if(slotInventory5.getIsMouseOverSlot(mouseX, mouseY)) {
				this.drawSlotTooltip(slotInventory5, mouseX, mouseY);
			}
		}

		i4 = this.getBlockAtPosition(mouseX, mouseY);
		this.drawGradientRect(this.width / 2 - 120, this.height / 2 + -90, this.width / 2 + 120, this.height / 2 + 60, -1878719232, -1070583712);
		if(i4 >= 0) {
			int i15 = this.width / 2 + i4 % 9 * 24 + -108;
			int i6 = this.height / 2 + i4 / 9 * 24 + -60;
			this.drawGradientRect(i15 - 3, i6 - 8, i15 + 23, i6 + 24 - 6, -1862270977, -1056964609);
		}

		this.drawCenteredString(this.fontRenderer, "Select Block", this.width / 2, this.height / 2 + -80, 0xFFFFFF);
		RenderEngine renderEngine16 = this.mc.renderEngine;
		Tessellator tessellator17 = Tessellator.instance;
		renderEngine16.bindTexture(renderEngine16.getTexture("/terrain.png"));

		for(int i7 = 0; i7 < Session.registeredBlocksList.size(); ++i7) {
			Block block8 = (Block)Session.registeredBlocksList.get(i7);
			GL11.glPushMatrix();
			int i9 = this.width / 2 + i7 % 9 * 24 + -108;
			int i10 = this.height / 2 + i7 / 9 * 24 + -60;
			GL11.glTranslatef((float)i9, (float)i10, 0.0F);
			GL11.glScalef(10.0F, 10.0F, 10.0F);
			GL11.glTranslatef(1.0F, 0.5F, 8.0F);
			GL11.glRotatef(-30.0F, 1.0F, 0.0F, 0.0F);
			int i11 = block8.getRenderType();
			if(i11 != 1) {
				GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
			}

			if(i11 == 1 || i11 == 12 || i11 == 2) {
				GL11.glScalef(1.6F, 1.6F, 1.6F);
			}

			if(i4 == i7) {
				GL11.glScalef(1.6F, 1.6F, 1.6F);
			}

			GL11.glTranslatef(-1.5F, 0.5F, 0.5F);
			GL11.glScalef(-1.0F, -1.0F, -1.0F);
			tessellator17.startDrawingQuads();
			if(i11 == 0) {
				block8.setBlockBoundsForItemRender();
				float f12 = 0.5F;
				float f13 = 0.8F;
				float f14 = 0.6F;
				tessellator17.setColorOpaque_F(f12, f12, f12);
				renderBlocks.renderBottomFace(block8, -2.0D, 0.0D, 0.0D, block8.getBlockTextureFromSide(0));
				tessellator17.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderBlocks.renderTopFace(block8, -2.0D, 0.0D, 0.0D, block8.getBlockTextureFromSide(1));
				tessellator17.setColorOpaque_F(f13, f13, f13);
				renderBlocks.renderEastFace(block8, -2.0D, 0.0D, 0.0D, block8.getBlockTextureFromSide(2));
				tessellator17.setColorOpaque_F(f13, f13, f13);
				renderBlocks.renderWestFace(block8, -2.0D, 0.0D, 0.0D, block8.getBlockTextureFromSide(3));
				tessellator17.setColorOpaque_F(f14, f14, f14);
				renderBlocks.renderNorthFace(block8, -2.0D, 0.0D, 0.0D, block8.getBlockTextureFromSide(4));
				tessellator17.setColorOpaque_F(f14, f14, f14);
				renderBlocks.renderSouthFace(block8, -2.0D, 0.0D, 0.0D, block8.getBlockTextureFromSide(5));
			} else if(i11 == 1) {
				tessellator17.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderBlocks.renderCrossedSquares(block8, -1, -2.0D, 0.0D, 0.0D);
			} else if(i11 == 2) {
				renderBlocks.renderTorchAtAngle(block8, -2.0D, 0.0D, 0.0D, 0.0D, 0.0D);
			}

			tessellator17.draw();
			GL11.glPopMatrix();
		}

	}

	private void drawSlotTooltip(SlotInventory slotInventory1, int i2, int i3) {
		i2 -= (this.width - this.width) / 2;
		i3 = (int)((double)i3 - (double)(this.height - this.height) / 1.4D);
		if(slotInventory1 != null && slotInventory1.getStack() != null) {
			ItemTooltip itemTooltip4 = slotInventory1.getTooltip();
			if(itemTooltip4 != null) {
				int i5 = 0;
				Iterator iterator6 = itemTooltip4.lines.iterator();

				int i8;
				while(iterator6.hasNext()) {
					String string7 = (String)iterator6.next();
					i8 = this.fontRenderer.getStringWidth(string7);
					if(i8 > i5) {
						i5 = i8;
					}
				}

				if(i2 + i5 + 48 > this.width) {
					i2 -= i5 + 24;
				}

				int i9 = itemTooltip4.size() * 8;
				this.drawGradientRect(i2 + 8, i3, i2 + i5 + 16, i3 + i9 + 8, ((Integer)itemTooltip4.gradient.item0).intValue(), ((Integer)itemTooltip4.gradient.item1).intValue());

				for(i8 = 0; i8 < itemTooltip4.size(); ++i8) {
					this.drawString(this.fontRenderer, (String)itemTooltip4.lines.get(i8), i2 + 12, i3 + 4 + i8 * 9, ((Integer)itemTooltip4.colors.get(i8)).intValue());
				}
			}
		}

	}

	protected void mouseClicked(int i1, int i2, int i3) {
		if(i3 == 0) {
			int i4 = this.getBlockAtPosition(i1, i2);
			if(i4 >= 0) {
				this.mc.thePlayer.inventory.replaceBlock((Block)Session.registeredBlocksList.get(i4));
				this.dragging = true;
				ItemStack itemStack5 = this.mc.thePlayer.inventory.getCurrentItem();
				itemStack5.stackSize = 64;
			}

			if(!GuiScreen.isShiftKeyDown()) {
				this.mc.displayGuiScreen((GuiScreen)null);
			}
		}

	}

	public boolean doesGuiPauseGame() {
		return false;
	}

	public void updateScreen() {
		if(!this.mc.playerController.hasInfiniteItems()) {
			;
		}

	}

	public void initGui() {
		if(this.mc.playerController.hasInfiniteItems()) {
			super.initGui();
			this.controlList.clear();
		}

	}

	public void onGuiClosed() {
		InventoryPlayer inventoryPlayer1 = this.mc.thePlayer.inventory;
		if(inventoryPlayer1.draggedItemStack != null) {
			this.mc.thePlayer.dropPlayerItem(inventoryPlayer1.draggedItemStack);
			inventoryPlayer1.draggedItemStack = null;
		}

	}

	protected void keyTyped(char character, int key) {
		if(key != 1 && key != this.mc.options.keyBindCreative.keyCode) {
			if(key == this.mc.options.keyBindDrop.keyCode) {
				this.mc.thePlayer.dropPlayerItemWithRandomChoice(this.mc.thePlayer.inventory.decrStackSize(this.mc.thePlayer.inventory.currentItem, 1), false);
			}
		} else {
			this.mc.displayGuiScreen((GuiScreen)null);
		}

		for(int i3 = 0; i3 < 9; ++i3) {
			if(key == 2 + i3) {
				this.mc.thePlayer.inventory.currentItem = i3;
			}
		}

		if(key == this.mc.options.keyBindToggleFog.keyCode) {
			;
		}

	}

	protected void mouseMovedOrUp(int i1, int i2, int i3) {
		if(i3 == 0) {
			this.dragging = false;
		}

	}

	public void handleMouseInput() {
		super.handleMouseInput();
		int i1 = Mouse.getEventDWheel();
		if(i1 != 0) {
			this.mc.thePlayer.inventory.changeCurrentItem(i1);
		}

	}
}
