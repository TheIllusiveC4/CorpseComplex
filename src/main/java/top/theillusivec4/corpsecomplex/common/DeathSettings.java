package top.theillusivec4.corpsecomplex.common;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.modules.effects.EffectsSetting;
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceSetting;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerSetting;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.Enums.ModuleType;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;

public class DeathSettings {

  private final Map<ModuleType, Setting<?>> modules = new EnumMap<>(ModuleType.class);

  public DeathSettings() {
    modules.put(ModuleType.HUNGER, new HungerSetting());
    modules.put(ModuleType.EXPERIENCE, new ExperienceSetting());
    modules.put(ModuleType.EFFECTS, new EffectsSetting());
    modules.values().forEach(Setting::importConfig);
  }

  public EffectsSetting getEffectsSettings() {
    return (EffectsSetting) this.modules.get(ModuleType.EFFECTS);
  }

  public ExperienceSetting getExperienceSettings() {
    return (ExperienceSetting) this.modules.get(ModuleType.EXPERIENCE);
  }

  public HungerSetting getHungerSettings() {
    return (HungerSetting) this.modules.get(ModuleType.HUNGER);
  }

  public static class Inventory {

    public Map<InventorySection, SectionSettings> inventorySettings = new HashMap<>();
    public Map<Item, DropMode> items = new HashMap<>();

    public Inventory() {
      this.inventorySettings.put(InventorySection.MAINHAND,
          new SectionSettings(CorpseComplexConfig.mainhandKeepChance,
              CorpseComplexConfig.mainhandDestroyChance,
              CorpseComplexConfig.mainhandKeepDurabilityLoss,
              CorpseComplexConfig.mainhandDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.OFFHAND,
          new SectionSettings(CorpseComplexConfig.offhandKeepChance,
              CorpseComplexConfig.offhandDestroyChance,
              CorpseComplexConfig.offhandKeepDurabilityLoss,
              CorpseComplexConfig.offhandDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.HOTBAR,
          new SectionSettings(CorpseComplexConfig.hotbarKeepChance,
              CorpseComplexConfig.hotbarDestroyChance, CorpseComplexConfig.hotbarKeepDurabilityLoss,
              CorpseComplexConfig.hotbarDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.MAIN,
          new SectionSettings(CorpseComplexConfig.mainKeepChance,
              CorpseComplexConfig.mainDestroyChance, CorpseComplexConfig.mainKeepDurabilityLoss,
              CorpseComplexConfig.mainDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.HEAD,
          new SectionSettings(CorpseComplexConfig.headKeepChance,
              CorpseComplexConfig.headDestroyChance, CorpseComplexConfig.headKeepDurabilityLoss,
              CorpseComplexConfig.headDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.CHEST,
          new SectionSettings(CorpseComplexConfig.chestKeepChance,
              CorpseComplexConfig.chestDestroyChance, CorpseComplexConfig.chestKeepDurabilityLoss,
              CorpseComplexConfig.chestDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.LEGS,
          new SectionSettings(CorpseComplexConfig.legsKeepChance,
              CorpseComplexConfig.legsDestroyChance, CorpseComplexConfig.legsKeepDurabilityLoss,
              CorpseComplexConfig.legsDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.FEET,
          new SectionSettings(CorpseComplexConfig.feetKeepChance,
              CorpseComplexConfig.feetDestroyChance, CorpseComplexConfig.feetKeepDurabilityLoss,
              CorpseComplexConfig.feetDropDurabilityLoss));
      CorpseComplexConfig.itemSettings.forEach(string -> {
        String[] parsed = string.split(";");
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parsed[0]));

        if (item != null) {
          DropMode dropMode = DropMode.DROP;

          if (parsed.length > 1) {
            String setting = parsed[1];

            if (!setting.equals("drop")) {

              if (setting.equals("keep")) {
                dropMode = DropMode.KEEP;
              } else if (setting.equals("destroy")) {
                dropMode = DropMode.DESTROY;
              }
            }
          }
          this.items.put(item, dropMode);
        }
      });
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

  public static class MementoMori {

    public List<Item> mementoCures = new ArrayList<>();
    public boolean noFood;
    public double percentXp;

    public MementoMori() {
      CorpseComplexConfig.mementoCures.forEach(string -> {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));

        if (item != null) {
          this.mementoCures.add(item);
        }
      });
      this.noFood = CorpseComplexConfig.noFood;
      this.percentXp = CorpseComplexConfig.percentXp;
    }
  }

  public static class Miscellaneous {

    public boolean restrictRespawning;
    public Map<Item, Integer> respawnItems = new HashMap<>();

    public Miscellaneous() {

    }
  }
}
