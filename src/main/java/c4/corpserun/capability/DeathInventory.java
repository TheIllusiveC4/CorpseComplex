package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class DeathInventory implements IDeathInventory {

    private NBTTagCompound deathInventory = new NBTTagCompound();

//    public NBTTagCompound assignStorage(String name, int size) {
//        DeathList deathList = new DeathList(name, new ItemStackHandler(size));
//        deathInventory.add(deathList);
//        return deathList.getStorage();
//    }

    public void addStorage(String modid, NBTTagCompound storage) {
        deathInventory.setTag(modid, storage);
    }

    public NBTTagCompound getStorage(String modid) {
        return (NBTTagCompound) deathInventory.getTag(modid);
    }

    public NBTTagCompound getDeathInventory() {
        return deathInventory;
    }

    public void setDeathInventory(NBTTagCompound nbt) {
        deathInventory = nbt;
    }

    public static class Provider implements ICapabilitySerializable<NBTBase> {

        @CapabilityInject(IDeathInventory.class)
        public static final Capability<IDeathInventory> DEATH_INV_CAP = null;

        private IDeathInventory instance = DEATH_INV_CAP.getDefaultInstance();

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == DEATH_INV_CAP;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            return capability == DEATH_INV_CAP ? DEATH_INV_CAP.<T> cast(this.instance) : null;
        }

        @Override
        public NBTBase serializeNBT()
        {
            return DEATH_INV_CAP.getStorage().writeNBT(DEATH_INV_CAP, this.instance, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            DEATH_INV_CAP.getStorage().readNBT(DEATH_INV_CAP, this.instance, null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<IDeathInventory> {

        @Override
        public NBTBase writeNBT(Capability<IDeathInventory> capability, IDeathInventory instance, EnumFacing side) {

            return instance.getDeathInventory();
        }

        @Override
        public void readNBT(Capability<IDeathInventory> capability, IDeathInventory instance, EnumFacing side, NBTBase nbt) {

            instance.setDeathInventory((NBTTagCompound) nbt);
        }
    }
}
