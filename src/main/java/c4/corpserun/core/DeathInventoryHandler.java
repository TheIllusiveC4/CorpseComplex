package c4.corpserun.core;

import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import c4.corpserun.config.values.ConfigStringList;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DeathInventoryHandler {

    private InventoryPlayer inventoryPlayer;
    private NonNullList<ItemStack> storage;

    public DeathInventoryHandler(InventoryPlayer inventory) {

        inventoryPlayer = inventory;
        storage = NonNullList.withSize(inventoryPlayer.getSizeInventory(), ItemStack.EMPTY);
        iterateInventory();
    }

    public NonNullList<ItemStack> getStorage() {
        return storage;
    }

    private void iterateInventory () {

        for (int index = 0; index < inventoryPlayer.getSizeInventory(); index++) {

            ItemStack itemStack = inventoryPlayer.getStackInSlot(index);

            if (!ConfigBool.KEEP_HOTBAR.value && index == inventoryPlayer.currentItem)
                { storeItem(index, itemStack, ConfigBool.KEEP_MAINHAND.value, ConfigFloat.MAINHAND_DURABILITY_LOSS.value);}
            else if (index < 9)
                { storeItem(index, itemStack, ConfigBool.KEEP_HOTBAR.value, ConfigFloat.HOTBAR_DURABILITY_LOSS.value);}
            else if (index >= 9 && index < 36)
                { storeItem(index, itemStack, ConfigBool.KEEP_MAIN_INVENTORY.value, ConfigFloat.MAIN_INVENTORY_DURABILITY_LOSS.value);}
            else if (index >= 36 && index < 40)
                { storeItem(index, itemStack, ConfigBool.KEEP_ARMOR.value, ConfigFloat.ARMOR_DURABILITY_LOSS.value);}
            else if (index == 40)
                { storeItem(index, itemStack, ConfigBool.KEEP_OFFHAND.value, ConfigFloat.OFFHAND_DURABILITY_LOSS.value);}
        }
    }

    private void storeItem (int index, ItemStack itemStack, boolean checkConfig, float durabilityConfig) {

        if (itemStack.isEmpty()) { return; }

        if (ConfigBool.ENABLE_DURABILITY_LOSS.value && itemStack.isItemStackDamageable()){
            itemStack.damageItem(Math.round(itemStack.getMaxDamage() * durabilityConfig), inventoryPlayer.player);
        }

        if (checkToKeepItem(index, itemStack, checkConfig)) {
            storage.set(index, itemStack);
            inventoryPlayer.removeStackFromSlot(index);
        }
    }

    private boolean checkToKeepItem (int index, ItemStack itemStack, boolean checkConfig) {

        if (isEssential(itemStack)) {
            return true;
        }

        if (isCursed(itemStack)) {
            if (ConfigBool.DESTROY_CURSED.value) {
                inventoryPlayer.removeStackFromSlot(index);
            }
            return false;
        }

        return checkConfig;
    }

    private boolean isEssential(ItemStack itemStack) {

        for (String s : ConfigStringList.ESSENTIAL_ITEMS.value) {
            if (s.equals(itemStack.getItem().getRegistryName().toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean isCursed(ItemStack itemStack) {

        for (String s : ConfigStringList.CURSED_ITEMS.value) {
            if (s.equals(itemStack.getItem().getRegistryName().toString())) {
                return true;
            }
        }
        return false;
    }

}
