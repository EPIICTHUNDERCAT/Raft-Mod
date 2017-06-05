package com.epiicthundercat.raft.item;

import java.util.List;

import javax.annotation.Nullable;

import com.epiicthundercat.raft.creativetab.RCreativeTab;
import com.epiicthundercat.raft.entity.EntityHook;
import com.epiicthundercat.raft.entity.FloatBarrel;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHook extends RItem {

	public ItemHook(String name) {
		super(name);
		this.setCreativeTab(RCreativeTab.RTabs);
		this.addPropertyOverride(new ResourceLocation("cast"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return entityIn == null ? 0.0F
						: (entityIn.getHeldItemMainhand() == stack && entityIn instanceof EntityPlayer
								&& ((EntityPlayer) entityIn).fishEntity != null ? 1.0F : 0.0F);
			}
		});
	}

	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	/**
	 * Returns true if this item should be rotated by 180 degrees around the Y
	 * axis when being held in an entities hands.
	 */
	@SideOnly(Side.CLIENT)
	public boolean shouldRotateAroundWhenRendering() {
		return true;
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack itemStackIn = playerIn.getHeldItem(handIn);
		FloatBarrel barrel = new FloatBarrel(worldIn);
		BlockPos pos = playerIn.getPosition();
		if (playerIn.fishEntity != null) {
			int i = playerIn.fishEntity.handleHookRetraction();
			itemStackIn.damageItem(i, playerIn);
			playerIn.swingArm(handIn);
		} else {
			worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
					SoundEvents.ENTITY_BOBBER_THROW, SoundCategory.NEUTRAL, 0.5F,
					0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if (!worldIn.isRemote) {
				worldIn.spawnEntity(new EntityHook(worldIn, playerIn));
			}

			playerIn.swingArm(handIn);
			playerIn.addStat(StatList.getObjectUseStats(this));
		}
		if (worldIn.isRemote) {
			RayTraceResult result = getBlockPlayerLookingAt(playerIn, Minecraft.getMinecraft().getRenderPartialTicks());
			if (result != null) {
				barrel.extractItems(worldIn, pos, playerIn);
				
			}
		}
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based
	 * on material.
	 */
	public int getItemEnchantability() {
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

		tooltip.add(String.format("Item Used To Hook Floating Trash"));

		super.addInformation(stack, playerIn, tooltip, advanced);
	}

	@Nullable
	private RayTraceResult getBlockPlayerLookingAt(EntityPlayer player, float partialTicks) {
		RayTraceResult result = player.rayTrace(35, partialTicks);

		if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY)
			return null;
		return result;
	}
}
