package com.epiicthundercat.raft.entity;

import com.epiicthundercat.raft.init.REventHandler;
import com.epiicthundercat.raft.init.barrel.BarrelLoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class EntityFishable extends Entity {

    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager
			.<Integer>createKey(EntityFishable.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager
			.<Integer>createKey(EntityFishable.class, DataSerializers.VARINT);
	private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.<Float>createKey(EntityFishable.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Boolean> CAN_DESPAWN = EntityDataManager.createKey(EntityFishable.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(EntityFishable.class,
			DataSerializers.VARINT);
	private static final DataParameter<Boolean> CUSTOM_WIND_ENABLED = EntityDataManager.createKey(EntityFishable.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Float> CUSTOM_WIND_X = EntityDataManager.createKey(EntityFishable.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Float> CUSTOM_WIND_Z = EntityDataManager.createKey(EntityFishable.class,
			DataSerializers.FLOAT);

	protected int age;

    public EntityFishable(World worldIn) {
        super(worldIn);
    }


    @Override
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

	public boolean getCanDespawn() {
		return this.dataManager.get(CAN_DESPAWN);
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
		return (this.dataManager.get(DAMAGE_TAKEN)).floatValue();
	}

    @Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Size", getSize());
		compound.setBoolean("CustomWindEnabled", getCustomWindEnabled());
		compound.setFloat("CustomWindX", getCustomWindX());
		compound.setFloat("CustomWindZ", getCustomWindZ());

		compound.setBoolean("CanDespawn", getCanDespawn());

	}

	/**
	 * Gets the time since the last hit.
	 */
	public int getTimeSinceHit() {
		return ((Integer) this.dataManager.get(TIME_SINCE_HIT)).intValue();
	}



	/**
	 * Gets the forward direction of the entity.
	 */
	public int getForwardDirection() {
		return ((Integer) this.dataManager.get(FORWARD_DIRECTION)).intValue();
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

	@SideOnly(Side.CLIENT)
	public void performHurtAnimation() {
		this.setForwardDirection(-this.getForwardDirection());
		this.setTimeSinceHit(10);
		this.setDamageTaken(this.getDamageTaken() * 11.0F);
	}

	/**
	 * Sets the time to count down from since the last time entity was hit.
	 */
	public void setTimeSinceHit(int timeSinceHit) {
		this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(timeSinceHit));
	}

	/**
	 * Sets the forward direction of the entity.
	 */
	public void setForwardDirection(int forwardDirection) {
		this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(forwardDirection));
	}


    public void extractItems(World world, BlockPos pos, EntityPlayer player) {

    }

    public void randomItemDrop(EntityPlayer player, Random rand, List<BarrelLoot> lootList) {
        for (int i = 0; i < MathHelper.getInt(world.rand, 3, 6); i++) {
            BarrelLoot returns = WeightedRandom.getRandomItem(world.rand, lootList);
            ItemStack itemStack = returns.returnItem.copy();
            if (!itemStack.isEmpty())
                player.inventory.addItemStackToInventory(itemStack);

        }
    }


	protected void dropItems(World world, BlockPos pos) {
		for (int i = 0; i < MathHelper.getInt(world.rand, 1, 1); i++) {
			BarrelLoot returns = WeightedRandom.getRandomItem(world.rand, REventHandler.thatch_loot);
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

    public void shouldDespawn(int radius) {
	    if (!getCanDespawn()) {
			this.age = 0;
		} else {
			Entity entity = this.world.getClosestPlayerToEntity(this, -0.1D);

			if (entity != null) {
				double d0 = entity.posX - this.posX;
				double d1 = entity.posY - this.posY;
				double d2 = entity.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;

				if (d3 > radius * radius)
					this.setDead();
			}

		}
    }
}
