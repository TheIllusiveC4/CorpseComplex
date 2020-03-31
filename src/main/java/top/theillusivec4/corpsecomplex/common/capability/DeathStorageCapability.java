package top.theillusivec4.corpsecomplex.common.capability;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.DeathSettings;
import top.theillusivec4.corpsecomplex.common.util.DeathInfo;
import top.theillusivec4.corpsecomplex.common.util.DeathSettingManager;

public class DeathStorageCapability {

  @CapabilityInject(IDeathStorage.class)
  public static final Capability<IDeathStorage> DEATH_STORAGE_CAP;

  public static final ResourceLocation ID = new ResourceLocation(CorpseComplex.MODID,
      "death_storage");

  private static final String INVENTORIES = "Inventories";
  private static final String EFFECTS = "Effects";

  static {
    DEATH_STORAGE_CAP = null;
  }

  public static void register() {
    CapabilityManager.INSTANCE.register(IDeathStorage.class, new IStorage<IDeathStorage>() {

      @Override
      public INBT writeNBT(Capability<IDeathStorage> capability, IDeathStorage instance,
          Direction side) {
        CompoundNBT compound = new CompoundNBT();
        CompoundNBT inventories = new CompoundNBT();
        instance.getDeathInventory().forEach(inventories::put);
        compound.put(INVENTORIES, inventories);
        ListNBT effects = new ListNBT();
        instance.getEffects().forEach(effectInstance -> {
          CompoundNBT effect = new CompoundNBT();
          effectInstance.write(effect);
          effects.add(effect);
        });
        compound.put(EFFECTS, effects);
        instance.getDeathDamageSource().write(compound);
        return compound;
      }

      @Override
      public void readNBT(Capability<IDeathStorage> capability, IDeathStorage instance,
          Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        CompoundNBT inventories = compound.getCompound(INVENTORIES);
        inventories.keySet().forEach(modid -> instance.addInventory(modid, compound.get(modid)));
        ListNBT effects = compound.getList(EFFECTS, NBT.TAG_COMPOUND);
        effects.forEach(effect -> {
          EffectInstance effectInstance = EffectInstance.read((CompoundNBT) effect);
          instance.addEffectInstance(effectInstance);
        });
        DeathInfo deathDamageSource = new DeathInfo();
        deathDamageSource.read(compound);
        instance.setDeathDamageSource(deathDamageSource);
      }
    }, DeathStorage::new);
  }

  public static LazyOptional<IDeathStorage> getCapability(final PlayerEntity playerEntity) {
    return playerEntity.getCapability(DEATH_STORAGE_CAP);
  }

  public interface IDeathStorage {

    PlayerEntity getPlayer();

    void buildSettings();

    DeathSettings getSettings();

    void setDeathDamageSource(DeathInfo deathDamageSource);

    DeathInfo getDeathDamageSource();

    void addInventory(String modid, INBT nbt);

    INBT getInventory(String modid);

    Map<String, INBT> getDeathInventory();

    void addEffectInstance(EffectInstance effectInstance);

    void clearEffects();

    List<EffectInstance> getEffects();
  }

  public static class DeathStorage implements IDeathStorage {

    private final Map<String, INBT> storage = new HashMap<>();
    private final List<EffectInstance> effects = new ArrayList<>();
    private final PlayerEntity player;

    private DeathInfo deathDamageSource;
    private DeathSettings deathSettings;

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
    public void buildSettings() {
      deathSettings = DeathSettingManager.buildSettings(this);
    }

    @Override
    public DeathSettings getSettings() {
      if (deathSettings == null) {
        this.buildSettings();
      }
      return deathSettings;
    }

    @Override
    public void setDeathDamageSource(DeathInfo deathDamageSource) {
      this.deathDamageSource = deathDamageSource;
    }

    @Override
    public DeathInfo getDeathDamageSource() {
      return this.deathDamageSource;
    }

    @Override
    public void addInventory(String modid, INBT nbt) {
      this.storage.put(modid, nbt);
    }

    @Override
    public INBT getInventory(String modid) {
      return this.storage.get(modid);
    }

    @Override
    public Map<String, INBT> getDeathInventory() {
      return ImmutableMap.copyOf(this.storage);
    }

    @Override
    public void addEffectInstance(EffectInstance effectInstance) {
      EffectInstance instance = new EffectInstance(effectInstance.getPotion(),
          effectInstance.getDuration(), effectInstance.getAmplifier());
      instance.setCurativeItems(effectInstance.getCurativeItems());
      this.effects.add(instance);
    }

    @Override
    public void clearEffects() {
      this.effects.clear();
    }

    @Override
    public List<EffectInstance> getEffects() {
      return ImmutableList.copyOf(this.effects);
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
