package top.theillusivec4.corpsecomplex.common;

import java.util.ArrayList;
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
import top.theillusivec4.corpsecomplex.common.modules.experience.ExperienceSettings;
import top.theillusivec4.corpsecomplex.common.modules.hunger.HungerSettings;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class DeathSettings {

  public static DeathSettings CONFIG_DEFAULT = new DeathSettings();

  private HungerSettings hungerSettings;
  public Inventory inventory;
  public ExperienceSettings experienceSettings;
  public MementoMori mementoMori;
  public Effects effects;
  public Miscellaneous miscellaneous;

  public DeathSettings() {

  }

  public HungerSettings getHungerSettings() {
    return this.hungerSettings;
  }

  public ExperienceSettings getExperienceSettings() {
    return this.experienceSettings;
  }

  public static void setConfigDefault() {
    CONFIG_DEFAULT.hungerSettings = new HungerSettings();
    CONFIG_DEFAULT.inventory = new Inventory();
    CONFIG_DEFAULT.experience = new Experience();
    CONFIG_DEFAULT.mementoMori = new MementoMori();
    CONFIG_DEFAULT.effects = new Effects();
    CONFIG_DEFAULT.miscellaneous = new Miscellaneous();
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

  public static class Effects {

    public List<ItemStack> cures = new ArrayList<>();
    public List<EffectInstance> effects = new ArrayList<>();
    public PermissionMode keepEffectsMode;
    public List<Effect> keepEffects = new ArrayList<>();

    public Effects() {
      this.keepEffectsMode = CorpseComplexConfig.keepEffectsMode;
      CorpseComplexConfig.cures.forEach(cure -> {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(cure));

        if (item != null) {
          this.cures.add(new ItemStack(item));
        }
      });
      CorpseComplexConfig.keepEffects.forEach(effect -> {
        Effect effect1 = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effect));

        if (effect1 != null) {
          this.keepEffects.add(effect1);
        }
      });
      CorpseComplexConfig.effects.forEach(effect -> {
        String[] parse = effect.split(";");

        if (parse.length >= 2) {
          Effect effect1 = ForgeRegistries.POTIONS.getValue(new ResourceLocation(parse[0]));

          if (effect1 != null) {
            int amplifier = parse.length >= 3 ? Integer.parseInt(parse[2]) : 0;
            EffectInstance instance = new EffectInstance(effect1, Integer.parseInt(parse[1]) * 20,
                amplifier);

            if (parse.length >= 4) {
              instance.setCurativeItems(new ArrayList<>());
            } else {
              instance.setCurativeItems(cures);
            }
            this.effects.add(instance);
          }
        }
      });
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
      CorpseComplexConfig.respawnItems.forEach(item -> {
        String[] parsed = item.split(";");
        Item item1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parsed[0]));

        if (item1 != null) {
          int amount = parsed.length > 1 ? Integer.parseInt(parsed[1]) : 1;
          respawnItems.put(item1, amount);
        }
      });
      this.restrictRespawning = CorpseComplexConfig.restrictRespawning;
    }
  }
}
