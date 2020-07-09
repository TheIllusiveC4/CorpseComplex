/*
 * Copyright (c) 2017-2020 C4
 *
 * This file is part of Corpse Complex, a mod made for Minecraft.
 *
 * Corpse Complex is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Corpse Complex is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Corpse Complex.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.theillusivec4.corpsecomplex.common.util;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.registries.ForgeRegistries;

public class DeathInfo {

  private String damageType;
  private boolean isFireDamage;
  private boolean isMagicDamage;
  private boolean isExplosion;
  private boolean isProjectile;
  @Nullable
  private EntityType<?> immediateSource;
  @Nullable
  private EntityType<?> trueSource;
  private ResourceLocation dimension;
  private List<String> gameStages;

  public DeathInfo() {
  }

  public DeathInfo(DamageSource source, World world, @Nonnull List<String> gameStages) {
    this.damageType = source.getDamageType();
    this.isFireDamage = source.isFireDamage();
    this.isMagicDamage = source.isMagicDamage();
    this.isExplosion = source.isExplosion();
    this.isProjectile = source.isProjectile();
    this.immediateSource =
        source.getImmediateSource() != null ? source.getImmediateSource().getType() : null;
    this.trueSource = source.getTrueSource() != null ? source.getTrueSource().getType() : null;
    this.dimension = world.func_234922_V_().func_240901_a_();
    this.gameStages = gameStages;
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

  public ResourceLocation getDimension() {
    return dimension;
  }

  public List<String> getGameStages() {
    return gameStages;
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
    tag.putString("Dimension", this.dimension.toString());
    ListNBT list = new ListNBT();
    this.gameStages.forEach(stage -> list.add(StringNBT.valueOf(stage)));
    tag.put("GameStages", list);
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
    this.dimension = new ResourceLocation(tag.getString("Dimension"));
    this.gameStages = new ArrayList<>();
    ListNBT list = tag.getList("GameStages", NBT.TAG_STRING);
    list.forEach(stage -> this.gameStages.add(stage.getString()));
  }
}
