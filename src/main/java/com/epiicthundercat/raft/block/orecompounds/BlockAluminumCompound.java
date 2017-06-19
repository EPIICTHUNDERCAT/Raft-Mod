package com.epiicthundercat.raft.block.orecompounds;

import static net.minecraft.util.EnumFacing.WEST;

import java.util.Random;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.block.RBlock;
import com.epiicthundercat.raft.init.RItems;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAluminumCompound extends RBlock implements IPlantable {
    public static final EnumPlantType ALUMINUM = EnumPlantType.getPlantType("Aluminum");
   protected static final AxisAlignedBB ALUMINUM_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);

    public BlockAluminumCompound(String name, Material material) {
        super(name, material);
        setTickRandomly(false);
        setHardness(0.5F);
        setSoundType(SoundType.METAL);
         }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    }

   

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this);
    }

 

    

    

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return ALUMINUM_AABB;
    }

    
  
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
    {
    	this.spawnAsEntity(worldIn, pos, new ItemStack(RItems.aluminum_compound));
    	  worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
   

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos.down());
        Block block = state.getBlock();

      //  if (worldIn.getBlockState(pos.up(2)).getMaterial() != Material.WATER) return false;
        if (block.canSustainPlant(state, worldIn, pos.down(), EnumFacing.UP, this)) return true;
        if (block == this) {
           
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        if (world.getBlockState(pos.up()).getMaterial() == Material.WATER) {
            double offset = 0.0625D;
            for (int i = 0; i < 6; i++) {
                double x1 = (pos.getX() + rand.nextDouble());
                double y1 = (pos.getY() + rand.nextDouble());
                double z1 = (pos.getZ() + rand.nextDouble());
                if (i == 0 && !world.getBlockState(pos.up()).isOpaqueCube()) {
                    y1 = (double) (pos.getY() + 1) + offset;
                }

                if (i == 1 && !world.getBlockState(pos.down()).isOpaqueCube()) {
                    y1 = (double) (pos.getY() + 0) - offset;
                }

                if (i == 2 && !world.getBlockState(pos.offset(EnumFacing.SOUTH)).isOpaqueCube()) {
                    z1 = (double) (pos.getZ() + 1) + offset;
                }

                if (i == 3 && !world.getBlockState(pos.offset(EnumFacing.NORTH)).isOpaqueCube()) {
                    z1 = (double) (pos.getZ() + 0) - offset;
                }

                if (i == 4 && !world.getBlockState(pos.offset(EnumFacing.EAST)).isOpaqueCube()) {
                    x1 = (double) (pos.getX() + 1) + offset;
                }

                if (i == 5 && !world.getBlockState(pos.offset(WEST)).isOpaqueCube()) {
                    x1 = (double) (pos.getX() + 0) - offset;
                }

                if (x1 < (double) pos.getX() || x1 > (double) (pos.getX() + 1) || y1 < 0.0D || y1 > (double) (pos.getY() + 1) || z1 < (double) pos.getZ() || z1 > (double) (pos.getZ() + 1)) {
                    world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, x1, y1, z1, 0, 0, 0);
                }
            }
        }
    }


    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        return canPlaceBlockAt(world, pos);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return 16;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return ALUMINUM;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos) {
        return world.getBlockState(pos);
    }

}
