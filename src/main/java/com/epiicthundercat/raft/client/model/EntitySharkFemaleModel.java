package com.epiicthundercat.raft.client.model;

import com.epiicthundercat.raft.entity.monster.EntitySharkFemale;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntitySharkFemaleModel extends ModelBase {
	public ModelRenderer Body;
	public ModelRenderer Tail1;
	public ModelRenderer Tail1Fin;
	public ModelRenderer Tail2;
	public ModelRenderer Tail3;
	public ModelRenderer Tail3Fin1;
	public ModelRenderer Tail3Fin2;
	public ModelRenderer Tail2FinTop;
	public ModelRenderer Tail2Fin1;
	public ModelRenderer Tail2Fin2;
	public ModelRenderer Tail1Fin2;
	public ModelRenderer Neck;
	public ModelRenderer MouthTop;
	public ModelRenderer MouthLiner;
	public ModelRenderer NeckFin2;
	public ModelRenderer NeckFin1;
	public ModelRenderer MouthBottom;
	public ModelRenderer TopFin;

	public EntitySharkFemaleModel() {
		this.textureWidth = 64;
		this.textureHeight = 64;

		this.Body = new ModelRenderer(this, 12, 47);
		this.Body.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.Body.addBox(0.0F, 0.0F, 0.0F, 17, 8, 9);
		this.Tail1 = new ModelRenderer(this, 32, 19);
		this.Tail1.setRotationPoint(1.5F, 4.5F, 3.5F);
		this.Tail1.addBox(-7.0F, -4.0F, -3.0F, 8, 7, 8);
		this.Body.addChild(this.Tail1);
		this.Tail1Fin = new ModelRenderer(this, 17, 31);
		this.Tail1Fin.setRotationPoint(-4.0F, 0.0F, -5.0F);
		this.Tail1Fin.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
		this.Tail1Fin.mirror = true;
		this.setRotationAngles(this.Tail1Fin, 0.0F, -0.8491027077268521F, 0.0F);
		this.Tail1.addChild(this.Tail1Fin);
		this.Tail2 = new ModelRenderer(this, 36, 0);
		this.Tail2.setRotationPoint(-7.0F, -0.3F, 1.4F);
		this.Tail2.addBox(-6.0F, -3.3F, -4.0F, 7, 6, 7);
		this.Tail1.addChild(this.Tail2);
		this.Tail3 = new ModelRenderer(this, 12, 0);
		this.Tail3.setRotationPoint(-7.0F, 0.5F, 0.0F);
		this.Tail3.addBox(-5.0F, -3.4F, -3.4F, 6, 5, 6);
		this.Tail2.addChild(this.Tail3);
		this.Tail3Fin1 = new ModelRenderer(this, 0, 10);
		this.Tail3Fin1.setRotationPoint(-5.0F, -2.0F, -0.9F);
		this.Tail3Fin1.addBox(0.0F, -9.0F, 0.0F, 3, 9, 1);
		this.setRotationAngles(this.Tail3Fin1, 0.0F, 0.0F, -0.33964105645913F);
		this.Tail3.addChild(this.Tail3Fin1);
		this.Tail3Fin2 = new ModelRenderer(this, 21, 15);
		this.Tail3Fin2.setRotationPoint(-5.0F, -0.4F, -0.8F);
		this.Tail3Fin2.addBox(0.0F, 0.0F, 0.0F, 3, 5, 1);
		this.setRotationAngles(this.Tail3Fin2, 0.0F, 0.0F, 0.5518731241279929F);
		this.Tail3.addChild(this.Tail3Fin2);
		this.Tail2FinTop = new ModelRenderer(this, 31, 17);
		this.Tail2FinTop.setRotationPoint(-5.0F, -3.5F, 0.0F);
		this.Tail2FinTop.addBox(-0.3F, 0.0F, -0.6F, 3, 3, 1);
		this.setRotationAngles(this.Tail2FinTop, 0.0F, 0.0F, -0.7641051252178287F);
		this.Tail2.addChild(this.Tail2FinTop);
		this.Tail2Fin1 = new ModelRenderer(this, 17, 31);
		this.Tail2Fin1.setRotationPoint(-3.0F, 0.0F, 0.4F);
		this.Tail2Fin1.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
		this.Tail2Fin1.mirror = true;
		this.setRotationAngles(this.Tail2Fin1, 0.0F, -0.7641051252178287F, 0.0F);
		this.Tail2.addChild(this.Tail2Fin1);
		this.Tail2Fin2 = new ModelRenderer(this, 17, 31);
		this.Tail2Fin2.setRotationPoint(-3.0F, 0.0F, -5.4F);
		this.Tail2Fin2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
		this.Tail2Fin2.mirror = true;
		this.setRotationAngles(this.Tail2Fin2, 0.0F, -0.7641051252178287F, 0.0F);
		this.Tail2.addChild(this.Tail2Fin2);
		this.Tail1Fin2 = new ModelRenderer(this, 17, 31);
		this.Tail1Fin2.setRotationPoint(-4.0F, 0.0F, 2.8F);
		this.Tail1Fin2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 3);
		this.Tail1Fin2.mirror = true;
		this.setRotationAngles(this.Tail1Fin2, 0.0F, -0.8491027077268521F, 0.0F);
		this.Tail1.addChild(this.Tail1Fin2);
		this.Neck = new ModelRenderer(this, 0, 14);
		this.Neck.setRotationPoint(17.0F, 4.5F, 4.4F);
		this.Neck.addBox(-1.0F, -4.0F, -4.0F, 6, 7, 8);
		this.Body.addChild(this.Neck);
		this.MouthTop = new ModelRenderer(this, 38, 36);
		this.MouthTop.setRotationPoint(3.1F, -0.6F, 0.4F);
		this.MouthTop.addBox(1.0F, -3.0F, -4.0F, 6, 3, 7);
		this.setRotationAngles(this.MouthTop, 0.0F, 0.0F, 0.04241150198859518F);
		this.Neck.addChild(this.MouthTop);
		this.MouthLiner = new ModelRenderer(this, 2, 38);
		this.MouthLiner.setRotationPoint(0.2F, 0.2F, -1.3F);
		this.MouthLiner.addBox(-2.0F, -2.0F, -2.0F, 6, 3, 6);
		this.setRotationAngles(this.MouthLiner, 0.0F, 0.0F, -0.2972295835988592F);
		this.MouthTop.addChild(this.MouthLiner);
		this.NeckFin2 = new ModelRenderer(this, 0, 30);
		this.NeckFin2.setRotationPoint(1.0F, 1.0F, 3.6F);
		this.NeckFin2.addBox(0.0F, 0.0F, 0.0F, 3, 1, 7);
		this.setRotationAngles(this.NeckFin2, -0.21223203437934937F, -0.08482300397719036F, 0.0F);
		this.Neck.addChild(this.NeckFin2);
		this.NeckFin1 = new ModelRenderer(this, 0, 30);
		this.NeckFin1.setRotationPoint(1.0F, 1.0F, -4.0F);
		this.NeckFin1.addBox(0.0F, 0.0F, -6.0F, 3, 1, 7);
		this.NeckFin1.mirror = true;
		this.setRotationAngles(this.NeckFin1, 0.21223203437934937F, 0.08482300397719036F, 0.0F);
		this.Neck.addChild(this.NeckFin1);
		this.MouthBottom = new ModelRenderer(this, 0, 48);
		this.MouthBottom.setRotationPoint(5.0F, 0.9F, -0.1F);
		this.MouthBottom.addBox(0.0F, -1.0F, -2.3F, 5, 2, 5);
		this.setRotationAngles(this.MouthBottom, 0.0F, 0.0F, 0.21223203437934937F);
		this.Neck.addChild(this.MouthBottom);
		this.TopFin = new ModelRenderer(this, 1, 1);
		this.TopFin.setRotationPoint(9.0F, 2.8F, 4.0F);
		this.TopFin.addBox(0.0F, 0.0F, 0.0F, 5, 4, 1);
		this.setRotationAngles(this.TopFin, 0.0F, 0.0F, -2.3349014520127254F);
		this.Body.addChild(this.TopFin);
	}

	public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw,
			float rotationPitch, float scale) {

		this.Body.render(scale);

	}

	public void setRotationAngles(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;

	}

	public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch, float scaleFactor, Entity entityIn) {
		EntitySharkFemale entitySharkFemale = (EntitySharkFemale) entityIn;
		float f = ageInTicks - (float) entitySharkFemale.ticksExisted;
		this.Body.rotateAngleY = netHeadYaw * 0.017453292F;
		this.Body.rotateAngleX = headPitch * 0.017453292F;
		float[] afloat = new float[] { 1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F };
		float[] afloat1 = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F };
		float[] afloat2 = new float[] { 0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F };
		float[] afloat3 = new float[] { 0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F };
		float[] afloat4 = new float[] { -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F };
		float[] afloat5 = new float[] { 8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F };

		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();

		if (entitySharkFemale.hasTargetedEntity()) {
			entity = entitySharkFemale.getTargetedEntity();
		}

		if (entity != null) {
			Vec3d vec3d = entity.getPositionEyes(0.0F);
			Vec3d vec3d1 = entityIn.getPositionEyes(0.0F);
			double d0 = vec3d.yCoord - vec3d1.yCoord;

			Vec3d vec3d2 = entityIn.getLook(0.0F);
			vec3d2 = new Vec3d(vec3d2.xCoord, 0.0D, vec3d2.zCoord);
			Vec3d vec3d3 = (new Vec3d(vec3d1.xCoord - vec3d.xCoord, 0.0D, vec3d1.zCoord - vec3d.zCoord)).normalize()
					.rotateYaw(((float) Math.PI / 2F));
			double d1 = vec3d2.dotProduct(vec3d3);

		}

		float f2 = EntitySharkFemale.getTailAnimation(f);
		this.Tail1.rotateAngleY = MathHelper.sin(f2) * (float) Math.PI * 0.05F;
		this.Tail2.rotateAngleY = MathHelper.sin(f2) * (float) Math.PI * 0.1F;
		this.Tail2.rotationPointX = -1.5F;
		this.Tail2.rotationPointY = 0.5F;
		this.Tail2.rotationPointZ = 14.0F;
		this.Tail3.rotateAngleY = MathHelper.sin(f2) * (float) Math.PI * 0.15F;
		this.Tail3.rotationPointX = 0.5F;
		this.Tail3.rotationPointY = 0.5F;
		this.Tail3.rotationPointZ = 6.0F;
	}
}
