package top.theillusivec4.corpsecomplex.common.modules.experience;

import java.util.Optional;
import javax.annotation.Nullable;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class ExperienceOverride {

  @Nullable
  private final Double lostXp;
  @Nullable
  private final XpDropMode xpDropMode;
  @Nullable
  private final Double droppedXpPercent;
  @Nullable
  private final Integer droppedXpPerLevel;
  @Nullable
  private final Integer maxDroppedXp;

  private ExperienceOverride(Builder builder) {
    this.lostXp = builder.lostXp;
    this.xpDropMode = builder.xpDropMode;
    this.droppedXpPercent = builder.droppedXpPercent;
    this.droppedXpPerLevel = builder.droppedXpPerLevel;
    this.maxDroppedXp = builder.maxDroppedXp;
  }

  public Optional<Double> getLostXp() {
    return Optional.ofNullable(this.lostXp);
  }

  public Optional<XpDropMode> getXpDropMode() {
    return Optional.ofNullable(this.xpDropMode);
  }

  public Optional<Double> getDroppedXpPercent() {
    return Optional.ofNullable(this.droppedXpPercent);
  }

  public Optional<Integer> getDroppedXpPerLevel() {
    return Optional.ofNullable(this.droppedXpPerLevel);
  }

  public Optional<Integer> getMaxDroppedXp() {
    return Optional.ofNullable(this.maxDroppedXp);
  }

  public static class Builder {

    private Double lostXp;
    private XpDropMode xpDropMode;
    private Double droppedXpPercent;
    private Integer droppedXpPerLevel;
    private Integer maxDroppedXp;

    public Builder lostXp(Double lostXp) {
      this.lostXp = lostXp;
      return this;
    }

    public Builder xpDropMode(XpDropMode xpDropMode) {
      this.xpDropMode = xpDropMode;
      return this;
    }

    public Builder droppedXpPercent(Double droppedXpPercent) {
      this.droppedXpPercent = droppedXpPercent;
      return this;
    }

    public Builder droppedXpPerLevel(Integer droppedXpPerLevel) {
      this.droppedXpPerLevel = droppedXpPerLevel;
      return this;
    }

    public Builder maxDroppedXp(Integer maxDroppedXp) {
      this.maxDroppedXp = maxDroppedXp;
      return this;
    }

    public ExperienceOverride build() {
      return new ExperienceOverride(this);
    }
  }
}
