package net.minecraft.src;

import java.util.Random;

public class iffix extends Block {
	protected iffix(int i1, int i2) {
		super(i1, i2, Material.circuits);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldObj, int x, int y, int z) {
		return null;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public MovingObjectPosition collisionRayTrace(World worldObj, int x, int y, int z, Vec3D vector1, Vec3D vector2) {
		this.setBlockBoundsBasedOnState(worldObj, x, y, z);
		return super.collisionRayTrace(worldObj, x, y, z, vector1, vector2);
	}

	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int i5 = blockAccess.getBlockMetadata(x, y, z);
		if(i5 >= 2 && i5 <= 5) {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		} else {
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		}

	}

	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		return metadata >= 6 ? this.blockIndexInTexture - 16 : this.blockIndexInTexture;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}

	public int getRenderType() {
		return 9;
	}

	public int quantityDropped(Random rand) {
		return 1;
	}

	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return world.isBlockNormalCube(x, y - 1, z);
	}

	public void onBlockAdded(World worldObj, int x, int y, int z) {
		worldObj.setBlockMetadataWithNotify(x, y, z, 15);
		this.h(worldObj, x, y, z);
	}

	public void onNeighborBlockChange(World worldObj, int x, int y, int z, int id) {
		int i6 = worldObj.getBlockMetadata(x, y, z);
		boolean z7 = false;
		if(!worldObj.isBlockNormalCube(x, y - 1, z)) {
			z7 = true;
		}

		if(i6 == 2 && !worldObj.isBlockNormalCube(x + 1, y, z)) {
			z7 = true;
		}

		if(i6 == 3 && !worldObj.isBlockNormalCube(x - 1, y, z)) {
			z7 = true;
		}

		if(i6 == 4 && !worldObj.isBlockNormalCube(x, y, z - 1)) {
			z7 = true;
		}

		if(i6 == 5 && !worldObj.isBlockNormalCube(x, y, z + 1)) {
			z7 = true;
		}

		if(z7) {
			this.dropBlockAsItem(worldObj, x, y, z, worldObj.getBlockMetadata(x, y, z));
			worldObj.setBlockWithNotify(x, y, z, 0);
		} else if(id > 0 && Block.blocksList[id].canProvidePower() && MinecartTrackLogic.getNAdjacentTracks(new MinecartTrackLogic(this, worldObj, x, y, z)) == 3) {
			this.h(worldObj, x, y, z);
		}

	}

	private void h(World world1, int i2, int i3, int i4) {
		(new MinecartTrackLogic(this, world1, i2, i3, i4)).place(world1.isBlockIndirectlyGettingPowered(i2, i3, i4));
	}
}
