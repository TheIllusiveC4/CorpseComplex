package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;

public interface IDeathInventory {

    NBTTagCompound getStorage(String name);

    void addStorage(String modid, NBTTagCompound storage);

    void setDeathInventory(NBTTagCompound nbt);

    NBTTagCompound getDeathInventory();

}
