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

package top.theillusivec4.corpsecomplex.common.util.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.DeathCondition;
import top.theillusivec4.corpsecomplex.common.DeathCondition.Builder;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.DeathInfo;

public class DeathConditionManager {

  public static final Map<String, DeathCondition> CONDITIONS = new HashMap<>();
  public static final List<BiFunction<DeathInfo, DeathCondition, Boolean>> CONDITION_ADDONS = new ArrayList<>();

  public static boolean matches(DeathCondition deathCondition, IDeathStorage deathStorage) {
    DeathInfo source = deathStorage.getDeathInfo();

    if (source == null) {
      return false;
    }

    for (BiFunction<DeathInfo, DeathCondition, Boolean> conditionAddon : CONDITION_ADDONS) {
      if (!conditionAddon.apply(source, deathCondition)) {
        return false;
      }
    }
    PlayerEntity playerEntity = deathStorage.getPlayer();
    Optional<List<String>> playersOpt = deathCondition.getPlayers();
    String name = playerEntity.getName().getString();
    String uuid = playerEntity.getUniqueID().toString();
    boolean matchesPlayer = playersOpt.map(
        players -> players.stream().anyMatch(player -> name.equals(player) || uuid.equals(player)))
        .orElse(true);

    if (!matchesPlayer) {
      return false;
    }
    Optional<Difficulty> difficultyOpt = deathCondition.getDifficulty();
    boolean matchesDifficulty = difficultyOpt
        .map(difficulty -> playerEntity.getEntityWorld().getDifficulty() == difficulty)
        .orElse(true);

    if (!matchesDifficulty) {
      return false;
    }
    Optional<String> damageTypeOpt = deathCondition.getDamageType();
    boolean matchesDamage = damageTypeOpt.map(damageType -> {
      if (source.getDamageType().equals(damageType)) {
        return true;
      } else if (source.isFireDamage() && damageType.equals("fire")) {
        return true;
      } else if (source.isMagicDamage() && damageType.equals("magic")) {
        return true;
      } else if (source.isExplosion() && damageType.equals("explosion")) {
        return true;
      } else {
        return source.isProjectile() && damageType.equals("projectile");
      }
    }).orElse(true);

    if (!matchesDamage) {
      return false;
    }
    Optional<ResourceLocation> dimensionOpt = deathCondition.getDimension();
    boolean matchesDimension = dimensionOpt
        .map(dimension -> source.getDimension().equals(dimension)).orElse(true);

    if (!matchesDimension) {
      return false;
    }
    Optional<EntityType<?>> imSrcOpt = deathCondition.getImmediateSource();
    boolean matchesImSrc = imSrcOpt.map(immediateSource -> {
      EntityType<?> imSrc = source.getImmediateSource();
      return imSrc == immediateSource;
    }).orElse(true);

    if (!matchesImSrc) {
      return false;
    }

    Optional<EntityType<?>> trueSrcOpt = deathCondition.getTrueSource();
    return trueSrcOpt.map(trueSource -> {
      EntityType<?> trueSrc = source.getTrueSource();
      return trueSrc == trueSource;
    }).orElse(true);
  }

  public static void importConfig() {
    CONDITIONS.clear();
    CorpseComplexConfig.conditions.forEach(condition -> {
      String identifier = condition.identifier;

      if (identifier == null) {
        CorpseComplex.LOGGER.error("Missing identifier in conditions! Skipping...");
        return;
      }
      ResourceLocation dimensionLocation = null;

      if (condition.dimension != null && !condition.dimension.isEmpty()) {
        dimensionLocation = new ResourceLocation(condition.dimension);
      }
      Builder builder = new Builder().damageType(condition.damageType)
          .immediateSource(getEntityType(condition.immediateSource))
          .trueSource(getEntityType(condition.trueSource)).dimension(dimensionLocation)
          .gameStages(condition.gameStages).difficulty(getDifficulty(condition.difficulty))
          .players(condition.players);
      CONDITIONS.put(identifier, builder.build());
    });
  }

  public static Difficulty getDifficulty(@Nullable String difficulty) {
    return difficulty == null ? null : Difficulty.byName(difficulty);
  }

  public static EntityType<?> getEntityType(@Nullable String name) {
    return name == null ? null : ForgeRegistries.ENTITIES.getValue(new ResourceLocation(name));
  }
}
