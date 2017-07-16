package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DeathList {

    private String modid;
    private NonNullList<ItemStack> list;

    public DeathList(String modid, NonNullList<ItemStack> list) {
        this.modid = modid;
        this.list = list;
    }

    public String getModid() {
        return modid;
    }

    public NonNullList<ItemStack> getList() {
        return list;
    }

}
