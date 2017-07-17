package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;

public class DeathStorage implements Capability.IStorage<IDeathInventory> {

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
