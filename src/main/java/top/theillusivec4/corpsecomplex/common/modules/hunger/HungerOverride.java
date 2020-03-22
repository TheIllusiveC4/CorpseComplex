package top.theillusivec4.corpsecomplex.common.modules.hunger;

import java.util.Optional;
import javax.annotation.Nullable;

public class HungerOverride {

  @Nullable
  private Boolean keepFood;
  @Nullable
  private Boolean keepSaturation;
  @Nullable
  private Boolean keepExhaustion;
  @Nullable
  private Integer minFood;
  @Nullable
  private Integer maxFood;

  private HungerOverride(Builder builder) {
    this.keepFood = builder.keepFood;
    this.keepSaturation = builder.keepSaturation;
    this.keepExhaustion = builder.keepExhaustion;
    this.minFood = builder.minFood;
    this.maxFood = builder.maxFood;
  }

  public Optional<Boolean> getKeepFood() {
    return Optional.ofNullable(this.keepFood);
  }

  public Optional<Boolean> getKeepSaturation() {
    return Optional.ofNullable(keepSaturation);
  }

  public Optional<Boolean> getKeepExhaustion() {
    return Optional.ofNullable(keepExhaustion);
  }

  public Optional<Integer> getMinFood() {
    return Optional.ofNullable(minFood);
  }

  public Optional<Integer> getMaxFood() {
    return Optional.ofNullable(maxFood);
  }

  public static class Builder {

    private Boolean keepFood;
    private Boolean keepSaturation;
    private Boolean keepExhaustion;
    private Integer minFood;
    private Integer maxFood;

    public Builder keepFood(Boolean keepFood) {
      this.keepFood = keepFood;
      return this;
    }

    public Builder keepSaturation(Boolean keepSaturation) {
      this.keepSaturation = keepSaturation;
      return this;
    }

    public Builder keepExhaustion(Boolean keepExhaustion) {
      this.keepExhaustion = keepExhaustion;
      return this;
    }

    public Builder minFood(Integer minFood) {
      this.minFood = minFood;
      return this;
    }

    public Builder maxFood(Integer maxFood) {
      this.maxFood = maxFood;
      return this;
    }

    public HungerOverride build() {
      return new HungerOverride(this);
    }
  }
}
