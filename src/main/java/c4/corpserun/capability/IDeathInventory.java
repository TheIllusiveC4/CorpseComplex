package c4.corpserun.capability;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IDeathInventory {

    void changeSize(int size);

    void storeDeathItem(InventoryPlayer inventoryPlayer, int index, ItemStack itemStack);

    NonNullList<ItemStack> getDeathInventory();

}
