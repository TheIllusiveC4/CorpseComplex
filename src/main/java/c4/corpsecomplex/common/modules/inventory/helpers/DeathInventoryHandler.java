/*
 * Copyright (c) 2017. C4, MIT License
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

    public DeathInventoryHandler (EntityPlayer player, String modid) {
        this.player = player;
        this.modid = modid;
        deathInventory = player.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null);
    }
}
