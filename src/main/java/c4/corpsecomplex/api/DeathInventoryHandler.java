package c4.corpsecomplex.api;

import c4.corpsecomplex.api.capability.DeathInventory;
import c4.corpsecomplex.api.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.InventoryModule;
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

    public static void addHandler(Class<? extends DeathInventoryHandler> handler) {
        if (!InventoryModule.handlerClasses.contains(handler)) {
            InventoryModule.handlerClasses.add(handler);
        }
    }

    public static void addHandler(Class<? extends DeathInventoryHandler> handler, int index) {
        if (!InventoryModule.handlerClasses.contains(handler)) {
            InventoryModule.handlerClasses.add(index, handler);
        }
    }
}
