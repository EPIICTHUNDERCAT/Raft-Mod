package com.epiicthundercat.raft.entity;

public class EntityTinCanPotion {
}/*
	 * extends EntityThrowable { private static final
	 * DataParameter<Optional<ItemStack>> ITEM = EntityDataManager
	 * .<Optional<ItemStack>>createKey(EntityTinCanPotion.class,
	 * DataSerializers.OPTIONAL_ITEM_STACK); private static final Logger LOGGER
	 * = LogManager.getLogger();
	 * 
	 * public EntityTinCanPotion(World worldIn) { super(worldIn); }
	 * 
	 * public EntityTinCanPotion(World worldIn, EntityLivingBase throwerIn,
	 * ItemStack potionDamageIn) { super(worldIn, throwerIn);
	 * this.setItem(potionDamageIn); }
	 * 
	 * public EntityTinCanPotion(World worldIn, double x, double y, double
	 * z, @Nullable ItemStack potionDamageIn) { super(worldIn, x, y, z);
	 * 
	 * if (potionDamageIn != null) { this.setItem(potionDamageIn); } }
	 * 
	 * protected void entityInit() { this.getDataManager().register(ITEM,
	 * Optional.<ItemStack>absent()); }
	 * 
	 * public ItemStack getPotion() { ItemStack itemstack = (ItemStack)
	 * ((Optional) this.getDataManager().get(ITEM)).orNull();
	 * 
	 * if (itemstack == null || itemstack.getItem() !=
	 * RItems.tin_can_splash_potion && itemstack.getItem() !=
	 * RItems.tin_can_lingering_potion) { if (this.worldObj != null) {
	 * LOGGER.error("ThrownTinPotion entity {} has no item?!", new Object[] {
	 * Integer.valueOf(this.getEntityId()) }); }
	 * 
	 * return new ItemStack(RItems.tin_can_splash_potion); } else { return
	 * itemstack; } }
	 * 
	 * public void setItem(@Nullable ItemStack stack) {
	 * this.getDataManager().set(ITEM, Optional.fromNullable(stack));
	 * this.getDataManager().setDirty(ITEM); }
	 * 
	 * /** Gets the amount of gravity to apply to the thrown entity with each
	 * tick.
	 */
/*
 * protected float getGravityVelocity() { return 0.05F; }
 * 
 * /** Called when this EntityThrowable hits a block or entity.
 */
/*
 * protected void onImpact(RayTraceResult result) { if (!this.worldObj.isRemote)
 * { ItemStack itemstack = this.getPotion(); PotionType potiontype =
 * PotionUtils.getPotionFromItem(itemstack); List<PotionEffect> list =
 * PotionUtils.getEffectsFromStack(itemstack);
 * 
 * if (result.typeOfHit == RayTraceResult.Type.BLOCK && potiontype ==
 * PotionTypes.WATER && list.isEmpty()) { BlockPos blockpos =
 * result.getBlockPos().offset(result.sideHit); this.extinguishFires(blockpos);
 * 
 * for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
 * this.extinguishFires(blockpos.offset(enumfacing)); }
 * 
 * this.worldObj.playEvent(2002, new BlockPos(this),
 * PotionType.getID(potiontype)); this.setDead(); } else { if (!list.isEmpty())
 * { if (this.isLingering()) { EntityAreaEffectCloud entityareaeffectcloud = new
 * EntityAreaEffectCloud(this.worldObj, this.posX, this.posY, this.posZ);
 * entityareaeffectcloud.setOwner(this.getThrower());
 * entityareaeffectcloud.setRadius(3.0F);
 * entityareaeffectcloud.setRadiusOnUse(-0.5F);
 * entityareaeffectcloud.setWaitTime(10);
 * entityareaeffectcloud.setRadiusPerTick( -entityareaeffectcloud.getRadius() /
 * (float) entityareaeffectcloud.getDuration());
 * entityareaeffectcloud.setPotion(potiontype);
 * 
 * for (PotionEffect potioneffect :
 * PotionUtils.getFullEffectsFromItem(itemstack)) {
 * entityareaeffectcloud.addEffect(new PotionEffect(potioneffect.getPotion(),
 * potioneffect.getDuration(), potioneffect.getAmplifier())); }
 * 
 * this.worldObj.spawnEntityInWorld(entityareaeffectcloud); } else {
 * AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 2.0D,
 * 4.0D); List<EntityLivingBase> list1 = this.worldObj
 * .<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class,
 * axisalignedbb);
 * 
 * if (!list1.isEmpty()) { for (EntityLivingBase entitylivingbase : list1) { if
 * (entitylivingbase.canBeHitWithPotion()) { double d0 =
 * this.getDistanceSqToEntity(entitylivingbase);
 * 
 * if (d0 < 16.0D) { double d1 = 1.0D - Math.sqrt(d0) / 4.0D;
 * 
 * if (entitylivingbase == result.entityHit) { d1 = 1.0D; }
 * 
 * for (PotionEffect potioneffect1 : list) { Potion potion =
 * potioneffect1.getPotion();
 * 
 * if (potion.isInstant()) { potion.affectEntity(this, this.getThrower(),
 * entitylivingbase, potioneffect1.getAmplifier(), d1); } else { int i = (int)
 * (d1 * (double) potioneffect1.getDuration() + 0.5D);
 * 
 * if (i > 20) { entitylivingbase.addPotionEffect( new PotionEffect(potion, i,
 * potioneffect1.getAmplifier())); } } } } } } } } }
 * 
 * this.worldObj.playEvent(2002, new BlockPos(this),
 * PotionType.getID(potiontype)); this.setDead(); } } }
 * 
 * private boolean isLingering() { return this.getPotion().getItem() ==
 * RItems.tin_can_lingering_potion; }
 * 
 * private void extinguishFires(BlockPos pos) { if
 * (this.worldObj.getBlockState(pos).getBlock() == Blocks.FIRE) {
 * this.worldObj.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); } }
 * 
 * public static void registerFixesPotion(DataFixer fixer) {
 * EntityThrowable.registerFixesThrowable(fixer, "ThrownPotion");
 * fixer.registerWalker(FixTypes.ENTITY, new ItemStackData("ThrownPotion", new
 * String[] { "Potion" })); }
 * 
 * /** (abstract) Protected helper method to read subclass entity data from NBT.
 */
/*
 * public void readEntityFromNBT(NBTTagCompound compound) {
 * super.readEntityFromNBT(compound); ItemStack itemstack =
 * ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Potion"));
 * 
 * if (itemstack == null) { this.setDead(); } else { this.setItem(itemstack); }
 * }
 * 
 * /** (abstract) Protected helper method to write subclass entity data to NBT.
 */
/*
 * public void writeEntityToNBT(NBTTagCompound compound) {
 * super.writeEntityToNBT(compound); ItemStack itemstack = this.getPotion();
 * 
 * if (itemstack != null) { compound.setTag("Potion", itemstack.writeToNBT(new
 * NBTTagCompound())); } } }
 */
