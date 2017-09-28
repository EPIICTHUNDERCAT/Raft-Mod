package com.epiicthundercat.raft.entity.passive;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.entity.PathNavigation;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeOcean;

public class EntityFish extends EntityAnimal {
	private static final DataParameter<Byte> STATUS = EntityDataManager.<Byte>createKey(EntityFish.class,
			DataSerializers.BYTE);
	public static final ResourceLocation LOOT_FISH = new ResourceLocation(Reference.ID, "entities/fish");

	private static final DataParameter<Integer> FISH_VARIANT = EntityDataManager.<Integer>createKey(EntityFish.class,
			DataSerializers.VARINT);
	private EntityAIAvoidEntity<EntityPlayer> avoidEntity;

	private EntityAIWander wander;
	private boolean clientSideTouchedGround;

	public EntityFish(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.7F);
		this.moveHelper = new EntityFish.FishMoveHelper(this);

	}

	@Override
	protected void initEntityAI() {
		this.wander = new EntityAIWander(this, 1.0D, 80);
		this.tasks.addTask(7, this.wander);

		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.wander.setMutexBits(3);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(STATUS, Byte.valueOf((byte) 0));
		this.dataManager.register(FISH_VARIANT, Integer.valueOf(0));
	}

	/**
	 * Determines if an entity can be despawned, used on idle far away entities
	 */
	@Override
	protected boolean canDespawn() {
		return this.ticksExisted > 2400;
	}

	protected PathNavigate getNewNavigator(World worldIn) {
		return new PathNavigateSwimmer(this, worldIn);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
	}

	public static void registerFixesFish(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntityFish.class);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {

		return null;
	}
	protected PathNavigate createNavigator(World worldIn)
	  {
	    return new PathNavigation(this, worldIn);
	  }
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
			} else if (this.isMoving()) {

			} else {
			}

			if (this.isMoving() && this.isInWater()) {
				Vec3d vec3d = this.getLook(0.0F);

				for (int i = 0; i < 2; ++i) {
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
							this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.xCoord * 0.5D,
							this.posY + this.rand.nextDouble() * (double) this.height - vec3d.yCoord * 0.5D,
							this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width - vec3d.zCoord * 0.5D,
							0.0D, 0.0D, 0.0D, new int[0]);
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

		super.onLivingUpdate();
	}

	static class FishMoveHelper extends EntityMoveHelper {
		private final EntityFish EntityFish;

		public FishMoveHelper(EntityFish fish) {
			super(fish);
			this.EntityFish = fish;
		}

		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.EntityFish.getNavigator().noPath()) {
				double d0 = this.posX - this.EntityFish.posX;
				double d1 = this.posY - this.EntityFish.posY;
				double d2 = this.posZ - this.EntityFish.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				d3 = (double) MathHelper.sqrt(d3);
				d1 = d1 / d3;
				float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
				this.EntityFish.rotationYaw = this.limitAngle(this.EntityFish.rotationYaw, f, 90.0F);
				this.EntityFish.renderYawOffset = this.EntityFish.rotationYaw;
				float f1 = (float) (this.speed * this.EntityFish
						.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				this.EntityFish.setAIMoveSpeed(
						this.EntityFish.getAIMoveSpeed() + (f1 - this.EntityFish.getAIMoveSpeed()) * 0.125F);
				double d4 = Math.sin((double) (this.EntityFish.ticksExisted + this.EntityFish.getEntityId()) * 0.5D)
						* 0.05D;
				double d5 = Math.cos((double) (this.EntityFish.rotationYaw * 0.017453292F));
				double d6 = Math.sin((double) (this.EntityFish.rotationYaw * 0.017453292F));
				this.EntityFish.motionX += d4 * d5;
				this.EntityFish.motionZ += d4 * d6;
				d4 = Math.sin((double) (this.EntityFish.ticksExisted + this.EntityFish.getEntityId()) * 0.75D) * 0.05D;
				this.EntityFish.motionY += d4 * (d6 + d5) * 0.25D;
				this.EntityFish.motionY += (double) this.EntityFish.getAIMoveSpeed() * d1 * 0.1D;
				EntityLookHelper entitylookhelper = this.EntityFish.getLookHelper();
				double d7 = this.EntityFish.posX + d0 / d3 * 2.0D;
				double d8 = (double) this.EntityFish.getEyeHeight() + this.EntityFish.posY + d1 / d3;
				double d9 = this.EntityFish.posZ + d2 / d3 * 2.0D;
				double d10 = entitylookhelper.getLookPosX();
				double d11 = entitylookhelper.getLookPosY();
				double d12 = entitylookhelper.getLookPosZ();

				if (!entitylookhelper.getIsLooking()) {
					d10 = d7;
					d11 = d8;
					d12 = d9;
				}

				this.EntityFish.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D,
						d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
				this.EntityFish.setMoving(true);
			} else {
				this.EntityFish.setAIMoveSpeed(0.0F);
				this.EntityFish.setMoving(false);
			}
		}
	}

	public boolean isMoving() {
		return this.isSyncedFlagSet(2);
	}

	private void setMoving(boolean moving) {
		this.setSyncedFlag(2, moving);
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

	public int getFishType() {
		return ((Integer) this.dataManager.get(FISH_VARIANT)).intValue();
	}

	public void setFishType(int FishTypeId) {

		this.dataManager.set(FISH_VARIANT, Integer.valueOf(FishTypeId));
	}

	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {

		livingdata = super.onInitialSpawn(difficulty, livingdata);

		int i = this.getRandomFishType();
		boolean flag = false;

		if (livingdata instanceof EntityFish.FishTypeData) {
			i = ((EntityFish.FishTypeData) livingdata).typeData;
			flag = true;
		} else {
			livingdata = new EntityFish.FishTypeData(i);
		}

		this.setFishType(i);

		if (flag) {
			this.setGrowingAge(-24000);
		}

		return livingdata;
	}

	private int getRandomFishType() {
		Biome biome = this.world.getBiome(new BlockPos(this));
		int i = this.rand.nextInt(100);
		return biome.isSnowyBiome() ? (i < 80 ? 1 : 3)
				: (biome instanceof BiomeOcean ? 4 : (i < 50 ? 0 : (i < 90 ? 5 : 2)));

	}

	public static class FishTypeData implements IEntityLivingData {
		public int typeData;

		public FishTypeData(int type) {
			this.typeData = type;
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}

	@Nullable
	
	protected ResourceLocation getLootTable() {
		return LOOT_FISH;
	}
}
