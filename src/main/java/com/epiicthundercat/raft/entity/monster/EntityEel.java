package com.epiicthundercat.raft.entity.monster;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.entity.PathNavigation;
import com.epiicthundercat.raft.entity.passive.EntityFish;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
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
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityEel extends EntityMob {
	private static final DataParameter<Boolean> MOVING = EntityDataManager.<Boolean>createKey(EntityEel.class,
			DataSerializers.BOOLEAN);
	public static final ResourceLocation LOOT_EEL = new ResourceLocation(Reference.ID, "entities/eel");

	protected EntityAIWander wander;

	public EntityEel(World worldIn) {
		super(worldIn);
		this.experienceValue = 10;
		this.setSize(1.5F, 0.8F);
		this.moveHelper = new EntityEel.EelMoveHelper(this);
	}
	@Override
	protected void initEntityAI() {
		

		
		this.wander = new EntityAIWander(this, 1.0D, 80);
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, this.wander);
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.wander.setMutexBits(3);

		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityEel.class, 12.0F, 0.01F));
		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, true, new Class[0]));
		this.applyEntityAI();

	}
	
	protected void applyEntityAI()
    {
       
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntitySharkFemale.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityFish.class, true));
    }

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean isPushedByWater() {
		return false;
	}


	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(MOVING, Boolean.valueOf(false));

	}

	public boolean isMoving() {
		return ((Boolean) this.dataManager.get(MOVING)).booleanValue();
	}

	private void setMoving(boolean moving) {
		this.dataManager.set(MOVING, Boolean.valueOf(moving));
	}

	public static void registerFixesSquid(DataFixer fixer) {
		EntityLiving.registerFixesMob(fixer, EntitySquid.class);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		 this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
	}

	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_SQUID_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound() {
		return SoundEvents.ENTITY_SQUID_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_SQUID_DEATH;
	}
	@Override
	 public boolean attackEntityAsMob(Entity entityIn)
	    {
	        if (!super.attackEntityAsMob(entityIn))
	        {
	            return false;
	        }
	        else
	        {
	            if (entityIn instanceof EntityLivingBase)
	            {
	                ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200, 40));
	            }

	            return true;
	        }
	    }
	private boolean shouldAttackPlayer(EntityPlayer player) {
		ItemStack itemstack = (ItemStack) player.inventory.armorInventory.get(3);
		
		Vec3d vec3d = player.getLook(1.0F).normalize();
		Vec3d vec3d1 = new Vec3d(this.posX - player.posX, this.getEntityBoundingBox().minY
				+ (double) this.getEyeHeight() - (player.posY + (double) player.getEyeHeight()),
				this.posZ - player.posZ);
		double d0 = vec3d1.lengthVector();
		vec3d1 = vec3d1.normalize();
		double d1 = vec3d.dotProduct(vec3d1);
		return d1 > 1.0D - 0.025D / d0 ? player.canEntityBeSeen(this) : false;
	}
	/**
	 * Returns the volume for the sounds this mob makes.
	 */
	@Override
	protected float getSoundVolume() {
		return 0.4F;
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LOOT_EEL;
	}
	protected PathNavigate createNavigator(World worldIn)
	  {
	    return new PathNavigation(this, worldIn);
	  }
	static class EelMoveHelper extends EntityMoveHelper {
		private final EntityEel entityEel;

		public EelMoveHelper(EntityEel guardian) {
			super(guardian);
			this.entityEel = guardian;
		}

		
		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.entityEel.getNavigator().noPath()) {
				double d0 = this.posX - this.entityEel.posX;
				double d1 = this.posY - this.entityEel.posY;
				double d2 = this.posZ - this.entityEel.posZ;
				double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				d1 = d1 / d3;
				float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
				this.entityEel.rotationYaw = this.limitAngle(this.entityEel.rotationYaw, f, 90.0F);
				this.entityEel.renderYawOffset = this.entityEel.rotationYaw;
				float f1 = (float) (this.speed * this.entityEel
						.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				this.entityEel.setAIMoveSpeed(
						this.entityEel.getAIMoveSpeed() + (f1 - this.entityEel.getAIMoveSpeed()) * 0.125F);
				double d4 = Math.sin((double) (this.entityEel.ticksExisted + this.entityEel.getEntityId()) * 0.5D)
						* 0.05D;
				double d5 = Math.cos((double) (this.entityEel.rotationYaw * 0.017453292F));
				double d6 = Math.sin((double) (this.entityEel.rotationYaw * 0.017453292F));
				this.entityEel.motionX += d4 * d5;
				this.entityEel.motionZ += d4 * d6;
				d4 = Math.sin((double) (this.entityEel.ticksExisted + this.entityEel.getEntityId()) * 0.75D) * 0.05D;
				this.entityEel.motionY += d4 * (d6 + d5) * 0.25D;
				this.entityEel.motionY += (double) this.entityEel.getAIMoveSpeed() * d1 * 0.1D;
				EntityLookHelper entitylookhelper = this.entityEel.getLookHelper();
				double d7 = this.entityEel.posX + d0 / d3 * 2.0D;
				double d8 = (double) this.entityEel.getEyeHeight() + this.entityEel.posY + d1 / d3;
				double d9 = this.entityEel.posZ + d2 / d3 * 2.0D;
				double d10 = entitylookhelper.getLookPosX();
				double d11 = entitylookhelper.getLookPosY();
				double d12 = entitylookhelper.getLookPosZ();

				if (!entitylookhelper.getIsLooking()) {
					d10 = d7;
					d11 = d8;
					d12 = d9;
				}

				this.entityEel.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D,
						d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
				this.entityEel.setMoving(true);
			} else {
				this.entityEel.setAIMoveSpeed(0.0F);
				this.entityEel.setMoving(false);
			}
		}
	}
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!this.isMoving() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
			EntityLivingBase entitylivingbase = (EntityLivingBase) source.getSourceOfDamage();

			if (!source.isExplosion()) {
				entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0F);
				entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 10));
			}
		}

		if (this.wander != null) {
			this.wander.makeUpdate();
		}

		return super.attackEntityFrom(source, amount);
	}
}
