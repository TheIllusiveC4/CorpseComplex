package top.theillusivec4.corpsecomplex.common.modules;

import java.lang.reflect.Field;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

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
        int minFood = deathStorage.getSettings().hunger.minFood;
        int maxFood = deathStorage.getSettings().hunger.maxFood;

        if (maxFood < minFood) {
          CorpseComplex.LOGGER.error("Config error: minFood cannot be greater than maxFood!");
        } else {
          int food = deathStorage.getSettings().hunger.keepFood ? oldStats.getFoodLevel() : 20;
          stats.setFoodLevel(Math.max(minFood, Math.min(maxFood, food)));
        }

        if (deathStorage.getSettings().hunger.keepSaturation) {
          try {
            SATURATION_LEVEL.setFloat(stats, oldStats.getSaturationLevel());
          } catch (IllegalAccessException e) {
            CorpseComplex.LOGGER.error("Error setting saturation level!");
          }
        }

        if (deathStorage.getSettings().hunger.keepExhaustion) {
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
