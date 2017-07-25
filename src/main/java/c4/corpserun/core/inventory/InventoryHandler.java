package c4.corpserun.core.inventory;

import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InventoryHandler extends DeathInventoryHandler {

    private InventoryPlayer inventoryPlayer;

    public InventoryHandler(EntityPlayer player) {
        super(player, "vanilla");
        inventoryPlayer = player.inventory;
    }

    protected void storeStackFromInventory(int index, ItemStack itemStack) {
        getStorage().set(index, itemStack);
    }

    protected int getSizeInventory() {

        return player.inventory.getSizeInventory();
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

    public static void retrieveStorage(EntityPlayer player, IDeathInventory deathInventory) {

        NonNullList<ItemStack> storage = deathInventory.getStorage("vanilla");
        InventoryPlayer inventoryPlayer = player.inventory;

        for (int index = 0; index < storage.size(); index++) {
            ItemStack itemStack = storage.get(index);
            if (itemStack.isEmpty()) { continue;}

            if (!inventoryPlayer.getStackInSlot(index).isEmpty()) {
                inventoryPlayer.addItemStackToInventory(storage.get(index));
            } else {
                inventoryPlayer.setInventorySlotContents(index, storage.get(index));
            }
        }
    }
}
