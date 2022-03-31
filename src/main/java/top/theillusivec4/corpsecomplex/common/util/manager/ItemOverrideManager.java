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
import java.util.List;
import java.util.Optional;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.ItemOverride;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class ItemOverrideManager {

  public static final List<ItemOverride> OVERRIDES = new ArrayList<>();

  public static void importConfig() {
    OVERRIDES.clear();
    CorpseComplexConfig.itemOverrides.forEach(override -> {
      List<String> itemList = override.items;

      if (itemList == null) {
        CorpseComplex.LOGGER.error("Found item override with no items! Skipping...");
        return;
      }
      List<Item> items = new ArrayList<>();
      itemList.forEach(item -> {
        Item foundItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(item));

        if (foundItem != null) {
          items.add(foundItem);
        }
      });
      ItemOverride itemOverride = new ItemOverride.Builder().items(items)
          .dropDurabilityLoss(override.dropDurabilityLoss)
          .keepDurabilityLoss(override.keepDurabilityLoss).build();
      OVERRIDES.add(itemOverride);
    });
  }

  public static Optional<ItemOverride> getOverride(Item item) {

    for (ItemOverride override : OVERRIDES) {
      List<Item> items = override.getItems();

      if (items != null) {

        if (items.stream().anyMatch(overrideItem -> item == overrideItem)) {
          return Optional.of(override);
        }
      }
    }
    return Optional.empty();
  }
}
