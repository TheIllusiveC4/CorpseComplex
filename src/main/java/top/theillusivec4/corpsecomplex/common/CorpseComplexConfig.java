package top.theillusivec4.corpsecomplex.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.EnumValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import org.apache.commons.lang3.tuple.Pair;
import top.theillusivec4.corpsecomplex.CorpseComplex;

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

  public enum PermissionMode {
    BLACKLIST, WHITELIST
  }

  public enum XpDropMode {
    PERCENT, PER_LEVEL
  }

  public enum InventorySection {
    MAINHAND, HOTBAR, OFFHAND, MAIN, HEAD, CHEST, LEGS, FEET
  }

  public static class Server {

    public final ConfigValue<List<? extends String>> keepInventory;
    public final ConfigValue<List<? extends String>> essentialItems;
    public final ConfigValue<List<? extends String>> cursedItems;

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

    public Server(ForgeConfigSpec.Builder builder) {
      builder.push("inventory");

      keepInventory = builder.comment(
          "List of inventory sections to keep on death\nAllowed Values: MAINHAND, OFFHAND, MAIN, HOTBAR, HEAD, CHEST, LEGS, FEET")
          .translation(CONFIG_PREFIX + "keepInventory")
          .defineList("keepInventory", new ArrayList<>(), s -> s instanceof String);

      essentialItems = builder.comment("List of items to always keep, regardless of other settings")
          .translation(CONFIG_PREFIX + "essentialItems")
          .defineList("essentialItems", new ArrayList<>(), s -> s instanceof String);

      cursedItems = builder.comment(
          "List of items to always drop, regardless of other settings\nAppend ';destroy' if it should be destroyed")
          .translation(CONFIG_PREFIX + "cursedItems")
          .defineList("cursedItems", new ArrayList<>(), s -> s instanceof String);

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

      builder.pop();
    }
  }
}
