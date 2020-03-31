/*
 * Copyright (c) 2017-2020 C4
 *
 * This file is part of Corpse Complex, a mod made for Minecraft.
 *
 * Corpse Complex is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Corpse Complex is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Corpse Complex.  If not, see <https://www.gnu.org/licenses/>.
 */

package top.theillusivec4.corpsecomplex.common.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.conversion.ObjectConverter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.enchantment.Enchantment.Rarity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.config.OverridesConfig.ConditionConfig;
import top.theillusivec4.corpsecomplex.common.config.OverridesConfig.OverrideConfig;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.XpDropMode;

public class CorpseComplexConfig {

  public static final ForgeConfigSpec serverSpec;
  public static final Server SERVER;
  private static final String CONFIG_PREFIX = "gui." + CorpseComplex.MODID + ".config.";

  static {
    final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
        .configure(Server::new);
    serverSpec = specPair.getRight();
    SERVER = specPair.getLeft();
  }

  public static double keepChance;
  public static double destroyChance;
  public static double keepDurabilityLoss;
  public static double dropDurabilityLoss;

  public static double mainhandKeepChance;
  public static double mainhandDestroyChance;
  public static double mainhandKeepDurabilityLoss;
  public static double mainhandDropDurabilityLoss;

  public static double hotbarKeepChance;
  public static double hotbarDestroyChance;
  public static double hotbarKeepDurabilityLoss;
  public static double hotbarDropDurabilityLoss;

  public static double offhandKeepChance;
  public static double offhandDestroyChance;
  public static double offhandKeepDurabilityLoss;
  public static double offhandDropDurabilityLoss;

  public static double mainKeepChance;
  public static double mainDestroyChance;
  public static double mainKeepDurabilityLoss;
  public static double mainDropDurabilityLoss;

  public static double headKeepChance;
  public static double headDestroyChance;
  public static double headKeepDurabilityLoss;
  public static double headDropDurabilityLoss;

  public static double chestKeepChance;
  public static double chestDestroyChance;
  public static double chestKeepDurabilityLoss;
  public static double chestDropDurabilityLoss;

  public static double legsKeepChance;
  public static double legsDestroyChance;
  public static double legsKeepDurabilityLoss;
  public static double legsDropDurabilityLoss;

  public static double feetKeepChance;
  public static double feetDestroyChance;
  public static double feetKeepDurabilityLoss;
  public static double feetDropDurabilityLoss;

  public static double curioKeepChance;
  public static double curioDestroyChance;
  public static double curioKeepDurabilityLoss;
  public static double curioDropDurabilityLoss;

  public static double cosmeticArmorKeepChance;
  public static double cosmeticArmorDestroyChance;
  public static double cosmeticArmorKeepDurabilityLoss;
  public static double cosmeticArmorDropDurabilityLoss;

  public static List<? extends String> itemSettings;
  public static boolean limitDurabilityLoss;
  public static int dropDespawnTime;

  public static Rarity rarity;
  public static double levelDropChance;
  public static double baseSave;
  public static double extraSavePerLevel;
  public static boolean allowedOnBooks;
  public static boolean canApplyEnchantingTable;
  public static int maxSoulbindingLevel;
  public static boolean isTreasure;

  public static List<? extends String> cures;
  public static List<? extends String> effects;
  public static PermissionMode keepEffectsMode;
  public static List<? extends String> keepEffects;

  public static boolean keepFood;
  public static boolean keepSaturation;
  public static boolean keepExhaustion;
  public static int minFood;
  public static int maxFood;

  public static double lostXp;
  public static XpDropMode xpDropMode;
  public static double droppedXpPercent;
  public static int droppedXpPerLevel;
  public static int maxDroppedXp;

  public static int healthMod;
  public static int armorMod;
  public static int toughnessMod;
  public static double movementMod;
  public static double damageMod;
  public static double attackSpeedMod;
  public static boolean gradualRecovery;
  public static int duration;
  public static List<? extends String> mementoCures;
  public static boolean noFood;
  public static double percentXp;
  public static boolean beneficial;

  public static boolean restrictRespawning;
  public static List<? extends String> respawnItems;
  public static List<? extends String> mobSpawnsOnDeath;

  public static List<OverrideConfig> overrides;
  public static List<ConditionConfig> conditions;

  public static final ForgeConfigSpec overridesSpec;
  public static final Overrides OVERRIDES;

