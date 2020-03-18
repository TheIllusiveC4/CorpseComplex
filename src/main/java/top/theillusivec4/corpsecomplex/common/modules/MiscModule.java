package top.theillusivec4.corpsecomplex.common.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;

public class MiscModule {

  @SubscribeEvent
  public void setSpawn(final PlayerSetSpawnEvent evt) {

    if (CorpseComplexConfig.SERVER.restrictRespawning.get()) {
      evt.setCanceled(true);
    }
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerRespawnEvent evt) {
    respawnItems.forEach((item, amount) -> ItemHandlerHelper
        .giveItemToPlayer(evt.getPlayer(), new ItemStack(item, amount)));
  }
}
