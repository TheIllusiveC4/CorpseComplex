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

package top.theillusivec4.corpsecomplex.common.modules.mementomori;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import javax.annotation.Nonnull;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.registry.CorpseComplexRegistry;
import top.theillusivec4.corpsecomplex.common.registry.RegistryReference;

public class MementoMoriEffect extends Effect {

  private static final double INT_CHANGE = 1.0D;
  private static final double PERCENT_CHANGE = 0.05D;
  private static final String NAME = "Memento Mori";

  public static final Map<Attribute, AttributeInfo> ATTRIBUTES = new HashMap<>();

  public MementoMoriEffect() {
    super(EffectType.HARMFUL, 0);
    this.setRegistryName(RegistryReference.MEMENTO_MORI);
  }

  @Override
  public void performEffect(@Nonnull LivingEntity entityLivingBaseIn, int amplifier) {
    EffectInstance effect = entityLivingBaseIn
        .getActivePotionEffect(CorpseComplexRegistry.MEMENTO_MORI);

    if (effect != null) {
      int duration = effect.getDuration();
      ATTRIBUTES.forEach((attribute, info) -> {

        if (info.modifier.getAmount() != 0 && duration % info.tick == 0) {
          ModifiableAttributeInstance instance = entityLivingBaseIn.getAttribute(attribute);

          if (instance != null) {
            AttributeModifier modifier = instance.getModifier(info.modifier.getID());

            if (modifier != null) {
              instance.removeModifier(modifier);
              instance.applyNonPersistentModifier(new AttributeModifier(modifier.getID(), modifier.getName(),
                  modifier.getAmount() + info.tickAmount, modifier.getOperation()));
            }
          }
        }
      });
    }

    if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth()) {
      entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
    }
  }

  @Override
  public boolean isReady(int duration, int amplifier) {
    return duration < CorpseComplexConfig.SERVER.duration.get() * 20
        && CorpseComplexConfig.SERVER.gradualRecovery.get() && isChangeTick(duration);
  }

  private boolean isChangeTick(int duration) {
    return ATTRIBUTES.values().stream().anyMatch((info -> duration % info.tick == 0));
  }

  @Override
  public boolean isBeneficial() {
    return CorpseComplexConfig.SERVER.beneficial.get();
  }

  @Override
  public void removeAttributesModifiersFromEntity(LivingEntity entityLivingBaseIn,
      @Nonnull AttributeModifierManager attributeMapIn, int amplifier) {

    for (Entry<Attribute, AttributeInfo> attribute : ATTRIBUTES.entrySet()) {
      ModifiableAttributeInstance iattributeinstance = attributeMapIn
          .createInstanceIfAbsent(attribute.getKey());

      if (iattributeinstance != null) {
        AttributeModifier modifier = iattributeinstance
            .getModifier(attribute.getValue().modifier.getID());

        if (modifier != null) {
          iattributeinstance.removeModifier(modifier);
        }
      }
    }
  }

  @Override
  public void applyAttributesModifiersToEntity(LivingEntity entityLivingBaseIn,
      @Nonnull AttributeModifierManager attributeMapIn, int amplifier) {

    for (Entry<Attribute, AttributeInfo> attribute : ATTRIBUTES.entrySet()) {
      ModifiableAttributeInstance iattributeinstance = attributeMapIn
          .createInstanceIfAbsent(attribute.getKey());

      if (iattributeinstance != null) {
        AttributeModifier attributemodifier = attribute.getValue().modifier;
        iattributeinstance.removeModifier(attributemodifier);
        iattributeinstance.applyNonPersistentModifier(
            new AttributeModifier(attributemodifier.getID(), NAME, attributemodifier.getAmount(),
                attributemodifier.getOperation()));
      }
    }
  }

  public static class AttributeInfo {

    final AttributeModifier modifier;
    int tick;
    double tickAmount;

    public AttributeInfo(double amount, UUID uuid, Operation operation) {
      this.modifier = new AttributeModifier(uuid, NAME, amount, operation);
      setTick(amount);
    }

    private void setTick(double amount) {
      double changeAmount;
      double sign = Math.signum(amount) * -1;
      double changeTick = Math.abs(CorpseComplexConfig.SERVER.duration.get() / amount) * 20;
      boolean percent = this.modifier.getOperation() != Operation.ADDITION;

      if (percent) {
        changeAmount = sign * PERCENT_CHANGE;
        changeTick *= PERCENT_CHANGE;
      } else {
        changeAmount = sign * INT_CHANGE;
        changeTick *= INT_CHANGE;
      }

      if (changeTick >= 1) {
        changeTick = Math.floor(changeTick);
      } else if (percent) {
        changeAmount = sign * (1 / changeTick);
        changeTick = 1;
      } else {
        changeAmount = sign * Math.floor(1 / changeTick);
        changeTick = 1;
      }
      this.tick = (int) changeTick;
      this.tickAmount = changeAmount;
    }
  }
}
