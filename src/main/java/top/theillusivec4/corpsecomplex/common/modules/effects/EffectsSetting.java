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

package top.theillusivec4.corpsecomplex.common.modules.effects;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;

public class EffectsSetting implements Setting<EffectsOverride> {

  private List<ItemStack> cures = new ArrayList<>();
  private List<EffectInstance> effects = new ArrayList<>();
  private PermissionMode keepEffectsMode;
  private List<Effect> keepEffects = new ArrayList<>();

  public List<ItemStack> getCures() {
    return cures;
  }

  public void setCures(List<ItemStack> cures) {
    this.cures = cures;
  }

  public List<EffectInstance> getEffects() {
    return effects;
  }

  public void setEffects(List<EffectInstance> effects) {
    this.effects = effects;
  }

  public PermissionMode getKeepEffectsMode() {
    return keepEffectsMode;
  }

  public void setKeepEffectsMode(PermissionMode keepEffectsMode) {
    this.keepEffectsMode = keepEffectsMode;
  }

  public List<Effect> getKeepEffects() {
    return keepEffects;
  }

  public void setKeepEffects(List<Effect> keepEffects) {
    this.keepEffects = keepEffects;
  }

  @Override
  public void importConfig() {
    this.setKeepEffectsMode(CorpseComplexConfig.keepEffectsMode);
    this.setKeepEffects(ConfigParser.parseEffects(CorpseComplexConfig.keepEffects));
    ConfigParser.parseItems(CorpseComplexConfig.cures).keySet()
        .forEach(item -> this.getCures().add(new ItemStack(item)));
    this.setEffects(
        ConfigParser.parseEffectInstances(CorpseComplexConfig.effects, this.getCures()));
  }

  @Override
  public void applyOverride(EffectsOverride override) {
    override.getCures().ifPresent(this::setCures);
    override.getEffects().ifPresent(this::setEffects);
    override.getKeepEffects().ifPresent(this::setKeepEffects);
    override.getKeepEffectsMode().ifPresent(this::setKeepEffectsMode);
  }
}
