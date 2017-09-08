/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.compatibility.baubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class BaublesHandler extends DeathStackHandler {

    private static final String MOD_ID = "baubles";
    private IBaublesItemHandler playerBaubles;

    public BaublesHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerBaubles = BaublesApi.getBaublesHandler(player);
        setSize(playerBaubles.getSlots());
    }

    public boolean checkToStore(int slot) {
        return BaublesModule.keepBaubles;
    }

    public ItemStack getStackInSlot(int index) {
        return playerBaubles.getStackInSlot(index);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {

        NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        storage.deserializeNBT(nbt);

        for (int slot = 0; slot < storage.getSlots(); slot++) {
            ItemStack stack = storage.getStackInSlot(slot);
            if (stack.isEmpty()) { continue;}

            playerBaubles.setStackInSlot(slot, stack);
        }
    }
}
