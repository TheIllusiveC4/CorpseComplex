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

package top.theillusivec4.corpsecomplex.common;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.Difficulty;

public class DeathCondition {

  @Nullable
  private final String damageType;
  @Nullable
  private final EntityType<?> immediateSource;
  @Nullable
  private final EntityType<?> trueSource;
  @Nullable
  private final ResourceLocation dimension;
  @Nullable
  private final List<String> gameStages;
  @Nullable
  private final Difficulty difficulty;
  @Nullable
  private final List<String> players;

  private DeathCondition(Builder builder) {
    this.damageType = builder.damageType;
    this.immediateSource = builder.immediateSource;
    this.trueSource = builder.trueSource;
    this.dimension = builder.dimension;
    this.gameStages = builder.gameStages;
    this.difficulty = builder.difficulty;
    this.players = builder.players;
  }

  public Optional<String> getDamageType() {
    return Optional.ofNullable(this.damageType);
  }

  public Optional<EntityType<?>> getImmediateSource() {
    return Optional.ofNullable(this.immediateSource);
  }

  public Optional<EntityType<?>> getTrueSource() {
    return Optional.ofNullable(this.trueSource);
  }

  public Optional<ResourceLocation> getDimension() {
    return Optional.ofNullable(this.dimension);
  }

  public Optional<List<String>> getGameStages() {
    return Optional.ofNullable(this.gameStages);
  }

  public Optional<Difficulty> getDifficulty() {
    return Optional.ofNullable(this.difficulty);
  }

  public Optional<List<String>> getPlayers() {
    return Optional.ofNullable(this.players);
  }

  public static class Builder {

    private String damageType;
    private EntityType<?> immediateSource;
    private EntityType<?> trueSource;
    private ResourceLocation dimension;
    private List<String> gameStages;
    private Difficulty difficulty;
    private List<String> players;

    public Builder damageType(String damageType) {
      this.damageType = damageType;
      return this;
    }

    public Builder immediateSource(EntityType<?> immediateSource) {
      this.immediateSource = immediateSource;
      return this;
    }

    public Builder trueSource(EntityType<?> trueSource) {
      this.trueSource = trueSource;
      return this;
    }

    public Builder dimension(ResourceLocation dimension) {
      this.dimension = dimension;
      return this;
    }

    public Builder gameStages(List<String> gameStages) {
      this.gameStages = gameStages;
      return this;
    }

    public Builder difficulty(Difficulty difficulty) {
      this.difficulty = difficulty;
      return this;
    }

    public Builder players(List<String> players) {
      this.players = players;
      return this;
    }

    public DeathCondition build() {
      return new DeathCondition(this);
    }
  }
}
