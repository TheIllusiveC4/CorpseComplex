package c4.corpsecomplex.compatibility.baubles;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.inventory.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class BaublesHandler extends DeathStackHandler {

    private static final String MOD_ID = "baubles";
    private IBaublesItemHandler playerBaubles;

    public BaublesHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerBaubles = BaublesApi.getBaublesHandler(player);
        setSize(playerBaubles.getSlots());
        cfgStore = BaublesModule.keepBaubles;
    }

    public boolean checkConfig(int index) {
        return cfgStore;
    }

    public ItemStack getStackInSlot(int index) {
        return playerBaubles.getStackInSlot(index);
    }

    public static void retrieveInventory(EntityPlayer player, IDeathInventory oldInventory) {

        NBTTagCompound nbt = oldInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        ItemStackHandler storage = new ItemStackHandler();
        storage.deserializeNBT(nbt);
        IBaublesItemHandler playerBaubles = BaublesApi.getBaublesHandler(player);

        for (int index = 0; index < storage.getSlots(); index++) {
            ItemStack stack = storage.getStackInSlot(index);
            if (stack.isEmpty()) { continue;}

            playerBaubles.setStackInSlot(index, stack);
        }
    }
}
