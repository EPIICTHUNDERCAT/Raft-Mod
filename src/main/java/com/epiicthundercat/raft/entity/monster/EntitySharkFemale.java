package com.epiicthundercat.raft.entity.monster;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.Reference;
import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySharkFemale extends EntityMob {

	public static final ResourceLocation LOOT_FEMALESHARK = new ResourceLocation(Reference.ID, "entities/femaleshark");
	private static final DataParameter<Byte> STATUS = EntityDataManager.<Byte>createKey(EntitySharkFemale.class,
			DataSerializers.BYTE);
	private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager
			.<Integer>createKey(EntitySharkFemale.class, DataSerializers.VARINT);

	private EntityLivingBase targetedEntity;
	private int clientSideAttackTime;
	private boolean clientSideTouchedGround;
	private EntityAIWander wander;

	public EntitySharkFemale(World worldIn) {
		super(worldIn);
		this.experienceValue = 10;
		this.setSize(0.85F, 0.85F);
	//	this.moveHelper = new EntitySharkFemale.SharkMoveHelper(this);
		
	}

	@Override
	protected void initEntityAI() {
	/*	EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
		this.wander = new EntityAIWander(this, 1.0D, 80);
		this.tasks.addTask(4, new EntitySharkFemale.AISharkAttack(this));
		this.tasks.addTask(5, entityaimovetowardsrestriction);
		this.tasks.addTask(7, this.wander);
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntitySharkFemale.class, 12.0F, 0.01F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.wander.setMutexBits(3);
		entityaimovetowardsrestriction.setMutexBits(3);
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false,
				new EntitySharkFemale.SharkTargetSelector(this)));*/
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
	}

	public static void registerFixesShark(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntitySharkFemale.class);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);

	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);

	}

	/**
	 * Returns new PathNavigateGround instance
	 */
	protected PathNavigate getNewNavigator(World worldIn) {
		return new PathNavigateSwimmer(this, worldIn);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(STATUS, Byte.valueOf((byte) 0));
		this.dataManager.register(TARGET_ENTITY, Integer.valueOf(0));
	}

	/**
	 * Returns true if given flag is set
	 */
	private boolean isSyncedFlagSet(int flagId) {
		return (((Byte) this.dataManager.get(STATUS)).byteValue() & flagId) != 0;
	}

	/**
	 * Sets a flag state "on/off" on both sides (client/server) by using
	 * DataWatcher
	 */

	private void setSyncedFlag(int flagId, boolean state) {
		byte b0 = ((Byte) this.dataManager.get(STATUS)).byteValue();

		if (state) {
			this.dataManager.set(STATUS, Byte.valueOf((byte) (b0 | flagId)));
		} else {
			this.dataManager.set(STATUS, Byte.valueOf((byte) (b0 & ~flagId)));
		}
	}

	public boolean isMoving() {
		return this.isSyncedFlagSet(2);
	}

	private void setMoving(boolean moving) {
		this.setSyncedFlag(2, moving);
	}

	public int getAttackDuration() {
		return 80;
	}

	private void setTargetedEntity(int entityId) {
		this.dataManager.set(TARGET_ENTITY, Integer.valueOf(entityId));
	}

	public boolean hasTargetedEntity() {
		return ((Integer) this.dataManager.get(TARGET_ENTITY)).intValue() != 0;
	}

	public EntityLivingBase getTargetedEntity() {
		if (!this.hasTargetedEntity()) {
			return null;
		} else if (this.world.isRemote) {
			if (this.targetedEntity != null) {
				return this.targetedEntity;
			} else {
				Entity entity = this.world.getEntityByID(((Integer) this.dataManager.get(TARGET_ENTITY)).intValue());

				if (entity instanceof EntityLivingBase) {
					this.targetedEntity = (EntityLivingBase) entity;
					return this.targetedEntity;
				} else {
					return null;
				}
			}
		} else {
			return this.getAttackTarget();
		}
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		super.notifyDataManagerChange(key);

		if (STATUS.equals(key)) {

		} else if (TARGET_ENTITY.equals(key)) {
			this.clientSideAttackTime = 0;
			this.targetedEntity = null;
		}
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	@Override
	public float getBlockPathWeight(BlockPos pos) {
		return this.world.getBlockState(pos).getMaterial() == Material.WATER
				? 10.0F + this.world.getLightBrightness(pos) - 0.5F : super.getBlockPathWeight(pos);
	}

	/**
	 * Called frequently so the entity can update its state every tick as
	 * required. For example, zombies and skeletons use this to react to
	 * sunlight and start to burn.
	 */
	@Override
	public void onLivingUpdate() {
		if (this.world.isRemote) {
			

			if (!this.isInWater()) {
				

				if (this.motionY > 0.0D && this.clientSideTouchedGround && !this.isSilent()) {
					this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_GUARDIAN_FLOP,
							this.getSoundCategory(), 1.0F, 1.0F, false);
				}

				this.clientSideTouchedGround = this.motionY < 0.0D
						&& this.world.isBlockNormalCube((new BlockPos(this)).down(), false);
			} 
			if (this.isMoving() && this.isInWater()) {
				Vec3d vec3d = this.getLook(0.0F);

				for (int i = 0; i < 2; ++i) {
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
							this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.xCoord * 1.5D,
							this.posY + this.rand.nextDouble() * (double) this.height - vec3d.yCoord * 1.5D,
							this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.zCoord * 1.5D,
							0.0D, 0.0D, 0.0D, new int[0]);
				}
			}

			if (this.hasTargetedEntity()) {
				if (this.clientSideAttackTime < this.getAttackDuration()) {
					++this.clientSideAttackTime;
				}

				EntityLivingBase entitylivingbase = this.getTargetedEntity();

				if (entitylivingbase != null) {
					this.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);
					this.getLookHelper().onUpdateLook();
					double d5 = (double) this.getAttackAnimationScale(0.0F);
					double d0 = entitylivingbase.posX - this.posX;
					double d1 = entitylivingbase.posY + (double) (entitylivingbase.height * 0.5F)
							- (this.posY + (double) this.getEyeHeight());
					double d2 = entitylivingbase.posZ - this.posZ;
					double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
					d0 = d0 / d3;
					d1 = d1 / d3;
					d2 = d2 / d3;
					double d4 = this.rand.nextDouble();

					while (d4 < d3) {
						d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
						this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4,
								this.posY + d1 * d4 + (double) this.getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D,
								0.0D, new int[0]);
					}
				}
			}
		}

		if (this.inWater) {
			this.setAir(300);
		} else if (this.onGround) {
			this.motionY += 0.5D;
			this.motionX += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			this.motionZ += (double) ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
			this.rotationYaw = this.rand.nextFloat() * 360.0F;
			this.onGround = false;
			this.isAirBorne = true;
		}

		if (this.hasTargetedEntity()) {
			this.rotationYaw = this.rotationYawHead;
		}

		super.onLivingUpdate();
	}

	
	public float getAttackAnimationScale(float p_175477_1_) {
		return ((float) this.clientSideAttackTime + p_175477_1_) / (float) this.getAttackDuration();
	}

	

	@Nullable
	@Override
	protected ResourceLocation getLootTable() {
		return LOOT_FEMALESHARK;
	}

	/**
	 * Checks to make sure the light is not too bright where the mob is spawning
	 */
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	/**
	 * Checks that the entity is not colliding with any blocks / liquids
	 */
	@Override
	public boolean isNotColliding() {
		return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
				&& this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty();
	}

	/**
	 * Checks if the entity's current position is a valid location to spawn this
	 * entity.
	 */
	@Override
	public boolean getCanSpawnHere() {
		return (this.rand.nextInt(20) == 0 || !this.world.canBlockSeeSky(new BlockPos(this)))
				&& super.getCanSpawnHere();
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.isMoving() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) source.getSourceOfDamage();

			if (!source.isExplosion()) {
				entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
			}
		}

		if (this.wander != null) {
			this.wander.makeUpdate();
		}

		return super.attackEntityFrom(source, amount);
	}

	/**
	 * The speed it takes to move the entityliving's rotationPitch through the
	 * faceEntity method. This is only currently use in wolves.
	 */
	public int getVerticalFaceSpeed() {
		return 180;
	}

	/**
	 * Moves the entity based on the specified heading.
	 */
	public void moveEntityWithHeading(float strafe, float forward) {
		if (this.isServerWorld()) {
			if (this.isInWater()) {
				this.moveRelative(strafe, forward, 0.1F);
				//this.faceEntity(this.motionX, this.motionY, this.motionZ);
				this.motionX *= 0.8999999761581421D;
				this.motionY *= 0.8999999761581421D;
				this.motionZ *= 0.8999999761581421D;

				if (!this.isMoving() && this.getAttackTarget() == null) {
					this.motionY -= 0.005D;
				}
			} else {
				super.moveEntityWithHeading(strafe, forward);
			}
		} else {
			super.moveEntityWithHeading(strafe, forward);
		}
	}

	static class AISharkAttack extends EntityAIBase {
		private final EntitySharkFemale theEntity;
		private int tickCounter;

		public AISharkAttack(EntitySharkFemale shark) {
			this.theEntity = shark;
			this.setMutexBits(3);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean continueExecuting() {
			return super.continueExecuting()
					&& (this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0D);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.tickCounter = -10;
			this.theEntity.getNavigator().clearPathEntity();
			this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
			this.theEntity.isAirBorne = true;
		}

		/**
		 * Resets the task
		 */
		public void resetTask() {
			this.theEntity.setTargetedEntity(0);
			this.theEntity.setAttackTarget((EntityLivingBase) null);
			this.theEntity.wander.makeUpdate();
		}

		/**
		 * Updates the task
		 */
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
			this.theEntity.getNavigator().clearPathEntity();
			this.theEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);

			if (!this.theEntity.canEntityBeSeen(entitylivingbase)) {
				this.theEntity.setAttackTarget((EntityLivingBase) null);
			} else {
				++this.tickCounter;

				if (this.tickCounter == 0) {
					this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
//					this.theEntity.world.setEntityState(this.theEntity, (byte) 21);
				} else if (this.tickCounter >= this.theEntity.getAttackDuration()) {
					float f = 1.0F;

					if (this.theEntity.world.getDifficulty() == EnumDifficulty.HARD) {
						f += 2.0F;
					}

					entitylivingbase
							.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), f);
					entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.theEntity),
							(float) this.theEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
									.getAttributeValue());
					this.theEntity.setAttackTarget((EntityLivingBase) null);
				}

				super.updateTask();
			}
		}
	}

	static class SharkMoveHelper extends EntityMoveHelper {
		private final EntitySharkFemale entitySharkFemale;

		public SharkMoveHelper(EntitySharkFemale shark) {
			super(shark);
			this.entitySharkFemale = shark;

		}

		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.entitySharkFemale.getNavigator().noPath()) {
				double d0 = this.posX - this.entitySharkFemale.posX;
				double d1 = this.posY - this.entitySharkFemale.posY;
				double d2 = this.posZ - this.entitySharkFemale.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				d3 = (double) MathHelper.sqrt(d3);
				d1 = d1 / d3;
				float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
				this.entitySharkFemale.rotationYaw = this.limitAngle(this.entitySharkFemale.rotationYaw, f, 90.0F);
				this.entitySharkFemale.renderYawOffset = this.entitySharkFemale.rotationYaw;
				float f1 = (float) (this.speed * this.entitySharkFemale
						.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				this.entitySharkFemale.setAIMoveSpeed(this.entitySharkFemale.getAIMoveSpeed()
						+ (f1 - this.entitySharkFemale.getAIMoveSpeed()) * 0.125F);
				double d4 = Math.sin(
						(double) (this.entitySharkFemale.ticksExisted + this.entitySharkFemale.getEntityId()) * 0.5D)
						* 0.05D;
				double d5 = Math.cos((double) (this.entitySharkFemale.rotationYaw * 0.017453292F));
				double d6 = Math.sin((double) (this.entitySharkFemale.rotationYaw * 0.017453292F));
				this.entitySharkFemale.motionX += d4 * d5;
				this.entitySharkFemale.motionZ += d4 * d6;
				d4 = Math.sin(
						(double) (this.entitySharkFemale.ticksExisted + this.entitySharkFemale.getEntityId()) * 0.75D)
						* 0.05D;
				this.entitySharkFemale.motionY += d4 * (d6 + d5) * 0.25D;
				this.entitySharkFemale.motionY += (double) this.entitySharkFemale.getAIMoveSpeed() * d1 * 0.1D;
				EntityLookHelper entitylookhelper = this.entitySharkFemale.getLookHelper();
				double d7 = this.entitySharkFemale.posX + d0 / d3 * 2.0D;
				double d8 = (double) this.entitySharkFemale.getEyeHeight() + this.entitySharkFemale.posY + d1 / d3;
				double d9 = this.entitySharkFemale.posZ + d2 / d3 * 2.0D;
				double d10 = entitylookhelper.getLookPosX();
				double d11 = entitylookhelper.getLookPosY();
				double d12 = entitylookhelper.getLookPosZ();

				if (!entitylookhelper.getIsLooking()) {
					d10 = d7;
					d11 = d8;
					d12 = d9;
				}

				this.entitySharkFemale.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D,
						d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
				this.entitySharkFemale.setMoving(true);
			} else {
				this.entitySharkFemale.setAIMoveSpeed(0.0F);
				this.entitySharkFemale.setMoving(false);
			}
		}
	}

	static class SharkTargetSelector implements Predicate<EntityLivingBase> {
		private final EntitySharkFemale parentEntity;

		public SharkTargetSelector(EntitySharkFemale shark) {
			this.parentEntity = shark;
		}

		public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
			return (p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof EntitySquid)
					&& p_apply_1_.getDistanceSqToEntity(this.parentEntity) > 9.0D;
		}
	}

	static class AISharkBreakBlock extends EntityAIBase {
		private final EntitySharkFemale theEntity;
		private int tickCounter;

		public AISharkBreakBlock(EntitySharkFemale shark) {
			this.theEntity = shark;
			this.setMutexBits(3);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
			return entitylivingbase != null && entitylivingbase.isEntityAlive();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean continueExecuting() {
			return super.continueExecuting()
					&& (this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0D);
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			this.tickCounter = -10;
			this.theEntity.getNavigator().clearPathEntity();
			this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0F, 90.0F);
			this.theEntity.isAirBorne = true;
		}

		/**
		 * Resets the task
		 */
		public void resetTask() {
			this.theEntity.setTargetedEntity(0);
			this.theEntity.setAttackTarget((EntityLivingBase) null);
			this.theEntity.wander.makeUpdate();
		}

		/**
		 * Updates the task
		 */
		public void updateTask() {
			EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
			this.theEntity.getNavigator().clearPathEntity();
			this.theEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 90.0F, 90.0F);

			if (!this.theEntity.canEntityBeSeen(entitylivingbase)) {
				this.theEntity.setAttackTarget((EntityLivingBase) null);
			} else {
				++this.tickCounter;

				if (this.tickCounter == 0) {
					this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
					//this.theEntity.world.setEntityState(this.theEntity, (byte) 21);
				} else if (this.tickCounter >= this.theEntity.getAttackDuration()) {
					float f = 1.0F;

					if (this.theEntity.world.getDifficulty() == EnumDifficulty.HARD) {
						f += 2.0F;
					}

					entitylivingbase
							.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), f);
					entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage(this.theEntity),
							(float) this.theEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
									.getAttributeValue());
					this.theEntity.setAttackTarget((EntityLivingBase) null);
				}

				super.updateTask();
			}
		}
	}

}
