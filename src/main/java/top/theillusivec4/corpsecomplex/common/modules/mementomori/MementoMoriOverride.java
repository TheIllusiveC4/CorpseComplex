package top.theillusivec4.corpsecomplex.common.modules.mementomori;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;

public class MementoMoriOverride {

  @Nullable
  private final List<ItemStack> mementoCures;
  @Nullable
  private final Boolean noFood;
  @Nullable
  private final Double percentXp;

  private MementoMoriOverride(Builder builder) {
    this.mementoCures = builder.mementoCures;
    this.noFood = builder.noFood;
    this.percentXp = builder.percentXp;
  }

  public Optional<List<ItemStack>> getMementoCures() {
    return Optional.ofNullable(this.mementoCures);
  }

  public Optional<Boolean> getNoFood() {
    return Optional.ofNullable(this.noFood);
  }

  public Optional<Double> getPercentXp() {
    return Optional.ofNullable(this.percentXp);
  }

  public static class Builder {

    private List<ItemStack> mementoCures;
    private Boolean noFood;
    private Double percentXp;

    public Builder mementoCures(List<ItemStack> mementoCures) {
      this.mementoCures = mementoCures;
      return this;
    }

    public Builder noFood(Boolean noFood) {
      this.noFood = noFood;
      return this;
    }

    public Builder percentXp(Double percentXp) {
      this.percentXp = percentXp;
      return this;
    }

    public MementoMoriOverride build() {
      return new MementoMoriOverride(this);
    }
  }
}
