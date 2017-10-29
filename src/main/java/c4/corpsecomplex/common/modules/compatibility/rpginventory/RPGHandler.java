/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.rpginventory;

import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import subaraki.rpginventory.capability.playerinventory.RpgInventoryData;
import subaraki.rpginventory.capability.playerinventory.RpgStackHandler;

public class RPGHandler extends DeathStackHandler {

    private static final String MOD_ID = "rpginventory";
    private RpgStackHandler rpgItems;

    public RPGHandler (EntityPlayer player) {
        super(player, MOD_ID);
        rpgItems = RpgInventoryData.get(player).getInventory();
        setSize(rpgItems.getSlots());
    }
    public boolean checkToStore(int slot) {
        return RPGModule.keepRPG;
    }

    public ItemStack getStackInSlot(int slot) {
        return rpgItems.getStackInSlot(slot);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {
        NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        storage.deserializeNBT(nbt);

        for (int slot = 0; slot < storage.getSlots(); slot++) {
            ItemStack stack = storage.getStackInSlot(slot);
            if (stack.isEmpty()) { continue;}

            rpgItems.setStackInSlot(slot, stack);
        }
    }
}
