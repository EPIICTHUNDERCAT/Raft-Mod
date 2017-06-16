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
	public ModelRenderer Neck;
	public ModelRenderer TopFin;
	public ModelRenderer Tail1Fin;
	public ModelRenderer Tail2;
	public ModelRenderer Tail1Fin2;
	public ModelRenderer Tail3;
	public ModelRenderer Tail2FinTop;
	public ModelRenderer Tail2Fin1;
	public ModelRenderer Tail2Fin2;
	public ModelRenderer Tail3Fin1;
	public ModelRenderer Tail3Fin2;
	public ModelRenderer MouthTop;
	public ModelRenderer NeckFin2;
	public ModelRenderer NeckFin1;
	public ModelRenderer MouthBottom;
	public ModelRenderer MouthLiner;

	public EntitySharkFemaleModel() {
		this.textureWidth = 64;
		this.textureHeight = 64;
		this.Tail2Fin2 = new ModelRenderer(this, 47, 13);
		this.Tail2Fin2.mirror = true;
		this.Tail2Fin2.setRotationPoint(1.5F, 0.0F, 4.1F);
		this.Tail2Fin2.addBox(0.0F, -0.6F, -3.0F, 3, 1, 3, 0.0F);
		this.setRotationAngles(Tail2Fin2, 0.0F, -0.7641051465231175F, 0.0F);
		this.NeckFin1 = new ModelRenderer(this, 20, 49);
		this.NeckFin1.mirror = true;
		this.NeckFin1.setRotationPoint(-3.0F, 1.6F, -3.0F);
		this.NeckFin1.addBox(-7.0F, -1.0F, -2.0F, 7, 1, 3, 0.0F);
		this.setRotationAngles(NeckFin1, 0.21223203704251048F, 0.08482300164692443F, -0.136659280431156F);
		this.TopFin = new ModelRenderer(this, 0, 0);
		this.TopFin.setRotationPoint(4.0F, 3.3F, 4.0F);
		this.TopFin.addBox(0.0F, -4.0F, 0.0F, 1, 4, 5, 0.0F);
		this.setRotationAngles(TopFin, 0.6988298324985296F, 0.0F, 0.0F);
		this.Tail2Fin1 = new ModelRenderer(this, 35, 13);
		this.Tail2Fin1.mirror = true;
		this.Tail2Fin1.setRotationPoint(-0.8F, -0.4F, 4.1F);
		this.Tail2Fin1.addBox(-3.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
		this.setRotationAngles(Tail2Fin1, 0.0F, -0.7641051465231175F, 0.0F);
		this.Tail2 = new ModelRenderer(this, 32, 25);
		this.Tail2.setRotationPoint(0.5F, -0.3F, 7.4F);
		this.Tail2.addBox(-3.0F, -3.3F, 0.0F, 7, 6, 7, 0.0F);
		this.Tail1Fin2 = new ModelRenderer(this, 0, 13);
		this.Tail1Fin2.mirror = true;
		this.Tail1Fin2.setRotationPoint(-0.7F, -0.8F, 4.0F);
		this.Tail1Fin2.addBox(-3.0F, 0.0F, 0.0F, 3, 1, 3, 0.0F);
		this.setRotationAngles(Tail1Fin2, 0.0F, -0.8491026810952413F, 0.0F);
		this.MouthBottom = new ModelRenderer(this, 35, 49);
		this.MouthBottom.setRotationPoint(0.4F, -0.1F, -5.5F);
		this.MouthBottom.addBox(-3.0F, -1.0F, -5.3F, 5, 2, 5, 0.0F);
		this.setRotationAngles(MouthBottom, 0.21223203704251048F, 0.0F, 0.0F);
		this.Tail3 = new ModelRenderer(this, 26, 38);
		this.Tail3.setRotationPoint(0.5F, 0.5F, 6.0F);
		this.Tail3.addBox(-3.0F, -3.4F, 0.0F, 6, 5, 6, 0.0F);
		this.Tail3Fin2 = new ModelRenderer(this, 0, 25);
		this.Tail3Fin2.setRotationPoint(-0.1F, -0.4F, 4.6F);
		this.Tail3Fin2.addBox(-0.6F, 0.0F, -1.7F, 1, 5, 3, 0.0F);
		this.setRotationAngles(Tail3Fin2, 0.551873109480607F, 0.0F, 0.0F);
		this.Tail1 = new ModelRenderer(this, 0, 25);
		this.Tail1.setRotationPoint(3.5F, 4.5F, 16.5F);
		this.Tail1.addBox(-3.0F, -4.0F, 0.0F, 8, 7, 8, 0.0F);
		this.NeckFin2 = new ModelRenderer(this, 0, 49);
		this.NeckFin2.setRotationPoint(3.0F, 1.6F, -3.0F);
		this.NeckFin2.addBox(0.0F, -0.6F, -1.6F, 7, 1, 3, 0.0F);
		this.setRotationAngles(NeckFin2, 0.21223203704251048F, -0.08656833089891874F, 0.21223203704251048F);
		this.Tail1Fin = new ModelRenderer(this, 0, 9);
		this.Tail1Fin.mirror = true;
		this.Tail1Fin.setRotationPoint(2.7F, -0.8F, 4.0F);
		this.Tail1Fin.addBox(0.0F, 0.0F, -3.0F, 3, 1, 3, 0.0F);
		this.setRotationAngles(Tail1Fin, 0.0F, -0.8491026810952413F, 0.0F);
		this.MouthTop = new ModelRenderer(this, 0, 40);
		this.MouthTop.setRotationPoint(0.6F, -0.6F, -6.0F);
		this.MouthTop.addBox(-4.0F, -3.0F, -6.0F, 7, 3, 6, 0.0F);
		this.Body = new ModelRenderer(this, 0, 0);
		this.Body.setRotationPoint(-4.0F, 16.0F, -8.0F);
		this.Body.addBox(0.0F, 0.0F, 0.0F, 9, 8, 17, 0.0F);
		this.MouthLiner = new ModelRenderer(this, 0, 53);
		this.MouthLiner.setRotationPoint(-1.8F, -0.5F, -1.3F);
		this.MouthLiner.addBox(-2.0F, -2.0F, -2.0F, 6, 3, 6, 0.0F);
		this.setRotationAngles(MouthLiner, -0.2972295716146343F, 0.0F, 0.0F);
		this.Tail2FinTop = new ModelRenderer(this, 9, 6);
		this.Tail2FinTop.setRotationPoint(0.4F, -1.4F, 3.8F);
		this.Tail2FinTop.addBox(-0.5F, -3.0F, -0.4F, 1, 3, 3, 0.0F);
		this.setRotationAngles(Tail2FinTop, 0.7766715171374766F, 0.0F, 0.0F);
		this.Neck = new ModelRenderer(this, 35, 0);
		this.Neck.setRotationPoint(4.4F, 4.5F, 0.4F);
		this.Neck.addBox(-4.0F, -4.0F, -6.0F, 8, 7, 6, 0.0F);
		this.Tail3Fin1 = new ModelRenderer(this, 53, 17);
		this.Tail3Fin1.setRotationPoint(-0.7F, -2.0F, 2.7F);
		this.Tail3Fin1.addBox(0.0F, -9.0F, 0.0F, 1, 9, 3, 0.0F);
		this.setRotationAngles(Tail3Fin1, -0.33161255787892263F, 0.0F, 0.0F);
		this.Tail2.addChild(this.Tail2Fin2);
		this.Neck.addChild(this.NeckFin1);
		this.Body.addChild(this.TopFin);
		this.Tail2.addChild(this.Tail2Fin1);
		this.Tail1.addChild(this.Tail2);
		this.Tail1.addChild(this.Tail1Fin2);
		this.Neck.addChild(this.MouthBottom);
		this.Tail2.addChild(this.Tail3);
		this.Tail3.addChild(this.Tail3Fin2);
		this.Body.addChild(this.Tail1);
		this.Neck.addChild(this.NeckFin2);
		this.Tail1.addChild(this.Tail1Fin);
		this.Neck.addChild(this.MouthTop);
		this.MouthTop.addChild(this.MouthLiner);
		this.Tail2.addChild(this.Tail2FinTop);
		this.Body.addChild(this.Neck);
		this.Tail3.addChild(this.Tail3Fin1);
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

	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entityIn) {
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entityIn);

		EntitySharkFemale entitySharkFemale = (EntitySharkFemale) entityIn;

		Entity entity = Minecraft.getMinecraft().getRenderViewEntity();

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

		//f = entity.ticksExisted;
		//f1 = 0.5f;
		float globalSpeed = 1.0f;
		float globalDegree = 1.0f;
		float globalHeight = 1.0f;

		swing(Body, 0.3f * globalSpeed, 0.3f * globalDegree, 0.0f, 0.0f, f, f1);
		swing(Neck, 0.4f * globalSpeed, 0.1f * globalDegree, 0f, 0.0f, f, f1);
		walk(MouthBottom, 0.4f * globalSpeed, 0.9f * globalDegree, false, -5.0f, 1.0f, f, f1);
		walk(MouthTop, 0.3f * globalSpeed, 0.9f * globalDegree, false, 3.0f, 0.0f, f, f1);
		flap(NeckFin1, 0.5f * globalSpeed, 0.9f * globalDegree, false, 5f, 0f, f, f1);
		flap(NeckFin2, 0.5f * globalSpeed, 0.9f * globalDegree, true, 5f, 0f, f, f1);

		swing(Tail2FinTop, 0.7f * globalSpeed, 0.5f * globalDegree, 0f, 0f, f, f1);
		swing(Tail3Fin2, 0.7f * globalSpeed, 1.5f * globalDegree, 0f, 0f, f, f1);
		swing(Tail3Fin1, 0.7f * globalSpeed, 1.5f * globalDegree, 0f, 0f, f, f1);

		swing(Tail1, 0.5f * globalSpeed, 0.4f * globalDegree, 0f, 0.0f, f, f1);

		swing(Tail2, 0.5f * globalSpeed, 0.5f * globalDegree, 0f, 0.0f, f, f1);
		swing(Tail3, 0.5f * globalSpeed, 0.6f * globalDegree, 0f, 0.0f, f, f1);

	}

	public void swing(ModelRenderer box, float speed, float degree, float offset, float weight, float f, float f1) {
		box.rotateAngleY = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
	}

	public void flap(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float f,
			float f1) {

		box.rotateAngleZ = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
	}

	public void bob(ModelRenderer box, float speed, float degree, float offset, float weight, float f, float f1) {
		box.rotationPointY = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
	}

	public void walk(ModelRenderer box, float speed, float degree, boolean invert, float offset, float weight, float f,
			float f1) {

		box.rotateAngleX = degree * f1 * MathHelper.cos(speed * f + offset) + weight * f1;
	}

}
