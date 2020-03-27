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
