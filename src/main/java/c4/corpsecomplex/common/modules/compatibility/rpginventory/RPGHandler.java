package c4.corpsecomplex.common.modules.compatibility.rpginventory;

import c4.corpsecomplex.api.capability.IDeathInventory;
import c4.corpsecomplex.api.DeathStackHandler;
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
            if (stack.isEmpty()) { continue;}

            rpgItems.setStackInSlot(slot, stack);
        }
    }
}
