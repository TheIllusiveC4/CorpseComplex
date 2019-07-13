/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.inventory.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface IDeathInventory {

  NBTTagCompound getStorage(String modid);

  void addStorage(String modid, NBTTagCompound storage);

  void setDeathInventory(NBTTagCompound nbt);

  NBTTagCompound getDeathInventory();

}
