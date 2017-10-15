package com.epiicthundercat.raft.item;

import java.util.Random;

import com.epiicthundercat.raft.ConfigHandler;
import com.epiicthundercat.raft.Reference;
import com.epiicthundercat.raft.client.model.ModelScuba;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCheapScuba extends RItemArmor {
	protected Random rand;

	public ItemCheapScuba(String name, ArmorMaterial material, int renderIndex, EntityEquipmentSlot armorType) {
		super(name, material, renderIndex, armorType);

	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (!player.isInsideOfMaterial(Material.WATER)) {
			player.setAir(ConfigHandler.raft.Air_On_Scuba);
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

					player.attackEntityFrom(DamageSource.DROWN, ConfigHandler.raft.Damage_If_Scuba_Air_Runs_Out);
				}
			}

		}

	}
	public static final String SCUBA = "raft:textures/models/armor/plastic_scuba.png";


	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot,
			ModelBiped _default) {
		return new ModelScuba();
	}
	
	@Override
	
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return SCUBA;		
	}
}
