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

package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.InventoryHelper;

public class VanillaInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity player = deathStorage.getPlayer();

    if (player != null) {
      PlayerInventory inventory = player.inventory;
      InventorySetting setting = deathStorage.getSettings().getInventorySettings();
      ListNBT list = new ListNBT();

      for (int i = 0; i < 9; i++) {
        InventoryHelper.process(player, inventory.getStackInSlot(i), i, list,
            i == inventory.currentItem ? InventorySection.MAINHAND : InventorySection.HOTBAR,
            setting);
      }

      for (int i = 9; i < 36; i++) {
        InventoryHelper
            .process(player, inventory.getStackInSlot(i), i, list, InventorySection.MAIN, setting);
      }
      InventoryHelper
          .process(player, inventory.getStackInSlot(36), 36, list, InventorySection.FEET, setting);
      InventoryHelper
          .process(player, inventory.getStackInSlot(37), 37, list, InventorySection.LEGS, setting);
      InventoryHelper
          .process(player, inventory.getStackInSlot(38), 38, list, InventorySection.CHEST, setting);
      InventoryHelper
          .process(player, inventory.getStackInSlot(39), 39, list, InventorySection.HEAD, setting);
      InventoryHelper
          .process(player, inventory.getStackInSlot(40), 40, list, InventorySection.OFFHAND,
              setting);
      deathStorage.addInventory("vanilla", list);
    }
  }

  @Override
  public void retrieveInventory(IDeathStorage newStorage, IDeathStorage oldStorage) {
    PlayerEntity player = newStorage.getPlayer();
    PlayerEntity oldPlayer = oldStorage.getPlayer();

    if (player != null && oldPlayer != null) {
      ListNBT list = (ListNBT) oldStorage.getInventory("vanilla");
      PlayerInventory inventory = player.inventory;

      if (list != null) {

        for (int i = 0; i < list.size(); ++i) {
          CompoundNBT compoundnbt = list.getCompound(i);
          int slot = compoundnbt.getInt("Slot");
          ItemStack itemstack = ItemStack.read(compoundnbt);
          if (!itemstack.isEmpty()) {
            ItemStack existing = inventory.getStackInSlot(slot);

            if (existing.isEmpty()) {
              inventory.setInventorySlotContents(slot, itemstack);
            } else {
              ItemHandlerHelper.giveItemToPlayer(inventory.player, itemstack);
            }
          }
        }
      }
    }
  }
}
