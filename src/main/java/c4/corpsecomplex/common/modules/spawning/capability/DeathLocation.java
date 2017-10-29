/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.spawning.capability;

import c4.corpsecomplex.CorpseComplex;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DeathLocation implements IDeathLocation {

    private BlockPos deathLocation;
    private int deathDimension;
    private boolean usedScroll = false;
    private boolean hasDeathLocation = false;

    public BlockPos getDeathLocation() { return deathLocation; }

    public void setDeathLocation(BlockPos pos) { deathLocation = pos; }

    public boolean hasDeathLocation() { return hasDeathLocation; }

    public void setHasDeathLocation(boolean hasDeathLocation) { this.hasDeathLocation = hasDeathLocation; }

    public int getDeathDimension() { return deathDimension; }

    public void setDeathDimension(int dimension) { deathDimension = dimension; }

    public boolean hasUsedScroll() { return usedScroll; };

    public void setUsedScroll(boolean usedScroll) { this.usedScroll = usedScroll; }

    public static class Provider implements ICapabilitySerializable<NBTBase> {

        @CapabilityInject(IDeathLocation.class)
        public static final Capability<IDeathLocation> DEATH_LOC_CAP = null;

        private IDeathLocation instance = DEATH_LOC_CAP.getDefaultInstance();

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return capability == DEATH_LOC_CAP;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            return capability == DEATH_LOC_CAP ? DEATH_LOC_CAP.<T> cast(this.instance) : null;
        }

        @Override
        public NBTBase serializeNBT()
        {
            return DEATH_LOC_CAP.getStorage().writeNBT(DEATH_LOC_CAP, this.instance, null);
        }

        @Override
        public void deserializeNBT(NBTBase nbt) {
            DEATH_LOC_CAP.getStorage().readNBT(DEATH_LOC_CAP, this.instance, null, nbt);
        }
    }

    public static class Storage implements Capability.IStorage<IDeathLocation> {

        @Override
        public NBTBase writeNBT(Capability<IDeathLocation> capability, IDeathLocation instance, EnumFacing side) {

            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("usedScroll", instance.hasUsedScroll());
            compound.setBoolean("hasDeathLocation", instance.hasDeathLocation());
            if (instance.hasDeathLocation()) {
                compound.setInteger("dimension", instance.getDeathDimension());
                compound.setInteger("x", instance.getDeathLocation().getX());
                compound.setInteger("y", instance.getDeathLocation().getY());
                compound.setInteger("z", instance.getDeathLocation().getZ());
            }
            return compound;
        }

        @Override
        public void readNBT(Capability<IDeathLocation> capability, IDeathLocation instance, EnumFacing side, NBTBase nbt) {

            NBTTagCompound compound = (NBTTagCompound) nbt;
            instance.setUsedScroll(compound.getBoolean("usedScroll"));
            instance.setHasDeathLocation(compound.getBoolean("hasDeathLocation"));
            if (compound.getBoolean("hasDeathLocation")) {
                instance.setDeathDimension(compound.getInteger("dimension"));
                instance.setDeathLocation(new BlockPos(compound.getInteger("x"), compound.getInteger("y"), compound.getInteger("z")));
            }
        }
    }
}
