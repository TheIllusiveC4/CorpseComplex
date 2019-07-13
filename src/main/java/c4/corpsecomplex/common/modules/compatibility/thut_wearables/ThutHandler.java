/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.compatibility.thut_wearables;

import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import thut.wearables.ThutWearables;
import thut.wearables.inventory.PlayerWearables;

public class ThutHandler extends DeathStackHandler {

  private static final String MOD_ID = "thut_wearables";
  private PlayerWearables playerWearables;

  public ThutHandler(EntityPlayer player) {
    super(player, MOD_ID);
    playerWearables = ThutWearables.getWearables(player);
    setSize(playerWearables.getSizeInventory());
  }

  public boolean checkToStore(int slot) {
    return ThutModule.keepThut;
  }

  public ItemStack getStackInSlot(int index) {
    return playerWearables.getStackInSlot(index);
  }

  public void retrieveInventory(IDeathInventory oldDeathInventory) {

    NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);
    if (nbt == null) {
      return;
    }

    storage.deserializeNBT(nbt);

    for (int slot = 0; slot < storage.getSlots(); slot++) {
      ItemStack stack = storage.getStackInSlot(slot);
      if (stack.isEmpty()) {
        continue;
      }

      playerWearables.setInventorySlotContents(slot, stack);
    }
  }
}
