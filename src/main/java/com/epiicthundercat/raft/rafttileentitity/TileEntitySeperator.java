package com.epiicthundercat.raft.rafttileentitity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntitySeperator extends TileEntity implements ITickable {

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	    {
	        return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	    {
	        super.readFromNBT(compound);
	}
}