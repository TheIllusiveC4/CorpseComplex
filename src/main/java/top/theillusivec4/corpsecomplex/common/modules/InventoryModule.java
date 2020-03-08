package top.theillusivec4.corpsecomplex.common.modules;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.Provider;
import top.theillusivec4.corpsecomplex.common.modules.inventory.Storage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.VanillaStorage;

public class InventoryModule {

  public static final Map<String, Class<? extends Storage>> STORAGE_ADDONS = new HashMap<>();
  public static final List<Storage> STORAGE = Collections.singletonList(new VanillaStorage());

  static {
    //    STORAGE_ADDONS.put("curios", CuriosStorage.class);
  }

  @SubscribeEvent
  public void attachCapability(final AttachCapabilitiesEvent<Entity> evt) {

    if (evt.getObject() instanceof PlayerEntity) {
      evt.addCapability(DeathStorageCapability.ID, new Provider((PlayerEntity) evt.getObject()));
    }
  }

  @SubscribeEvent
  public void playerDeath(final LivingDeathEvent evt) {

    if (!(evt.getEntityLiving() instanceof PlayerEntity)) {
      return;
    }
    PlayerEntity playerEntity = (PlayerEntity) evt.getEntityLiving();
    World world = playerEntity.getEntityWorld();

    if (!world.isRemote() && !world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
      DeathStorageCapability.getCapability(playerEntity).ifPresent(
          deathStorage -> STORAGE.forEach(storage -> storage.storeInventory(deathStorage)));
    }
  }

  @SubscribeEvent
  public void playerRespawn(final PlayerEvent.Clone evt) {

    if (!evt.getEntity().getEntityWorld().isRemote() && evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
          newStorage -> DeathStorageCapability.getCapability(evt.getOriginal()).ifPresent(
              oldStorage -> STORAGE
                  .forEach(storage -> storage.retrieveInventory(newStorage, oldStorage))));
    }
  }
}
