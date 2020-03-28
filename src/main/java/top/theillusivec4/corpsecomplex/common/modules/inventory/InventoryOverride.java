package top.theillusivec4.corpsecomplex.common.modules.inventory;

import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.item.Item;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;

public class InventoryOverride {

  @Nullable
  private final Map<InventorySection, SectionSettings> inventorySettings;
  @Nullable
  private final Map<Item, DropMode> items;
  @Nullable
  private final Boolean limitDurabilityLoss;

  private InventoryOverride(Builder builder) {
    this.inventorySettings = builder.inventorySettings;
    this.items = builder.items;
    this.limitDurabilityLoss = builder.limitDurabilityLoss;
  }

  public Optional<Map<InventorySection, SectionSettings>> getInventorySettings() {
    return Optional.ofNullable(inventorySettings);
  }

  public Optional<Map<Item, DropMode>> getItems() {
    return Optional.ofNullable(items);
  }

  public Optional<Boolean> getLimitDurabilityLoss() {
    return Optional.ofNullable(limitDurabilityLoss);
  }

  public static class Builder {

    private Map<InventorySection, SectionSettings> inventorySettings;
    private Map<Item, DropMode> items;
    private Boolean limitDurabilityLoss;

    public Builder inventorySettings(Map<InventorySection, SectionSettings> inventorySettings) {
      this.inventorySettings = inventorySettings;
      return this;
    }

    public Builder items(Map<Item, DropMode> items) {
      this.items = items;
      return this;
    }

    public Builder limitDurabilityLoss(Boolean limitDurabilityLoss) {
      this.limitDurabilityLoss = limitDurabilityLoss;
      return this;
    }

    public InventoryOverride build() {
      return new InventoryOverride(this);
    }
  }

  public static class SectionSettings {

    @Nullable
    private Double keepChance;
    @Nullable
    private Double destroyChance;
    @Nullable
    private Double keepDurabilityLoss;
    @Nullable
    private Double dropDurabilityLoss;

    public SectionSettings(@Nullable Double keepChance, @Nullable Double destroyChance,
        @Nullable Double keepDurabilityLoss, @Nullable Double dropDurabilityLoss) {
      this.keepChance = keepChance;
      this.destroyChance = destroyChance;
      this.keepDurabilityLoss = keepDurabilityLoss;
      this.dropDurabilityLoss = dropDurabilityLoss;
    }

    public Optional<Double> getKeepChance() {
      return Optional.ofNullable(keepChance);
    }

    public Optional<Double> getDestroyChance() {
      return Optional.ofNullable(destroyChance);
    }

    public Optional<Double> getKeepDurabilityLoss() {
      return Optional.ofNullable(keepDurabilityLoss);
    }

    public Optional<Double> getDropDurabilityLoss() {
      return Optional.ofNullable(dropDurabilityLoss);
    }
  }
}
