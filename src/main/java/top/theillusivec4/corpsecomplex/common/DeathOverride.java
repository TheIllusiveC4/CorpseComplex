package top.theillusivec4.corpsecomplex.common;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import top.theillusivec4.corpsecomplex.common.modules.effects.EffectsOverride;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceOverride;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerOverride;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriOverride;
import top.theillusivec4.corpsecomplex.common.modules.miscellaneous.MiscellaneousOverride;

public class DeathOverride {

  private final int priority;
  private final List<DeathCondition> conditions;

  private final ExperienceOverride experience;
  private final HungerOverride hunger;
  private final EffectsOverride effects;
  private final MementoMoriOverride mementoMori;
  private final MiscellaneousOverride miscellaneous;

  @Nullable
  private Double mainhandKeepChance;
  @Nullable
  private Double mainhandDestroyChance;
  @Nullable
  private Double mainhandKeepDurabilityLoss;
  @Nullable
  private Double mainhandDropDurabilityLoss;

  @Nullable
  private Double hotbarKeepChance;
  @Nullable
  private Double hotbarDestroyChance;
  @Nullable
  private Double hotbarKeepDurabilityLoss;
  @Nullable
  private Double hotbarDropDurabilityLoss;

  @Nullable
  private Double offhandKeepChance;
  @Nullable
  private Double offhandDestroyChance;
  @Nullable
  private Double offhandKeepDurabilityLoss;
  @Nullable
  private Double offhandDropDurabilityLoss;

  @Nullable
  private Double mainKeepChance;
  @Nullable
  private Double mainDestroyChance;
  @Nullable
  private Double mainKeepDurabilityLoss;
  @Nullable
  private Double mainDropDurabilityLoss;

  @Nullable
  private Double headKeepChance;
  @Nullable
  private Double headDestroyChance;
  @Nullable
  private Double headKeepDurabilityLoss;
  @Nullable
  private Double headDropDurabilityLoss;

  @Nullable
  private Double chestKeepChance;
  @Nullable
  private Double chestDestroyChance;
  @Nullable
  private Double chestKeepDurabilityLoss;
  @Nullable
  private Double chestDropDurabilityLoss;

  @Nullable
  private Double legsKeepChance;
  @Nullable
  private Double legsDestroyChance;
  @Nullable
  private Double legsKeepDurabilityLoss;
  @Nullable
  private Double legsDropDurabilityLoss;

  @Nullable
  private Double feetKeepChance;
  @Nullable
  private Double feetDestroyChance;
  @Nullable
  private Double feetKeepDurabilityLoss;
  @Nullable
  private Double feetDropDurabilityLoss;

  @Nullable
  private List<? extends String> itemSettings;

  private DeathOverride(Builder builder) {
    this.priority = builder.priority;
    this.conditions = builder.conditions;

    this.experience = builder.experience;
    this.hunger = builder.hunger;
    this.effects = builder.effects;
    this.miscellaneous = builder.miscellaneous;
    this.mementoMori = builder.mementoMori;
  }

  public int getPriority() {
    return this.priority;
  }

  public List<DeathCondition> getConditions() {
    return this.conditions;
  }

  public ExperienceOverride getExperienceOverride() {
    return this.experience;
  }

  public HungerOverride getHungerOverride() {
    return this.hunger;
  }

  public EffectsOverride getEffectsOverride() {
    return this.effects;
  }

  public MementoMoriOverride getMementoMoriOverride() {
    return this.mementoMori;
  }

  public MiscellaneousOverride getMiscellaneousOverride() {
    return this.miscellaneous;
  }

  public void apply(DeathSettings settings) {
    settings.getExperienceSettings().applyOverride(this.getExperienceOverride());
    settings.getHungerSettings().applyOverride(this.getHungerOverride());
    settings.getEffectsSettings().applyOverride(this.getEffectsOverride());
    settings.getMementoMoriSettings().applyOverride(this.getMementoMoriOverride());
    settings.getMiscellaneousSettings().applyOverride(this.getMiscellaneousOverride());
  }

  public static class Builder {

    private List<DeathCondition> conditions = new ArrayList<>();
    private Integer priority;

    private ExperienceOverride experience;
    private HungerOverride hunger;
    private EffectsOverride effects;
    private MementoMoriOverride mementoMori;
    private MiscellaneousOverride miscellaneous;

    public Builder priority(Integer priority) {
      this.priority = priority;
      return this;
    }

    public Builder conditions(List<DeathCondition> conditions) {
      this.conditions = conditions;
      return this;
    }

    public Builder experience(ExperienceOverride experience) {
      this.experience = experience;
      return this;
    }

    public Builder hunger(HungerOverride hunger) {
      this.hunger = hunger;
      return this;
    }

    public Builder effects(EffectsOverride effects) {
      this.effects = effects;
      return this;
    }

    public Builder mementoMori(MementoMoriOverride mementoMori) {
      this.mementoMori = mementoMori;
      return this;
    }

    public Builder miscellaneous(MiscellaneousOverride misc) {
      this.miscellaneous = misc;
      return this;
    }

    public DeathOverride build() {
      if (this.priority == null) {
        this.priority = 0;
      }
      return new DeathOverride(this);
    }
  }
}
