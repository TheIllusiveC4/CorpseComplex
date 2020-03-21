package top.theillusivec4.corpsecomplex.common.modules.hunger;

import java.util.Optional;
import javax.annotation.Nullable;
import top.theillusivec4.corpsecomplex.common.DeathSettings;
import top.theillusivec4.corpsecomplex.common.Settings;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class HungerSettings implements Settings {

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

  public Optional<Boolean> getKeepFood() {
    return Optional.ofNullable(keepFood);
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

  public void setKeepFood(@Nullable Boolean keepFood) {
    this.keepFood = keepFood;
  }

  public void setKeepSaturation(@Nullable Boolean keepSaturation) {
    this.keepSaturation = keepSaturation;
  }

  public void setKeepExhaustion(@Nullable Boolean keepExhaustion) {
    this.keepExhaustion = keepExhaustion;
  }

  public void setMinFood(@Nullable Integer minFood) {
    this.minFood = minFood;
  }

  public void setMaxFood(@Nullable Integer maxFood) {
    this.maxFood = maxFood;
  }

  @Override
  public void importConfig() {
    this.setKeepFood(CorpseComplexConfig.keepFood);
    this.setKeepSaturation(CorpseComplexConfig.keepSaturation);
    this.setKeepExhaustion(CorpseComplexConfig.keepExhaustion);
    this.setMinFood(CorpseComplexConfig.minFood);
    this.setMaxFood(CorpseComplexConfig.maxFood);
  }

  @Override
  public void applyOverride(DeathSettings overrideSettings) {
    HungerSettings overrides = overrideSettings.getHungerSettings();
    overrides.getKeepFood().ifPresent(this::setKeepFood);
    overrides.getKeepSaturation().ifPresent(this::setKeepSaturation);
    overrides.getKeepExhaustion().ifPresent(this::setKeepExhaustion);
    overrides.getMinFood().ifPresent(this::setMinFood);
    overrides.getMaxFood().ifPresent(this::setMaxFood);
  }
}
