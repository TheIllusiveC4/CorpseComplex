/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.compatibility.rpginventory;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import subaraki.rpginventory.capability.playerinventory.RpgInventoryCapability;
import subaraki.rpginventory.capability.playerinventory.RpgPlayerInventory;
import subaraki.rpginventory.capability.playerinventory.RpgStackHandler;

public class RPGHandler extends DeathStackHandler {

    private static final String MOD_ID = "rpginventory";
    private RpgStackHandler rpgItems;

    public RPGHandler (EntityPlayer player) {
        super(player, MOD_ID);
        RpgPlayerInventory inventory = player.getCapability(RpgInventoryCapability.CAPABILITY, null);
        rpgItems = inventory.getTheRpgInventory();
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
            if ( CorpseComplex.isStackEmpty(stack)) { continue;}

            rpgItems.setStackInSlot(slot, stack);
        }
    }
}
