/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.inventory.capability;

import net.minecraft.nbt.NBTTagCompound;

public interface IDeathInventory {

    NBTTagCompound getStorage(String modid);

    void addStorage(String modid, NBTTagCompound storage);

    void setDeathInventory(NBTTagCompound nbt);

    NBTTagCompound getDeathInventory();

}
