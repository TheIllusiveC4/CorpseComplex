package c4.corpsecomplex.core.inventory;

import c4.corpsecomplex.capability.DeathInventory;
import c4.corpsecomplex.capability.IDeathInventory;
import net.minecraft.entity.player.EntityPlayer;

public abstract class DeathInventoryHandler {

    protected EntityPlayer player;
    protected String modid;
    protected IDeathInventory deathInventory;
    protected boolean cfgStore;

    public DeathInventoryHandler (EntityPlayer player, String modid) {
        this.player = player;
        this.modid = modid;
        deathInventory = player.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null);
    }

    public abstract void storeInventory();
}
