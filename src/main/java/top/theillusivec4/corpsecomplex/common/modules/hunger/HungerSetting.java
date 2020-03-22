package top.theillusivec4.corpsecomplex.common.modules.hunger;

import top.theillusivec4.corpsecomplex.common.DeathOverride;
import top.theillusivec4.corpsecomplex.common.Setting;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class HungerSetting implements Setting<HungerOverride> {

  private boolean keepFood;
  private boolean keepSaturation;
  private boolean keepExhaustion;
  private int minFood;
  private int maxFood;

  public boolean isKeepFood() {
    return keepFood;
  }

  public void setKeepFood(boolean keepFood) {
    this.keepFood = keepFood;
  }

  public boolean isKeepSaturation() {
    return keepSaturation;
  }

  public void setKeepSaturation(boolean keepSaturation) {
    this.keepSaturation = keepSaturation;
  }

  public boolean isKeepExhaustion() {
    return keepExhaustion;
  }

  public void setKeepExhaustion(boolean keepExhaustion) {
    this.keepExhaustion = keepExhaustion;
  }

  public int getMinFood() {
    return minFood;
  }

  public void setMinFood(int minFood) {
    this.minFood = minFood;
  }

  public int getMaxFood() {
    return maxFood;
  }

  public void setMaxFood(int maxFood) {
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
  public void applyOverride(HungerOverride overrides) {
    overrides.getKeepFood().ifPresent(this::setKeepFood);
    overrides.getKeepSaturation().ifPresent(this::setKeepSaturation);
    overrides.getKeepExhaustion().ifPresent(this::setKeepExhaustion);
    overrides.getMinFood().ifPresent(this::setMinFood);
    overrides.getMaxFood().ifPresent(this::setMaxFood);
  }
}
