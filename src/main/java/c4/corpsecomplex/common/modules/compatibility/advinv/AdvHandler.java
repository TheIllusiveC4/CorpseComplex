package c4.corpsecomplex.common.modules.compatibility.advinv;

import c4.corpsecomplex.api.DeathStackHandler;
import c4.corpsecomplex.api.capability.IDeathInventory;
import cubex2.advInv.api.AdvInvAPI;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AdvHandler extends DeathStackHandler {

    private static final String MOD_ID = "advinv";
    private IInventory playerAdv;

    public AdvHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerAdv = AdvInvAPI.getExtendedInventory(player);
        setSize(playerAdv.getSizeInventory());
    }

    public boolean checkToStore(int slot) {
        return AdvModule.keepAdv;
    }

    public ItemStack getStackInSlot(int index) {
        return playerAdv.getStackInSlot(index);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {

        NBTTagCompound nbt = oldDeathInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        storage.deserializeNBT(nbt);

        for (int slot = 0; slot < storage.getSlots(); slot++) {
            ItemStack stack = storage.getStackInSlot(slot);
            if (stack.isEmpty()) { continue;}

            playerAdv.setInventorySlotContents(slot, stack);
        }
    }
}
