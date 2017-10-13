/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.inventory.helpers;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public abstract class DeathStackHandler extends DeathInventoryHandler {

    public abstract boolean checkToStore(int index);
    public abstract ItemStack getStackInSlot(int index);
    public abstract void retrieveInventory(IDeathInventory oldDeathInventory);

    protected ItemStackHandler storage;

    public DeathStackHandler (EntityPlayer player, String modid) {
        super(player, modid);
        storage = new ItemStackHandler();
    }

    public void setSize(int size) {
        storage.setSize(size);
    }

    public void storeInventory() {

        for (int index = 0; index < storage.getSlots(); index++) {

            ItemStack stack = getStackInSlot(index);

            if (!CorpseComplex.isStackEmpty(stack)) {
                storeStack(index, DeathStackHelper.stackToStore(player, stack, checkToStore(index)));
            }

            if (!CorpseComplex.isStackEmpty(stack) && InventoryModule.randomDestroy > 0) {
                DeathStackHelper.randomlyDestroy(stack);
            }
        }

        deathInventory.addStorage(modid, storage.serializeNBT());
    }

    public void storeStack(int index, ItemStack stack) {

        if (!CorpseComplex.isStackEmpty(stack)) {
            storage.insertItem(index, stack, false);
        }
    }
}
