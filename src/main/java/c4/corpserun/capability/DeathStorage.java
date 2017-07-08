package c4.corpserun.capability;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class DeathStorage implements Capability.IStorage<IDeathInventory> {

    private NBTTagCompound tag = new NBTTagCompound();

    @Override
    public NBTBase writeNBT(Capability<IDeathInventory> capability, IDeathInventory instance, EnumFacing side) {

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < instance.getDeathInventory().size(); ++i)
        {
            ItemStack itemstack = instance.getDeathInventory().get(i);

            if (!itemstack.isEmpty())
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                itemstack.writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }

        if (!nbttaglist.hasNoTags())
        {
            tag.setTag("Death Inventory", nbttaglist);
        }

        return tag;
    }

    @Override
    public void readNBT(Capability<IDeathInventory> capability, IDeathInventory instance, EnumFacing side, NBTBase nbt) {

        NBTTagList nbttaglist = tag.getTagList("Death Inventory", 10);

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;

            if (j >= 0 && j < instance.getDeathInventory().size())
            {
                instance.getDeathInventory().set(j, new ItemStack(nbttagcompound));
            }
        }

    }

}
