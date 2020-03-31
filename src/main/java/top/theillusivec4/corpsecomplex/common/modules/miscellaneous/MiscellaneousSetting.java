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

package top.theillusivec4.corpsecomplex.common.modules.miscellaneous;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class MiscellaneousSetting implements Setting<MiscellaneousOverride> {

  private boolean restrictRespawning;
  private List<ItemStack> respawnItems = new ArrayList<>();
  private List<EntityType<?>> mobSpawnsOnDeath = new ArrayList<>();

  public boolean isRestrictRespawning() {
    return restrictRespawning;
  }

  public void setRestrictRespawning(boolean restrictRespawning) {
    this.restrictRespawning = restrictRespawning;
  }

  public List<ItemStack> getRespawnItems() {
    return respawnItems;
  }

  public void setRespawnItems(List<ItemStack> respawnItems) {
    this.respawnItems = respawnItems;
  }

  public List<EntityType<?>> getMobSpawnsOnDeath() {
    return mobSpawnsOnDeath;
  }

  public void setMobSpawnsOnDeath(List<EntityType<?>> mobSpawnsOnDeath) {
    this.mobSpawnsOnDeath = mobSpawnsOnDeath;
  }

  @Override
  public void importConfig() {
    ConfigParser.parseItems(CorpseComplexConfig.respawnItems)
        .forEach((item, integer) -> this.getRespawnItems().add(new ItemStack(item, integer)));
    this.setRestrictRespawning(CorpseComplexConfig.restrictRespawning);
    this.setMobSpawnsOnDeath(ConfigParser.parseMobs(CorpseComplexConfig.mobSpawnsOnDeath));
  }

  @Override
  public void applyOverride(MiscellaneousOverride override) {
    override.getRespawnItems().ifPresent(this::setRespawnItems);
    override.getRestrictRespawning().ifPresent(this::setRestrictRespawning);
    override.getMobSpawnsOnDeath().ifPresent(this::setMobSpawnsOnDeath);
  }
}
