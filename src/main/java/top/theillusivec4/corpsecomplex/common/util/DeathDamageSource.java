package top.theillusivec4.corpsecomplex.common.util;

import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class DeathDamageSource {

  private String damageType;
  private boolean isFireDamage;
  private boolean isMagicDamage;
  private boolean isExplosion;
  private boolean isProjectile;
  @Nullable
  private EntityType<?> immediateSource;
  @Nullable
  private EntityType<?> trueSource;

  public DeathDamageSource() {
  }

  public DeathDamageSource(DeathDamageSource deathDamageSource) {
    this.damageType = deathDamageSource.getDamageType();
    this.isFireDamage = deathDamageSource.isFireDamage();
    this.isMagicDamage = deathDamageSource.isMagicDamage();
    this.isExplosion = deathDamageSource.isExplosion();
    this.isProjectile = deathDamageSource.isProjectile();
    this.immediateSource = deathDamageSource.getImmediateSource();
    this.trueSource = deathDamageSource.getTrueSource();
  }

  public DeathDamageSource(DamageSource source) {
    this.damageType = source.getDamageType();
    this.isFireDamage = source.isFireDamage();
    this.isMagicDamage = source.isMagicDamage();
    this.isExplosion = source.isExplosion();
    this.isProjectile = source.isProjectile();
    this.immediateSource =
        source.getImmediateSource() != null ? source.getImmediateSource().getType() : null;
    this.trueSource = source.getTrueSource() != null ? source.getTrueSource().getType() : null;
  }

  public String getDamageType() {
    return damageType;
  }

  public boolean isFireDamage() {
    return isFireDamage;
  }

  public boolean isMagicDamage() {
    return isMagicDamage;
  }

  public boolean isExplosion() {
    return isExplosion;
  }

  public boolean isProjectile() {
    return isProjectile;
  }

  @Nullable
  public EntityType<?> getImmediateSource() {
    return immediateSource;
  }

  @Nullable
  public EntityType<?> getTrueSource() {
    return trueSource;
  }

  public CompoundNBT write(CompoundNBT compoundNBT) {
    CompoundNBT tag = new CompoundNBT();
    tag.putString("DamageType", this.damageType);
    tag.putBoolean("FireDamage", this.isFireDamage);
    tag.putBoolean("MagicDamage", this.isMagicDamage);
    tag.putBoolean("Explosion", this.isExplosion);
    tag.putBoolean("Projectile", this.isProjectile);
    if (this.immediateSource != null && this.immediateSource.getRegistryName() != null) {
      tag.putString("ImmediateSource", this.immediateSource.getRegistryName().toString());
    }
    if (this.trueSource != null && this.trueSource.getRegistryName() != null) {
      tag.putString("TrueSource", this.trueSource.getRegistryName().toString());
    }
    compoundNBT.put("DeathDamageSource", tag);
    return compoundNBT;
  }

  public void read(CompoundNBT compoundNBT) {
    CompoundNBT tag = compoundNBT.getCompound("DeathDamageSource");
    this.damageType = tag.getString("DamageType");
    this.isFireDamage = tag.getBoolean("FireDamage");
    this.isMagicDamage = tag.getBoolean("MagicDamage");
    this.isExplosion = tag.getBoolean("Explosion");
    this.isProjectile = tag.getBoolean("Projectile");
    if (tag.contains("ImmediateSource")) {
      this.immediateSource = ForgeRegistries.ENTITIES
          .getValue(new ResourceLocation(tag.getString("ImmediateSource")));
    }
    if (tag.contains("TrueSource")) {
      this.trueSource = ForgeRegistries.ENTITIES
          .getValue(new ResourceLocation(tag.getString("TrueSource")));
    }
  }
}
