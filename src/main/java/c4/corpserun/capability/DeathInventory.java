package c4.corpserun.capability;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DeathInventory implements IDeathInventory {

    private NonNullList<ItemStack> deathInventory = NonNullList.withSize(41,ItemStack.EMPTY);
    private NonNullList<ItemStack> compatInventory = NonNullList.withSize(13, ItemStack.EMPTY);

    public void changeSize(int size) {
        deathInventory = NonNullList.withSize(size, ItemStack.EMPTY);
    }

    public void storeDeathItem(InventoryPlayer inventoryPlayer, int index, ItemStack itemStack) {
        deathInventory.set(index, itemStack);
        inventoryPlayer.removeStackFromSlot(index);
    }

    public NonNullList<ItemStack> getDeathInventory() {
        return deathInventory;
    }

}
