package c4.corpserun.core.inventory;

import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class InventoryHandler extends DeathStackHandler {

    private static final String MOD_ID = "vanilla";

    private InventoryPlayer inventoryPlayer;

    public InventoryHandler(EntityPlayer player) {
        super(player, MOD_ID, player.inventory.getSizeInventory());
        inventoryPlayer = player.inventory;
    }

    protected void storeStackFromInventory(int index, ItemStack itemStack) {
        storage.insertItem(index, itemStack, false);
    }

    protected ItemStack getStackInSlot(int index) {

        return player.inventory.getStackInSlot(index);
    }

    protected void removeStackFromSlot(int index) {
        player.inventory.removeStackFromSlot(index);
    }

    protected boolean toStoreStack(int index, ItemStack itemStack) {

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

    public static void retrieve(EntityPlayer player, IDeathInventory deathInventory) {

        NBTTagCompound nbt = deathInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        ItemStackHandler storage = new ItemStackHandler();
        storage.deserializeNBT(nbt);
        InventoryPlayer inventoryPlayer = player.inventory;

        for (int index = 0; index < storage.getSlots(); index++) {
            ItemStack itemStack = storage.getStackInSlot(index);
            if (itemStack.isEmpty()) { continue;}

            if (!inventoryPlayer.getStackInSlot(index).isEmpty()) {
                inventoryPlayer.addItemStackToInventory(itemStack);
            } else {
                inventoryPlayer.setInventorySlotContents(index, itemStack);
            }
        }
    }
}
