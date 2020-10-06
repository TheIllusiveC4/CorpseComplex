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

package top.theillusivec4.corpsecomplex.common.util;

import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting.SectionSettings;
import top.theillusivec4.corpsecomplex.common.registry.CorpseComplexRegistry;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.manager.ItemOverrideManager;

public class InventoryHelper {

  public static final Random RAND = new Random();

  public static void process(PlayerEntity player, ItemStack stack, int index, ListNBT list,
      InventorySection section, InventorySetting setting) {
    DropMode inventoryRule = getDropModeOverride(stack, setting);
    SectionSettings defaultSettings = setting.getInventorySettings().get(InventorySection.DEFAULT);
    SectionSettings sectionSettings = setting.getInventorySettings().get(section);
    double keepChance =
        sectionSettings.keepChance >= 0 ? sectionSettings.keepChance : defaultSettings.keepChance;
    double destroyChance = sectionSettings.destroyChance >= 0 ? sectionSettings.destroyChance
        : defaultSettings.destroyChance;

    if (inventoryRule != null) {
      if (inventoryRule == DropMode.KEEP) {
        keepChance = 1.0D;
      } else if (inventoryRule == DropMode.DESTROY) {
        destroyChance = 1.0D;
      } else if (inventoryRule == DropMode.DROP) {
        keepChance = 0.0D;
      }
    }
    ItemStack keep = stack.split(getRandomAmount(stack.getCount(), keepChance));

    if (!keep.isEmpty()) {
      double keepDurabilityLoss =
          sectionSettings.keepDurabilityLoss >= 0 ? sectionSettings.keepDurabilityLoss
              : defaultSettings.keepDurabilityLoss;
      double finalKeepDurabilityLoss = keepDurabilityLoss;
      keepDurabilityLoss = ItemOverrideManager.getOverride(keep.getItem())
          .map(override -> override.getKeepDurabilityLoss().orElse(finalKeepDurabilityLoss))
          .orElse(keepDurabilityLoss);
      applyDurabilityLoss(player, keep, setting, keepDurabilityLoss);
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putInt("Slot", index);
      keep.write(compoundnbt);
      list.add(compoundnbt);
    }
    double dropDurabilityLoss =
        sectionSettings.dropDurabilityLoss >= 0 ? sectionSettings.dropDurabilityLoss
            : defaultSettings.dropDurabilityLoss;
    double finalDropDurabilityLoss = dropDurabilityLoss;
    dropDurabilityLoss = ItemOverrideManager.getOverride(stack.getItem())
        .map(override -> override.getDropDurabilityLoss().orElse(finalDropDurabilityLoss))
        .orElse(dropDurabilityLoss);
    applyDurabilityLoss(player, stack, setting, dropDurabilityLoss);
    stack.shrink(getRandomAmount(stack.getCount(), destroyChance));
  }

  @Nullable
  public static DropMode getDropModeOverride(ItemStack stack, InventorySetting setting) {

    if (saveSoulbound(stack)) {
      return DropMode.KEEP;
    } else if (EnchantmentHelper.hasVanishingCurse(stack)) {
      return DropMode.DESTROY;
    }
    return setting.getItems().get(stack.getItem());
  }

  public static void applyDurabilityLoss(PlayerEntity playerEntity, ItemStack stack,
      InventorySetting setting, double durabilityLoss) {

    if (!stack.isDamageable()) {
      return;
    }
    LazyOptional<IEnergyStorage> energyStorage = stack.getCapability(CapabilityEnergy.ENERGY);

    energyStorage.ifPresent(energy -> {
      int energyLoss = (int) Math.round(energy.getMaxEnergyStored() * durabilityLoss);
      if (energy.canExtract()) {
        energy.extractEnergy(energyLoss, false);
      }
    });
    int maxLoss = setting.isLimitDurabilityLoss() ? stack.getMaxDamage() - stack.getDamage() - 1
        : stack.getMaxDamage();
    int loss = (int) Math.round(stack.getMaxDamage() * durabilityLoss);
    stack.damageItem(Math.min(maxLoss, loss), playerEntity, damager -> {
    });
  }

  public static int getRandomAmount(int num, double chance) {

    if (chance >= 1.0D) {
      return num;
    } else if (chance <= 0.0D) {
      return 0;
    }
    int amount = 0;

    for (int i = 0; i < num; i++) {
      if (RAND.nextDouble() < chance) {
        amount++;
      }
    }
    return amount;
  }

  private static boolean saveSoulbound(ItemStack stack) {
    int level = EnchantmentHelper.getEnchantmentLevel(CorpseComplexRegistry.SOULBINDING, stack);

    if (level <= 0) {
      return false;
    }

    double savePercent =
        CorpseComplexConfig.baseSave + CorpseComplexConfig.extraSavePerLevel * (level - 1);
    boolean activated = false;

    if (RAND.nextDouble() < savePercent) {
      activated = true;
    }
    double levelDropChance = CorpseComplexConfig.levelDropChance;

    if (levelDropChance > 0 && activated) {
      if (RAND.nextDouble() < levelDropChance) {
        level = Math.max(0, level - 1);
      }

      Map<Enchantment, Integer> enchMap = EnchantmentHelper.getEnchantments(stack);
      enchMap.remove(CorpseComplexRegistry.SOULBINDING);

      if (level > 0) {
        enchMap.put(CorpseComplexRegistry.SOULBINDING, level);
      }
      EnchantmentHelper.setEnchantments(enchMap, stack);
    }
    return activated;
  }
}
