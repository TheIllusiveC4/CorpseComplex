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

package top.theillusivec4.corpsecomplex.common.util.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.DeathCondition;
import top.theillusivec4.corpsecomplex.common.DeathOverride;
import top.theillusivec4.corpsecomplex.common.DeathSettings;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.effects.EffectsOverride;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceOverride;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerOverride;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryOverride;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryOverride.SectionSettings;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriOverride;
import top.theillusivec4.corpsecomplex.common.modules.miscellaneous.MiscellaneousOverride;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;

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
      List<String> conditionsConfig = override.conditions;

      if (conditionsConfig == null || conditionsConfig.isEmpty()) {
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

      List<ItemStack> cures = ConfigParser
          .parseItems(override.cures != null ? override.cures : CorpseComplexConfig.cures).keySet()
          .stream().map(ItemStack::new).collect(Collectors.toList());
      List<EffectInstance> effectsConfig =
          override.effects != null ? ConfigParser.parseEffectInstances(override.effects, cures)
              : null;
      List<Effect> keepEffectsConfig =
          override.keepEffects != null ? ConfigParser.parseEffects(override.keepEffects) : null;
      EffectsOverride effects = new EffectsOverride.Builder().cures(cures).effects(effectsConfig)
          .keepEffectsMode(override.keepEffectsMode).keepEffects(keepEffectsConfig).build();

      List<ItemStack> mementoCures =
          override.mementoCures != null ? ConfigParser.parseItems(override.mementoCures).keySet()
              .stream().map(ItemStack::new).collect(Collectors.toList()) : null;
      MementoMoriOverride mementoMori = new MementoMoriOverride.Builder().mementoCures(mementoCures)
          .noFood(override.noFood).percentXp(override.percentXp).build();

      List<ItemStack> respawnItems =
          override.respawnItems != null ? ConfigParser.parseItems(override.respawnItems).keySet()
              .stream().map(ItemStack::new).collect(Collectors.toList()) : null;
      List<EntityType<?>> mobSpawnsOnDeath =
          override.mobSpawnsOnDeath != null ? ConfigParser.parseMobs(override.mobSpawnsOnDeath)
              : null;
      MiscellaneousOverride misc = new MiscellaneousOverride.Builder().respawnItems(respawnItems)
          .restrictRespawning(override.restrictRespawning).mobSpawnsOnDeath(mobSpawnsOnDeath)
          .build();

      Map<InventorySection, SectionSettings> inventorySettings = new HashMap<>();
      inventorySettings.put(InventorySection.DEFAULT,
          new SectionSettings(override.keepChance, override.destroyChance,
              override.keepDurabilityLoss, override.dropDurabilityLoss));
      inventorySettings.put(InventorySection.MAINHAND,
          new SectionSettings(override.mainhandKeepChance, override.mainhandDestroyChance,
              override.mainhandKeepDurabilityLoss, override.mainhandDropDurabilityLoss));
      inventorySettings.put(InventorySection.OFFHAND,
          new SectionSettings(override.offhandKeepChance, override.offhandDestroyChance,
              override.offhandKeepDurabilityLoss, override.offhandDropDurabilityLoss));
      inventorySettings.put(InventorySection.HOTBAR,
          new SectionSettings(override.hotbarKeepChance, override.hotbarDestroyChance,
              override.hotbarKeepDurabilityLoss, override.hotbarDropDurabilityLoss));
      inventorySettings.put(InventorySection.MAIN,
          new SectionSettings(override.mainKeepChance, override.mainDestroyChance,
              override.mainKeepDurabilityLoss, override.mainDropDurabilityLoss));
      inventorySettings.put(InventorySection.HEAD,
          new SectionSettings(override.headKeepChance, override.headDestroyChance,
              override.headKeepDurabilityLoss, override.headDropDurabilityLoss));
      inventorySettings.put(InventorySection.CHEST,
          new SectionSettings(override.chestKeepChance, override.chestDestroyChance,
              override.chestKeepDurabilityLoss, override.chestDropDurabilityLoss));
      inventorySettings.put(InventorySection.LEGS,
          new SectionSettings(override.legsKeepChance, override.legsDestroyChance,
              override.legsKeepDurabilityLoss, override.legsDropDurabilityLoss));
      inventorySettings.put(InventorySection.FEET,
          new SectionSettings(override.feetKeepChance, override.feetDestroyChance,
              override.feetKeepDurabilityLoss, override.feetDropDurabilityLoss));
      inventorySettings.put(InventorySection.CURIOS,
          new SectionSettings(override.curioKeepChance, override.curioDestroyChance,
              override.curioKeepDurabilityLoss, override.curioDropDurabilityLoss));
      inventorySettings.put(InventorySection.COSMETIC_ARMOR,
          new SectionSettings(override.cosmeticArmorKeepChance, override.cosmeticArmorDestroyChance,
              override.cosmeticArmorKeepDurabilityLoss, override.cosmeticArmorDropDurabilityLoss));

      Map<Item, DropMode> itemSettings =
          override.itemSettings != null ? ConfigParser.parseDrops(override.itemSettings) : null;
      InventoryOverride inventory = new InventoryOverride.Builder()
          .inventorySettings(inventorySettings).items(itemSettings)
          .limitDurabilityLoss(override.limitDurabilityLoss)
          .dropDespawnTime(override.dropDespawnTime).build();

      OVERRIDES.add((new DeathOverride.Builder().priority(override.priority).inventory(inventory)
          .conditions(conditions).experience(experience).hunger(hunger).effects(effects)
          .mementoMori(mementoMori).miscellaneous(misc)).build());
    });
  }

  public static void apply(DeathSettings settings, IDeathStorage deathStorage) {
    OVERRIDES.forEach(override -> {
      if (override.getConditions().stream()
          .allMatch(condition -> DeathConditionManager.matches(condition, deathStorage))) {
        override.apply(settings);
      }
    });
  }
}
