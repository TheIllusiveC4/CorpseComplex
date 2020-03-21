package top.theillusivec4.corpsecomplex.common.modules.experience;

import java.util.Optional;
import javax.annotation.Nullable;
import top.theillusivec4.corpsecomplex.common.DeathSettings;
import top.theillusivec4.corpsecomplex.common.Settings;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class ExperienceSettings implements Settings {

  @Nullable
  public Double lostXp;
  @Nullable
  public XpDropMode xpDropMode;
  @Nullable
  public Double droppedXpPercent;
  @Nullable
  public Integer droppedXpPerLevel;
  @Nullable
  public Integer maxDroppedXp;

  public Optional<Double> getLostXp() {
    return Optional.ofNullable(lostXp);
  }

  public void setLostXp(@Nullable Double lostXp) {
    this.lostXp = lostXp;
  }

  public Optional<XpDropMode> getXpDropMode() {
    return Optional.ofNullable(xpDropMode);
  }

  public void setXpDropMode(@Nullable XpDropMode xpDropMode) {
    this.xpDropMode = xpDropMode;
  }

  public Optional<Double> getDroppedXpPercent() {
    return Optional.ofNullable(droppedXpPercent);
  }

  public void setDroppedXpPercent(@Nullable Double droppedXpPercent) {
    this.droppedXpPercent = droppedXpPercent;
  }

  public Optional<Integer> getDroppedXpPerLevel() {
    return Optional.ofNullable(droppedXpPerLevel);
  }

  public void setDroppedXpPerLevel(@Nullable Integer droppedXpPerLevel) {
    this.droppedXpPerLevel = droppedXpPerLevel;
  }

  public Optional<Integer> getMaxDroppedXp() {
    return Optional.ofNullable(maxDroppedXp);
  }

  public void setMaxDroppedXp(@Nullable Integer maxDroppedXp) {
    this.maxDroppedXp = maxDroppedXp;
  }

  @Override
  public void importConfig() {
    this.setLostXp(CorpseComplexConfig.lostXp);
    this.setXpDropMode(CorpseComplexConfig.xpDropMode);
    this.setDroppedXpPercent(CorpseComplexConfig.droppedXpPercent);
    this.setDroppedXpPerLevel(CorpseComplexConfig.droppedXpPerLevel);
    this.setMaxDroppedXp(CorpseComplexConfig.maxDroppedXp);
  }

  @Override
  public void applyOverride(DeathSettings overrideSettings) {
    ExperienceSettings overrides = overrideSettings.getExperienceSettings();
    overrides.getLostXp().ifPresent(this::setLostXp);
    overrides.getXpDropMode().ifPresent(this::setXpDropMode);
    overrides.getDroppedXpPercent().ifPresent(this::setDroppedXpPercent);
    overrides.getDroppedXpPerLevel().ifPresent(this::setDroppedXpPerLevel);
    overrides.getMaxDroppedXp().ifPresent(this::setMaxDroppedXp);
  }
}
