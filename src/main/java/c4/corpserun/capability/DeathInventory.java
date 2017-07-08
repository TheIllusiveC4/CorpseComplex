package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DeathInventory implements IDeathInventory {

    private NonNullList<ItemStack> deathInventory = NonNullList.withSize(41,ItemStack.EMPTY);

    public void assignDeathInventory(NonNullList<ItemStack> storage) {

        deathInventory = storage;

    }

    public NonNullList<ItemStack> getDeathInventory() {
        return deathInventory;
    }

}
