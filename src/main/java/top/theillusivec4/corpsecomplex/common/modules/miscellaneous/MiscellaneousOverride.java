package top.theillusivec4.corpsecomplex.common.modules.miscellaneous;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;

public class MiscellaneousOverride {

  @Nullable
  private final Boolean restrictRespawning;
  @Nullable
  private final List<ItemStack> respawnItems;
  @Nullable
  private final List<EntityType<?>> mobSpawnsOnDeath;

  private MiscellaneousOverride(Builder builder) {
    this.restrictRespawning = builder.restrictRespawning;
    this.respawnItems = builder.respawnItems;
    this.mobSpawnsOnDeath = builder.mobSpawnsOnDeath;
  }

  public Optional<Boolean> getRestrictRespawning() {
    return Optional.ofNullable(this.restrictRespawning);
  }

  public Optional<List<ItemStack>> getRespawnItems() {
    return Optional.ofNullable(this.respawnItems);
  }

  public Optional<List<EntityType<?>>> getMobSpawnsOnDeath() {
    return Optional.ofNullable(this.mobSpawnsOnDeath);
  }

  public static class Builder {

    private Boolean restrictRespawning;
    private List<ItemStack> respawnItems;
    private List<EntityType<?>> mobSpawnsOnDeath;

    public Builder restrictRespawning(Boolean restrictRespawning) {
      this.restrictRespawning = restrictRespawning;
      return this;
    }

    public Builder respawnItems(List<ItemStack> respawnItems) {
      this.respawnItems = respawnItems;
      return this;
    }

    public Builder mobSpawnsOnDeath(List<EntityType<?>> mobSpawnsOnDeath) {
      this.mobSpawnsOnDeath = mobSpawnsOnDeath;
      return this;
    }

    public MiscellaneousOverride build() {
      return new MiscellaneousOverride(this);
    }
  }
}
