package c4.corpsecomplex.api.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface IDeathInventory {

    NBTTagCompound getStorage(String modid);

    void addStorage(String modid, NBTTagCompound storage);

    void setDeathInventory(NBTTagCompound nbt);

    NBTTagCompound getDeathInventory();

}
