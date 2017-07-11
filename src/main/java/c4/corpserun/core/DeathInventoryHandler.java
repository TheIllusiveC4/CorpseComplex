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
    private float durabilityLoss;
    private float energyLoss;
    private boolean toKeep;

    public DeathInventoryHandler(EntityPlayer player) {
        inventoryPlayer = player.inventory;
        deathInventory = player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
        durabilityLoss = 0;
        energyLoss = 0;
        toKeep = false;
    }

    public void iterateInventory () {

        for (int index = 0; index < inventoryPlayer.getSizeInventory(); index++) {
            ItemStack itemStack = inventoryPlayer.getStackInSlot(index);

            if (itemStack.isEmpty()) {  continue;}

            assignConfigValues(index);

            if (ConfigBool.ENABLE_DURABILITY_LOSS.getValue()) {
                DeathItemHandler.loseDurability(inventoryPlayer.player, itemStack, durabilityLoss);
            }

            if (ConfigBool.ENABLE_ENERGY_DRAIN.getValue()) {
                DeathItemHandler.loseEnergy(itemStack, energyLoss);
            }

            if (ConfigBool.ENABLE_INVENTORY.getValue()) {
                storeItem(itemStack, index);
            }
        }
    }

    private void assignConfigValues (int index) {
        if (index == inventoryPlayer.currentItem) {
            durabilityLoss = ConfigFloat.MAINHAND_DURABILITY_LOSS.getValue();
            energyLoss = ConfigFloat.MAINHAND_ENERGY_LOSS.getValue();
            toKeep = ConfigBool.KEEP_MAINHAND.getValue();
        }
        else if (index < 9) {
            durabilityLoss = ConfigFloat.HOTBAR_DURABILITY_LOSS.getValue();
            energyLoss = ConfigFloat.HOTBAR_ENERGY_LOSS.getValue();
            toKeep = ConfigBool.KEEP_HOTBAR.getValue();
        }
        else if (index >= 9 && index < 36) {
            durabilityLoss = ConfigFloat.MAIN_INVENTORY_DURABILITY_LOSS.getValue();
            energyLoss = ConfigFloat.MAIN_INVENTORY_ENERGY_LOSS.getValue();
            toKeep = ConfigBool.KEEP_MAIN_INVENTORY.getValue();
        }
        else if (index >= 36 && index < 40) {
            durabilityLoss = ConfigFloat.ARMOR_DURABILITY_LOSS.getValue();
            energyLoss = ConfigFloat.ARMOR_ENERGY_LOSS.getValue();
            toKeep = ConfigBool.KEEP_ARMOR.getValue();
        }
        else if (index == 40) {
            durabilityLoss = ConfigFloat.OFFHAND_DURABILITY_LOSS.getValue();
            energyLoss = ConfigFloat.OFFHAND_ENERGY_LOSS.getValue();
            toKeep = ConfigBool.KEEP_OFFHAND.getValue();
        }
    }

    private void storeItem(ItemStack itemStack, int index) {

        if (DeathItemHandler.keepItem(itemStack, toKeep)) {
            deathInventory.storeDeathItem(inventoryPlayer, index, itemStack);
//            System.out.println("Index: " + index + ", " + "Item: " + itemStack);
        } else {
            if (ConfigBool.DESTROY_CURSED.getValue()) {
                inventoryPlayer.removeStackFromSlot(index);
            }
        }
    }

    public static void addStorageContents(NonNullList<ItemStack> storage, InventoryPlayer inventory) {
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
