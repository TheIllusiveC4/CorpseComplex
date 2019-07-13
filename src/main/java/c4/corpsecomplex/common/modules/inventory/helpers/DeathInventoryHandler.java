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

import c4.corpsecomplex.common.modules.inventory.capability.DeathInventory;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import net.minecraft.entity.player.EntityPlayer;

public abstract class DeathInventoryHandler {

  public abstract boolean checkToStore(int slot);

  public abstract void storeInventory();

  public abstract void retrieveInventory(IDeathInventory oldDeathInventory);

  protected EntityPlayer player;
  protected String modid;
  protected IDeathInventory deathInventory;

  public DeathInventoryHandler(EntityPlayer player, String modid) {
    this.player = player;
    this.modid = modid;
    deathInventory = player.getCapability(DeathInventory.Provider.DEATH_INV_CAP,
            null);
  }
}
