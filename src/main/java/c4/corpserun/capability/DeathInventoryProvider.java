package c4.corpserun.capability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class DeathInventoryProvider implements ICapabilitySerializable<NBTBase> {

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
