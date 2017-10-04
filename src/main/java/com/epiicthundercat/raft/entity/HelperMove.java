package com.epiicthundercat.raft.entity;

import com.epiicthundercat.raft.entity.passive.EntitySeagull;

import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class HelperMove extends EntityMoveHelper
{
	  private EntitySeagull birdEntity;
	  private int stepCount;
	  
	  public HelperMove(EntitySeagull entity)
	  {
	    super(entity);
	    this.birdEntity = entity;
	  }
	  
	  public void onUpdateMoveHelper()
	  {
	    if (!this.birdEntity.getCanFly())
	    {
	      super.onUpdateMoveHelper();
	    }
	    else if (this.action == EntityMoveHelper.Action.MOVE_TO)
	    {
	      double d0 = this.posX - this.birdEntity.posX;
	      double d1 = this.posY - this.birdEntity.posY;
	      double d2 = this.posZ - this.birdEntity.posZ;
	      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
	      
	      this.entity.getLookHelper().setLookPosition(d0, d1, d2, 10.0F, this.entity.getVerticalFaceSpeed());
	      if (this.stepCount-- <= 0)
	      {
	        this.stepCount += this.birdEntity.getRNG().nextInt(5) + 2;
	        d3 = MathHelper.sqrt(d3);
	        if (canMove(this.posX, this.posY, this.posZ, d3))
	        {
	          this.birdEntity.motionX += d0 / d3 * 0.3D;
	          this.birdEntity.motionY += d1 / d3 * 0.2D;
	          this.birdEntity.motionZ += d2 / d3 * 0.3D;
	        }
	        else
	        {
	          this.action = EntityMoveHelper.Action.WAIT;
	        }
	      }
	    }
	    if ((this.birdEntity.getCanFly()) && (this.entity.world.rand.nextInt(50) == 0)) {}
	  }
	  
	  private boolean canMove(double varX, double varY, double varZ, double distance)
	  {
	    double d4 = (varX - this.birdEntity.posX) / distance;
	    double d5 = (varY - this.birdEntity.posY) / distance;
	    double d6 = (varZ - this.birdEntity.posZ) / distance;
	    AxisAlignedBB axisalignedbb = this.birdEntity.getEntityBoundingBox();
	    for (int i = 1; i < distance; i++)
	    {
	      axisalignedbb = axisalignedbb.offset(d4, d5, d6);
	      if (!this.birdEntity.world.getCollisionBoxes(this.birdEntity, axisalignedbb).isEmpty()) {
	        return false;
	      }
	    }
	    return true;
	  }
	}
