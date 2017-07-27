package c4.corpserun.capability;

import net.mcft.copy.backpacks.api.IBackpackData;
import net.mcft.copy.backpacks.misc.BackpackDataItems;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class DeathList {

    private String modid;
    private NBTTagCompound storage;

    public DeathList(String modid, NBTBase nbt) {
        this.modid = modid;
        this.storage = new NBTTagCompound();
        storage.setTag(modid, nbt);
    }

    public String getModid() {
        return modid;
    }

    public NBTTagCompound getStorage() {
        return storage;
    }

}
