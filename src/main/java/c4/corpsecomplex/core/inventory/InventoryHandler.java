package c4.corpsecomplex.core.inventory;

import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryHandler extends DeathStackHandler {

    private static final String MOD_ID = "vanilla";

    private InventoryPlayer inventoryPlayer;

    public InventoryHandler(EntityPlayer player) {
        super(player, MOD_ID);
        inventoryPlayer = player.inventory;
        setSize(inventoryPlayer.getSizeInventory());
    }

    public ItemStack getStackInSlot(int index) {

        return player.inventory.getStackInSlot(index);
    }

    public boolean checkConfig(int index) {

        if (index == inventoryPlayer.currentItem) {
            return InventoryModule.keepMainhand;
        }
        else if (index < 9) {
            return InventoryModule.keepHotbar;
        }
        else if (index >= 9 && index < 36) {
            return InventoryModule.keepMainInventory;
        }
        else if (index >= 36 && index < 40) {
            return InventoryModule.keepArmor;
        }
        else if (index == 40) {
            return InventoryModule.keepOffhand;
        }
        return false;
    }

    public static void retrieveInventory(EntityPlayer player, IDeathInventory oldInventory) {

        NBTTagCompound nbt = oldInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        ItemStackHandler storage = new ItemStackHandler();
        storage.deserializeNBT(nbt);
        InventoryPlayer inventoryPlayer = player.inventory;

        for (int index = 0; index < storage.getSlots(); index++) {
            ItemStack stack = storage.getStackInSlot(index);
            if (stack.isEmpty()) { continue;}

            if (!inventoryPlayer.getStackInSlot(index).isEmpty()) {
                inventoryPlayer.addItemStackToInventory(stack);
            } else {
                inventoryPlayer.setInventorySlotContents(index, stack);
            }
        }
    }
}
