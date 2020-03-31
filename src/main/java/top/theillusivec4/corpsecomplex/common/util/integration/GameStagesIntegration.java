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
