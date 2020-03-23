/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.compatibility.toolbelt;

import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import gigaherz.toolbelt.slot.ExtensionSlotBelt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ToolbeltHandler extends DeathStackHandler {

  private static final String MOD_ID = "toolbelt";
  private ExtensionSlotBelt beltSlot;

  public ToolbeltHandler(EntityPlayer player) {
    super(player, MOD_ID);
    beltSlot = ExtensionSlotBelt.get(player);
  }

  public boolean checkToStore(int slot) {
    return ToolbeltModule.keepToolbelt;
  }

  public ItemStack getStackInSlot(int index) {
    return beltSlot.getBelt().getContents();
  }

  public void retrieveInventory(IDeathInventory oldDeathInventory) {

    NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);

    if (nbt == null) {
      return;
    }

    storage.deserializeNBT(nbt);
    beltSlot.getBelt().setContents(storage.getStackInSlot(0));
  }
}
