package top.theillusivec4.corpsecomplex.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.Provider;

public class CommonEventHandler {

  @SubscribeEvent
  public void attachCapability(final AttachCapabilitiesEvent<Entity> evt) {

    if (evt.getObject() instanceof PlayerEntity) {
      evt.addCapability(DeathStorageCapability.ID, new Provider((PlayerEntity) evt.getObject()));
    }
  }

//  @SubscribeEvent(priority = EventPriority.HIGHEST)
//  public void initDeathSettings(final LivingDeathEvent evt) {
//
//    if (evt.getEntityLiving() instanceof PlayerEntity) {
//      DeathStorageCapability.getCapability((PlayerEntity) evt.getEntityLiving())
//          .ifPresent(deathStorage -> {
//            deathStorage.setSettings(DeathSettings.DEFAULT);
//          });
//    }
//  }
}
