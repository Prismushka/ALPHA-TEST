package net.minecraft.src;

import net.minecraft.client.Minecraft;

public class PlayerController {
	protected final Minecraft mc;
	private final NetClientHandler netClientHandler;
	public boolean isInTestMode;
	public boolean instabuild;
	private int curBlockX;
	private int curBlockY;
	private int curBlockZ;
	private float curBlockDamage;
	private float prevBlockDamage;
	private float blockDestroySoundCounter;
	private int blockHitWait;
	private boolean isHittingBlock;
	private boolean creativeMode;
	private int currentPlayerItem;

	public PlayerController(Minecraft minecraft) {
		this(minecraft, (NetClientHandler)null);
	}

	public PlayerController(Minecraft minecraft1, NetClientHandler netClientHandler2) {
		this.isInTestMode = false;
		this.instabuild = false;
		this.curBlockX = -1;
		this.curBlockY = -1;
		this.curBlockZ = -1;
		this.curBlockDamage = 0.0F;
		this.prevBlockDamage = 0.0F;
		this.blockDestroySoundCounter = 0.0F;
		this.blockHitWait = 0;
		this.isHittingBlock = false;
		this.currentPlayerItem = 0;
		this.mc = minecraft1;
		this.netClientHandler = netClientHandler2;
	}

	public void onWorldChange(World world) {
	}

	public void clickBlock(int x, int y, int z, int side) {
		if(this.netClientHandler != null) {
			this.isHittingBlock = true;
			this.netClientHandler.addToSendQueue(new Packet14BlockDig(0, x, y, z, side));
		}

		if(this.creativeMode) {
			this.sendBlockRemoved(x, y, z, side);
		} else {
			int i5 = this.mc.theWorld.getBlockId(x, y, z);
			if(i5 > 0 && this.curBlockDamage == 0.0F) {
				Block.blocksList[i5].onBlockClicked(this.mc.theWorld, x, y, z, this.mc.thePlayer);
			}

			if(i5 > 0 && Block.blocksList[i5].blockStrength(this.mc.thePlayer) >= 1.0F) {
				this.sendBlockRemoved(x, y, z, side);
			}
		}

	}