  static {
    final Pair<Overrides, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder()
        .configure(Overrides::new);
    overridesSpec = specPair.getRight();
    OVERRIDES = specPair.getLeft();
  }

  public static class Overrides {

    public OverridesConfig overrides;

    public Overrides(ForgeConfigSpec.Builder builder) {
      builder.comment("List of overridden settings based on conditions")
          .define("overrides", new ArrayList<>());
      builder.comment("List of possible conditions for overrides")
          .define("conditions", new ArrayList<>());
      builder.build();
    }
  }

  public static class Server {

    public final DoubleValue keepChance;
    public final DoubleValue destroyChance;
    public final DoubleValue keepDurabilityLoss;
    public final DoubleValue dropDurabilityLoss;

    public final DoubleValue mainhandKeepChance;
    public final DoubleValue mainhandDestroyChance;
    public final DoubleValue mainhandKeepDurabilityLoss;
    public final DoubleValue mainhandDropDurabilityLoss;

    public final DoubleValue hotbarKeepChance;
    public final DoubleValue hotbarDestroyChance;
    public final DoubleValue hotbarKeepDurabilityLoss;
    public final DoubleValue hotbarDropDurabilityLoss;

    public final DoubleValue offhandKeepChance;
    public final DoubleValue offhandDestroyChance;
    public final DoubleValue offhandKeepDurabilityLoss;
    public final DoubleValue offhandDropDurabilityLoss;

    public final DoubleValue mainKeepChance;
    public final DoubleValue mainDestroyChance;
    public final DoubleValue mainKeepDurabilityLoss;
    public final DoubleValue mainDropDurabilityLoss;

    public final DoubleValue headKeepChance;
    public final DoubleValue headDestroyChance;
    public final DoubleValue headKeepDurabilityLoss;
    public final DoubleValue headDropDurabilityLoss;

    public final DoubleValue chestKeepChance;
    public final DoubleValue chestDestroyChance;
    public final DoubleValue chestKeepDurabilityLoss;
    public final DoubleValue chestDropDurabilityLoss;

    public final DoubleValue legsKeepChance;
    public final DoubleValue legsDestroyChance;
    public final DoubleValue legsKeepDurabilityLoss;
    public final DoubleValue legsDropDurabilityLoss;

    public final DoubleValue feetKeepChance;
    public final DoubleValue feetDestroyChance;
    public final DoubleValue feetKeepDurabilityLoss;
    public final DoubleValue feetDropDurabilityLoss;

    public final DoubleValue curioKeepChance;
    public final DoubleValue curioDestroyChance;
    public final DoubleValue curioKeepDurabilityLoss;
    public final DoubleValue curioDropDurabilityLoss;

    public final DoubleValue cosmeticArmorKeepChance;
    public final DoubleValue cosmeticArmorDestroyChance;
    public final DoubleValue cosmeticArmorKeepDurabilityLoss;
    public final DoubleValue cosmeticArmorDropDurabilityLoss;

    public final ConfigValue<List<? extends String>> itemSettings;
    public final BooleanValue limitDurabilityLoss;
    public final IntValue dropDespawnTime;

    public final ConfigValue<Rarity> rarity;
    public final DoubleValue levelDropChance;
    public final DoubleValue baseSave;
    public final DoubleValue extraSavePerLevel;
    public final BooleanValue allowedOnBooks;
    public final BooleanValue canApplyEnchantingTable;
    public final IntValue maxSoulbindingLevel;
    public final BooleanValue isTreasure;

    public final ConfigValue<List<? extends String>> cures;
    public final ConfigValue<List<? extends String>> effects;
    public final EnumValue<PermissionMode> keepEffectsMode;
    public final ConfigValue<List<? extends String>> keepEffects;

    public final BooleanValue keepFood;
    public final BooleanValue keepSaturation;
    public final BooleanValue keepExhaustion;
    public final IntValue minFood;
    public final IntValue maxFood;

    public final DoubleValue lostXp;
    public final EnumValue<XpDropMode> xpDropMode;
    public final DoubleValue droppedXpPercent;
    public final IntValue droppedXpPerLevel;
    public final IntValue maxDroppedXp;

