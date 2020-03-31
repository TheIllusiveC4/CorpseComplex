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

import java.lang.reflect.Field;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;

public class HungerModule {

  private static final Field SATURATION_LEVEL = ObfuscationReflectionHelper
      .findField(FoodStats.class, "field_75125_b");
  private static final Field EXHAUSTION_LEVEL = ObfuscationReflectionHelper
      .findField(FoodStats.class, "field_75126_c");

  @SubscribeEvent
  public void playerRespawn(final PlayerEvent.Clone evt) {

    if (evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(deathStorage -> {
        FoodStats stats = evt.getPlayer().getFoodStats();
        FoodStats oldStats = evt.getOriginal().getFoodStats();
        HungerSetting setting = deathStorage.getSettings().getHungerSettings();
        int minFood = setting.getMinFood();
        int maxFood = setting.getMaxFood();

        if (maxFood < minFood) {
          CorpseComplex.LOGGER.error("Config error: minFood cannot be greater than maxFood!");
        } else {
          int food = setting.isKeepFood() ? oldStats.getFoodLevel() : 20;
          stats.setFoodLevel(Math.max(minFood, Math.min(maxFood, food)));
        }

        if (setting.isKeepSaturation()) {
          try {
            SATURATION_LEVEL.setFloat(stats, oldStats.getSaturationLevel());
          } catch (IllegalAccessException e) {
            CorpseComplex.LOGGER.error("Error setting saturation level!");
          }
        }

        if (setting.isKeepExhaustion()) {
          try {
            float exhaustion = EXHAUSTION_LEVEL.getFloat(oldStats);
            EXHAUSTION_LEVEL.setFloat(stats, exhaustion);
          } catch (IllegalAccessException e) {
            CorpseComplex.LOGGER.error("Error setting exhaustion level!");
          }
        }
      });
    }
  }
}
