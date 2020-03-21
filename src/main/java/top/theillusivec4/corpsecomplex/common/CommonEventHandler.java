package top.theillusivec4.corpsecomplex.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.Provider;
import top.theillusivec4.corpsecomplex.common.util.DeathConditionManager;
import top.theillusivec4.corpsecomplex.common.util.DeathSettingsManager;

public class CommonEventHandler {

  @SubscribeEvent
  public void attachCapability(final AttachCapabilitiesEvent<Entity> evt) {

    if (evt.getObject() instanceof PlayerEntity) {
      evt.addCapability(DeathStorageCapability.ID, new Provider((PlayerEntity) evt.getObject()));
    }
  }

  @SubscribeEvent
  public void serverAboutToStart(final FMLServerAboutToStartEvent evt) {

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
