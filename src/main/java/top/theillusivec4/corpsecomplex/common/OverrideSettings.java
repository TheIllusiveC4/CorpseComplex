package top.theillusivec4.corpsecomplex.common;

import java.util.List;
import java.util.PriorityQueue;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.corpsecomplex.common.util.DeathConditionManager;

public class OverrideSettings {

  public static PriorityQueue<OverrideSettings> OVERRIDES = new PriorityQueue<>((s1, s2) -> {
    if (s1.getPriority() < s2.getPriority()) {
      return 1;
    } else if (s1.getPriority() > s2.getPriority()) {
      return -1;
    }
    return 0;
  });

  public int priority = 0;
  public List<DeathCondition> conditions;

  public int getPriority() {
    return this.priority;
  }

  public void apply(DeathSettings settings) {
    this.hunger.getKeepFood().ifPresent(setting -> settings.hunger.keepFood = setting);
  }

  public static void apply(DeathSettings settings, LivingDeathEvent evt) {
    OVERRIDES.forEach(override -> {
      if (override.conditions.stream()
          .anyMatch(condition -> DeathConditionManager.matches(condition, evt))) {
        override.apply(settings);
      }
    });
  }
}
