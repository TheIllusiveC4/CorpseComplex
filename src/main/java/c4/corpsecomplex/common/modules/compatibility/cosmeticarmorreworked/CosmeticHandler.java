/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.cosmeticarmorreworked;

import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import lain.mods.cos.CosmeticArmorReworked;
import lain.mods.cos.inventory.InventoryCosArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CosmeticHandler extends DeathStackHandler {

    private static final String MOD_ID = "cosmeticarmorreworked";
    private InventoryCosArmor playerCosmetics;

    public CosmeticHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerCosmetics = CosmeticArmorReworked.invMan.getCosArmorInventory(player.getUniqueID());
        setSize(playerCosmetics.func_70302_i_());
    }

    public boolean checkToStore(int slot) {
        return CosmeticModule.keepCosmetic;
    }

    public ItemStack getStackInSlot(int slot) {
        return playerCosmetics.func_70301_a(slot);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {

        NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        storage.deserializeNBT(nbt);

        for (int slot = 0; slot < storage.getSlots(); slot++) {
            ItemStack stack = storage.getStackInSlot(slot);
            if (stack.isEmpty()) { continue;}

            playerCosmetics.func_70299_a(slot, stack);
        }
    }
}
