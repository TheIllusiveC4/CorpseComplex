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

package top.theillusivec4.corpsecomplex.common.config;

import java.util.List;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class OverridesConfig {

  public List<OverrideConfig> overrides;
  public List<ConditionConfig> conditions;

  public static class OverrideConfig {
    public Integer priority;

    public Double keepChance;
    public Double destroyChance;
    public Double keepDurabilityLoss;
    public Double dropDurabilityLoss;

    public Double mainhandKeepChance;
    public Double mainhandDestroyChance;
    public Double mainhandKeepDurabilityLoss;
    public Double mainhandDropDurabilityLoss;

    public Double hotbarKeepChance;
    public Double hotbarDestroyChance;
    public Double hotbarKeepDurabilityLoss;
    public Double hotbarDropDurabilityLoss;

    public Double offhandKeepChance;
    public Double offhandDestroyChance;
    public Double offhandKeepDurabilityLoss;
    public Double offhandDropDurabilityLoss;

    public Double mainKeepChance;
    public Double mainDestroyChance;
    public Double mainKeepDurabilityLoss;
    public Double mainDropDurabilityLoss;

    public Double headKeepChance;
    public Double headDestroyChance;
    public Double headKeepDurabilityLoss;
    public Double headDropDurabilityLoss;

    public Double chestKeepChance;
    public Double chestDestroyChance;
    public Double chestKeepDurabilityLoss;
    public Double chestDropDurabilityLoss;

    public Double legsKeepChance;
    public Double legsDestroyChance;
    public Double legsKeepDurabilityLoss;
    public Double legsDropDurabilityLoss;

    public Double feetKeepChance;
    public Double feetDestroyChance;
    public Double feetKeepDurabilityLoss;
    public Double feetDropDurabilityLoss;

    public Double curioKeepChance;
    public Double curioDestroyChance;
    public Double curioKeepDurabilityLoss;
    public Double curioDropDurabilityLoss;

    public Double cosmeticArmorKeepChance;
    public Double cosmeticArmorDestroyChance;
    public Double cosmeticArmorKeepDurabilityLoss;
    public Double cosmeticArmorDropDurabilityLoss;

    public List<String> itemSettings;
    public Boolean limitDurabilityLoss;
    public Integer dropDespawnTime;

    public List<String> cures;
    public List<String> effects;
    public PermissionMode keepEffectsMode;
    public List<String> keepEffects;

    public Boolean keepFood;
    public Boolean keepSaturation;
    public Boolean keepExhaustion;
    public Integer minFood;
    public Integer maxFood;

    public Double lostXp;
    public XpDropMode xpDropMode;
    public Double droppedXpPercent;
    public Integer droppedXpPerLevel;
    public Integer maxDroppedXp;

    public List<String> mementoCures;
    public Boolean noFood;
    public Double percentXp;

    public Boolean restrictRespawning;
    public List<String> respawnItems;
    public List<String> mobSpawnsOnDeath;

    public List<String> conditions;
  }

  public static class ConditionConfig {

    public String identifier;
    public String damageType;
    public String immediateSource;
    public String trueSource;
    public Integer dimension;
    public List<String> gameStages;
    public String difficulty;
  }
}
