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

import java.util.ArrayList;
import java.util.List;
import top.theillusivec4.corpsecomplex.common.modules.effects.EffectsOverride;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceOverride;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerOverride;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryOverride;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriOverride;
import top.theillusivec4.corpsecomplex.common.modules.miscellaneous.MiscellaneousOverride;

public class DeathOverride {

  private final int priority;
  private final List<DeathCondition> conditions;

  private final ExperienceOverride experience;
  private final HungerOverride hunger;
  private final InventoryOverride inventory;
  private final EffectsOverride effects;
  private final MementoMoriOverride mementoMori;
  private final MiscellaneousOverride miscellaneous;

  private DeathOverride(Builder builder) {
    this.priority = builder.priority;
    this.conditions = builder.conditions;

    this.experience = builder.experience;
    this.hunger = builder.hunger;
    this.effects = builder.effects;
    this.miscellaneous = builder.miscellaneous;
    this.mementoMori = builder.mementoMori;
    this.inventory = builder.inventory;
  }

  public int getPriority() {
    return this.priority;
  }

  public List<DeathCondition> getConditions() {
    return this.conditions;
  }

  public ExperienceOverride getExperienceOverride() {
    return this.experience;
  }

  public HungerOverride getHungerOverride() {
    return this.hunger;
  }

  public EffectsOverride getEffectsOverride() {
    return this.effects;
  }

  public InventoryOverride getInventoryOverride() {
    return this.inventory;
  }

  public MementoMoriOverride getMementoMoriOverride() {
    return this.mementoMori;
  }

  public MiscellaneousOverride getMiscellaneousOverride() {
    return this.miscellaneous;
  }

  public void apply(DeathSettings settings) {
    settings.getExperienceSettings().applyOverride(this.getExperienceOverride());
    settings.getHungerSettings().applyOverride(this.getHungerOverride());
    settings.getEffectsSettings().applyOverride(this.getEffectsOverride());
    settings.getMementoMoriSettings().applyOverride(this.getMementoMoriOverride());
    settings.getMiscellaneousSettings().applyOverride(this.getMiscellaneousOverride());
    settings.getInventorySettings().applyOverride(this.getInventoryOverride());
  }

  public static class Builder {

    private List<DeathCondition> conditions = new ArrayList<>();
    private Integer priority;

    private ExperienceOverride experience;
    private HungerOverride hunger;
    private EffectsOverride effects;
    private InventoryOverride inventory;
    private MementoMoriOverride mementoMori;
    private MiscellaneousOverride miscellaneous;

    public Builder priority(Integer priority) {
      this.priority = priority;
      return this;
    }

    public Builder conditions(List<DeathCondition> conditions) {
      this.conditions = conditions;
      return this;
    }

    public Builder experience(ExperienceOverride experience) {
      this.experience = experience;
      return this;
    }

    public Builder hunger(HungerOverride hunger) {
      this.hunger = hunger;
      return this;
    }

    public Builder effects(EffectsOverride effects) {
      this.effects = effects;
      return this;
    }

    public Builder mementoMori(MementoMoriOverride mementoMori) {
      this.mementoMori = mementoMori;
      return this;
    }

    public Builder miscellaneous(MiscellaneousOverride misc) {
      this.miscellaneous = misc;
      return this;
    }

    public Builder inventory(InventoryOverride inventory) {
      this.inventory = inventory;
      return this;
    }

    public DeathOverride build() {
      if (this.priority == null) {
        this.priority = 0;
      }
      return new DeathOverride(this);
    }
  }
}
