package com.epiicthundercat.raft.item;

import java.util.List;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.init.RItems;
import com.google.common.base.Predicate;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ItemTinCan extends RItem {
	public ItemTinCan(String name) {
		super(name);
		this.setCreativeTab(RCreativeTab.RTabs);
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		List<EntityAreaEffectCloud> list = worldIn.<EntityAreaEffectCloud>getEntitiesWithinAABB(
				EntityAreaEffectCloud.class, playerIn.getEntityBoundingBox().expandXyz(2.0D),
				new Predicate<EntityAreaEffectCloud>() {
					public boolean apply(@Nullable EntityAreaEffectCloud p_apply_1_) {
						return p_apply_1_ != null && p_apply_1_.isEntityAlive()
								&& p_apply_1_.getOwner() instanceof EntityDragon;
					}
				});
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if (!list.isEmpty()) {
			EntityAreaEffectCloud entityareaeffectcloud = (EntityAreaEffectCloud) list.get(0);
			entityareaeffectcloud.setRadius(entityareaeffectcloud.getRadius() - 0.5F);
			worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
					SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			return new ActionResult(EnumActionResult.SUCCESS,
					this.turnTinCanIntoItem(itemstack, playerIn, new ItemStack(Items.DRAGON_BREATH)));
		}

		else {
			RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

			if (raytraceresult == null) {
				return new ActionResult(EnumActionResult.PASS, itemstack);
			} else {
				if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK) {
					BlockPos blockpos = raytraceresult.getBlockPos();

					if (!worldIn.isBlockModifiable(playerIn, blockpos)
							|| !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit,
									itemstack)) {
						return new ActionResult(EnumActionResult.PASS, itemstack);
					}

					if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER) {
						worldIn.playSound(playerIn, playerIn.posX, playerIn.posY, playerIn.posZ,
								SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
						return new ActionResult(EnumActionResult.SUCCESS,
								this.turnTinCanIntoItem(itemstack, playerIn, new ItemStack(RItems.tin_can_dirty)));
					}
				}

				return new ActionResult(EnumActionResult.PASS, itemstack);
			}
		}
	}

	protected ItemStack turnTinCanIntoItem(ItemStack stack1, EntityPlayer player, ItemStack stack) {
		stack1.shrink(1);
		;
		player.addStat(StatList.getObjectUseStats(this));

		if (stack1.getCount() <= 0) {
			return stack;
		} else {
			if (!player.inventory.addItemStackToInventory(stack)) {
				player.dropItem(stack, false);
			}

			return stack1;
		}
	}
}