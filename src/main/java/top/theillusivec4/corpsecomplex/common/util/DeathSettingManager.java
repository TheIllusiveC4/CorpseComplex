package top.theillusivec4.corpsecomplex.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;
import top.theillusivec4.corpsecomplex.common.DeathSettings;

public class DeathSettingManager {

  public static final Map<UUID, DeathSettings> SETTINGS = new HashMap<>();

  public static void buildSettings(PlayerEntity playerEntity) {
    DeathSettings settings = new DeathSettings();

  }
}
