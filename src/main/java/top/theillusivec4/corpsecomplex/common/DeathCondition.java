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
  @Nullable
  private final Integer dimension;

  private DeathCondition(Builder builder) {
    this.damageType = builder.damageType;
    this.immediateSource = builder.immediateSource;
    this.trueSource = builder.trueSource;
    this.dimension = builder.dimension;
  }

  public Optional<String> getDamageType() {
    return Optional.ofNullable(this.damageType);
  }

  public Optional<EntityType<?>> getImmediateSource() {
    return Optional.ofNullable(this.immediateSource);
  }

  public Optional<EntityType<?>> getTrueSource() {
    return Optional.ofNullable(this.trueSource);
  }

  public Optional<Integer> getDimension() { return Optional.ofNullable(this.dimension); }

  public static class Builder {

    private String damageType;
    private EntityType<?> immediateSource;
    private EntityType<?> trueSource;
    private Integer dimension;

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

    public Builder dimension(Integer dimension) {
      this.dimension = dimension;
      return this;
    }

    public DeathCondition build() {
      return new DeathCondition(this);
    }
  }
}
