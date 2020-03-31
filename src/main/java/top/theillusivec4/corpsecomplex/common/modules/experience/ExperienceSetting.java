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

package top.theillusivec4.corpsecomplex.common.modules.experience;

import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class ExperienceSetting implements Setting<ExperienceOverride> {

  private double lostXp;
  private XpDropMode xpDropMode;
  private double droppedXpPercent;
  private int droppedXpPerLevel;
  private int maxDroppedXp;

  public double getLostXp() {
    return lostXp;
  }

  public void setLostXp(double lostXp) {
    this.lostXp = lostXp;
  }

  public XpDropMode getXpDropMode() {
    return xpDropMode;
  }

  public void setXpDropMode(XpDropMode xpDropMode) {
    this.xpDropMode = xpDropMode;
  }

  public double getDroppedXpPercent() {
    return droppedXpPercent;
  }

  public void setDroppedXpPercent(double droppedXpPercent) {
    this.droppedXpPercent = droppedXpPercent;
  }

  public int getDroppedXpPerLevel() {
    return droppedXpPerLevel;
  }

  public void setDroppedXpPerLevel(int droppedXpPerLevel) {
    this.droppedXpPerLevel = droppedXpPerLevel;
  }

  public int getMaxDroppedXp() {
    return maxDroppedXp;
  }

  public void setMaxDroppedXp(int maxDroppedXp) {
    this.maxDroppedXp = maxDroppedXp;
  }

  @Override
  public void importConfig() {
    this.setLostXp(CorpseComplexConfig.lostXp);
    this.setXpDropMode(CorpseComplexConfig.xpDropMode);
    this.setDroppedXpPercent(CorpseComplexConfig.droppedXpPercent);
    this.setDroppedXpPerLevel(CorpseComplexConfig.droppedXpPerLevel);
    this.setMaxDroppedXp(CorpseComplexConfig.maxDroppedXp);
  }

  @Override
  public void applyOverride(ExperienceOverride overrides) {
    overrides.getLostXp().ifPresent(this::setLostXp);
    overrides.getXpDropMode().ifPresent(this::setXpDropMode);
    overrides.getDroppedXpPercent().ifPresent(this::setDroppedXpPercent);
    overrides.getDroppedXpPerLevel().ifPresent(this::setDroppedXpPerLevel);
    overrides.getMaxDroppedXp().ifPresent(this::setMaxDroppedXp);
  }
}
