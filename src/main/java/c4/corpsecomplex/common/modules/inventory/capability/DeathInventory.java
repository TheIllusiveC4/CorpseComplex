/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.inventory.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DeathInventory implements IDeathInventory {

    private NBTTagCompound deathInventory = new NBTTagCompound();

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
