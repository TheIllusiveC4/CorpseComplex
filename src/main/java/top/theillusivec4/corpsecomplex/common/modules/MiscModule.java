package top.theillusivec4.corpsecomplex.common.modules;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;

public class MiscModule {

  @SubscribeEvent
  public void setSpawn(final PlayerSetSpawnEvent evt) {
    DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(deathStorage -> {
      if (deathStorage.getSettings().miscellaneous.restrictRespawning) {
        evt.setCanceled(true);
      }
    });
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {
    DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(deathStorage -> {
      deathStorage.getSettings().miscellaneous.respawnItems.forEach(
          (item, amount) -> ItemHandlerHelper
              .giveItemToPlayer(evt.getPlayer(), new ItemStack(item, amount)));
    });
  }
}
