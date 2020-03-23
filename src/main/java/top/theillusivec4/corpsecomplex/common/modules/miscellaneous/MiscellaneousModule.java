package top.theillusivec4.corpsecomplex.common.modules.miscellaneous;

import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;

public class MiscellaneousModule {

  @SubscribeEvent
  public void setSpawn(final PlayerSetSpawnEvent evt) {
    DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(deathStorage -> {
      if (deathStorage.getSettings().getMiscellaneousSettings().isRestrictRespawning()) {
        evt.setCanceled(true);
      }
    });
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {
    DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(deathStorage -> {
      deathStorage.getSettings().getMiscellaneousSettings().getRespawnItems()
          .forEach((item) -> ItemHandlerHelper.giveItemToPlayer(evt.getPlayer(), item.copy()));
    });
  }
}
