package com.epiicthundercat.raft.entity.ai;

import java.util.Random;

import com.epiicthundercat.raft.entity.passive.EntitySeagull;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

/**
 * 
 * @author Pavocado
 *         https://minecraft.curseforge.com/projects/exotic-birds?gameCategorySlug=mc-mods&projectID=242830
 *
 */
public class SpecialAI extends EntityAIBase {
	private EntitySeagull entity;
	private int flightHeight;

	public SpecialAI(EntitySeagull creature) {
		this(creature, 5);
	}

	public SpecialAI(EntitySeagull creature, int height) {
		this.entity = creature;
		this.flightHeight = height;
		setMutexBits(1);
	}

	public boolean shouldExecute() {
		if (!this.entity.getCanFly()) {
			return false;
		}
		EntityMoveHelper entitymovehelper = this.entity.getMoveHelper();
		if (!entitymovehelper.isUpdating()) {
			return true;
		}
		double d0 = entitymovehelper.getX() - this.entity.posX;
		double d1 = entitymovehelper.getY() - this.entity.posY;
		double d2 = entitymovehelper.getZ() - this.entity.posZ;
		double d3 = d0 * d0 + d1 * d1 + d2 * d2;
		return (d3 < 1.0D) || (d3 > 3600.0D);
	}

	public void updateTask() {
		super.updateTask();
	}

	public boolean continueExecuting() {
		return false;
	}

	public void startExecuting() {
		if (this.entity.getCanFly()) {
			Random random = this.entity.getRNG();
			double d0 = this.entity.posX + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d1 = this.entity.posY + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			double d2 = this.entity.posZ + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
			d1 = getTopBlock(new BlockPos(d0, d1, d2)).getY() + this.flightHeight + random.nextInt(this.flightHeight);

			this.entity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
		}
	}

	public BlockPos getTopBlock(BlockPos pos) {
		Chunk chunk = this.entity.world.getChunkFromBlockCoords(pos);
		BlockPos blockpos2;
		for (BlockPos blockpos1 = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos1
				.getY() >= 0; blockpos1 = blockpos2) {
			blockpos2 = blockpos1.down();
			Block block = chunk.getBlockState(blockpos2).getBlock();
			if ((!block.isFoliage(this.entity.world, blockpos2))
					&& (!block.isAir(chunk.getBlockState(blockpos2), this.entity.world, blockpos2))) {
				break;
			}

		}

	
		return pos;

	}
}