	public boolean sendBlockRemoved(int x, int y, int z, int side) {
		this.mc.effectRenderer.addBlockDestroyEffects(x, y, z);
		World world5 = this.mc.theWorld;
		Block block6 = Block.blocksList[world5.getBlockId(x, y, z)];
		int i7 = world5.getBlockMetadata(x, y, z);
		boolean z8 = world5.setBlockWithNotify(x, y, z, 0);
		if(block6 != null && z8) {
			this.mc.sndManager.playSound(block6.stepSound.getBreakSound(), (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F, (block6.stepSound.getVolume() + 1.0F) / 2.0F, block6.stepSound.getPitch() * 0.8F);
			block6.onBlockDestroyedByPlayer(world5, x, y, z, i7);
		}

		return z8;
	}

	public void sendBlockRemoving(int x, int y, int z, int side) {
	}

	public boolean onPlayerDestroyBlock(int i1, int i2, int i3) {
		this.mc.effectRenderer.addBlockDestroyEffects(i1, i2, i3);
		World world4 = this.mc.theWorld;
		Block block5 = Block.blocksList[world4.getBlockId(i1, i2, i3)];
		int i6 = world4.getBlockMetadata(i1, i2, i3);
		boolean z7 = world4.setBlockWithNotify(i1, i2, i3, 0);
		if(block5 != null && z7) {
			this.mc.sndManager.playSound(block5.stepSound.getBreakSound(), (float)i1 + 0.5F, (float)i2 + 0.5F, (float)i3 + 0.5F, (block5.stepSound.getVolume() + 1.0F) / 2.0F, block5.stepSound.getPitch() * 0.8F);
			block5.onBlockDestroyedByPlayer(world4, i1, i2, i3, i6);
		}

		return z7;
	}

	public void resetBlockRemoving() {
		if(this.netClientHandler != null) {
			if(!this.isHittingBlock) {
				return;
			}

			this.isHittingBlock = false;
		}

		this.curBlockDamage = 0.0F;
		this.blockHitWait = 0;
	}

	public void setPartialTime(float renderPartialTick) {
		if(this.curBlockDamage <= 0.0F) {
			this.mc.ingameGUI.damageGuiPartialTime = 0.0F;
			this.mc.renderGlobal.damagePartialTime = 0.0F;
		} else {
			float f2 = this.prevBlockDamage + (this.curBlockDamage - this.prevBlockDamage) * renderPartialTick;
			this.mc.ingameGUI.damageGuiPartialTime = f2;
			this.mc.renderGlobal.damagePartialTime = f2;
		}

	}

	public float getBlockReachDistance() {
		return this.creativeMode ? 5.0F : (this.netClientHandler != null ? 4.5F : 4.0F);
	}

	public void flipPlayer(EntityPlayer entityPlayer) {
		if(!this.creativeMode) {
			entityPlayer.rotationYaw = -180.0F;
		}

	}

	public void onUpdate() {
		if(this.netClientHandler != null) {
			this.syncCurrentPlayItem();
		}

		this.prevBlockDamage = this.curBlockDamage;
	}

	public boolean shouldDrawHUD() {
		return !this.creativeMode;
	}

	public void onRespawn(EntityPlayer entityPlayer) {
		if(this.creativeMode) {
			this.isInTestMode = true;
			this.instabuild = true;

			for(int i2 = 0; i2 < 9; ++i2) {
				if(entityPlayer.inventory.mainInventory[i2] == null) {
					this.mc.thePlayer.inventory.mainInventory[i2] = new ItemStack(((Block)Session.registeredBlocksList.get(i2)).blockID);
				} else {
					this.mc.thePlayer.inventory.mainInventory[i2].stackSize = 1;
				}
			}
		} else {
			this.isInTestMode = false;
			this.instabuild = false;
		}

	}

	public void setCreative(boolean z1) {
		this.creativeMode = z1;
		if(this.creativeMode) {
			this.isInTestMode = true;
			this.instabuild = true;
		} else {
			this.isInTestMode = false;
			this.instabuild = false;
		}

	}

	private void syncCurrentPlayItem() {
		ItemStack itemStack1 = this.mc.thePlayer.inventory.getCurrentItem();
		int i2 = 0;
		if(itemStack1 != null) {
			i2 = itemStack1.itemID;
		}

		if(i2 != this.currentPlayerItem) {
			this.currentPlayerItem = i2;
			this.netClientHandler.addToSendQueue(new Packet16BlockItemSwitch(0, this.currentPlayerItem));
		}

	}

	public boolean onPlayerRightClick(EntityPlayer entityPlayer, World world, ItemStack itemStack, int x, int y, int z, int side) {
		if(this.netClientHandler != null) {
			this.syncCurrentPlayItem();
			this.netClientHandler.addToSendQueue(new Packet15Place(itemStack != null ? itemStack.itemID : -1, x, y, z, side));
		}

		int i8 = world.getBlockId(x, y, z);
		if(i8 > 0 && Block.blocksList[i8].blockActivated(world, x, y, z, entityPlayer)) {
			return true;
		} else if(itemStack == null) {
			return false;
		} else if(this.creativeMode) {
			i8 = itemStack.itemDmg;
			int i9 = itemStack.stackSize;
			boolean z10 = itemStack.useItem(entityPlayer, world, x, y, z, side);
			itemStack.itemDmg = i8;
			itemStack.stackSize = i9;
			return z10;
		} else {
			return itemStack.useItem(entityPlayer, world, x, y, z, side);
		}
	}

	public EntityPlayer createPlayer(World world) {
		return (EntityPlayer)(this.netClientHandler != null ? new EntityClientPlayerMP(this.mc, world, this.mc.session, this.netClientHandler) : new EntityPlayerSP(this.mc, world, this.mc.session));
	}

	public boolean hasMissTime() {
		return !this.creativeMode;
	}

	public boolean hasInfiniteItems() {
		return this.creativeMode;
	}

	public boolean hasFarPickRange() {
		return this.creativeMode;
	}

	public boolean hasExperience() {
		return !this.creativeMode;
	}
}
