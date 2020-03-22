package top.theillusivec4.corpsecomplex.common.modules.effects;

import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;

public class EffectsOverride {

  @Nullable
  private final List<ItemStack> cures;
  @Nullable
  private final List<EffectInstance> effects;
  @Nullable
  private final PermissionMode keepEffectsMode;
  @Nullable
  private final List<Effect> keepEffects;

  private EffectsOverride(Builder builder) {
    this.cures = builder.cures;
    this.effects = builder.effects;
    this.keepEffects = builder.keepEffects;
    this.keepEffectsMode = builder.keepEffectsMode;
  }

  public Optional<List<ItemStack>> getCures() {
    return Optional.ofNullable(this.cures);
  }

  public Optional<List<EffectInstance>> getEffects() {
    return Optional.ofNullable(this.effects);
  }

  public Optional<PermissionMode> getKeepEffectsMode() {
    return Optional.ofNullable(this.keepEffectsMode);
  }

  public Optional<List<Effect>> getKeepEffects() {
    return Optional.ofNullable(this.keepEffects);
  }

  public static class Builder {

    private List<ItemStack> cures;
    private List<EffectInstance> effects;
    private PermissionMode keepEffectsMode;
    private List<Effect> keepEffects;

    public Builder cures(List<ItemStack> cures) {
      this.cures = cures;
      return this;
    }

    public Builder effects(List<EffectInstance> effects) {
      this.effects = effects;
      return this;
    }

    public Builder keepEffects(List<Effect> keepEffects) {
      this.keepEffects = keepEffects;
      return this;
    }

    public Builder keepEffectsMode(PermissionMode keepEffectsMode) {
      this.keepEffectsMode = keepEffectsMode;
      return this;
    }

    public EffectsOverride build() {
      return new EffectsOverride(this);
    }
  }
}
