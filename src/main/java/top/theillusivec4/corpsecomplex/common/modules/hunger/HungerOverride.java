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

package top.theillusivec4.corpsecomplex.common.modules.hunger;

import java.util.Optional;
import javax.annotation.Nullable;

public class HungerOverride {

  @Nullable
  private Boolean keepFood;
  @Nullable
  private Boolean keepSaturation;
  @Nullable
  private Boolean keepExhaustion;
  @Nullable
  private Integer minFood;
  @Nullable
  private Integer maxFood;

  private HungerOverride(Builder builder) {
    this.keepFood = builder.keepFood;
    this.keepSaturation = builder.keepSaturation;
    this.keepExhaustion = builder.keepExhaustion;
    this.minFood = builder.minFood;
    this.maxFood = builder.maxFood;
  }

  public Optional<Boolean> getKeepFood() {
    return Optional.ofNullable(this.keepFood);
  }

  public Optional<Boolean> getKeepSaturation() {
    return Optional.ofNullable(keepSaturation);
  }

  public Optional<Boolean> getKeepExhaustion() {
    return Optional.ofNullable(keepExhaustion);
  }

  public Optional<Integer> getMinFood() {
    return Optional.ofNullable(minFood);
  }

  public Optional<Integer> getMaxFood() {
    return Optional.ofNullable(maxFood);
  }

  public static class Builder {

    private Boolean keepFood;
    private Boolean keepSaturation;
    private Boolean keepExhaustion;
    private Integer minFood;
    private Integer maxFood;

    public Builder keepFood(Boolean keepFood) {
      this.keepFood = keepFood;
      return this;
    }

    public Builder keepSaturation(Boolean keepSaturation) {
      this.keepSaturation = keepSaturation;
      return this;
    }

    public Builder keepExhaustion(Boolean keepExhaustion) {
      this.keepExhaustion = keepExhaustion;
      return this;
    }

    public Builder minFood(Integer minFood) {
      this.minFood = minFood;
      return this;
    }

    public Builder maxFood(Integer maxFood) {
      this.maxFood = maxFood;
      return this;
    }

    public HungerOverride build() {
      return new HungerOverride(this);
    }
  }
}
