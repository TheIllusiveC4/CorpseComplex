package c4.corpsecomplex.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface IDeathInventory {

    NBTTagCompound getStorage(String name);

    void addStorage(String modid, NBTTagCompound storage);

    void setDeathInventory(NBTTagCompound nbt);

    NBTTagCompound getDeathInventory();

}
