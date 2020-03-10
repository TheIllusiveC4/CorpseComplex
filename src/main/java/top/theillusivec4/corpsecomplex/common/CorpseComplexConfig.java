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

  public static class Server {

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

    public Server(ForgeConfigSpec.Builder builder) {
      builder.push("effects");

      cures = builder.comment("List of valid items to cure curable effects")
          .translation(CONFIG_PREFIX + "cures")
          .defineList("cures", Collections.singletonList("minecraft:milk_bucket"),
              s -> s instanceof String);

      effects = builder.comment(
          "List of effects to apply on respawn\nFormat: modid:effect;duration(seconds);amplifier[;incurable]")
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
    }
  }
}
