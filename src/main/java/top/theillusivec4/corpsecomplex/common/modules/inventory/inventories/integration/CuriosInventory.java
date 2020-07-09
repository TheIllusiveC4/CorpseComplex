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

package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.Inventory;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.InventoryHelper;
import top.theillusivec4.curios.api.CuriosApi;

public class CuriosInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity playerEntity = deathStorage.getPlayer();
    ListNBT list = new ListNBT();
    CuriosApi.getCuriosHelper().getCuriosHandler(playerEntity)
        .ifPresent(curioHandler -> curioHandler.getCurios().forEach((id, stackHandler) -> {
          ListNBT list1 = new ListNBT();
          ListNBT list2 = new ListNBT();

          for (int i = 0; i < stackHandler.getSlots(); i++) {
            InventoryHelper.process((PlayerEntity) curioHandler.getWearer(),
                stackHandler.getStacks().getStackInSlot(i), i, list1, InventorySection.CURIOS,
                deathStorage.getSettings().getInventorySettings());
            InventoryHelper.process((PlayerEntity) curioHandler.getWearer(),
                stackHandler.getCosmeticStacks().getStackInSlot(i), i, list2,
                InventorySection.CURIOS, deathStorage.getSettings().getInventorySettings());
          }
          CompoundNBT tag = new CompoundNBT();
          tag.putString("Identifier", id);
          tag.put("Stacks", list1);
          tag.put("CosmeticStacks", list2);
          list.add(tag);
        }));
    deathStorage.addInventory("curios", list);
  }

  @Override
  public void retrieveInventory(IDeathStorage newStorage, IDeathStorage oldStorage) {
    PlayerEntity player = newStorage.getPlayer();
    PlayerEntity oldPlayer = oldStorage.getPlayer();

    if (player != null && oldPlayer != null) {
      ListNBT list = (ListNBT) oldStorage.getInventory("curios");
      CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(newHandler -> {

        for (int i = 0; i < list.size(); i++) {
          CompoundNBT tag = list.getCompound(i);
          String id = tag.getString("Identifier");
          newHandler.getStacksHandler(id).ifPresent(stacksHandler -> {
            ListNBT stacks = tag.getList("Stacks", NBT.TAG_COMPOUND);

            for (int j = 0; j < stacks.size(); j++) {
              CompoundNBT compoundnbt = stacks.getCompound(j);
              int slot = compoundnbt.getInt("Slot");
              ItemStack itemstack = ItemStack.read(compoundnbt);

              if (!itemstack.isEmpty()) {
                ItemStack existing = stacksHandler.getStacks().getStackInSlot(slot);

                if (existing.isEmpty()) {
                  stacksHandler.getStacks().setStackInSlot(slot, itemstack);
                  CuriosApi.getCuriosHelper().getCurio(itemstack).ifPresent((curio) -> {
                    player.getAttributeManager().func_233793_b_(curio.getAttributeModifiers(id));
                    curio.onEquip(id, slot, player);
                  });
                } else {
                  ItemHandlerHelper.giveItemToPlayer(player, itemstack);
                }
              }
            }
            ListNBT cosmeticStacks = tag.getList("CosmeticStacks", NBT.TAG_COMPOUND);

            for (int j = 0; j < cosmeticStacks.size(); j++) {
              CompoundNBT compoundnbt = stacks.getCompound(j);
              int slot = compoundnbt.getInt("Slot");
              ItemStack itemstack = ItemStack.read(compoundnbt);

              if (!itemstack.isEmpty()) {
                ItemStack existing = stacksHandler.getCosmeticStacks().getStackInSlot(slot);

                if (existing.isEmpty()) {
                  stacksHandler.getCosmeticStacks().setStackInSlot(slot, itemstack);
                } else {
                  ItemHandlerHelper.giveItemToPlayer(player, itemstack);
                }
              }
            }
          });
        }
      });
    }
  }
}
