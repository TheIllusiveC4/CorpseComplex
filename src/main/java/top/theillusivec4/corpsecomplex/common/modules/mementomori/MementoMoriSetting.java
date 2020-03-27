package top.theillusivec4.corpsecomplex.common.modules.mementomori;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class MementoMoriSetting implements Setting<MementoMoriOverride> {

  private List<ItemStack> mementoCures = new ArrayList<>();
  private boolean noFood;
  private double percentXp;

  public List<ItemStack> getMementoCures() {
    return mementoCures;
  }

  public void setMementoCures(List<ItemStack> mementoCures) {
    this.mementoCures = mementoCures;
  }

  public boolean isNoFood() {
    return noFood;
  }

  public void setNoFood(boolean noFood) {
    this.noFood = noFood;
  }

  public double getPercentXp() {
    return percentXp;
  }

  public void setPercentXp(double percentXp) {
    this.percentXp = percentXp;
  }

  @Override
  public void importConfig() {
    ConfigParser.parseItems(CorpseComplexConfig.mementoCures)
        .forEach((item, integer) -> this.getMementoCures().add(new ItemStack(item, integer)));
    this.setNoFood(CorpseComplexConfig.noFood);
    this.setPercentXp(CorpseComplexConfig.percentXp);
  }

  @Override
  public void applyOverride(MementoMoriOverride override) {
    override.getMementoCures().ifPresent(this::setMementoCures);
    override.getNoFood().ifPresent(this::setNoFood);
    override.getPercentXp().ifPresent(this::setPercentXp);
  }
}
