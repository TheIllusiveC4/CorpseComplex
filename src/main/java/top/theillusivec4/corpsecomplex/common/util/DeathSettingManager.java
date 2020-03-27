package top.theillusivec4.corpsecomplex.common.util;

import top.theillusivec4.corpsecomplex.common.DeathSettings;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;

public class DeathSettingManager {

  public static DeathSettings buildSettings(IDeathStorage deathStorage) {
    DeathSettings settings = new DeathSettings();
    DeathOverrideManager.apply(settings, deathStorage);
    return settings;
  }
}
