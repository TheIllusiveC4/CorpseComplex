package c4.corpsecomplex.compatibility.thut_wearables;

import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.inventory.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import thut.wearables.ThutWearables;
import thut.wearables.inventory.PlayerWearables;

public class ThutHandler extends DeathStackHandler {

    private static final String MOD_ID = "thut_wearables";
    private PlayerWearables playerWearables;

    public ThutHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerWearables = ThutWearables.getWearables(player);
        setSize(playerWearables.getSizeInventory());
        cfgStore = ThutModule.keepThut;
    }

    public boolean checkConfig(int index) {
        return cfgStore;
    }

    public ItemStack getStackInSlot(int index) {
        return playerWearables.getStackInSlot(index);
    }

    public static void retrieveInventory(EntityPlayer player, IDeathInventory oldInventory) {

        NBTTagCompound nbt = oldInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        ItemStackHandler storage = new ItemStackHandler();
        storage.deserializeNBT(nbt);
        PlayerWearables playerWearables = ThutWearables.getWearables(player);

        for (int index = 0; index < storage.getSlots(); index++) {
            ItemStack stack = storage.getStackInSlot(index);
            if (stack.isEmpty()) { continue;}

            playerWearables.setInventorySlotContents(index, stack);
        }
    }
}
