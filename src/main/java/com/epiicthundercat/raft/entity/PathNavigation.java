package com.epiicthundercat.raft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.SwimNodeProcessor;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PathNavigation extends PathNavigateSwimmer {
	public PathNavigation(EntityLiving entitylivingIn, World worldIn) {
		super(entitylivingIn, worldIn);
	}

	protected PathFinder getPathFinder() {
		return new PathFinder(new SwimNodeProcessor());
	}

	protected void pathFollow() {
		Vec3d entityPos = getEntityPosition();
		double maxDistance = this.theEntity.width * 2.0F;
		if (entityPos.squareDistanceTo(this.currentPath.getVectorFromIndex(this.theEntity,
				this.currentPath.getCurrentPathIndex())) < maxDistance) {
			this.currentPath.incrementPathIndex();
		}
		for (int i = Math.min(this.currentPath.getCurrentPathIndex() + 6,
				this.currentPath.getCurrentPathLength() - 1); i > this.currentPath.getCurrentPathIndex(); i--) {
			Vec3d pathPos = this.currentPath.getVectorFromIndex(this.theEntity, i);
			if ((pathPos.squareDistanceTo(entityPos) <= 36.0D)
					&& (isDirectPathBetweenPoints(entityPos, pathPos, 0, 0, 0))) {
				this.currentPath.setCurrentPathIndex(i);
				break;
			}
		}
		checkForStuck(entityPos);
	}
}
