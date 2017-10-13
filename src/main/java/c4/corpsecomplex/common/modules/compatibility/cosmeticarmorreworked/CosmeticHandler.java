/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.compatibility.cosmeticarmorreworked;

import c4.corpsecomplex.CorpseComplex;
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
        setSize(playerCosmetics.getSizeInventory());
    }

    public boolean checkToStore(int slot) {
        return CosmeticModule.keepCosmetic;
    }

    public ItemStack getStackInSlot(int slot) {
        return playerCosmetics.getStackInSlot(slot);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {

        NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        storage.deserializeNBT(nbt);

        for (int slot = 0; slot < storage.getSlots(); slot++) {
            ItemStack stack = storage.getStackInSlot(slot);
            if ( CorpseComplex.isStackEmpty(stack)) { continue;}

            playerCosmetics.setInventorySlotContents(slot, stack);
        }
    }
}
