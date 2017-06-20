package com.epiicthundercat.raft.block;

import java.util.Random;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RBlocks;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockPalmWoodSlab extends BlockSlab{

	
	 public BlockPalmWoodSlab(String name, Material material)
	    {
	        super(material);
	        IBlockState iblockstate = this.blockState.getBaseState();

	        if (!this.isDouble())
	        {
	            iblockstate = iblockstate.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
	        }

	        
	        this.setCreativeTab(RCreativeTab.RTabs);
	    }

	   

	    /**
	     * Get the Item that this Block should drop when harvested.
	     */
	    public Item getItemDropped(IBlockState state, Random rand, int fortune)
	    {
	        return Item.getItemFromBlock(RBlocks.palm_slab);
	    }

	    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	    {
	        return new ItemStack(RBlocks.palm_slab, 1);
	    }


		@Override
		public boolean isDouble() {
			// TODO Auto-generated method stub
			return true;
		}



		@Override
		public IProperty<?> getVariantProperty() {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public String getUnlocalizedName(int meta) {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public Comparable<?> getTypeForItem(ItemStack stack) {
			// TODO Auto-generated method stub
			return null;
		}

	  
	  

	   
	}

