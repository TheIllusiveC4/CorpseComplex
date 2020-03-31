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

import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class HungerSetting implements Setting<HungerOverride> {

  private boolean keepFood;
  private boolean keepSaturation;
  private boolean keepExhaustion;
  private int minFood;
  private int maxFood;

  public boolean isKeepFood() {
    return keepFood;
  }

  public void setKeepFood(boolean keepFood) {
    this.keepFood = keepFood;
  }

  public boolean isKeepSaturation() {
    return keepSaturation;
  }

  public void setKeepSaturation(boolean keepSaturation) {
    this.keepSaturation = keepSaturation;
  }

  public boolean isKeepExhaustion() {
    return keepExhaustion;
  }

  public void setKeepExhaustion(boolean keepExhaustion) {
    this.keepExhaustion = keepExhaustion;
  }

  public int getMinFood() {
    return minFood;
  }

  public void setMinFood(int minFood) {
    this.minFood = minFood;
  }

  public int getMaxFood() {
    return maxFood;
  }

  public void setMaxFood(int maxFood) {
    this.maxFood = maxFood;
  }

  @Override
  public void importConfig() {
    this.setKeepFood(CorpseComplexConfig.keepFood);
    this.setKeepSaturation(CorpseComplexConfig.keepSaturation);
    this.setKeepExhaustion(CorpseComplexConfig.keepExhaustion);
    this.setMinFood(CorpseComplexConfig.minFood);
    this.setMaxFood(CorpseComplexConfig.maxFood);
  }

  @Override
  public void applyOverride(HungerOverride overrides) {
    overrides.getKeepFood().ifPresent(this::setKeepFood);
    overrides.getKeepSaturation().ifPresent(this::setKeepSaturation);
    overrides.getKeepExhaustion().ifPresent(this::setKeepExhaustion);
    overrides.getMinFood().ifPresent(this::setMinFood);
    overrides.getMaxFood().ifPresent(this::setMaxFood);
  }
}
