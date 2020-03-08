package top.theillusivec4.corpsecomplex.common.capability;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.corpsecomplex.CorpseComplex;

public class DeathStorageCapability {

  @CapabilityInject(IDeathStorage.class)
  public static final Capability<IDeathStorage> DEATH_STORAGE_CAP;

  public static final ResourceLocation ID = new ResourceLocation(CorpseComplex.MODID,
      "death_storage");

  static {
    DEATH_STORAGE_CAP = null;
  }

  public static void register() {
    CapabilityManager.INSTANCE.register(IDeathStorage.class, new IStorage<IDeathStorage>() {

      @Override
      public INBT writeNBT(Capability<IDeathStorage> capability, IDeathStorage instance,
          Direction side) {
        CompoundNBT compound = new CompoundNBT();
        instance.getDeathStorage().forEach(compound::put);
        return compound;
      }

      @Override
      public void readNBT(Capability<IDeathStorage> capability, IDeathStorage instance,
          Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        compound.keySet().forEach(modid -> instance.addStorage(modid, compound.get(modid)));
      }
    }, DeathStorage::new);
  }

  public static LazyOptional<IDeathStorage> getCapability(final PlayerEntity playerEntity) {
    return playerEntity.getCapability(DEATH_STORAGE_CAP);
  }

  public interface IDeathStorage {

    PlayerEntity getPlayer();

    void addStorage(String modid, INBT nbt);

    INBT getStorage(String modid);

    Map<String, INBT> getDeathStorage();
  }

  public static class DeathStorage implements IDeathStorage {

    private final Map<String, INBT> storage = new HashMap<>();
    private final PlayerEntity player;

    public DeathStorage() {
      this(null);
    }

    public DeathStorage(@Nullable PlayerEntity playerEntity) {
      this.player = playerEntity;
    }

    @Nullable
    @Override
    public PlayerEntity getPlayer() {
      return this.player;
    }

    @Override
    public void addStorage(String modid, INBT nbt) {
      this.storage.put(modid, nbt);
    }

    @Override
    public INBT getStorage(String modid) {
      return storage.get(modid);
    }

    @Override
    public Map<String, INBT> getDeathStorage() {
      return ImmutableMap.copyOf(storage);
    }
  }

  public static class Provider implements ICapabilitySerializable<INBT> {

    final LazyOptional<IDeathStorage> optional;
    final IDeathStorage data;

    public Provider(PlayerEntity player) {
      this.data = new DeathStorage(player);
      this.optional = LazyOptional.of(() -> data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nullable Capability<T> capability, Direction side) {
      return DEATH_STORAGE_CAP.orEmpty(capability, optional);
    }

    @Override
    public INBT serializeNBT() {
      return DEATH_STORAGE_CAP.writeNBT(data, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
      DEATH_STORAGE_CAP.readNBT(data, null, nbt);
    }
  }
}
