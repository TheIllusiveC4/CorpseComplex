package top.theillusivec4.corpsecomplex.common;

import net.minecraftforge.common.ForgeConfigSpec;
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

    public final DoubleValue lostXp;
    public final EnumValue<XpDropMode> xpDropMode;
    public final DoubleValue droppedXpPercent;
    public final IntValue droppedXpPerLevel;
    public final IntValue maxDroppedXp;

    public Server(ForgeConfigSpec.Builder builder) {
      builder.push("experience");

      lostXp = builder.comment("Percentage of experience lost on death")
          .translation(CONFIG_PREFIX + ".lostXp").defineInRange("lostXp", 1.0D, 0.0D, 1.0D);

      xpDropMode = builder.comment(
          "Sets whether dropped XP is based on a percentage of the lost total or per lost level")
          .translation(CONFIG_PREFIX + ".xpDropMode")
          .defineEnum("xpDropMode", XpDropMode.PER_LEVEL);

      droppedXpPercent = builder
          .comment("Percentage of lost experience that is dropped when PERCENT is active")
          .translation(CONFIG_PREFIX + ".droppedXpPercent")
          .defineInRange("droppedXpPercent", 0.2D, 0.0D, 1.0D);

      droppedXpPerLevel = builder
          .comment("Amount of experience that is dropped per lost level when PER_LEVEL is active")
          .translation(CONFIG_PREFIX + ".droppedXpPerLevel")
          .defineInRange("droppedXpPerLevel", 7, 0, 100000);

      maxDroppedXp = builder.comment("Maximum amount of dropped experience")
          .translation(CONFIG_PREFIX + ".maxDroppedXp")
          .defineInRange("maxDroppedXp", 100, 0, 100000);

      builder.pop();
    }
  }
}
