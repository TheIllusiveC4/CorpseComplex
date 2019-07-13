/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class HungerModule extends Module {

  private static final Field FOOD_SATURATION_LEVEL = ReflectionHelper.findField(
          FoodStats.class, "foodSaturationLevel", "field_75125_b");

  private static boolean keepFood;
  private static int minFood;
  private static int maxFood;
  private static boolean cfgEnabled;
  private static boolean keepSaturation;

  @SubscribeEvent
  public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

    if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) {
      return;
    }

    restoreHunger(e.getEntityPlayer(), e.getOriginal());
  }

  public HungerModule() {
    super("Hunger", "Customize hunger and saturation values on respawn");
  }

  public void loadModuleConfig() {
    setCategoryComment();
    cfgEnabled = getBool("Enable Hunger Module", false,
            "Set to true to enable hunger module", false);
    keepFood = getBool("Keep Food Level", false,
            "Set to true to retain food level on death", false);
    minFood = getInt("Minimum Food Level", 6, 0, 20,
            "Lowest amount of food level you can respawn with", false);
    maxFood = getInt("Maximum Food Level", 20, minFood, 20,
            "Highest amount of food level you can respawn with", false);
    keepSaturation = getBool("Keep Saturation", false,
            "Set to true to retain saturation on death", false);
  }

  public void initPropOrder() {
    propOrder = new ArrayList<>(
            Arrays.asList("Enable Hunger Module", "Keep Food Level",
                    "Maximum Food Level", "Minimum Food Level"));
  }

  public void setEnabled() {
    enabled = cfgEnabled;
  }

  private static void restoreHunger(EntityPlayer player,
          EntityPlayer oldPlayer) {
    FoodStats oldFood = oldPlayer.getFoodStats();
    FoodStats newFood = player.getFoodStats();

    if (keepFood) {
      newFood.setFoodLevel(
              Math.max(minFood, (Math.min(maxFood, oldFood.getFoodLevel()))));
    } else {
      newFood.setFoodLevel(Math.max(minFood, (Math.min(maxFood, 20))));
    }

    if (keepSaturation) {

      try {
        FOOD_SATURATION_LEVEL.setFloat(newFood, oldFood.getSaturationLevel());
      } catch (IllegalAccessException e) {
        CorpseComplex.logger.error(
                "Error syncing food saturation level in hunger module!");
      }
    }
  }
}
