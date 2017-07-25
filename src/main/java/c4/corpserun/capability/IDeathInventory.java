package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

public interface IDeathInventory {

    NonNullList<ItemStack> assignStorage(String name, int size);

    NonNullList<ItemStack> getStorage(String name);

    ArrayList<DeathList> getDeathInventory();

}
