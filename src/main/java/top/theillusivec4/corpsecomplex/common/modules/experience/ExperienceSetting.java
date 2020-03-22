package top.theillusivec4.corpsecomplex.common.modules.experience;

import top.theillusivec4.corpsecomplex.common.Setting;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class ExperienceSetting implements Setting<ExperienceOverride> {

  private double lostXp;
  private XpDropMode xpDropMode;
  private double droppedXpPercent;
  private int droppedXpPerLevel;
  private int maxDroppedXp;

  public double getLostXp() {
    return lostXp;
  }

  public void setLostXp(double lostXp) {
    this.lostXp = lostXp;
  }

  public XpDropMode getXpDropMode() {
    return xpDropMode;
  }

  public void setXpDropMode(XpDropMode xpDropMode) {
    this.xpDropMode = xpDropMode;
  }

  public double getDroppedXpPercent() {
    return droppedXpPercent;
  }

  public void setDroppedXpPercent(double droppedXpPercent) {
    this.droppedXpPercent = droppedXpPercent;
  }

  public int getDroppedXpPerLevel() {
    return droppedXpPerLevel;
  }

  public void setDroppedXpPerLevel(int droppedXpPerLevel) {
    this.droppedXpPerLevel = droppedXpPerLevel;
  }

  public int getMaxDroppedXp() {
    return maxDroppedXp;
  }

  public void setMaxDroppedXp(int maxDroppedXp) {
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
  public void applyOverride(ExperienceOverride overrides) {
    overrides.getLostXp().ifPresent(this::setLostXp);
    overrides.getXpDropMode().ifPresent(this::setXpDropMode);
    overrides.getDroppedXpPercent().ifPresent(this::setDroppedXpPercent);
    overrides.getDroppedXpPerLevel().ifPresent(this::setDroppedXpPerLevel);
    overrides.getMaxDroppedXp().ifPresent(this::setMaxDroppedXp);
  }
}
