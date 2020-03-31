package top.theillusivec4.corpsecomplex.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.Provider;
import top.theillusivec4.corpsecomplex.common.util.DeathDamageSource;

public class CommonEventHandler {

  @SubscribeEvent
  public void attachCapability(final AttachCapabilitiesEvent<Entity> evt) {

    if (evt.getObject() instanceof PlayerEntity) {
      evt.addCapability(DeathStorageCapability.ID, new Provider((PlayerEntity) evt.getObject()));
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void playerDeath(final LivingDeathEvent evt) {

    if (!(evt.getEntityLiving() instanceof PlayerEntity)) {
      return;
    }
    PlayerEntity playerEntity = (PlayerEntity) evt.getEntityLiving();
    World world = playerEntity.getEntityWorld();

    if (!world.isRemote()) {
      DeathStorageCapability.getCapability(playerEntity).ifPresent(deathStorage -> {
        deathStorage.setDeathDamageSource(new DeathDamageSource(evt.getSource(), world));
        deathStorage.buildSettings();
      });
    }
  }

  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public void playerClone(final PlayerEvent.Clone evt) {

    if (evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
          deathStorage -> DeathStorageCapability.getCapability(evt.getOriginal()).ifPresent(
              oldDeathStorage -> deathStorage
                  .setDeathDamageSource(oldDeathStorage.getDeathDamageSource())));
    }
  }
}
