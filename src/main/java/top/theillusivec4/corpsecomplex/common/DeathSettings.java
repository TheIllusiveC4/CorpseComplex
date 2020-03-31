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

import java.util.EnumMap;
import java.util.Map;
import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.modules.effects.EffectsSetting;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceSetting;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerSetting;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriSetting;
import top.theillusivec4.corpsecomplex.common.modules.miscellaneous.MiscellaneousSetting;
import top.theillusivec4.corpsecomplex.common.util.Enums.ModuleType;

public class DeathSettings {

  private final Map<ModuleType, Setting<?>> modules = new EnumMap<>(ModuleType.class);

  public DeathSettings() {
    modules.put(ModuleType.HUNGER, new HungerSetting());
    modules.put(ModuleType.EXPERIENCE, new ExperienceSetting());
    modules.put(ModuleType.EFFECTS, new EffectsSetting());
    modules.put(ModuleType.INVENTORY, new InventorySetting());
    modules.put(ModuleType.MEMENTO_MORI, new MementoMoriSetting());
    modules.put(ModuleType.MISCELLANEOUS, new MiscellaneousSetting());
    modules.values().forEach(Setting::importConfig);
  }

  public EffectsSetting getEffectsSettings() {
    return (EffectsSetting) this.modules.get(ModuleType.EFFECTS);
  }

  public ExperienceSetting getExperienceSettings() {
    return (ExperienceSetting) this.modules.get(ModuleType.EXPERIENCE);
  }

  public HungerSetting getHungerSettings() {
    return (HungerSetting) this.modules.get(ModuleType.HUNGER);
  }

  public InventorySetting getInventorySettings() {
    return (InventorySetting) this.modules.get(ModuleType.INVENTORY);
  }

  public MementoMoriSetting getMementoMoriSettings() {
    return (MementoMoriSetting) this.modules.get(ModuleType.MEMENTO_MORI);
  }

  public MiscellaneousSetting getMiscellaneousSettings() {
    return (MiscellaneousSetting) this.modules.get(ModuleType.MISCELLANEOUS);
  }
}
