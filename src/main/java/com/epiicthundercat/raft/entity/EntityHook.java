package com.epiicthundercat.raft.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityHook extends EntityThrowable {

	public static boolean entity;
	public static boolean player;
	public static boolean block;
	private EntityLivingBase shootingEntity;
	public FloatBarrel barrel;
	public PlankEntity plank;
	public ThatchEntity thatch;
	public ScrapEntity scrap;

	public DamageSource sourceDamage;
	BlockPos posB;
	EntityPlayer playerIn;

	public EntityHook(World worldIn) {
		super(worldIn);

	}

	public EntityHook(World worldIn, EntityLivingBase shooter) {
		this(worldIn, shooter.posX, shooter.posY + (double) shooter.getEyeHeight() - 0.10000000149011612D,
				shooter.posZ);
		this.shootingEntity = shooter;
	}

	public EntityHook(World worldIn, DamageSource source, BlockPos pos, EntityPlayer player, FloatBarrel barrel1,
			PlankEntity plank1, ThatchEntity thatch1, ScrapEntity scrap1) {
		super(worldIn);
		sourceDamage = source;
		posB = pos;
		playerIn = player;
		barrel = barrel1;
		plank = plank1;
		thatch = thatch1;
		scrap = scrap1;

	}

	@SideOnly(Side.CLIENT)
	public EntityHook(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		World world = this.world;
		int x = this.ticksExisted;
		if ((this.ticksExisted % 2) == 0) {
			world.spawnParticle(EnumParticleTypes.DRIP_WATER, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.3);
		}

	}

	protected float getGravityVelocity() {
		return 0.0F;

	}

	// protected EntityLightningBolt Bolt = new EntityLightningBolt(World,
	// lastTickPosX, lastTickPosX, lastTickPosX, inGround);

	@Override
	protected void onImpact(RayTraceResult result) {
		// getEntityWorld().addWeatherEffect(new
		// EntityLightningBolt(getEntityWorld(),posX,posY,posZ,false));

		if (result.entityHit != null && result.entityHit != this.shootingEntity) {
			result.entityHit.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) this.shootingEntity),
					10.0F);
			
			  if(sourceDamage.getEntity() instanceof EntityPlayer &&
			  ((EntityPlayer)
			  sourceDamage.getEntity()).capabilities.isCreativeMode){
			  barrel.extractItems(world, posB, playerIn);
			  thatch.extractItems(world, posB, playerIn);
			  scrap.extractItems(world, posB, playerIn);
			  plank.extractItems(world, posB, playerIn);
			  
			  }
			 
			/*if (result.entityHit != null && result.entityHit != this.shootingEntity) {
				if (result.entityHit instanceof FloatBarrel) {
					barrel.extractItems(world, posB, playerIn);
				} else if (result.entityHit instanceof ThatchEntity) {
					thatch.extractItems(world, posB, playerIn);
				} else if (result.entityHit instanceof ScrapEntity) {
					scrap.extractItems(world, posB, playerIn);
				} else if (result.entityHit instanceof PlankEntity) {
					plank.extractItems(world, posB, playerIn);
				}*/
//			}

			for (int j = 0; j < 4; ++j) {
				this.world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D,
						0.0D);
			}
		}
		if (!this.world.isRemote) {
			this.setDead();
		}
	}
}