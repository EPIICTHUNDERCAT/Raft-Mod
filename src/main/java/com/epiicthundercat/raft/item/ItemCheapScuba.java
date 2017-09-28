package com.epiicthundercat.raft.item;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class ItemCheapScuba extends RItemArmor {
	protected Random rand;

	public ItemCheapScuba(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
		super(name, material, renderIndex, armorType);

	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (!player.isInsideOfMaterial(Material.WATER)) {
			player.setAir(900);
		} else {
			if (!player.canBreatheUnderwater()) {
				player.setAir((player.getAir()));

				if (player.getAir() == -20) {
					player.setAir(0);

					for (int i = 0; i < 8; ++i) {
						float f2 = this.rand.nextFloat() - this.rand.nextFloat();
						float f = this.rand.nextFloat() - this.rand.nextFloat();
						float f1 = this.rand.nextFloat() - this.rand.nextFloat();
						world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, player.posX + (double) f2,
								player.posY + (double) f, player.posZ + (double) f1, player.motionX, player.motionY,
								player.motionZ, new int[0]);
					}

					player.attackEntityFrom(DamageSource.DROWN, 2.0F);
				}
			}

		}

	}
}
