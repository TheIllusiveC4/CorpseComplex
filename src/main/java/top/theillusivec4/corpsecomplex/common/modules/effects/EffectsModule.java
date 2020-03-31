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

package top.theillusivec4.corpsecomplex.common.modules.effects;

import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.util.Enums.PermissionMode;

public class EffectsModule {

  @SubscribeEvent
  public void finishItemUse(LivingEntityUseItemEvent.Finish evt) {
    LivingEntity entity = evt.getEntityLiving();

    if (!entity.getEntityWorld().isRemote() && entity instanceof PlayerEntity) {
      DeathStorageCapability.getCapability((PlayerEntity) entity).ifPresent(
          deathStorage -> deathStorage.getSettings().getEffectsSettings().getCures()
              .forEach(itemStack -> {
                if (ItemStack.areItemsEqual(evt.getItem(), itemStack)) {
                  entity.curePotionEffects(evt.getItem());
                }
              }));
    }
  }

  @SubscribeEvent
  public void playerDeath(final LivingDeathEvent evt) {

    if (!(evt.getEntityLiving() instanceof PlayerEntity)) {
      return;
    }
    PlayerEntity playerEntity = (PlayerEntity) evt.getEntityLiving();
    World world = playerEntity.getEntityWorld();

    if (!world.isRemote()) {
      DeathStorageCapability.getCapability(playerEntity).ifPresent(
          deathStorage -> playerEntity.getActivePotionEffects().forEach(effectInstance -> {
            boolean flag;
            EffectsSetting setting = deathStorage.getSettings().getEffectsSettings();
            List<Effect> keepEffects = setting.getKeepEffects();

            if (keepEffects.isEmpty()) {
              flag = true;
            } else if (setting.getKeepEffectsMode() == PermissionMode.BLACKLIST) {
              flag = !keepEffects.contains(effectInstance.getPotion());
            } else {
              flag = keepEffects.contains(effectInstance.getPotion());
            }

            if (flag) {
              deathStorage.addEffectInstance(effectInstance);
            }
          }));
    }
  }

  @SubscribeEvent
  public void playerClone(final PlayerEvent.Clone evt) {

    if (evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
          deathStorage -> DeathStorageCapability.getCapability(evt.getOriginal()).ifPresent(
              oldDeathStorage -> oldDeathStorage.getEffects()
                  .forEach(deathStorage::addEffectInstance)));
    }
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {

    if (!evt.isEndConquered()) {
      PlayerEntity player = evt.getPlayer();
      DeathStorageCapability.getCapability(player).ifPresent(deathStorage -> {
        deathStorage.getEffects().forEach(player::addPotionEffect);
        deathStorage.clearEffects();
        deathStorage.getSettings().getEffectsSettings().getEffects().forEach(effectInstance -> {
          EffectInstance newEffect = new EffectInstance(effectInstance.getPotion(),
              effectInstance.getDuration(), effectInstance.getAmplifier());
          newEffect.setCurativeItems(effectInstance.getCurativeItems());
          player.addPotionEffect(newEffect);
        });
      });
    }
  }
}
