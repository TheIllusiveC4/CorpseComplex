package top.theillusivec4.corpsecomplex.common.util.integration;

import java.util.Collection;
import java.util.function.BiFunction;
import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.entity.player.PlayerEntity;
import top.theillusivec4.corpsecomplex.common.DeathCondition;
import top.theillusivec4.corpsecomplex.common.util.DeathInfo;

public class GameStagesIntegration {

  public static final BiFunction<DeathInfo, DeathCondition, Boolean> HAS_STAGE = (deathDamageSource, deathCondition) -> deathCondition
      .getGameStages().map(gameStages -> {
        for (String gameStage : gameStages) {
          if (!deathDamageSource.getGameStages().contains(gameStage)) {
            return false;
          }
        }
        return true;
      }).orElse(true);

  public static Collection<String> getGameStages(PlayerEntity playerEntity) {
    return GameStageHelper.getPlayerData(playerEntity).getStages();
  }
}
