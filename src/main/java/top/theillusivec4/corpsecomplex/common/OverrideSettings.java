package top.theillusivec4.corpsecomplex.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class OverrideSettings {

  public static PriorityQueue<OverrideSettings> OVERRIDES = new PriorityQueue<>((s1, s2) -> {
    if (s1.getPriority() < s2.getPriority()) {
      return 1;
    } else if (s1.getPriority() > s2.getPriority()) {
      return -1;
    }
    return 0;
  });

  public int priority = 0;
  public Hunger hunger;
  public Inventory inventory;
  public Experience experience;
  public MementoMori mementoMori;
  public Effects effects;
  public Miscellaneous miscellaneous;

  public int getPriority() {
    return this.priority;
  }

  public static class Inventory {

    public Map<InventorySection, DeathSettings.Inventory.SectionSettings> inventorySettings = new HashMap<>();
    public Map<Item, DropMode> items = new HashMap<>();

    public Inventory() {
      this.inventorySettings.put(InventorySection.MAINHAND,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.mainhandKeepChance,
              CorpseComplexConfig.mainhandDestroyChance,
              CorpseComplexConfig.mainhandKeepDurabilityLoss,
              CorpseComplexConfig.mainhandDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.OFFHAND,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.offhandKeepChance,
              CorpseComplexConfig.offhandDestroyChance,
              CorpseComplexConfig.offhandKeepDurabilityLoss,
              CorpseComplexConfig.offhandDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.HOTBAR,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.hotbarKeepChance,
              CorpseComplexConfig.hotbarDestroyChance, CorpseComplexConfig.hotbarKeepDurabilityLoss,
              CorpseComplexConfig.hotbarDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.MAIN,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.mainKeepChance,
              CorpseComplexConfig.mainDestroyChance, CorpseComplexConfig.mainKeepDurabilityLoss,
              CorpseComplexConfig.mainDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.HEAD,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.headKeepChance,
              CorpseComplexConfig.headDestroyChance, CorpseComplexConfig.headKeepDurabilityLoss,
              CorpseComplexConfig.headDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.CHEST,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.chestKeepChance,
              CorpseComplexConfig.chestDestroyChance, CorpseComplexConfig.chestKeepDurabilityLoss,
              CorpseComplexConfig.chestDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.LEGS,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.legsKeepChance,
              CorpseComplexConfig.legsDestroyChance, CorpseComplexConfig.legsKeepDurabilityLoss,
              CorpseComplexConfig.legsDropDurabilityLoss));
      this.inventorySettings.put(InventorySection.FEET,
          new DeathSettings.Inventory.SectionSettings(CorpseComplexConfig.feetKeepChance,
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

  public static class Hunger {

    public boolean keepFood;
    public boolean keepSaturation;
    public boolean keepExhaustion;
    public int minFood;
    public int maxFood;

    public Hunger() {
      this.keepFood = CorpseComplexConfig.keepFood;
      this.keepSaturation = CorpseComplexConfig.keepSaturation;
      this.keepExhaustion = CorpseComplexConfig.keepExhaustion;
      this.minFood = CorpseComplexConfig.minFood;
      this.maxFood = CorpseComplexConfig.maxFood;
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

  public static class Experience {

    public double lostXp;
    public XpDropMode xpDropMode;
    public double droppedXpPercent;
    public int droppedXpPerLevel;
    public int maxDroppedXp;

    public Experience() {
      this.lostXp = CorpseComplexConfig.lostXp;
      this.xpDropMode = CorpseComplexConfig.xpDropMode;
      this.droppedXpPercent = CorpseComplexConfig.droppedXpPercent;
      this.droppedXpPerLevel = CorpseComplexConfig.droppedXpPerLevel;
      this.maxDroppedXp = CorpseComplexConfig.maxDroppedXp;
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
