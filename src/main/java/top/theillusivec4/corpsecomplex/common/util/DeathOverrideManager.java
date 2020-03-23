package top.theillusivec4.corpsecomplex.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.DeathCondition;
import top.theillusivec4.corpsecomplex.common.DeathOverride;
import top.theillusivec4.corpsecomplex.common.DeathSettings;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.effects.EffectsOverride;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceOverride;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerOverride;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriOverride;
import top.theillusivec4.corpsecomplex.common.modules.miscellaneous.MiscellaneousOverride;

public class DeathOverrideManager {

  public static final PriorityQueue<DeathOverride> OVERRIDES = new PriorityQueue<>((s1, s2) -> {
    if (s1.getPriority() < s2.getPriority()) {
      return 1;
    } else if (s1.getPriority() > s2.getPriority()) {
      return -1;
    }
    return 0;
  });

  public static void importConfig() {
    OVERRIDES.clear();

    CorpseComplexConfig.overrides.forEach(override -> {
      List<DeathCondition> conditions = new ArrayList<>();
      List<? extends String> conditionsConfig = override.conditions;

      if (conditionsConfig.isEmpty()) {
        CorpseComplex.LOGGER.error("Found override with empty conditions! Skipping...");
        return;
      }
      conditionsConfig.forEach(condition -> {
        if (DeathConditionManager.CONDITIONS.containsKey(condition)) {
          conditions.add(DeathConditionManager.CONDITIONS.get(condition));
        }
      });

      ExperienceOverride experience = new ExperienceOverride.Builder().lostXp(override.lostXp)
          .xpDropMode(override.xpDropMode).droppedXpPercent(override.droppedXpPercent)
          .droppedXpPerLevel(override.droppedXpPerLevel).maxDroppedXp(override.maxDroppedXp)
          .build();
      HungerOverride hunger = new HungerOverride.Builder().keepFood(override.keepFood)
          .keepSaturation(override.keepSaturation).keepExhaustion(override.keepExhaustion)
          .minFood(override.minFood).maxFood(override.maxFood).build();

      List<ItemStack> cures = ConfigParser.parseItems(override.cures).keySet().stream()
          .map(ItemStack::new).collect(Collectors.toList());
      EffectsOverride effects = new EffectsOverride.Builder().cures(cures)
          .effects(ConfigParser.parseEffectInstances(override.effects, cures))
          .keepEffectsMode(override.keepEffectsMode)
          .keepEffects(ConfigParser.parseEffects(override.keepEffects)).build();

      List<ItemStack> mementoCures = ConfigParser.parseItems(override.mementoCures).keySet()
          .stream().map(ItemStack::new).collect(Collectors.toList());
      MementoMoriOverride mementoMori = new MementoMoriOverride.Builder().mementoCures(mementoCures)
          .noFood(override.noFood).percentXp(override.percentXp).build();

      List<ItemStack> respawnItems = ConfigParser.parseItems(override.respawnItems).keySet()
          .stream().map(ItemStack::new).collect(Collectors.toList());
      MiscellaneousOverride misc = new MiscellaneousOverride.Builder().respawnItems(respawnItems)
          .restrictRespawning(override.restrictRespawning).build();

      OVERRIDES.add((new DeathOverride.Builder().priority(override.priority).conditions(conditions)
          .experience(experience).hunger(hunger).effects(effects).mementoMori(mementoMori)
          .miscellaneous(misc)).build());
    });
  }

  public static void apply(DeathSettings settings, LivingDeathEvent evt) {
    OVERRIDES.forEach(override -> {
      if (override.getConditions().stream()
          .anyMatch(condition -> DeathConditionManager.matches(condition, evt))) {
        override.apply(settings);
      }
    });
  }
}
