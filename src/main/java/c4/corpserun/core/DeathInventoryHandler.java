package c4.corpserun.core;

import c4.corpserun.capability.DeathInventoryProvider;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DeathInventoryHandler {

    private InventoryPlayer inventoryPlayer;
    private IDeathInventory deathInventory;
    private boolean toKeep;

    public DeathInventoryHandler(EntityPlayer player) {
        inventoryPlayer = player.inventory;
        deathInventory = player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
        toKeep = false;
    }

    public void iterateInventory () {

        for (int index = 0; index < inventoryPlayer.getSizeInventory(); index++) {
            ItemStack itemStack = inventoryPlayer.getStackInSlot(index);

            if (itemStack.isEmpty()) {  continue;}

            if (ConfigBool.ENABLE_INVENTORY.getValue()) {
                toKeep = DeathItemHandler.keepItem(inventoryPlayer, index, itemStack);
                if (DeathItemHandler.isCursed(itemStack) && ConfigBool.DESTROY_CURSED.getValue()){
                    inventoryPlayer.removeStackFromSlot(index);
                    continue;
                }
            }

            if (ConfigBool.ENABLE_DURABILITY_LOSS.getValue()) {
                if (toKeep) {
                    DeathItemHandler.loseDurability(inventoryPlayer.player, itemStack, ConfigFloat.KEEP_DURABILITY_LOSS.getValue());
                } else {
                    DeathItemHandler.loseDurability(inventoryPlayer.player, itemStack, ConfigFloat.DROP_DURABILITY_LOSS.getValue());
                }
            }

            if (ConfigBool.ENABLE_ENERGY_DRAIN.getValue()) {
                if (toKeep) {
                    DeathItemHandler.loseEnergy(itemStack, ConfigFloat.KEEP_ENERGY_DRAIN.getValue());
                } else {
                    DeathItemHandler.loseEnergy(itemStack, ConfigFloat.DROP_ENERGY_DRAIN.getValue());
                }
            }

            if (ConfigBool.ENABLE_INVENTORY.getValue()) {
                if (Math.random() >= ConfigFloat.RANDOM_DROP_CHANCE.getValue()) {
                    deathInventory.storeDeathItem(inventoryPlayer, index, itemStack);
//                  System.out.println("Index: " + index + ", " + "Item: " + itemStack);
                }
            }
        }
    }

    public static void addStorageToInventory(NonNullList<ItemStack> storage, InventoryPlayer inventory) {
        for (int x = 0; x < storage.size(); x++) {
            if (storage.get(x).isEmpty()) { continue;}

            if (!inventory.getStackInSlot(x).isEmpty()) {
                inventory.addItemStackToInventory(storage.get(x));
            } else {
                inventory.setInventorySlotContents(x, storage.get(x));
            }
        }
    }
}
