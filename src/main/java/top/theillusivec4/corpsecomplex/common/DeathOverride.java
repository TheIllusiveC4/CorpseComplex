package top.theillusivec4.corpsecomplex.common;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceOverride;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerOverride;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;

public class DeathOverride {

  private final int priority;
  private final List<DeathCondition> conditions;

  private final ExperienceOverride experience;
  private final HungerOverride hunger;

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

  @Nullable
  private List<? extends String> cures;
  @Nullable
  private List<? extends String> effects;
  @Nullable
  private PermissionMode keepEffectsMode;
  @Nullable
  private List<? extends String> keepEffects;


  @Nullable
  private List<? extends String> mementoCures;
  @Nullable
  private Boolean noFood;
  @Nullable
  private Double percentXp;

  @Nullable
  private Boolean restrictRespawning;
  @Nullable
  private List<? extends String> respawnItems;

  private DeathOverride(Builder builder) {
    this.priority = builder.priority;
    this.conditions = builder.conditions;

    this.experience = builder.experience;
    this.hunger = builder.hunger;
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

  public void apply(DeathSettings settings) {
    settings.getExperienceSettings().applyOverride(this.getExperienceOverride());
    settings.getHungerSettings().applyOverride(this.getHungerOverride());
  }

  public static class Builder {

    private List<DeathCondition> conditions = new ArrayList<>();
    private Integer priority;

    private ExperienceOverride experience;
    private HungerOverride hunger;

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

    public DeathOverride build() {
      if (this.priority == null) {
        this.priority = 0;
      }
      return new DeathOverride(this);
    }
  }
}
