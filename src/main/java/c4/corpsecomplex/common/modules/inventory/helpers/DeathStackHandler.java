/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.inventory.helpers;

import c4.corpsecomplex.common.modules.inventory.InventoryHandler;
import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public abstract class DeathStackHandler extends DeathInventoryHandler {

  public abstract boolean checkToStore(int index);

  public abstract ItemStack getStackInSlot(int index);

  public abstract void retrieveInventory(IDeathInventory oldDeathInventory);

  protected ItemStackHandler storage;

  public DeathStackHandler(EntityPlayer player, String modid) {
    super(player, modid);
    storage = new ItemStackHandler();
  }

  public void setSize(int size) {
    storage.setSize(size);
  }

  public void storeInventory() {

    for (int index = 0; index < storage.getSlots(); index++) {

      ItemStack stack = getStackInSlot(index);

      if (!stack.isEmpty()) {
        boolean isMainInventory =
                this instanceof InventoryHandler && index >= 9 && index < 36;
        storeStack(index, DeathStackHelper
                .stackToStore(player, stack, checkToStore(index),
                        isMainInventory));
      }

      if (!stack.isEmpty() && InventoryModule.randomDestroy > 0) {
        DeathStackHelper.randomlyDestroy(stack);
      }
    }

    deathInventory.addStorage(modid, storage.serializeNBT());
  }

  public void storeStack(int index, ItemStack stack) {

    if (!stack.isEmpty()) {
      storage.insertItem(index, stack, false);
    }
  }
}
