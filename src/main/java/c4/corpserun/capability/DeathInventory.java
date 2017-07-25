package c4.corpserun.capability;

import jdk.internal.dynalink.linker.LinkerServices;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.concurrent.Callable;

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

    public static class Provider implements ICapabilitySerializable<NBTBase> {

        @CapabilityInject(IDeathInventory.class)
        public static final Capability<IDeathInventory> DEATH_INV_CAP = null;

        private IDeathInventory instance = DEATH_INV_CAP.getDefaultInstance();

        @Override
        @SuppressWarnings("")
        public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
            return capability == DEATH_INV_CAP;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
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

        private NBTTagCompound tag = new NBTTagCompound();

        @Override
        public NBTBase writeNBT(Capability<IDeathInventory> capability, IDeathInventory instance, EnumFacing side) {

            for (DeathList deathList : instance.getDeathInventory()) {
                String modid = deathList.getModid();
                NonNullList<ItemStack> storage = deathList.getList();
                NBTTagList nbttaglist = new NBTTagList();
                NBTTagCompound compound = new NBTTagCompound();
                for (int i = 0; i < storage.size(); ++i) {
                    ItemStack itemstack = storage.get(i);

                    if (!itemstack.isEmpty()) {
                        NBTTagCompound nbttagcompound = new NBTTagCompound();
                        nbttagcompound.setByte("Slot", (byte) i);
                        itemstack.writeToNBT(nbttagcompound);
                        nbttaglist.appendTag(nbttagcompound);
                    }
                }

                if (!nbttaglist.hasNoTags()) {
                    compound.setTag("Inventory", nbttaglist);
                    compound.setInteger("Size", storage.size());
                    tag.setTag(modid, compound);
                }
            }

            return tag;
        }

        @Override
        public void readNBT(Capability<IDeathInventory> capability, IDeathInventory instance, EnumFacing side, NBTBase nbt) {

            for (String modid : tag.getKeySet()) {

                NBTTagCompound compound = tag.getCompoundTag(modid);
                NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
                NonNullList<ItemStack> storage = instance.assignStorage(modid, compound.getInteger("Size"));

                for (int j = 0; j < nbttaglist.tagCount(); ++j) {
                    NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
                    int k = nbttagcompound.getByte("Slot") & 255;

                    if (k >= 0 && k < storage.size()) {
                        storage.set(j, new ItemStack(nbttagcompound));
                    }
                }
            }
        }
    }
}
