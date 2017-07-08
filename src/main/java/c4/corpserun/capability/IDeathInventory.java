package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public interface IDeathInventory {

    public void assignDeathInventory(NonNullList<ItemStack> storage);

    public NonNullList<ItemStack> getDeathInventory();

}
