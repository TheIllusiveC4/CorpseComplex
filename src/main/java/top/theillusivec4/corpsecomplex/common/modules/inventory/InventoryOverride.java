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

package top.theillusivec4.corpsecomplex.common.modules.inventory;

import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;

public class InventoryOverride {

  @Nullable
  private final Map<InventorySection, SectionSettings> inventorySettings;
  @Nullable
  private final Map<Item, DropMode> items;
  @Nullable
  private final Boolean limitDurabilityLoss;
  @Nullable
  private final Integer dropDespawnTime;

  private InventoryOverride(Builder builder) {
    this.inventorySettings = builder.inventorySettings;
    this.items = builder.items;
    this.limitDurabilityLoss = builder.limitDurabilityLoss;
    this.dropDespawnTime = builder.dropDespawnTime;
  }

  public Optional<Map<InventorySection, SectionSettings>> getInventorySettings() {
    return Optional.ofNullable(this.inventorySettings);
  }

  public Optional<Map<Item, DropMode>> getItems() {
    return Optional.ofNullable(this.items);
  }

  public Optional<Boolean> getLimitDurabilityLoss() {
    return Optional.ofNullable(this.limitDurabilityLoss);
  }

  public Optional<Integer> getDropDespawnTime() {
    return Optional.ofNullable(this.dropDespawnTime);
  }

  public static class Builder {

    private Map<InventorySection, SectionSettings> inventorySettings;
    private Map<Item, DropMode> items;
    private Boolean limitDurabilityLoss;
    private Integer dropDespawnTime;

    public Builder inventorySettings(Map<InventorySection, SectionSettings> inventorySettings) {
      this.inventorySettings = inventorySettings;
      return this;
    }

    public Builder dropDespawnTime(Integer dropDespawnTime) {
      this.dropDespawnTime = dropDespawnTime;
      return this;
    }

    public Builder items(Map<Item, DropMode> items) {
      this.items = items;
      return this;
    }

    public Builder limitDurabilityLoss(Boolean limitDurabilityLoss) {
      this.limitDurabilityLoss = limitDurabilityLoss;
      return this;
    }

    public InventoryOverride build() {
      return new InventoryOverride(this);
    }
  }

  public static class SectionSettings {

    @Nullable
    private Double keepChance;
    @Nullable
    private Double destroyChance;
    @Nullable
    private Double keepDurabilityLoss;
    @Nullable
    private Double dropDurabilityLoss;

    public SectionSettings(@Nullable Double keepChance, @Nullable Double destroyChance,
        @Nullable Double keepDurabilityLoss, @Nullable Double dropDurabilityLoss) {
      this.keepChance = keepChance;
      this.destroyChance = destroyChance;
      this.keepDurabilityLoss = keepDurabilityLoss;
      this.dropDurabilityLoss = dropDurabilityLoss;
    }

    public Optional<Double> getKeepChance() {
      return Optional.ofNullable(keepChance);
    }

    public Optional<Double> getDestroyChance() {
      return Optional.ofNullable(destroyChance);
    }

    public Optional<Double> getKeepDurabilityLoss() {
      return Optional.ofNullable(keepDurabilityLoss);
    }

    public Optional<Double> getDropDurabilityLoss() {
      return Optional.ofNullable(dropDurabilityLoss);
    }
  }
}
