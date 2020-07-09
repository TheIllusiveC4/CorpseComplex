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

package top.theillusivec4.corpsecomplex.common.modules.miscellaneous;

import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;

public class MiscellaneousModule {

  @SubscribeEvent
  public void setSpawn(final PlayerSetSpawnEvent evt) {

    if (!evt.getPlayer().getEntityWorld().isRemote) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(deathStorage -> {
        if (deathStorage.getSettings().getMiscellaneousSettings().isRestrictRespawning()) {
          evt.setCanceled(true);
        }
      });
    }
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {
    DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
        deathStorage -> deathStorage.getSettings().getMiscellaneousSettings().getRespawnItems()
            .forEach(item -> ItemHandlerHelper.giveItemToPlayer(evt.getPlayer(), item.copy())));
  }

  @SubscribeEvent
  public void playerDeath(final LivingDeathEvent evt) {

    if (!(evt.getEntityLiving() instanceof PlayerEntity)) {
      return;
    }
    PlayerEntity playerEntity = (PlayerEntity) evt.getEntityLiving();
    World world = playerEntity.world;

    if (!world.isRemote()) {
      DeathStorageCapability.getCapability(playerEntity).ifPresent(
          deathStorage -> deathStorage.getSettings().getMiscellaneousSettings()
              .getMobSpawnsOnDeath()
              .forEach(mob -> spawnMob(mob, playerEntity.func_233580_cy_(), world)));
    }
  }

  private static void spawnMob(EntityType<?> type, BlockPos blockPos, World world) {
    double d0 =
        (double) blockPos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4 + 0.5D;
    double d1 = (blockPos.getY() + world.rand.nextInt(3) - 1);
    double d2 =
        (double) blockPos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4 + 0.5D;

    if (world.hasNoCollisions(type.func_220328_a(d0, d1, d2))) {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putString("id", Objects.requireNonNull(type.getRegistryName()).toString());
      Entity entity = EntityType.func_220335_a(compoundnbt, world, (entity1) -> {
        entity1.setLocationAndAngles(d0, d1, d2, entity1.rotationYaw, entity1.rotationPitch);
        return entity1;
      });

      if (entity != null) {
        entity.setLocationAndAngles(entity.getPosX(), entity.getPosY(), entity.getPosZ(),
            world.rand.nextFloat() * 360.0F, 0.0F);

        if (entity instanceof MobEntity) {
          ((MobEntity) entity)
              .onInitialSpawn(world, world.getDifficultyForLocation(entity.func_233580_cy_()),
                  SpawnReason.TRIGGERED, null, null);
        }
        addEntity(entity, world);
        world.playEvent(2004, blockPos, 0);

        if (entity instanceof MobEntity) {
          ((MobEntity) entity).spawnExplosionParticle();
          ((MobEntity) entity).enablePersistence();
        }
      }
    }
  }

  private static void addEntity(Entity entity, World world) {

    if (world.addEntity(entity)) {

      for (Entity entity1 : entity.getPassengers()) {
        addEntity(entity1, world);
      }
    }
  }
}
