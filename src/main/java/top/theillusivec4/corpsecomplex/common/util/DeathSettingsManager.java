package top.theillusivec4.corpsecomplex.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import top.theillusivec4.corpsecomplex.common.DeathSettings;

public class DeathSettingsManager {

  public static final Map<UUID, DeathSettings> SETTINGS = new HashMap<>();

  public static DeathSettings defaultSettings;

  public static void importConfig() {
    DeathSettings settings = new DeathSettings();

  }
}
