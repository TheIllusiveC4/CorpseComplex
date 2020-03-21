package top.theillusivec4.corpsecomplex.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.DeathCondition;
import top.theillusivec4.corpsecomplex.common.DeathCondition.Builder;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class DeathConditionManager {

  public static final Map<String, DeathCondition> CONDITIONS = new HashMap<>();

  public static boolean matches(DeathCondition deathCondition, LivingDeathEvent evt) {
    DamageSource source = evt.getSource();
    Optional<String> damageTypeOpt = deathCondition.getDamageType();
    boolean matchesDamage = damageTypeOpt.map(damageType -> {
      if (source.damageType.equals(damageType)) {
        return true;
      } else if (source.isFireDamage() && damageType.equals("fire")) {
        return true;
      } else if (source.isMagicDamage() && damageType.equals("magic")) {
        return true;
      } else if (source.isExplosion() && damageType.equals("explosion")) {
        return true;
      } else {
        return source.isProjectile() && damageType.equals("projectile");
      }
    }).orElse(true);

    if (!matchesDamage) {
      return false;
    }
    Optional<EntityType<?>> imSrcOpt = deathCondition.getImmediateSource();
    boolean matchesImSrc = imSrcOpt.map(immediateSource -> {
      Entity imSrc = source.getImmediateSource();
      return imSrc != null && imSrc.getType() == immediateSource;
    }).orElse(true);

    if (!matchesImSrc) {
      return false;
    }

    Optional<EntityType<?>> trueSrcOpt = deathCondition.getTrueSource();
    return trueSrcOpt.map(trueSource -> {
      Entity trueSrc = source.getTrueSource();
      return trueSrc != null && trueSrc.getType() == trueSource;
    }).orElse(true);
  }

  public static void importConfig() {
    CONDITIONS.clear();
    CorpseComplexConfig.conditions.forEach(condition -> {
      String identifier = condition.identifier;

      if (identifier == null) {
        CorpseComplex.LOGGER.error("Missing identifier in conditions! Skipping...");
        return;
      }
      Builder builder = new Builder().damageType(condition.damageType)
          .immediateSource(getEntityType(condition.immediateSource))
          .trueSource(getEntityType(condition.trueSource));
      CONDITIONS.put(identifier, builder.build());
    });
  }

  public static EntityType<?> getEntityType(@Nullable String name) {
    return name == null ? null : ForgeRegistries.ENTITIES.getValue(new ResourceLocation(name));
  }
}
