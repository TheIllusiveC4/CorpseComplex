package top.theillusivec4.corpsecomplex.common;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;

public class DeathCondition {

  @Nullable
  private final String damageType;
  @Nullable
  private final EntityType<?> immediateSource;
  @Nullable
  private final EntityType<?> trueSource;

  private DeathCondition(Builder builder) {
    this.damageType = builder.damageType;
    this.immediateSource = builder.immediateSource;
    this.trueSource = builder.trueSource;
  }

  public Optional<String> getDamageType() {
    return Optional.ofNullable(damageType);
  }

  public Optional<EntityType<?>> getImmediateSource() {
    return Optional.ofNullable(immediateSource);
  }

  public Optional<EntityType<?>> getTrueSource() {
    return Optional.ofNullable(trueSource);
  }

  public static class Builder {

    private String damageType;
    private EntityType<?> immediateSource;
    private EntityType<?> trueSource;

    public Builder damageType(String damageType) {
      this.damageType = damageType;
      return this;
    }

    public Builder immediateSource(EntityType<?> immediateSource) {
      this.immediateSource = immediateSource;
      return this;
    }

    public Builder trueSource(EntityType<?> trueSource) {
      this.trueSource = trueSource;
      return this;
    }

    public DeathCondition build() {
      return new DeathCondition(this);
    }
  }
}
