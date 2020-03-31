package top.theillusivec4.corpsecomplex.common;

import java.util.List;
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
  @Nullable
  private final List<String> gameStages;

  private DeathCondition(Builder builder) {
    this.damageType = builder.damageType;
    this.immediateSource = builder.immediateSource;
    this.trueSource = builder.trueSource;
    this.dimension = builder.dimension;
    this.gameStages = builder.gameStages;
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

  public Optional<List<String>> getGameStages() { return Optional.ofNullable(this.gameStages); }

  public static class Builder {

    private String damageType;
    private EntityType<?> immediateSource;
    private EntityType<?> trueSource;
    private Integer dimension;
    private List<String> gameStages;

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

    public Builder gameStages(List<String> gameStages) {
      this.gameStages = gameStages;
      return this;
    }

    public DeathCondition build() {
      return new DeathCondition(this);
    }
  }
}
