package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public abstract class GuiContainer extends GuiScreen {
	private static RenderItem itemRenderer = new RenderItem();
	protected int xSize = 176;
	protected int ySize = 166;
	protected List inventorySlots = new ArrayList();

	public void drawScreen(int mouseX, int mouseY, float renderPartialTick) {
		this.drawDefaultBackground();
		int i4 = (this.width - this.xSize) / 2;
		int i5 = (this.height - this.ySize) / 2;
		this.drawGuiContainerBackgroundLayer(renderPartialTick);
		super.drawScreen(mouseX, mouseY, renderPartialTick);
		GL11.glPushMatrix();
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glTranslatef((float)i4, (float)i5, 0.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);

		for(int i6 = 0; i6 < this.inventorySlots.size(); ++i6) {
			SlotInventory slotInventory7 = (SlotInventory)this.inventorySlots.get(i6);
			this.drawSlotInventory(slotInventory7);
			if(slotInventory7.getIsMouseOverSlot(mouseX, mouseY)) {
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_DEPTH_TEST);
				int i8 = slotInventory7.xDisplayPosition;
				int i9 = slotInventory7.yDisplayPosition;
				this.drawGradientRect(i8, i9, i8 + 16, i9 + 16, -2130706433, -2130706433);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
			}
		}

		InventoryPlayer inventoryPlayer10 = this.mc.thePlayer.inventory;
		if(inventoryPlayer10.draggedItemStack != null) {
			GL11.glTranslatef(0.0F, 0.0F, 32.0F);
			itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryPlayer10.draggedItemStack, mouseX - i4 - 8, mouseY - i5 - 8);
			itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, inventoryPlayer10.draggedItemStack, mouseX - i4 - 8, mouseY - i5 - 8);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		this.drawGuiContainerForegroundLayer();

		for(int i11 = 0; i11 < this.inventorySlots.size(); ++i11) {
			SlotInventory slotInventory12 = (SlotInventory)this.inventorySlots.get(i11);
			if(slotInventory12.getIsMouseOverSlot(mouseX, mouseY)) {
				this.drawSlotTooltip(slotInventory12, mouseX, mouseY);
			}
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glPopMatrix();
	}

	protected void drawGuiContainerForegroundLayer() {
	}

	protected abstract void drawGuiContainerBackgroundLayer(float f1);

	private void drawSlotInventory(SlotInventory slotInventory) {
		IInventory iInventory2 = slotInventory.inventory;
		int i3 = slotInventory.slotIndex;
		int i4 = slotInventory.xDisplayPosition;
		int i5 = slotInventory.yDisplayPosition;
		ItemStack itemStack6 = iInventory2.getStackInSlot(i3);
		if(itemStack6 == null) {
			int i7 = slotInventory.getBackgroundIconIndex();
			if(i7 >= 0) {
				GL11.glDisable(GL11.GL_LIGHTING);
				this.mc.renderEngine.bindTexture(this.mc.renderEngine.getTexture("/gui/items.png"));
				this.drawTexturedModalRect(i4, i5, i7 % 16 * 16, i7 / 16 * 16, 16, 16);
				GL11.glEnable(GL11.GL_LIGHTING);
				return;
			}
		}

		itemRenderer.renderItemIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack6, i4, i5);
		itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, this.mc.renderEngine, itemStack6, i4, i5);
	}

	private void drawSlotTooltip(SlotInventory slotInventory1, int i2, int i3) {
		i2 -= (this.width - this.xSize) / 2;
		i3 = (int)((double)i3 - (double)(this.height - this.ySize) / 1.4D);
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

	private Slot getSlotAtPosition(int i1, int i2) {
		for(int i3 = 0; i3 < this.inventorySlots.size(); ++i3) {
			SlotInventory slotInventory4 = (SlotInventory)this.inventorySlots.get(i3);
			if(slotInventory4.getIsMouseOverSlot(i1, i2)) {
				return slotInventory4;
			}
		}

		return null;
	}

	protected void mouseClicked(int i1, int i2, int i3) {
		if(i3 == 0 || i3 == 1) {
			Slot slot4 = this.getSlotAtPosition(i1, i2);
			InventoryPlayer inventoryPlayer5 = this.mc.thePlayer.inventory;
			int i6;
			if(slot4 != null) {
				ItemStack itemStack7 = slot4.getStack();
				if(itemStack7 != null || inventoryPlayer5.draggedItemStack != null) {
					if(itemStack7 != null && inventoryPlayer5.draggedItemStack == null) {
						i6 = i3 == 0 ? itemStack7.stackSize : (itemStack7.stackSize + 1) / 2;
						inventoryPlayer5.draggedItemStack = slot4.inventory.decrStackSize(slot4.slotIndex, i6);
						if(itemStack7.stackSize == 0) {
							slot4.putStack((ItemStack)null);
						}

						slot4.onPickupFromSlot();
					} else if(itemStack7 == null && inventoryPlayer5.draggedItemStack != null && slot4.isItemValid(inventoryPlayer5.draggedItemStack)) {
						i6 = i3 == 0 ? inventoryPlayer5.draggedItemStack.stackSize : 1;
						if(i6 > slot4.inventory.getInventoryStackLimit()) {
							i6 = slot4.inventory.getInventoryStackLimit();
						}

						slot4.putStack(inventoryPlayer5.draggedItemStack.splitStack(i6));
						if(inventoryPlayer5.draggedItemStack.stackSize == 0) {
							inventoryPlayer5.draggedItemStack = null;
						}
					} else if(itemStack7 != null && inventoryPlayer5.draggedItemStack != null) {
						if(slot4.isItemValid(inventoryPlayer5.draggedItemStack)) {
							if(itemStack7.itemID == inventoryPlayer5.draggedItemStack.itemID && itemStack7.itemMetadata == inventoryPlayer5.draggedItemStack.itemMetadata) {
								if(itemStack7.itemID == inventoryPlayer5.draggedItemStack.itemID && itemStack7.itemMetadata == inventoryPlayer5.draggedItemStack.itemMetadata) {
									if(i3 == 0) {
										i6 = inventoryPlayer5.draggedItemStack.stackSize;
										if(i6 > slot4.inventory.getInventoryStackLimit() - itemStack7.stackSize) {
											i6 = slot4.inventory.getInventoryStackLimit() - itemStack7.stackSize;
										}

										if(i6 > inventoryPlayer5.draggedItemStack.getMaxStackSize() - itemStack7.stackSize) {
											i6 = inventoryPlayer5.draggedItemStack.getMaxStackSize() - itemStack7.stackSize;
										}

										inventoryPlayer5.draggedItemStack.splitStack(i6);
										if(inventoryPlayer5.draggedItemStack.stackSize == 0) {
											inventoryPlayer5.draggedItemStack = null;
										}

										itemStack7.stackSize += i6;
									} else if(i3 == 1) {
										i6 = 1;
										if(i6 > slot4.inventory.getInventoryStackLimit() - itemStack7.stackSize) {
											i6 = slot4.inventory.getInventoryStackLimit() - itemStack7.stackSize;
										}

										if(i6 > inventoryPlayer5.draggedItemStack.getMaxStackSize() - itemStack7.stackSize) {
											i6 = inventoryPlayer5.draggedItemStack.getMaxStackSize() - itemStack7.stackSize;
										}

										inventoryPlayer5.draggedItemStack.splitStack(i6);
										if(inventoryPlayer5.draggedItemStack.stackSize == 0) {
											inventoryPlayer5.draggedItemStack = null;
										}

										itemStack7.stackSize += i6;
									}
								}
							} else if(inventoryPlayer5.draggedItemStack.stackSize <= slot4.inventory.getInventoryStackLimit()) {
								slot4.putStack(inventoryPlayer5.draggedItemStack);
								inventoryPlayer5.draggedItemStack = itemStack7;
							}
						} else if(itemStack7.itemID == inventoryPlayer5.draggedItemStack.itemID && itemStack7.itemMetadata == inventoryPlayer5.draggedItemStack.itemMetadata && inventoryPlayer5.draggedItemStack.getMaxStackSize() > 1) {
							i6 = itemStack7.stackSize;
							if(i6 > 0 && i6 + inventoryPlayer5.draggedItemStack.stackSize <= inventoryPlayer5.draggedItemStack.getMaxStackSize()) {
								inventoryPlayer5.draggedItemStack.stackSize += i6;
								itemStack7.splitStack(i6);
								if(itemStack7.stackSize == 0) {
									slot4.putStack((ItemStack)null);
								}

								slot4.onPickupFromSlot();
							}
						}
					}
				}

				slot4.onSlotChanged();
			} else if(inventoryPlayer5.draggedItemStack != null) {
				int i9 = (this.width - this.xSize) / 2;
				i6 = (this.height - this.ySize) / 2;
				if(i1 < i9 || i2 < i6 || i1 >= i9 + this.xSize || i2 >= i6 + this.xSize) {
					EntityPlayerSP entityPlayerSP8 = this.mc.thePlayer;
					if(i3 == 0) {
						entityPlayerSP8.dropPlayerItem(inventoryPlayer5.draggedItemStack);
						inventoryPlayer5.draggedItemStack = null;
					}

					if(i3 == 1) {
						entityPlayerSP8.dropPlayerItem(inventoryPlayer5.draggedItemStack.splitStack(1));
						if(inventoryPlayer5.draggedItemStack.stackSize == 0) {
							inventoryPlayer5.draggedItemStack = null;
						}
					}
				}
			}
		}

		super.mouseClicked(i1, i2, i3);
	}

	protected void mouseMovedOrUp(int i1, int i2, int i3) {
		if(i3 == 0) {
			;
		}

	}

	protected void keyTyped(char character, int key) {
		if(key == 1 || key == this.mc.options.keyBindInventory.keyCode) {
			this.mc.displayGuiScreen((GuiScreen)null);
		}

	}

	public void onGuiClosed() {
		InventoryPlayer inventoryPlayer1 = this.mc.thePlayer.inventory;
		if(inventoryPlayer1.draggedItemStack != null) {
			this.mc.thePlayer.dropPlayerItem(inventoryPlayer1.draggedItemStack);
			inventoryPlayer1.draggedItemStack = null;
		}

	}

	public boolean doesGuiPauseGame() {
		return false;
	}
}
