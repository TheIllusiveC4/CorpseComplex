package c4.corpsecomplex.compatibility.rpginventory;

import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.inventory.DeathStackHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import subaraki.rpginventory.capability.playerinventory.RpgInventoryData;
import subaraki.rpginventory.capability.playerinventory.RpgStackHandler;

public class RPGHandler extends DeathStackHandler {

    private static final String MOD_ID = "rpginventory";
    private RpgStackHandler rpgItems;

    public RPGHandler (EntityPlayer player) {
        super(player, MOD_ID);
        rpgItems = RpgInventoryData.get(player).getInventory();
        setSize(rpgItems.getSlots());
    }
    public boolean checkConfig(int index) {
        return RPGModule.keepRPG;
    }

    public ItemStack getStackInSlot(int index) {
        return rpgItems.getStackInSlot(index);
    }

    public static void retrieveInventory(EntityPlayer player, IDeathInventory oldInventory) {
        NBTTagCompound nbt = oldInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        ItemStackHandler storage = new ItemStackHandler();
        storage.deserializeNBT(nbt);
        RpgStackHandler rpgItems = RpgInventoryData.get(player).getInventory();

        for (int index = 0; index < storage.getSlots(); index++) {
            ItemStack stack = storage.getStackInSlot(index);
            if (stack.isEmpty()) { continue;}

            rpgItems.setStackInSlot(index, stack);
        }
    }
}
