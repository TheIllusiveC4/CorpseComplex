package top.theillusivec4.corpsecomplex.common.modules.effects;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import top.theillusivec4.corpsecomplex.common.Setting;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;

public class EffectsSetting implements Setting<EffectsOverride> {

  private List<ItemStack> cures = new ArrayList<>();
  private List<EffectInstance> effects = new ArrayList<>();
  private PermissionMode keepEffectsMode;
  private List<Effect> keepEffects = new ArrayList<>();

  public List<ItemStack> getCures() {
    return cures;
  }

  public void setCures(List<ItemStack> cures) {
    this.cures = cures;
  }

  public List<EffectInstance> getEffects() {
    return effects;
  }

  public void setEffects(List<EffectInstance> effects) {
    this.effects = effects;
  }

  public PermissionMode getKeepEffectsMode() {
    return keepEffectsMode;
  }

  public void setKeepEffectsMode(PermissionMode keepEffectsMode) {
    this.keepEffectsMode = keepEffectsMode;
  }

  public List<Effect> getKeepEffects() {
    return keepEffects;
  }

  public void setKeepEffects(List<Effect> keepEffects) {
    this.keepEffects = keepEffects;
  }

  @Override
  public void importConfig() {
    this.setKeepEffectsMode(CorpseComplexConfig.keepEffectsMode);
    this.setKeepEffects(ConfigParser.parseEffects(CorpseComplexConfig.keepEffects));
    ConfigParser.parseItems(CorpseComplexConfig.cures).keySet()
        .forEach(item -> this.getCures().add(new ItemStack(item)));
    this.setEffects(
        ConfigParser.parseEffectInstances(CorpseComplexConfig.effects, this.getCures()));
  }

  @Override
  public void applyOverride(EffectsOverride override) {
    override.getCures().ifPresent(this::setCures);
    override.getEffects().ifPresent(this::setEffects);
    override.getKeepEffects().ifPresent(this::setKeepEffects);
    override.getKeepEffectsMode().ifPresent(this::setKeepEffectsMode);
  }
}