    public final IntValue healthMod;
    public final IntValue armorMod;
    public final IntValue toughnessMod;
    public final DoubleValue movementMod;
    public final DoubleValue damageMod;
    public final DoubleValue attackSpeedMod;
    public final BooleanValue gradualRecovery;
    public final IntValue duration;
    public final ConfigValue<List<? extends String>> mementoCures;
    public final BooleanValue noFood;
    public final DoubleValue percentXp;
    public final BooleanValue beneficial;

    public final BooleanValue restrictRespawning;
    public final ConfigValue<List<? extends String>> respawnItems;
    public final ConfigValue<List<? extends String>> mobSpawnsOnDeath;

    public Server(ForgeConfigSpec.Builder builder) {
      builder.push("inventory");

      builder.comment("Default values if specific section value is -1").push("defaults");

      keepChance = builder.comment("Default percent chance to keep item")
          .translation(CONFIG_PREFIX + "keepChance").defineInRange("keepChance", 0.0D, 0.0D, 1.0D);
      destroyChance = builder.comment("Default percent chance to destroy dropped item")
          .translation(CONFIG_PREFIX + "destroyChance")
          .defineInRange("destroyChance", 0.0D, 0.0D, 1.0D);
      keepDurabilityLoss = builder.comment("Default percent durability loss on kept item")
          .translation(CONFIG_PREFIX + "keepDurabilityLoss")
          .defineInRange("keepDurabilityLoss", 0.0D, 0.0D, 1.0D);
      dropDurabilityLoss = builder.comment("Default percent durability loss on dropped item")
          .translation(CONFIG_PREFIX + "dropDurabilityLoss")
          .defineInRange("dropDurabilityLoss", 0.0D, 0.0D, 1.0D);

      builder.pop();

      builder.push("mainhand");

      mainhandKeepChance = builder.comment("Percent chance to keep mainhand item")
          .translation(CONFIG_PREFIX + "mainhandKeepChance")
          .defineInRange("mainhandKeepChance", -1.0D, -1.0D, 1.0D);

      mainhandDestroyChance = builder.comment("Percent chance to destroy dropped mainhand item")
          .translation(CONFIG_PREFIX + "mainhandDestroyChance")
          .defineInRange("mainhandDestroyChance", -1.0D, -1.0D, 1.0D);

      mainhandKeepDurabilityLoss = builder.comment("Percent durability loss on kept mainhand item")
          .translation(CONFIG_PREFIX + "mainhandKeepDurabilityLoss")
          .defineInRange("mainhandKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      mainhandDropDurabilityLoss = builder
          .comment("Percent durability loss on dropped mainhand item")
          .translation(CONFIG_PREFIX + "mainhandDropDurabilityLoss")
          .defineInRange("mainhandDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("hotbar");

      hotbarKeepChance = builder.comment("Percent chance to keep hotbar items")
          .translation(CONFIG_PREFIX + "hotbarKeepChance")
          .defineInRange("hotbarKeepChance", -1.0D, -1.0D, 1.0D);

      hotbarDestroyChance = builder.comment("Percent chance to destroy dropped hotbar items")
          .translation(CONFIG_PREFIX + "hotbarDestroyChance")
          .defineInRange("hotbarDestroyChance", -1.0D, -1.0D, 1.0D);

      hotbarKeepDurabilityLoss = builder.comment("Percent durability loss on kept hotbar items")
          .translation(CONFIG_PREFIX + "hotbarKeepDurabilityLoss")
          .defineInRange("hotbarKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      hotbarDropDurabilityLoss = builder.comment("Percent durability loss on dropped hotbar items")
          .translation(CONFIG_PREFIX + "hotbarDropDurabilityLoss")
          .defineInRange("hotbarDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("offhand");

      offhandKeepChance = builder.comment("Percent chance to keep offhand item")
          .translation(CONFIG_PREFIX + "offhandKeepChance")
          .defineInRange("offhandKeepChance", -1.0D, -1.0D, 1.0D);

      offhandDestroyChance = builder.comment("Percent chance to destroy dropped offhand item")
          .translation(CONFIG_PREFIX + "offhandDestroyChance")
          .defineInRange("offhandDestroyChance", -1.0D, -1.0D, 1.0D);

      offhandKeepDurabilityLoss = builder.comment("Percent durability loss on kept offhand item")
          .translation(CONFIG_PREFIX + "offhandKeepDurabilityLoss")
          .defineInRange("offhandKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      offhandDropDurabilityLoss = builder.comment("Percent durability loss on dropped offhand item")
          .translation(CONFIG_PREFIX + "offhandDropDurabilityLoss")
          .defineInRange("offhandDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("main");

      mainKeepChance = builder.comment("Percent chance to keep main inventory items")
          .translation(CONFIG_PREFIX + "mainKeepChance")
          .defineInRange("mainKeepChance", -1.0D, -1.0D, 1.0D);

      mainDestroyChance = builder.comment("Percent chance to destroy dropped main inventory items")
          .translation(CONFIG_PREFIX + "mainDestroyChance")
          .defineInRange("mainDestroyChance", -1.0D, -1.0D, 1.0D);

      mainKeepDurabilityLoss = builder
          .comment("Percent durability loss on kept main inventory items")
          .translation(CONFIG_PREFIX + "mainKeepDurabilityLoss")
          .defineInRange("mainKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      mainDropDurabilityLoss = builder
          .comment("Percent durability loss on dropped main inventory items")
          .translation(CONFIG_PREFIX + "mainDropDurabilityLoss")
          .defineInRange("mainDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("head");

      headKeepChance = builder.comment("Percent chance to keep head item")
          .translation(CONFIG_PREFIX + "headKeepChance")
          .defineInRange("headKeepChance", -1.0D, -1.0D, 1.0D);

      headDestroyChance = builder.comment("Percent chance to destroy dropped head item")
          .translation(CONFIG_PREFIX + "headDestroyChance")
          .defineInRange("headDestroyChance", -1.0D, -1.0D, 1.0D);

      headKeepDurabilityLoss = builder.comment("Percent durability loss on kept head item")
          .translation(CONFIG_PREFIX + "headKeepDurabilityLoss")
          .defineInRange("headKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      headDropDurabilityLoss = builder.comment("Percent durability loss on dropped head item")
          .translation(CONFIG_PREFIX + "headDropDurabilityLoss")
          .defineInRange("headDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("chest");

      chestKeepChance = builder.comment("Percent chance to keep chest item")
          .translation(CONFIG_PREFIX + "chestKeepChance")
          .defineInRange("chestKeepChance", -1.0D, -1.0D, 1.0D);

      chestDestroyChance = builder.comment("Percent chance to destroy dropped chest item")
          .translation(CONFIG_PREFIX + "chestDestroyChance")
          .defineInRange("chestDestroyChance", -1.0D, -1.0D, 1.0D);

      chestKeepDurabilityLoss = builder.comment("Percent durability loss on kept chest item")
          .translation(CONFIG_PREFIX + "chestKeepDurabilityLoss")
          .defineInRange("chestKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      chestDropDurabilityLoss = builder.comment("Percent durability loss on dropped chest item")
          .translation(CONFIG_PREFIX + "chestDropDurabilityLoss")
          .defineInRange("chestDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("legs");

      legsKeepChance = builder.comment("Percent chance to keep legs item")
          .translation(CONFIG_PREFIX + "legsKeepChance")
          .defineInRange("legsKeepChance", -1.0D, -1.0D, 1.0D);

      legsDestroyChance = builder.comment("Percent chance to destroy dropped legs item")
          .translation(CONFIG_PREFIX + "legsDestroyChance")
          .defineInRange("legsDestroyChance", -1.0D, -1.0D, 1.0D);

      legsKeepDurabilityLoss = builder.comment("Percent durability loss on kept legs item")
          .translation(CONFIG_PREFIX + "legsKeepDurabilityLoss")
          .defineInRange("legsKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      legsDropDurabilityLoss = builder.comment("Percent durability loss on dropped legs item")
          .translation(CONFIG_PREFIX + "legsDropDurabilityLoss")
          .defineInRange("legsDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("feet");

      feetKeepChance = builder.comment("Percent chance to keep feet item")
          .translation(CONFIG_PREFIX + "feetKeepChance")
          .defineInRange("feetKeepChance", -1.0D, -1.0D, 1.0D);

      feetDestroyChance = builder.comment("Percent chance to destroy dropped feet item")
          .translation(CONFIG_PREFIX + "feetDestroyChance")
          .defineInRange("feetDestroyChance", -1.0D, -1.0D, 1.0D);

      feetKeepDurabilityLoss = builder.comment("Percent durability loss on kept feet item")
          .translation(CONFIG_PREFIX + "feetKeepDurabilityLoss")
          .defineInRange("feetKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      feetDropDurabilityLoss = builder.comment("Percent durability loss on dropped head item")
          .translation(CONFIG_PREFIX + "feetDropDurabilityLoss")
          .defineInRange("feetDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("curios");

      curioKeepChance = builder.comment("Percent chance to keep curio item")
          .translation(CONFIG_PREFIX + "curioKeepChance")
          .defineInRange("curioKeepChance", -1.0D, -1.0D, 1.0D);

      curioDestroyChance = builder.comment("Percent chance to destroy dropped curio item")
          .translation(CONFIG_PREFIX + "curioDestroyChance")
          .defineInRange("curioDestroyChance", -1.0D, -1.0D, 1.0D);

      curioKeepDurabilityLoss = builder.comment("Percent durability loss on kept curio item")
          .translation(CONFIG_PREFIX + "curioKeepDurabilityLoss")
          .defineInRange("curioKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      curioDropDurabilityLoss = builder.comment("Percent durability loss on dropped curio item")
          .translation(CONFIG_PREFIX + "curioDropDurabilityLoss")
          .defineInRange("curioDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      builder.push("cosmetic_armor");

      cosmeticArmorKeepChance = builder.comment("Percent chance to keep cosmetic armor item")
          .translation(CONFIG_PREFIX + "cosmeticArmorKeepChance")
          .defineInRange("cosmeticArmorKeepChance", -1.0D, -1.0D, 1.0D);

      cosmeticArmorDestroyChance = builder
          .comment("Percent chance to destroy dropped cosmetic armor item")
          .translation(CONFIG_PREFIX + "cosmeticArmorDestroyChance")
          .defineInRange("cosmeticArmorDestroyChance", -1.0D, -1.0D, 1.0D);

      cosmeticArmorKeepDurabilityLoss = builder
          .comment("Percent durability loss on kept cosmetic armor item")
          .translation(CONFIG_PREFIX + "cosmeticArmorKeepDurabilityLoss")
          .defineInRange("cosmeticArmorKeepDurabilityLoss", -1.0D, -1.0D, 1.0D);

      cosmeticArmorDropDurabilityLoss = builder
          .comment("Percent durability loss on dropped cosmetic armor item")
          .translation(CONFIG_PREFIX + "cosmeticArmorDropDurabilityLoss")
          .defineInRange("cosmeticArmorDropDurabilityLoss", -1.0D, -1.0D, 1.0D);

      builder.pop();

      itemSettings = builder
          .comment("List of items to always keep, drop, or destroy, regardless of other settings",
              "Format: modid:item;[keep/drop/destroy]").translation(CONFIG_PREFIX + "itemSettings")
          .defineList("itemSettings", new ArrayList<>(), s -> s instanceof String);

      limitDurabilityLoss = builder.comment(
          "Set to true to limit durability loss so that items do not break from death penalties")
          .translation(CONFIG_PREFIX + "limitDurabilityLoss").define("limitDurabilityLoss", false);

      dropDespawnTime = builder.comment(
          "Time (in seconds) that dropped items will take to despawn, -1 to disable despawning")
          .translation(CONFIG_PREFIX + "dropDespawnTime")
          .defineInRange("dropDespawnTime", 300, -1, Integer.MAX_VALUE);

      builder.pop();

      builder.push("soulbinding");

      rarity = builder.comment("The rarity of the enchantment")
          .translation(CONFIG_PREFIX + "rarity").defineEnum("rarity", Rarity.VERY_RARE);
      levelDropChance = builder.comment(
          "Percent chance that the item will drop a level in the enchantment on death when kept")
          .translation(CONFIG_PREFIX + "levelDropChance")
          .defineInRange("levelDropChance", 0.0D, 0.0D, 1.0D);
      baseSave = builder
          .comment("Base percent chance that the enchantment will save an item on death")
          .translation(CONFIG_PREFIX + "baseSave").defineInRange("baseSave", 1.0D, 0.0D, 1.0D);
      extraSavePerLevel = builder.comment(
          "Percent chance increase with each level in the enchantment that the item will be saved on death")
          .translation(CONFIG_PREFIX + "extraSavePerLevel")
          .defineInRange("extraSavePerLevel", 0.0D, 0.0D, 1.0D);
      allowedOnBooks = builder.comment("Set to true to allow enchanting Soulbinding on books")
          .translation(CONFIG_PREFIX + "allowedOnBooks").define("allowedOnBooks", false);
      canApplyEnchantingTable = builder
          .comment("Set to true to allow enchanting Soulbinding at the enchantment table")
          .translation(CONFIG_PREFIX + "canApplyEnchantingTable")
          .define("canApplyEnchantingTable", false);
      maxSoulbindingLevel = builder.comment("Maximum level of the enchantment")
          .translation(CONFIG_PREFIX + "maxSoulbindingLevel")
          .defineInRange("maxSoulbindingLevel", 1, 1, 5);
      isTreasure = builder.comment("Set to true to consider as a treasure enchantment")
          .translation(CONFIG_PREFIX + "isTreasure").define("isTreasure", false);

      builder.pop();

      builder.push("effects");

      cures = builder.comment("List of valid items to cure curable effects")
          .translation(CONFIG_PREFIX + "cures")
          .defineList("cures", Collections.singletonList("minecraft:milk_bucket"),
              s -> s instanceof String);

      effects = builder.comment(
          "List of effects to apply on respawn\nFormat: modid:effect;duration(seconds);amplifier\nAppend ';incurable' if applicable")
          .translation(CONFIG_PREFIX + "effects")
          .defineList("effects", new ArrayList<>(), s -> s instanceof String);

      keepEffectsMode = builder
          .comment("Sets whether kept effects list is a blacklist or whitelist")
          .translation(CONFIG_PREFIX + "keepEffectsMode")
          .defineEnum("keepEffectsMode", PermissionMode.WHITELIST);

      keepEffects = builder.comment("List of effects to retain on death")
          .translation(CONFIG_PREFIX + "keepEffects")
          .defineList("keepEffects", new ArrayList<>(), s -> s instanceof String);

      builder.pop();

      builder.push("hunger");

      keepFood = builder.comment("Set to true to retain food level on death")
          .translation(CONFIG_PREFIX + "keepFood").define("keepFood", false);

      keepSaturation = builder.comment("Set to true to retain saturation on death")
          .translation(CONFIG_PREFIX + "keepSaturation").define("keepSaturation", false);

      keepExhaustion = builder.comment("Set to true to retain exhaustion on death")
          .translation(CONFIG_PREFIX + "keepExhaustion").define("keepExhaustion", false);

      minFood = builder.comment("Lowest amount of food level on respawn")
          .translation(CONFIG_PREFIX + "minFood").defineInRange("minFood", 0, 0, 20);

      maxFood = builder.comment("Highest amount of food level on respawn")
          .translation(CONFIG_PREFIX + "maxFood").defineInRange("maxFood", 20, 0, 20);

      builder.pop();

      builder.push("experience");

      lostXp = builder.comment("Percentage of experience lost on death")
          .translation(CONFIG_PREFIX + "lostXp").defineInRange("lostXp", 1.0D, 0.0D, 1.0D);

      xpDropMode = builder.comment(
          "Sets whether dropped XP is based on a percentage of the lost total or per lost level")
          .translation(CONFIG_PREFIX + "xpDropMode").defineEnum("xpDropMode", XpDropMode.PER_LEVEL);

      droppedXpPercent = builder
          .comment("Percentage of lost experience that is dropped when PERCENT is active")
          .translation(CONFIG_PREFIX + "droppedXpPercent")
          .defineInRange("droppedXpPercent", 0.2D, 0.0D, 1.0D);

      droppedXpPerLevel = builder
          .comment("Amount of experience that is dropped per lost level when PER_LEVEL is active")
          .translation(CONFIG_PREFIX + "droppedXpPerLevel")
          .defineInRange("droppedXpPerLevel", 7, 0, 100000);

      maxDroppedXp = builder.comment("Maximum amount of dropped experience")
          .translation(CONFIG_PREFIX + "maxDroppedXp")
          .defineInRange("maxDroppedXp", 100, 0, 100000);

      builder.pop();

      builder.push("mementomori");

      healthMod = builder.comment("Amount modifier for maximum health")
          .translation(CONFIG_PREFIX + "healthMod").defineInRange("healthMod", 0, -1024, 1024);

      armorMod = builder.comment("Amount modifier for armor")
          .translation(CONFIG_PREFIX + "armorMod").defineInRange("armorMod", 0, -30, 30);

      toughnessMod = builder.comment("Amount modifier for armor toughness")
          .translation(CONFIG_PREFIX + "toughnessMod").defineInRange("toughnessMod", 0, -20, 20);

      movementMod = builder.comment("Percent modifier for movement speed")
          .translation(CONFIG_PREFIX + "movementMod")
          .defineInRange("movementMod", 0.0F, -1.0F, 10.0F);

      damageMod = builder.comment("Amount modifier for attack damage")
          .translation(CONFIG_PREFIX + "damageMod")
          .defineInRange("damageMod", 0.0F, -2048.0F, 2048.0F);

      attackSpeedMod = builder.comment("Percent modifier for attack speed")
          .translation(CONFIG_PREFIX + "attackSpeedMod")
          .defineInRange("attackSpeedMod", 0.0F, -1.0F, 10.0F);

      gradualRecovery = builder
          .comment("Set to true to enable gradual recovery (modifiers will gradually diminish)")
          .translation(CONFIG_PREFIX + "gradualRecovery").define("gradualRecovery", false);

      duration = builder.comment("Duration in seconds of the effect")
          .translation(CONFIG_PREFIX + "duration").defineInRange("duration", 30, 1, 1600);

      mementoCures = builder.comment("List of items that can cure the effect")
          .translation(CONFIG_PREFIX + "mementoCures")
          .defineList("mementoCures", Collections.singletonList("minecraft:milk_bucket"),
              s -> s instanceof String);

      noFood = builder.comment("Set to true to disable eating while the effect is active")
          .translation(CONFIG_PREFIX + "noFood").define("noFood", false);

      percentXp = builder.comment("Percent modifier for XP gain while the effect is active")
          .translation(CONFIG_PREFIX + "percentXp").defineInRange("percentXp", 0.0F, -1.0F, 10.0F);

      beneficial = builder.comment("Set to true to set the effect as beneficial instead of harmful")
          .translation(CONFIG_PREFIX + "beneficial").define("beneficial", false);

      builder.pop();

      builder.push("miscellaneous");

      restrictRespawning = builder.comment(
          "Set to true to restrict respawning to the world spawn, players cannot set new spawn points")
          .translation(CONFIG_PREFIX + "restrictRespawning").define("restrictRespawning", false);

      respawnItems = builder
          .comment("List of items to give players on respawn\nFormat: modid:item;stacksize")
          .translation(CONFIG_PREFIX + "respawnItems")
          .defineList("respawnItems", new ArrayList<>(), s -> s instanceof String);

      mobSpawnsOnDeath = builder
          .comment("List of mobs to spawn on player death\nFormat: modid:name")
          .translation(CONFIG_PREFIX + "mobSpawnsOnDeath")
          .defineList("mobSpawnsOnDeath", new ArrayList<>(), s -> s instanceof String);

      builder.pop();
    }
  }

  public static void transform(CommentedConfig configData) {
    OVERRIDES.overrides = new ObjectConverter().toObject(configData, OverridesConfig::new);
    overrides = OVERRIDES.overrides.overrides;
    conditions = OVERRIDES.overrides.conditions;
  }

  public static void bakeConfigs() {
    keepChance = SERVER.keepChance.get();
    destroyChance = SERVER.destroyChance.get();
    keepDurabilityLoss = SERVER.keepDurabilityLoss.get();
    dropDurabilityLoss = SERVER.dropDurabilityLoss.get();

    mainhandKeepChance = SERVER.mainhandKeepChance.get();
    mainhandDestroyChance = SERVER.mainhandDestroyChance.get();
    mainhandKeepDurabilityLoss = SERVER.mainhandKeepDurabilityLoss.get();
    mainhandDropDurabilityLoss = SERVER.mainhandDropDurabilityLoss.get();

    hotbarKeepChance = SERVER.hotbarKeepChance.get();
    hotbarDestroyChance = SERVER.hotbarDestroyChance.get();
    hotbarKeepDurabilityLoss = SERVER.hotbarKeepDurabilityLoss.get();
    hotbarDropDurabilityLoss = SERVER.hotbarDropDurabilityLoss.get();

    offhandKeepChance = SERVER.offhandKeepChance.get();
    offhandDestroyChance = SERVER.offhandDestroyChance.get();
    offhandKeepDurabilityLoss = SERVER.offhandKeepDurabilityLoss.get();
    offhandDropDurabilityLoss = SERVER.offhandDropDurabilityLoss.get();

    mainKeepChance = SERVER.mainKeepChance.get();
    mainDestroyChance = SERVER.mainDestroyChance.get();
    mainKeepDurabilityLoss = SERVER.mainKeepDurabilityLoss.get();
    mainDropDurabilityLoss = SERVER.mainDropDurabilityLoss.get();

    headKeepChance = SERVER.headKeepChance.get();
    headDestroyChance = SERVER.headDestroyChance.get();
    headKeepDurabilityLoss = SERVER.headKeepDurabilityLoss.get();
    headDropDurabilityLoss = SERVER.headDropDurabilityLoss.get();

    chestKeepChance = SERVER.chestKeepChance.get();
    chestDestroyChance = SERVER.chestDestroyChance.get();
    chestKeepDurabilityLoss = SERVER.chestKeepDurabilityLoss.get();
    chestDropDurabilityLoss = SERVER.chestDropDurabilityLoss.get();

    legsKeepChance = SERVER.legsKeepChance.get();
    legsDestroyChance = SERVER.legsDestroyChance.get();
    legsKeepDurabilityLoss = SERVER.legsKeepDurabilityLoss.get();
    legsDropDurabilityLoss = SERVER.legsDropDurabilityLoss.get();

    feetKeepChance = SERVER.feetKeepChance.get();
    feetDestroyChance = SERVER.feetDestroyChance.get();
    feetKeepDurabilityLoss = SERVER.feetKeepDurabilityLoss.get();
    feetDropDurabilityLoss = SERVER.feetDropDurabilityLoss.get();

    curioKeepChance = SERVER.curioKeepChance.get();
    curioDestroyChance = SERVER.curioDestroyChance.get();
    curioKeepDurabilityLoss = SERVER.curioKeepDurabilityLoss.get();
    curioDropDurabilityLoss = SERVER.curioDropDurabilityLoss.get();

    cosmeticArmorKeepChance = SERVER.cosmeticArmorKeepChance.get();
    cosmeticArmorDestroyChance = SERVER.cosmeticArmorDestroyChance.get();
    cosmeticArmorKeepDurabilityLoss = SERVER.cosmeticArmorKeepDurabilityLoss.get();
    cosmeticArmorDropDurabilityLoss = SERVER.cosmeticArmorDropDurabilityLoss.get();

    itemSettings = SERVER.itemSettings.get();
    limitDurabilityLoss = SERVER.limitDurabilityLoss.get();
    dropDespawnTime = SERVER.dropDespawnTime.get();

    rarity = SERVER.rarity.get();
    levelDropChance = SERVER.levelDropChance.get();
    baseSave = SERVER.baseSave.get();
    extraSavePerLevel = SERVER.extraSavePerLevel.get();
    allowedOnBooks = SERVER.allowedOnBooks.get();
    canApplyEnchantingTable = SERVER.canApplyEnchantingTable.get();
    maxSoulbindingLevel = SERVER.maxSoulbindingLevel.get();
    isTreasure = SERVER.isTreasure.get();

    cures = SERVER.cures.get();
    effects = SERVER.effects.get();
    keepEffectsMode = SERVER.keepEffectsMode.get();
    keepEffects = SERVER.keepEffects.get();

    keepFood = SERVER.keepFood.get();
    keepSaturation = SERVER.keepSaturation.get();
    keepExhaustion = SERVER.keepExhaustion.get();
    minFood = SERVER.minFood.get();
    maxFood = SERVER.maxFood.get();

    lostXp = SERVER.lostXp.get();
    xpDropMode = SERVER.xpDropMode.get();
    droppedXpPercent = SERVER.droppedXpPercent.get();
    droppedXpPerLevel = SERVER.droppedXpPerLevel.get();
    maxDroppedXp = SERVER.maxDroppedXp.get();

    healthMod = SERVER.healthMod.get();
    armorMod = SERVER.armorMod.get();
    toughnessMod = SERVER.toughnessMod.get();
    movementMod = SERVER.movementMod.get();
    damageMod = SERVER.damageMod.get();
    attackSpeedMod = SERVER.attackSpeedMod.get();
    gradualRecovery = SERVER.gradualRecovery.get();
    duration = SERVER.duration.get();
    mementoCures = SERVER.mementoCures.get();
    noFood = SERVER.noFood.get();
    percentXp = SERVER.percentXp.get();
    beneficial = SERVER.beneficial.get();

    restrictRespawning = SERVER.restrictRespawning.get();
    respawnItems = SERVER.respawnItems.get();
    mobSpawnsOnDeath = SERVER.mobSpawnsOnDeath.get();
  }
}
