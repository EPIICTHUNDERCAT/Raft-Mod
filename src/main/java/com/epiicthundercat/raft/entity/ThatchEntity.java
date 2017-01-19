package com.epiicthundercat.raft.entity;

import java.util.List;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.RItems;
import com.epiicthundercat.raft.init.barrel.BarrelLoot;
import com.google.common.collect.Lists;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThatchEntity extends Entity{
	private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.<Integer>createKey(ThatchEntity.class,
			DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager
			.<Integer>createKey(ThatchEntity.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(ThatchEntity.class,
			DataSerializers.FLOAT);
	

	/** How much of current speed to retain. Value zero to one. */
	private float momentum;
	private float outOfControlTicks;
	private float deltaRotation;
	private int lerpSteps;
	private double ThatchPitch;
	private double lerpY;
	
	private double lerpZ;
	private double ThatchYaw;
	private double lerpXRot;
	
	private boolean leftInputDown;
	private boolean rightInputDown;
	private boolean forwardInputDown;
	private boolean backInputDown;
	private double waterLevel;
	/**
	 * How much the Thatch should glide given the slippery blocks it's currently
	 * gliding over. Halved every tick.
	 */
	private float ThatchGlide;
	private ThatchEntity.Status status;
	private ThatchEntity.Status previousStatus;
	private double lastYd;

	public ThatchEntity(World worldIn) {
		super(worldIn);

		this.preventEntitySpawning = true;
		this.setSize(1F, 0.9F);
	}

	public ThatchEntity(World worldIn, double x, double y, double z) {
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
	protected boolean canTriggerWalking() {
		return false;
	}

	

	protected void entityInit() {
		this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(0));
		this.dataManager.register(FORWARD_DIRECTION, Integer.valueOf(1));
		this.dataManager.register(DAMAGE_TAKEN, Float.valueOf(0.0F));
		
	}

	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like Thatchs
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
	public boolean canBePushed() {
		return true;
	}

	/**
	 * Called when the entity is attacked.
	 * 
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else if (!this.worldObj.isRemote && !this.isDead) {
			if (source instanceof EntityDamageSourceIndirect && source.getEntity() != null) {
				return false;
			} else {
				this.setForwardDirection(-this.getForwardDirection());
				this.setTimeSinceHit(10);
				this.setDamageTaken(this.getDamageTaken() + amount * 10.0F);
				this.setBeenAttacked();
				// if (source.getEntity() instanceof EntityPlayer) {
				boolean flag = source.getEntity() instanceof EntityPlayer
						&& ((EntityPlayer) source.getEntity()).capabilities.isCreativeMode;

				EntityPlayer player = (EntityPlayer) source.getEntity();

				// if (this.getDamageTaken() > 5.0F) {
				if (flag || this.getDamageTaken() > 5.0F) {

					// if (!player.capabilities.isCreativeMode) {
					if (!flag && this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
						BlockPos pos = getPosition();
						this.extractItems(worldObj, pos, player);
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
	public void applyEntityCollision(Entity entityIn) {
		if (entityIn instanceof ThatchEntity) {
			if (entityIn.getEntityBoundingBox().minY < this.getEntityBoundingBox().maxY) {
				super.applyEntityCollision(entityIn);
			}
		} else if (entityIn.getEntityBoundingBox().minY <= this.getEntityBoundingBox().minY) {
			super.applyEntityCollision(entityIn);
		}
	}

	

	/**
	 * Setups the entity to do the hurt animation. Only used by packets in
	 * multiplayer.
	 */
	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch,
			int posRotationIncrements, boolean teleport) {
		this.ThatchPitch = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.ThatchYaw = (double) yaw;
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

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.previousStatus = this.status;
		this.status = this.getThatchStatus();

		if (this.status != ThatchEntity.Status.UNDER_WATER && this.status != ThatchEntity.Status.UNDER_FLOWING_WATER) {
			this.outOfControlTicks = 0.0F;
		} else {
			++this.outOfControlTicks;
		}

		if (this.getTimeSinceHit() > 0) {
			this.setTimeSinceHit(this.getTimeSinceHit() - 1);
		}

		if (this.getDamageTaken() > 0.0F) {
			this.setDamageTaken(this.getDamageTaken() - 1.0F);
		}

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		super.onUpdate();
		this.tickLerp();

		if (!canPassengerSteer()) {
			this.updateMotion();

			if (this.worldObj.isRemote) {
				this.controlBoat();
				// this.worldObj.sendPacketToServer(null);
			}

			this.moveEntity(this.motionX, this.motionY, this.motionZ);
		} else {
			this.motionX = 0.0D;
			this.motionY = 0.0D;
			this.motionZ = 0.0D;
		}

		this.doBlockCollisions();
		List<Entity> list = this.worldObj.getEntitiesInAABBexcluding(this,
				this.getEntityBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D),
				EntitySelectors.<Entity>getTeamCollisionPredicate(this));

	}

	private void tickLerp() {
		if (this.lerpSteps > 0) {
			double d0 = this.posX + (this.ThatchPitch - this.posX) / (double) this.lerpSteps;
			double d1 = this.posY + (this.lerpY - this.posY) / (double) this.lerpSteps;
			double d2 = this.posZ + (this.lerpZ - this.posZ) / (double) this.lerpSteps;
			double d3 = MathHelper.wrapDegrees(this.ThatchYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
			this.rotationPitch = (float) ((double) this.rotationPitch
					+ (this.lerpXRot - (double) this.rotationPitch) / (double) this.lerpSteps);
			--this.lerpSteps;
			this.setPosition(d0, d1, d2);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	/**
	 * Determines whether the Thatch is in water, gliding on land, or in air
	 */
	private ThatchEntity.Status getThatchStatus() {
		ThatchEntity.Status ThatchEntity$status = this.getUnderwaterStatus();

		if (ThatchEntity$status != null) {
			this.waterLevel = this.getEntityBoundingBox().maxY;
			return ThatchEntity$status;
		} else if (this.checkInWater()) {
			return ThatchEntity.Status.IN_WATER;
		} else {
			float f = this.getThatchGlide();

			if (f > 0.0F) {
				this.ThatchGlide = f;
				return ThatchEntity.Status.ON_LAND;
			} else {
				return ThatchEntity.Status.IN_AIR;
			}
		}
	}

	public float getWaterLevelAbove() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		int i = MathHelper.floor_double(axisalignedbb.minX);
		int j = MathHelper.ceiling_double_int(axisalignedbb.maxX);
		int k = MathHelper.floor_double(axisalignedbb.maxY);
		int l = MathHelper.ceiling_double_int(axisalignedbb.maxY - this.lastYd);
		int i1 = MathHelper.floor_double(axisalignedbb.minZ);
		int j1 = MathHelper.ceiling_double_int(axisalignedbb.maxZ);
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
						IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);

						if (iblockstate.getMaterial() == Material.WATER) {
							f = Math.max(f,
									getBlockLiquidHeight(iblockstate, this.worldObj, blockpos$pooledmutableblockpos));
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
	 * Decides how much the Thatch should be gliding on the land (based on any
	 * slippery blocks)
	 */
	public float getThatchGlide() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY - 0.001D,
				axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
		int i = MathHelper.floor_double(axisalignedbb1.minX) - 1;
		int j = MathHelper.ceiling_double_int(axisalignedbb1.maxX) + 1;
		int k = MathHelper.floor_double(axisalignedbb1.minY) - 1;
		int l = MathHelper.ceiling_double_int(axisalignedbb1.maxY) + 1;
		int i1 = MathHelper.floor_double(axisalignedbb1.minZ) - 1;
		int j1 = MathHelper.ceiling_double_int(axisalignedbb1.maxZ) + 1;
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
								IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);
								iblockstate.addCollisionBoxToList(this.worldObj, blockpos$pooledmutableblockpos,
										axisalignedbb1, list, this);

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
		int i = MathHelper.floor_double(axisalignedbb.minX);
		int j = MathHelper.ceiling_double_int(axisalignedbb.maxX);
		int k = MathHelper.floor_double(axisalignedbb.minY);
		int l = MathHelper.ceiling_double_int(axisalignedbb.minY + 0.001D);
		int i1 = MathHelper.floor_double(axisalignedbb.minZ);
		int j1 = MathHelper.ceiling_double_int(axisalignedbb.maxZ);
		boolean flag = false;
		this.waterLevel = Double.MIN_VALUE;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		try {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);

						if (iblockstate.getMaterial() == Material.WATER) {
							float f = getLiquidHeight(iblockstate, this.worldObj, blockpos$pooledmutableblockpos);
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
	 * Decides whether the Thatch is currently underwater.
	 */
	@Nullable
	private ThatchEntity.Status getUnderwaterStatus() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();
		double d0 = axisalignedbb.maxY + 0.001D;
		int i = MathHelper.floor_double(axisalignedbb.minX);
		int j = MathHelper.ceiling_double_int(axisalignedbb.maxX);
		int k = MathHelper.floor_double(axisalignedbb.maxY);
		int l = MathHelper.ceiling_double_int(d0);
		int i1 = MathHelper.floor_double(axisalignedbb.minZ);
		int j1 = MathHelper.ceiling_double_int(axisalignedbb.maxZ);
		boolean flag = false;
		BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();

		try {
			for (int k1 = i; k1 < j; ++k1) {
				for (int l1 = k; l1 < l; ++l1) {
					for (int i2 = i1; i2 < j1; ++i2) {
						blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
						IBlockState iblockstate = this.worldObj.getBlockState(blockpos$pooledmutableblockpos);

						if (iblockstate.getMaterial() == Material.WATER && d0 < (double) getLiquidHeight(iblockstate,
								this.worldObj, blockpos$pooledmutableblockpos)) {
							if (((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() != 0) {
								ThatchEntity.Status ThatchEntity$status = ThatchEntity.Status.UNDER_FLOWING_WATER;
								return ThatchEntity$status;
							}

							flag = true;
						}
					}
				}
			}
		} finally {
			blockpos$pooledmutableblockpos.release();
		}

		return flag ? ThatchEntity.Status.UNDER_WATER : null;
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
	 * Update the Thatch's speed, based on momentum.
	 */
	private void updateMotion() {
		double d0 = -0.03999999910593033D;
		double d1 = this.hasNoGravity() ? 0.0D : -0.03999999910593033D;
		double d2 = 0.0D;
		this.momentum = 0.05F;

		if (this.previousStatus == ThatchEntity.Status.IN_AIR && this.status != ThatchEntity.Status.IN_AIR
				&& this.status != ThatchEntity.Status.ON_LAND) {
			this.waterLevel = this.getEntityBoundingBox().minY + (double) this.height;
			this.setPosition(this.posX, (double) (this.getWaterLevelAbove() - this.height) + 0.101D, this.posZ);
			this.motionY = 0.0D;
			this.lastYd = 0.0D;
			this.status = ThatchEntity.Status.IN_WATER;
		} else {
			if (this.status == ThatchEntity.Status.IN_WATER) {
				d2 = (this.waterLevel - this.getEntityBoundingBox().minY) / (double) this.height;
				this.momentum = 0.9F;
				ThatchGlide /= 2f;
			} else if (this.status == ThatchEntity.Status.UNDER_FLOWING_WATER) {
				d1 = -7.0E-4D;
				this.momentum = 0.9F;
			} else if (this.status == ThatchEntity.Status.UNDER_WATER) {
				d2 = 0.009999999776482582D;
				this.momentum = 0.45F;
			} else if (this.status == ThatchEntity.Status.IN_AIR) {
				this.momentum = 0.9F;
			} else if (this.status == ThatchEntity.Status.ON_LAND) {
				this.momentum = this.ThatchGlide;

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

	

	/*
	 * public boolean pickup(World world, BlockPos pos, IBlockState state,
	 * EntityPlayer player, EnumHand hand, EnumFacing par6, float par7, float
	 * par8, float par9) { if(!world.isRemote){ world.playSound(null, pos,
	 * SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.2F,
	 * world.rand.nextFloat()*0.1F+0.9F); this.dropItems(world, pos);
	 * 
	 * 
	 * 
	 * } return true; }
	 */
	private void extractItems(World world, BlockPos pos, EntityPlayer player) {
		for (int i = 0; i < MathHelper.getRandomIntegerInRange(world.rand, 3, 6); i++) {
			BarrelLoot returns = WeightedRandom.getRandomItem(world.rand, REventHandler.barrel_loot);
			ItemStack itemStack = returns.returnItem.copy();

			player.inventory.addItemStackToInventory(itemStack);
		}

	}

	public boolean processInitialInteract(EntityPlayer player, @Nullable ItemStack stack, EnumHand hand) {

		if (!this.worldObj.isRemote && !player.isSneaking() && this.outOfControlTicks < 60.0F) {
			BlockPos pos = getPosition();
			this.extractItems(worldObj, pos, player);
		}
		this.setDead();
		return true;
	}

	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		this.lastYd = this.motionY;

		if (!this.isRiding()) {
			if (onGroundIn) {
				if (this.fallDistance > 3.0F) {
					if (this.status != ThatchEntity.Status.ON_LAND) {
						this.fallDistance = 0.0F;
						return;
					}

					this.fall(this.fallDistance, 1.0F);

					if (!this.worldObj.isRemote && !this.isDead) {
						this.setDead();

						if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
							for (int i = 0; i < 3; ++i) {
								this.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.PLANKS), 1), 0f);
							}

							for (int j = 0; j < 2; ++j) {
								this.dropItemWithOffset(Items.STICK, 1, 0.0F);
							}
							for (int j = 0; j < 2; ++j) {
								this.dropItemWithOffset(RItems.scrap, 1, 0.0F);
							}
							for (int j = 0; j < 2; ++j) {
								this.dropItemWithOffset(RItems.thatch, 1, 0.0F);
							}
							for (int j = 0; j < 2; ++j) {
								this.dropItemWithOffset(RItems.plank, 1, 0.0F);
							}

						}
					}
				}

				this.fallDistance = 0.0F;
			} else if (this.worldObj.getBlockState((new BlockPos(this)).down()).getMaterial() != Material.WATER
					&& y < 0.0D) {
				this.fallDistance = (float) ((double) this.fallDistance - y);
			}
		}
	}

	private void controlBoat() {
		if (!isBeingRidden()) {
			float f = 0.0F;

			if (this.leftInputDown) {
				this.deltaRotation += -1.0F;
			}

			if (this.rightInputDown) {
				++this.deltaRotation;
			}

			if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
				f += 0.005F;
			}

			this.rotationYaw += this.deltaRotation;

			if (this.forwardInputDown) {
				f += 0.04F;
			}

			if (this.backInputDown) {
				f -= 0.005F;
			}

			// this.motionX += (double)(MathHelper.sin(-this.rotationYaw *
			// 0.017453292F) * f);
			// this.motionZ += (double)(MathHelper.cos(this.rotationYaw *
			// 0.017453292F) * f);
			// this.setPaddleState(this.rightInputDown || this.forwardInputDown,
			// this.leftInputDown || this.forwardInputDown);
		}
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
		
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
		
	}

}
