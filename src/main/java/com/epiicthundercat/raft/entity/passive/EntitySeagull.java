package com.epiicthundercat.raft.entity.passive;

import java.util.Random;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntitySeagull extends EntityAnimal {

	public int noOfTypes;

	public float startRotation;
	public float destPos;
	public float groundOffset;
	public float wingRotation;
	public float speedBuffer = 1.0F;
	public static final ResourceLocation LOOT_GULL = new ResourceLocation(Reference.ID, "entities/seagull");

	private static final DataParameter<Boolean> CAN_FLY = EntityDataManager.createKey(EntitySeagull.class,
			DataSerializers.BOOLEAN);
	private BlockPos spawnPosition;

	public EntitySeagull(World worldIn) {
		super(worldIn);
		setSize(0.7F, 0.8F);
		setPathPriority(PathNodeType.WATER, 0.0F);
	}

	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 0.8D));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));

	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.200000003D);
	}

	public float getEyeHeight() {
		return this.height * 0.6F;
	}

	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(CAN_FLY, Boolean.valueOf(false));
	}

	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!getCanFly()) {
			this.wingRotation = this.startRotation;
			this.groundOffset = this.destPos;
			this.destPos = ((float) (this.destPos + ((this.onGround) || (this.inWater) ? -1 : 4) * 0.3D));
			if (this.destPos < 0.0F) {
				this.destPos = 0.0F;
			}
			if (this.destPos > 1.0F) {
				this.destPos = 1.0F;
			}
			if ((!this.onGround) && (this.speedBuffer < 1.0F)) {
				this.speedBuffer = 1.0F;
			}
			this.speedBuffer = ((float) (this.speedBuffer * 0.9D));
			if ((!this.onGround) && (this.motionY < 0.0D)) {
				this.motionY *= 0.6D;
			}
			this.startRotation += this.speedBuffer * 2.0F;
		}
		EntityPlayer nearestPlayer = this.world.getClosestPlayerToEntity(this, 3.0D);
		if ((nearestPlayer != null) && (!nearestPlayer.isSneaking()) && (!getCanFly())) {
			setCanFly(true);
			this.motionY += 0.400000006D;
			this.spawnPosition = null;
		}
	}

	protected void updateAITasks() {
		super.updateAITasks();
		if (getCanFly()) {
			if ((this.spawnPosition != null)
					&& ((!this.world.isAirBlock(new BlockPos(this.spawnPosition.getX(), this.spawnPosition.getY(),
							this.spawnPosition.getZ())))
							|| (this.spawnPosition.getY() < 1)
							|| (this.spawnPosition.getY() > this.world
									.getTopSolidOrLiquidBlock(
											new BlockPos(this.spawnPosition.getX(), 0, this.spawnPosition.getZ()))
									.getY() + 12))) {
				this.spawnPosition = null;
			}
			if ((this.spawnPosition == null) || (this.rand.nextInt(30) == 0)
					|| (this.spawnPosition.distanceSq((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0D)) {
				this.spawnPosition = new BlockPos((int) (this.posX + this.rand.nextInt(7) - this.rand.nextInt(7)),
						(int) (this.posY + this.rand.nextInt(6) - 2.0D),
						(int) (this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7)));
			}
			double d0 = this.spawnPosition.getX() + 0.5D - this.posX;
			double d1 = this.spawnPosition.getY() + 0.1D - this.posY;
			double d2 = this.spawnPosition.getZ() + 0.5D - this.posZ;
			this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
			this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
			this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
			float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
			float f1 = MathHelper.wrapDegrees(f);
			this.moveForward = 0.5F;
			this.rotationYaw = f1;
			if ((this.onGround) || (this.inWater)) {
				this.motionY += 0.10000000149011612D;
			}
			if ((this.rand.nextInt(100) == 0) && (this.world
					.getBlockState(
							new BlockPos(MathHelper.floor(this.posX), (int) this.posY - 1, MathHelper.floor(this.posZ)))
					.isBlockNormalCube())) {
				setCanFly(false);
			}
		}
	}

	public boolean isOnLadder() {
		return false;
	}

	public boolean getCanFly() {
		return ((Boolean) this.dataManager.get(CAN_FLY)).booleanValue();
	}

	public void setCanFly(boolean setFly) {
		this.dataManager.set(CAN_FLY, Boolean.valueOf(setFly));
	}

	public void writeEntityToNBT(NBTTagCompound tagCompound) {
		super.writeEntityToNBT(tagCompound);
		tagCompound.setBoolean("FlightBool", getCanFly());
	}

	public void readEntityFromNBT(NBTTagCompound tagCompund) {
		super.readEntityFromNBT(tagCompund);
		setCanFly(tagCompund.getBoolean("FlightBool"));
	}

	/**
	 * Checks if the parameter is an item which this animal can be fed to breed
	 * it (wheat, carrots or seeds depending on the animal type)
	 */
	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockLeaves || block == Blocks.GRASS || block instanceof BlockLog
				|| block == Blocks.AIR && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	public void fall(float distance, float damageMultiplier) {
	}

	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	public boolean canMateWith(EntityAnimal otherAnimal) {
		return false;
	}

	@Nullable
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
	}

	/*
	 * private static SoundEvent getAmbientSound(Random random) {
	 * 
	 * return SoundEvents.ENTITY_PARROT_AMBIENT;
	 * 
	 * }
	 * 
	 * protected SoundEvent getHurtSound(DamageSource p_184601_1_) { return
	 * SoundEvents.ENTITY_PARROT_HURT; }
	 * 
	 * protected SoundEvent getDeathSound() { return
	 * SoundEvents.ENTITY_PARROT_DEATH; }
	 * 
	 * protected void playStepSound(BlockPos pos, Block blockIn) {
	 * this.playSound(SoundEvents.ENTITY_PARROT_STEP, 0.15F, 1.0F); }
	 * 
	 * protected float playFlySound(float p_191954_1_) {
	 * this.playSound(SoundEvents.ENTITY_PARROT_FLY, 0.15F, 1.0F); return
	 * p_191954_1_ + this.flapSpeed / 2.0F; }
	 */

	protected boolean makeFlySound() {
		return true;
	}

	/**
	 * Gets the pitch of living sounds in living entities.
	 */
	protected float getSoundPitch() {
		return getPitch(this.rand);
	}

	private static float getPitch(Random random) {
		return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
	}

	public SoundCategory getSoundCategory() {
		return SoundCategory.NEUTRAL;
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	public boolean canBePushed() {
		return true;
	}

	protected void collideWithEntity(Entity entityIn) {
		if (!(entityIn instanceof EntityPlayer)) {
			super.collideWithEntity(entityIn);
		}
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}

		return super.attackEntityFrom(source, amount);
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LOOT_GULL;
	}

	public boolean isFlying() {
		return !this.onGround;
	}

}
