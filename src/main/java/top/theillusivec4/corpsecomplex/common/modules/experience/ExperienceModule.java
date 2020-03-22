package top.theillusivec4.corpsecomplex.common.modules.experience;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class ExperienceModule {

  @SubscribeEvent
  public void playerXpDrop(final LivingExperienceDropEvent evt) {

    if (evt.getEntityLiving() instanceof PlayerEntity) {
      PlayerEntity player = (PlayerEntity) evt.getEntityLiving();
      DeathStorageCapability.getCapability(player).ifPresent(deathStorage -> {
        double lose = deathStorage.getSettings().getExperienceSettings().getLostXp();

        if (lose <= 0.0D) {
          evt.setCanceled(true);
          return;
        }
        int lostXp = (int) (lose * player.experienceTotal);
        int droppedXp = getExperiencePoints(player, lostXp, deathStorage);
        evt.setDroppedExperience(droppedXp);
        int keptXp = player.experienceTotal - lostXp;
        player.experience = 0;
        player.experienceTotal = 0;
        player.experienceLevel = 0;
        player.experience += (float) keptXp / (float) player.xpBarCap();
        player.experienceTotal = MathHelper
            .clamp(player.experienceTotal + keptXp, 0, Integer.MAX_VALUE);

        while (player.experience < 0.0F) {
          float f = player.experience * (float) player.xpBarCap();
          if (player.experienceLevel > 0) {
            player.addExperienceLevel(-1);
            player.experience = 1.0F + f / (float) player.xpBarCap();
          } else {
            player.addExperienceLevel(-1);
            player.experience = 0.0F;
          }
        }

        while (player.experience >= 1.0F) {
          player.experience = (player.experience - 1.0F) * (float) player.xpBarCap();
          player.addExperienceLevel(1);
          player.experience /= (float) player.xpBarCap();
        }
      });
    }
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerEvent.Clone evt) {

    if (evt.isWasDeath()) {
      evt.getPlayer().giveExperiencePoints(evt.getOriginal().experienceTotal);
    }
  }

  private static int getExperiencePoints(PlayerEntity player, int lostXp,
      IDeathStorage deathStorage) {

    if (!player.isSpectator()) {
      int i;
      ExperienceSetting setting = deathStorage.getSettings().getExperienceSettings();

      if (setting.getXpDropMode() == XpDropMode.PER_LEVEL) {
        int newTotal = player.experienceTotal - lostXp;
        int lostLevels;

        if (player.experienceTotal >= 1628) {
          lostLevels = (int) (player.experienceLevel - (18.0556D + 0.0555556D * Math
              .sqrt(72 * newTotal - 54215)));
        } else if (player.experienceTotal >= 394) {
          lostLevels = (int) (player.experienceLevel - (8.1 + 0.1 * Math
              .sqrt(40 * newTotal - 7839)));
        } else {
          lostLevels = (int) (player.experienceLevel - (-3 + Math.sqrt(newTotal + 9)));
        }
        i = lostLevels * setting.getDroppedXpPerLevel();
      } else {
        i = (int) (lostXp * setting.getDroppedXpPercent());
      }
      return Math.min(i, setting.getMaxDroppedXp());
    } else {
      return 0;
    }
  }
}
