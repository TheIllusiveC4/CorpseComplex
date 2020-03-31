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

package top.theillusivec4.corpsecomplex.common.modules.miscellaneous;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;

public class MiscellaneousOverride {

  @Nullable
  private final Boolean restrictRespawning;
  @Nullable
  private final List<ItemStack> respawnItems;
  @Nullable
  private final List<EntityType<?>> mobSpawnsOnDeath;

  private MiscellaneousOverride(Builder builder) {
    this.restrictRespawning = builder.restrictRespawning;
    this.respawnItems = builder.respawnItems;
    this.mobSpawnsOnDeath = builder.mobSpawnsOnDeath;
  }

  public Optional<Boolean> getRestrictRespawning() {
    return Optional.ofNullable(this.restrictRespawning);
  }

  public Optional<List<ItemStack>> getRespawnItems() {
    return Optional.ofNullable(this.respawnItems);
  }

  public Optional<List<EntityType<?>>> getMobSpawnsOnDeath() {
    return Optional.ofNullable(this.mobSpawnsOnDeath);
  }

  public static class Builder {

    private Boolean restrictRespawning;
    private List<ItemStack> respawnItems;
    private List<EntityType<?>> mobSpawnsOnDeath;

    public Builder restrictRespawning(Boolean restrictRespawning) {
      this.restrictRespawning = restrictRespawning;
      return this;
    }

    public Builder respawnItems(List<ItemStack> respawnItems) {
      this.respawnItems = respawnItems;
      return this;
    }

    public Builder mobSpawnsOnDeath(List<EntityType<?>> mobSpawnsOnDeath) {
      this.mobSpawnsOnDeath = mobSpawnsOnDeath;
      return this;
    }

    public MiscellaneousOverride build() {
      return new MiscellaneousOverride(this);
    }
  }
}
