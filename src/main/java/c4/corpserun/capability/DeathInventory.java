package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;

public class DeathInventory implements IDeathInventory {

    private ArrayList<DeathList> deathInventory = new ArrayList<>();

    public NonNullList<ItemStack> assignStorage(String name, int size) {
        DeathList deathList = new DeathList(name, NonNullList.withSize(size, ItemStack.EMPTY));
        deathInventory.add(deathList);
        return deathList.getList();
    }

    public NonNullList<ItemStack> getStorage(String modid) {
        for (DeathList deathList : deathInventory) {
            if (deathList.getModid().equals(modid)) {
                return deathList.getList();
            }
        }
        return null;
    }

    public ArrayList<DeathList> getDeathInventory() {
        return deathInventory;
    }

}
