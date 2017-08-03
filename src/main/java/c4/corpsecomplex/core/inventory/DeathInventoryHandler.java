package c4.corpserun.core.inventory;

import c4.corpserun.capability.DeathInventory;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

public class DeathInventoryHandler {

    protected EntityPlayer player;
    protected String modid;
    protected IDeathInventory deathInventory;

    public DeathInventoryHandler (EntityPlayer player, String modid) {
        this.player = player;
        this.modid = modid;
        deathInventory = player.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null);
    }
}
