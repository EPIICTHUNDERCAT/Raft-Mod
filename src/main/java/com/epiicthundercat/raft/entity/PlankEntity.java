package com.epiicthundercat.raft.entity;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.util.vector.Quaternion;

import com.epiicthundercat.raft.client.renderer.RenderPlank;
import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;
import com.epiicthundercat.raft.init.barrel.BarrelLoot;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ReportedException;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlankEntity extends Entity {
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer>createKey(PlankEntity.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager
			.<Integer>createKey(PlankEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(PlankEntity.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Boolean> CAN_DESPAWN = EntityDataManager.createKey(PlankEntity.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(PlankEntity.class,
			DataSerializers.VARINT);
	private static final DataParameter<Boolean> CUSTOM_WIND_ENABLED = EntityDataManager.createKey(PlankEntity.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Float> CUSTOM_WIND_X = EntityDataManager.createKey(PlankEntity.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> CUSTOM_WIND_Z = EntityDataManager.createKey(PlankEntity.class,
			DataSerializers.FLOAT);
	private static final float BASE_SIZE = 0.75f;

	/** How much of current speed to retain. Value zero to one. */
	private float momentum;
	private float outOfControlTicks;
	private float deltaRotation;
	private int lerpSteps;
	private double BarrelPitch;
	private double lerpY;
	private double lerpZ;
	private double BarrelYaw;
	private double lerpXRot;
	private boolean leftInputDown;
	private boolean rightInputDown;
	private boolean forwardInputDown;
	private boolean backInputDown;
	private double waterLevel;

	private int age;
	public float rot1, rot2, rot3;
	private int groundTicks;
	private int currentSize;

	@SideOnly(Side.CLIENT)
	public Quaternion quat;
	@SideOnly(Side.CLIENT)
	public Quaternion prevQuat;
	private float windModX, windModZ;
	private int nextStepDistance;

	/**
	 * How much the Barrel should glide given the slippery blocks it's currently
	 * gliding over. Halved every tick.
	 */
	private float BarrelGlide;
	private PlankEntity.Status status;
	private PlankEntity.Status previousStatus;
	private double lastYd;

	public PlankEntity(World worldIn) {
		super(worldIn);

		this.preventEntitySpawning = true;
		this.setSize(1F, 0.9F);
		this.windModX = 1.2f - 0.4f * world.rand.nextFloat();
		this.windModZ = 1.2f - 0.4f * world.rand.nextFloat();

		if (this.world.isRemote) {
			this.rot1 = 360f * world.rand.nextFloat();
			this.rot2 = 360f * world.rand.nextFloat();
			this.rot3 = 360f * world.rand.nextFloat();

			this.quat = new Quaternion();
			this.prevQuat = new Quaternion();
		}
	}

	public PlankEntity(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	protected void entityInit() {
		this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(0));
		this.dataManager.register(FORWARD_DIRECTION, Integer.valueOf(1));
		this.dataManager.register(DAMAGE_TAKEN, Float.valueOf(0.0F));
		this.dataManager.register(CAN_DESPAWN, true);
		this.dataManager.register(SIZE, -2 + world.rand.nextInt(5));
		this.dataManager.register(CUSTOM_WIND_ENABLED, false);
		this.dataManager.register(CUSTOM_WIND_X, 0f);
		this.dataManager.register(CUSTOM_WIND_Z, 0f);

	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like Barrels
	 * or minecarts.
	 */
	@Nullable
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.getEntityBoundingBox();
	}

	/**
	 * Returns the collision bounding box for this entity
	 */
	@Nullable
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 * 
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if (!this.world.isRemote && !this.isDead) {
			if (source instanceof EntityDamageSourceIndirect && source.getEntity() != null) {
				return false;
			} else {
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
				this.setBeenAttacked();

				boolean flag = source.getEntity() instanceof EntityPlayer
						&& ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				

				boolean flag1 = source.getEntity() instanceof EntityCreeper;
				EntityPlayer player = (EntityPlayer) source.getEntity();

				if (flag || this.getDamageTaken() > 5.0F) {

					if (!flag && this.world.getGameRules().getBoolean("doEntityDrops") && !flag1) {
						BlockPos pos = getPosition();

						this.dropItems(world, pos);
					}

					this.setDead();
				}
				

				return true;
			}
		} else {
			return true;
		}
	}

	/**
	 * Applies a velocity to the entities, to push them away from eachother.
	 */
	@Override
	public void applyEntityCollision(Entity entityIn) {
		if (entityIn instanceof PlankEntity) {
			if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
				super.applyEntityCollision(entityIn);
			}
		} else if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY) {
			super.applyEntityCollision(entityIn);
		}
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch,
			int posRotationIncrements, boolean teleport) {
		this.BarrelPitch = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.BarrelYaw = (double) yaw;
		this.lerpXRot = (double) pitch;
		this.lerpSteps = 10;
	}

	/**
	 * Gets the horizontal facing direction of this Entity, adjusted to take
	 * specially-treated entity types into account.
	 */
	public EnumFacing getAdjustedHorizontalFacing() {
		return this.getHorizontalFacing().rotateY();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.currentSize != this.getSize()) {
			this.currentSize = this.getSize();

		}

		if (this.getRidingEntity() != null) {
			this.motionX = 0;
			this.motionY = 0;
			this.motionZ = 0;
		} else {
			if (!this.isInWater())
				this.motionY -= 0.012;

			double x = this.motionX;
			double y = this.motionY;
			double z = this.motionZ;

			boolean ground = onGround;
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			float windX = 0.08F;
			float windZ = 0.08F;
			if (this.isInWater()) {
				
				this.motionY += 0.0045;
				this.motionX += 0.0015;
				this.motionZ += 0.0015;
				
			} else if (windX != 0 || windZ != 0) {
				this.motionX = windX;
				this.motionZ = windZ;
			}

			// Rotate
			if (this.world.isRemote) {
				groundTicks--;

				if ((!ground && onGround) || isInWater())
					groundTicks = 10;
				else if (getCustomWindEnabled())
					groundTicks = 5;

				this.prevQuat = this.quat;

				Quaternion.mul(this.quat, RenderPlank.CURRENT, this.quat);

				Quaternion.mul(this.quat, RenderPlank.CURRENT, this.quat);
			}

			// Bounce on ground
			if (this.onGround) {
				this.motionX *= 0.098;
				this.motionY *= 0.098;
				this.motionZ *= 0.098;
			}

			// Bounce on walls
			if (this.isCollidedHorizontally) {
				this.motionX = -x * 0.054;
				this.motionZ = -z * 0.054;

				this.motionX += 0.028;
				this.motionY += 0.0008;
				this.motionZ += 0.018;

				if (Math.abs(this.motionX) < 0.005)
					this.motionX = 0.0;

				if (Math.abs(this.motionY) < 0.005)
					this.motionY = 0.0;

				if (Math.abs(this.motionZ) < 0.005)
					this.motionZ = 0.0;

				collideWithNearbyEntities();
			}
			if (!this.world.isRemote) {
				this.age++;
				despawnEntity();

			}

		}
	}

	private void tickLerp() {
		if (this.lerpSteps > 0) {
			double d0 = this.posX + (this.BarrelPitch - this.posX) / (double) this.lerpSteps;
			double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
			double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
			double d3 = MathHelper.wrapDegrees(this.BarrelYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
			this.rotationPitch = (float) ((double) this.rotationPitch
					+ (this.lerpXRot - (double) this.rotationPitch) / (double) this.lerpSteps);
			--this.lerpSteps;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	/**
	 * Determines whether the Barrel is in water, gliding on land, or in air
	 */
	private PlankEntity.Status getBarrelStatus() {
		PlankEntity.Status PlankEntity$status = this.getUnderwaterStatus();

		if (PlankEntity$status != null) {
			this.waterLevel = this.getEntityBoundingBox().maxY;
			return PlankEntity$status;
		} else if (this.checkInWater()) {
			return PlankEntity.Status.IN_WATER;
		} else {
			float f = this.getBarrelGlide();

			if (f > 0.0F) {
				this.BarrelGlide = f;
				return PlankEntity.Status.ON_LAND;
			} else {
				return PlankEntity.Status.IN_AIR;
			}
		}
	}

	public float getWaterLevelAbove() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.maxY);
		int l = MathHelper.ceil(axisalignedbb.maxY - this.lastYd);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		try {
			label78:

			for (int k1 = k; k1 < l; ++k1) {
				float f = 0.0F;
				int l1 = i;

				while (true) {
					if (l1 >= j) {
						if (f < 1.0F) {
							float f2 = (float) blockpos$pooledmutableblockpos.getY() + f;
							return f2;
						}

						break;
					}

					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(l1, k1, i2);
						IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);

						if (iblockstate.getMaterial() == Material.WATER) {
							f = Math.max(f,
									getBlockLiquidHeight(iblockstate, this.world, blockpos$pooledmutableblockpos));
						}

						if (f >= 1.0F) {
							continue label78;
						}
					}

					++l1;
				}
			}

			float f1 = (float) (l + 1);
			return f1;
		} finally {
			blockpos$pooledmutableblockpos.release();
		}
	}

	/**
	 * Decides how much the Barrel should be gliding on the land (based on any
	 * slippery blocks)
	 */
	public float getBarrelGlide() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY - 0.001D,
				axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		int i = MathHelper.floor(axisalignedbb1.minX) - 1;
		int j = MathHelper.ceil(axisalignedbb1.maxX) + 1;
		int k = MathHelper.floor(axisalignedbb1.minY) - 1;
		int l = MathHelper.ceil(axisalignedbb1.maxY) + 1;
		int i1 = MathHelper.floor(axisalignedbb1.minZ) - 1;
		int j1 = MathHelper.ceil(axisalignedbb1.maxZ) + 1;
		List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
		float f = 0.0F;
		int k1 = 0;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		try {
			for (int l1 = i; l1 < j; ++l1) {
				for (int i2 = i1; i2 < j1; ++i2) {
					int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);

					if (j2 != 2) {
						for (int k2 = k; k2 < l; ++k2) {
							if (j2 <= 0 || k2 != k && k2 != l - 1) {
								blockpos$pooledmutableblockpos.setPos(l1, k2, i2);
								IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);
								iblockstate.addCollisionBoxToList(this.world, blockpos$pooledmutableblockpos,
										axisalignedbb1, list, this, true);

								if (!list.isEmpty()) {
									f += iblockstate.getBlock().slipperiness;
									++k1;
								}

								list.clear();
							}
						}
					}
				}
			}
		} finally {
			blockpos$pooledmutableblockpos.release();
		}

		return f / (float) k1;
	}

	private boolean checkInWater() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.minY);
		int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		boolean flag = false;
		this.waterLevel = Double.MIN_VALUE;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		try {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);

						if (iblockstate.getMaterial() == Material.WATER) {
							float f = getLiquidHeight(iblockstate, this.world, blockpos$pooledmutableblockpos);
							this.waterLevel = Math.max((double) f, this.waterLevel);
							flag |= axisalignedbb.minY < (double) f;
						}
					}
				}
			}
		} finally {
			blockpos$pooledmutableblockpos.release();
		}

		return flag;
	}

	/**
	 * Decides whether the Barrel is currently underwater.
	 */
	@Nullable
	private PlankEntity.Status getUnderwaterStatus() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		double d0 = axisalignedbb.maxY + 0.001D;
		int i = MathHelper.floor(axisalignedbb.minX);
		int j = MathHelper.ceil(axisalignedbb.maxX);
		int k = MathHelper.floor(axisalignedbb.maxY);
		int l = MathHelper.ceil(d0);
		int i1 = MathHelper.floor(axisalignedbb.minZ);
		int j1 = MathHelper.ceil(axisalignedbb.maxZ);
		boolean flag = false;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		try {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IBlockState iblockstate = this.world.getBlockState(blockpos$pooledmutableblockpos);

						if (iblockstate.getMaterial() == Material.WATER && d0 < (double) getLiquidHeight(iblockstate,
								this.world, blockpos$pooledmutableblockpos)) {
							if (((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() != 0) {
								PlankEntity.Status PlankEntity$status = PlankEntity.Status.UNDER_FLOWING_WATER;
								return PlankEntity$status;
							}

							flag = true;
						}
					}
				}
			}
		} finally {
			blockpos$pooledmutableblockpos.release();
		}

		return flag ? PlankEntity.Status.UNDER_WATER : null;
	}

	public static float getBlockLiquidHeight(IBlockState p_184456_0_, IBlockAccess p_184456_1_, BlockPos p_184456_2_) {
		int i = ((Integer) p_184456_0_.getValue(BlockLiquid.LEVEL)).intValue();
		return (i & 7) == 0 && p_184456_1_.getBlockState(p_184456_2_.up()).getMaterial() == Material.WATER ? 1.0F
				: 1.0F - BlockLiquid.getLiquidHeightPercent(i);
	}

	public static float getLiquidHeight(IBlockState p_184452_0_, IBlockAccess p_184452_1_, BlockPos p_184452_2_) {
		return (float) p_184452_2_.getY() + getBlockLiquidHeight(p_184452_0_, p_184452_1_, p_184452_2_);
	}

	/**
	 * Update the Barrel's speed, based on momentum.
	 */
	private void updateMotion() {
		double d0 = -0.03999999910593033D;
		double d1 = this.hasNoGravity() ? 0.0D : -0.03999999910593033D;
		double d2 = 0.0D;
		this.momentum = 0.05F;

		if (this.previousStatus == PlankEntity.Status.IN_AIR && this.status != PlankEntity.Status.IN_AIR
				&& this.status != PlankEntity.Status.ON_LAND) {
			this.waterLevel = this.getEntityBoundingBox().minY + (double) this.height;
			this.setPosition(this.posX, (double) (this.getWaterLevelAbove() - this.height) + 0.101D, this.posZ);
			this.motionY = 0.0D;
			this.lastYd = 0.0D;
			this.status = PlankEntity.Status.IN_WATER;
		} else {
			if (this.status == PlankEntity.Status.IN_WATER) {
				d2 = (this.waterLevel - this.getEntityBoundingBox().minY) / (double) this.height;
				this.momentum = 0.9F;
				BarrelGlide /= 2f;
			} else if (this.status == PlankEntity.Status.UNDER_FLOWING_WATER) {
				d1 = -7.0E-4D;
				this.momentum = 0.9F;
			} else if (this.status == PlankEntity.Status.UNDER_WATER) {
				d2 = 0.009999999776482582D;
				this.momentum = 0.45F;
			} else if (this.status == PlankEntity.Status.IN_AIR) {
				this.momentum = 0.9F;
			} else if (this.status == PlankEntity.Status.ON_LAND) {
				this.momentum = this.BarrelGlide;

			}

			this.motionX *= (double) this.momentum;
			this.motionZ *= (double) this.momentum;
			this.deltaRotation *= this.momentum;
			this.motionY += d1;

			if (d2 > 0.0D) {
				double d3 = 0.65D;
				this.motionY += d2 * 0.06153846016296973D;
				double d4 = 0.75D;
				this.motionY *= 0.75D;
			}
		}
	}

	private void dropItems(World world, BlockPos pos) {
		for (int i = 0; i < MathHelper.getInt(world.rand, 1, 1); i++) {
			BarrelLoot returns = WeightedRandom.getRandomItem(world.rand, REventHandler.plank_loot);
			ItemStack itemStack = returns.returnItem.copy();
			float dX = world.rand.nextFloat() * 0.8F + 0.1F;
			float dY = world.rand.nextFloat() * 0.8F + 0.1F;
			float dZ = world.rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(world, pos.getX() + dX, pos.getY() + dY, pos.getZ() + dZ, itemStack);
			float factor = 0.05F;
			entityItem.motionX = world.rand.nextGaussian() * factor;
			entityItem.motionY = world.rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = world.rand.nextGaussian() * factor;
			world.spawnEntity(entityItem);
		}
	}

	private void extractItems(World world, BlockPos pos, EntityPlayer player) {
		for (int i = 0; i < MathHelper.getInt(world.rand, 1, 1); i++) {
			BarrelLoot returns = WeightedRandom.getRandomItem(world.rand, REventHandler.plank_loot);
			ItemStack itemStack = returns.returnItem.copy();

			if (itemStack != null)
				player.inventory.addItemStackToInventory(itemStack);
		}

	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
	
		if (!this.world.isRemote && !player.isSneaking() && this.outOfControlTicks < 60.0F) {
			BlockPos pos = getPosition();
			this.extractItems(world, pos, player);
		}
		this.setDead();
		return true;
	}


	/**
	 * Sets the damage taken from the last hit.
	 */
	public void setDamageTaken(float damageTaken) {
		this.dataManager.set(DAMAGE_TAKEN, Float.valueOf(damageTaken));
	}

	/**
	 * Gets the damage taken from the last hit.
	 */
	public float getDamageTaken() {
		return ((Float) this.dataManager.get(DAMAGE_TAKEN)).floatValue();
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int timeSinceHit) {
		this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(timeSinceHit));
	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return ((Integer) this.dataManager.get(TIME_SINCE_HIT)).intValue();
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int forwardDirection) {
		this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(forwardDirection));
	}

	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return ((Integer) this.dataManager.get(FORWARD_DIRECTION)).intValue();
	}

	public static enum Status {
		IN_WATER, UNDER_WATER, UNDER_FLOWING_WATER, ON_LAND, IN_AIR;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("Size"))
			this.dataManager.set(SIZE, compound.getInteger("Size"));

		if (compound.hasKey("CustomWindEnabled"))
			this.dataManager.set(CUSTOM_WIND_ENABLED, compound.getBoolean("CustomWindEnabled"));

		if (compound.hasKey("CustomWindX"))
			this.dataManager.set(CUSTOM_WIND_X, compound.getFloat("CustomWindX"));

		if (compound.hasKey("CustomWindZ"))
			this.dataManager.set(CUSTOM_WIND_Z, compound.getFloat("CustomWindZ"));
		if (compound.hasKey("CanDespawn"))
			this.dataManager.set(CAN_DESPAWN, compound.getBoolean("CanDespawn"));

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Size", getSize());
		compound.setBoolean("CustomWindEnabled", getCustomWindEnabled());
		compound.setFloat("CustomWindX", getCustomWindX());
		compound.setFloat("CustomWindZ", getCustomWindZ());

		compound.setBoolean("CanDespawn", getCanDespawn());

	}

	public boolean getCanDespawn() {
		return this.dataManager.get(CAN_DESPAWN);
	}

	
	public void moveEntity(double x, double y, double z) {
		if (this.noClip) {
			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, y, z));
			this.resetPositionToBB();
		} else {
			double d0 = this.posX;
			double d1 = this.posY;
			double d2 = this.posZ;

			if (this.isInWeb) {
				this.isInWeb = false;
				x *= 0.25D;
				y *= 0.05D;
				z *= 0.25D;
				this.motionX = 0.0D;
				this.motionY = 0.0D;
				this.motionZ = 0.0D;
			}

			double d3 = x;
			double d4 = y;
			double d5 = z;

			List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(this,
					this.getEntityBoundingBox().addCoord(x, y, z));

			for (AxisAlignedBB axisalignedbb1 : list1)
				y = axisalignedbb1.calculateYOffset(this.getEntityBoundingBox(), y);

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));

			for (AxisAlignedBB axisalignedbb2 : list1)
				x = axisalignedbb2.calculateXOffset(this.getEntityBoundingBox(), x);

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));

			for (AxisAlignedBB axisalignedbb13 : list1)
				z = axisalignedbb13.calculateZOffset(this.getEntityBoundingBox(), z);

			this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));

			this.resetPositionToBB();
			this.isCollidedHorizontally = d3 != x || d5 != z;
			this.isCollidedVertically = d4 != y;
			this.onGround = this.isCollidedVertically && d4 < 0.0D;
			this.isCollided = this.isCollidedHorizontally || this.isCollidedVertically;
			int i = MathHelper.floor(this.posX);
			int j = MathHelper.floor(this.posY - 0.2D);
			int k = MathHelper.floor(this.posZ);
			BlockPos blockpos = new BlockPos(i, j, k);
			IBlockState state = this.world.getBlockState(blockpos);
			Block block = state.getBlock();

			if (state.getBlock() == Blocks.AIR) {
				IBlockState state1 = this.world.getBlockState(blockpos.down());
				Block block1 = state1.getBlock();

				if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
					state = state1;
					blockpos = blockpos.down();
				}
			}

			this.updateFallState(y, this.onGround, state, blockpos);

			if (d3 != x)
				this.motionX = 0.0D;

			if (d5 != z)
				this.motionZ = 0.0D;

			if (d4 != y) {
				block.onLanded(this.world, this);

				if (block == Blocks.FARMLAND) {
					if (!world.isRemote && world.rand.nextFloat() < 0.7F) {
						if (!world.getGameRules().getBoolean("mobGriefing"))
							return;

						world.setBlockState(blockpos, Blocks.DIRT.getDefaultState());
					}
				}
			}

			double d15 = this.posX - d0;
			double d16 = this.posY - d1;
			double d17 = this.posZ - d2;

			if (block != Blocks.LADDER)
				d16 = 0.0D;

			if (this.onGround)
				block.onEntityWalk(this.world, blockpos, this);

			this.distanceWalkedModified = (float) ((double) this.distanceWalkedModified
					+ (double) MathHelper.sqrt(d15 * d15 + d17 * d17) * 0.6D);
			this.distanceWalkedOnStepModified = (float) ((double) this.distanceWalkedOnStepModified
					+ (double) MathHelper.sqrt(d15 * d15 + d16 * d16 + d17 * d17) * 0.6D);

			if (this.distanceWalkedOnStepModified > (float) this.nextStepDistance
					&& state.getMaterial() != Material.AIR) {
				this.nextStepDistance = (int) this.distanceWalkedOnStepModified + 1;

				if (this.isInWater()) {
					float f = MathHelper.sqrt(this.motionX * this.motionX * 0.2D + this.motionY * this.motionY
							+ this.motionZ * this.motionZ * 0.2D) * 0.35F;

					if (f > 1.0F)
						f = 1.0F;

					this.playSound(this.getSwimSound(), f,
							1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
				}

				if (!state.getMaterial().isLiquid()) {
					SoundType sound = SoundType.PLANT;
					this.playSound(sound.getStepSound(), sound.getVolume() * 0.15F, sound.getPitch());
				}
			}

			try {
				this.doBlockCollisions();
			} catch (Throwable throwable) {
				CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
				CrashReportCategory crashreportcategory = crashreport
						.makeCategory("Entity being checked for collision");
				this.addEntityCrashInfo(crashreportcategory);
				throw new ReportedException(crashreport);
			}
		}
	}

	private void collideWithNearbyEntities() {
		List list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(0.2D, 0.0D, 0.2D),
				new Predicate<Entity>() {
					public boolean apply(Entity p_apply_1_) {
						return p_apply_1_.canBePushed();
					}
				});

		if (!list.isEmpty())
			for (int i = 0; i < list.size(); ++i) {
				Entity entity = (Entity) list.get(i);
				collision(entity);
			}
	}

	private void collision(Entity entity) {
		if (!isPassenger(entity) && this.getRidingEntity() != entity) {
			if (!this.noClip && !entity.noClip) {
				if (!this.world.isRemote && entity instanceof EntityMinecart
						&& ((EntityMinecart) entity).getType() == EntityMinecart.Type.RIDEABLE
						&& entity.motionX * entity.motionX + entity.motionZ * entity.motionZ > 0.01D
						&& entity.getPassengers().isEmpty() && this.getRidingEntity() == null) {
					this.startRiding(entity);
					this.motionY += 0.25;
					this.velocityChanged = true;
				} else {
					double dx = this.posX - entity.posX;
					double dz = this.posZ - entity.posZ;
					double dmax = MathHelper.absMax(dx, dz);

					if (dmax >= 0.01D) {
						dmax = (double) MathHelper.sqrt(dmax);
						dx /= dmax;
						dz /= dmax;
						double d3 = 1.0D / dmax;

						if (d3 > 1.0D)
							d3 = 1.0D;

						dx *= d3;
						dz *= d3;
						dx *= 0.05D;
						dz *= 0.05D;
						dx *= (double) (1.0F - entity.entityCollisionReduction);
						dz *= (double) (1.0F - entity.entityCollisionReduction);

						if (entity.getPassengers().isEmpty()) {
							entity.motionX += -dx;
							entity.motionZ += -dz;
						}

						if (this.getPassengers().isEmpty()) {
							this.motionX += dx;
							this.motionZ += dz;
						}
					}
				}
			}
		}
	}

	private void despawnEntity() {
		if (!getCanDespawn()) {
			this.age = 0;
		} else {
			Entity entity = this.world.getClosestPlayerToEntity(this, -0.1D);

			if (entity != null) {
				double d0 = entity.posX - this.posX;
				double d1 = entity.posY - this.posY;
				double d2 = entity.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;

				if (d3 > 16 * 16)
					this.setDead();
			}

		}
	
	}

	public int getSize() {
		return this.dataManager.get(SIZE);
	}

	public float getCustomWindX() {
		return this.dataManager.get(CUSTOM_WIND_X);
	}

	public float getCustomWindZ() {
		return this.dataManager.get(CUSTOM_WIND_Z);
	}

	public boolean getCustomWindEnabled() {
		return this.dataManager.get(CUSTOM_WIND_ENABLED);
	}

}
