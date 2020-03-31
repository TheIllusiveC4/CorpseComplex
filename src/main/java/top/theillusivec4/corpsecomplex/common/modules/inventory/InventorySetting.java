package top.theillusivec4.corpsecomplex.common.modules.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.Item;
import top.theillusivec4.corpsecomplex.common.config.ConfigParser;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.Setting;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;

public class InventorySetting implements Setting<InventoryOverride> {

  private Map<InventorySection, SectionSettings> inventorySettings = new HashMap<>();
  private Map<Item, DropMode> items = new HashMap<>();
  private boolean limitDurabilityLoss;
  private int dropDespawnTime;

  public Map<InventorySection, SectionSettings> getInventorySettings() {
    return inventorySettings;
  }

  public void setInventorySettings(Map<InventorySection, SectionSettings> inventorySettings) {
    this.inventorySettings = inventorySettings;
  }

  public Map<Item, DropMode> getItems() {
    return items;
  }

  public void setItems(Map<Item, DropMode> items) {
    this.items = items;
  }

  public boolean isLimitDurabilityLoss() {
    return limitDurabilityLoss;
  }

  public void setLimitDurabilityLoss(boolean limitDurabilityLoss) {
    this.limitDurabilityLoss = limitDurabilityLoss;
  }

  public int getDropDespawnTime() {
    return dropDespawnTime;
  }

  public void setDropDespawnTime(int dropDespawnTime) {
    this.dropDespawnTime = dropDespawnTime;
  }

  @Override
  public void importConfig() {
    this.getInventorySettings().clear();
    this.getInventorySettings().put(InventorySection.DEFAULT,
        new SectionSettings(CorpseComplexConfig.keepChance, CorpseComplexConfig.destroyChance,
            CorpseComplexConfig.keepDurabilityLoss, CorpseComplexConfig.dropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.MAINHAND,
        new SectionSettings(CorpseComplexConfig.mainhandKeepChance,
            CorpseComplexConfig.mainhandDestroyChance,
            CorpseComplexConfig.mainhandKeepDurabilityLoss,
            CorpseComplexConfig.mainhandDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.OFFHAND,
        new SectionSettings(CorpseComplexConfig.offhandKeepChance,
            CorpseComplexConfig.offhandDestroyChance, CorpseComplexConfig.offhandKeepDurabilityLoss,
            CorpseComplexConfig.offhandDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.HOTBAR,
        new SectionSettings(CorpseComplexConfig.hotbarKeepChance,
            CorpseComplexConfig.hotbarDestroyChance, CorpseComplexConfig.hotbarKeepDurabilityLoss,
            CorpseComplexConfig.hotbarDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.MAIN,
        new SectionSettings(CorpseComplexConfig.mainKeepChance,
            CorpseComplexConfig.mainDestroyChance, CorpseComplexConfig.mainKeepDurabilityLoss,
            CorpseComplexConfig.mainDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.HEAD,
        new SectionSettings(CorpseComplexConfig.headKeepChance,
            CorpseComplexConfig.headDestroyChance, CorpseComplexConfig.headKeepDurabilityLoss,
            CorpseComplexConfig.headDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.CHEST,
        new SectionSettings(CorpseComplexConfig.chestKeepChance,
            CorpseComplexConfig.chestDestroyChance, CorpseComplexConfig.chestKeepDurabilityLoss,
            CorpseComplexConfig.chestDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.LEGS,
        new SectionSettings(CorpseComplexConfig.legsKeepChance,
            CorpseComplexConfig.legsDestroyChance, CorpseComplexConfig.legsKeepDurabilityLoss,
            CorpseComplexConfig.legsDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.FEET,
        new SectionSettings(CorpseComplexConfig.feetKeepChance,
            CorpseComplexConfig.feetDestroyChance, CorpseComplexConfig.feetKeepDurabilityLoss,
            CorpseComplexConfig.feetDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.CURIOS,
        new SectionSettings(CorpseComplexConfig.curioKeepChance,
            CorpseComplexConfig.curioDestroyChance, CorpseComplexConfig.curioKeepDurabilityLoss,
            CorpseComplexConfig.curioDropDurabilityLoss));
    this.getInventorySettings().put(InventorySection.COSMETIC_ARMOR,
        new SectionSettings(CorpseComplexConfig.cosmeticArmorKeepChance,
            CorpseComplexConfig.cosmeticArmorDestroyChance,
            CorpseComplexConfig.cosmeticArmorKeepDurabilityLoss,
            CorpseComplexConfig.cosmeticArmorDropDurabilityLoss));
    this.setItems(ConfigParser.parseDrops(CorpseComplexConfig.itemSettings));
    this.setLimitDurabilityLoss(CorpseComplexConfig.limitDurabilityLoss);
    this.setDropDespawnTime(CorpseComplexConfig.dropDespawnTime);
  }

  @Override
  public void applyOverride(InventoryOverride override) {
    override.getInventorySettings()
        .ifPresent(map -> Arrays.asList(InventorySection.values()).forEach(section -> {
          InventoryOverride.SectionSettings overrideSection = map.get(section);
          SectionSettings inventorySection = this.getInventorySettings().get(section);

          if (overrideSection != null) {
            overrideSection.getDestroyChance()
                .ifPresent(setting -> inventorySection.destroyChance = setting);
            overrideSection.getKeepChance()
                .ifPresent(setting -> inventorySection.keepChance = setting);
            overrideSection.getDropDurabilityLoss()
                .ifPresent(setting -> inventorySection.dropDurabilityLoss = setting);
            overrideSection.getKeepDurabilityLoss()
                .ifPresent(setting -> inventorySection.keepDurabilityLoss = setting);
          }
        }));
    override.getItems().ifPresent(this::setItems);
    override.getLimitDurabilityLoss().ifPresent(this::setLimitDurabilityLoss);
    override.getDropDespawnTime().ifPresent(this::setDropDespawnTime);
  }

  public static class SectionSettings {

    public double keepChance;
    public double destroyChance;
    public double keepDurabilityLoss;
    public double dropDurabilityLoss;

    public SectionSettings(double keepChance, double destroyChance, double keepDurabilityLoss,
        double dropDurabilityLoss) {
      this.keepChance = keepChance;
      this.destroyChance = destroyChance;
      this.keepDurabilityLoss = keepDurabilityLoss;
      this.dropDurabilityLoss = dropDurabilityLoss;
    }
  }
}
