package top.theillusivec4.corpsecomplex.common.modules.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.Inventory;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.VanillaInventory;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration.CosmeticArmorInventory;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration.CuriosInventory;

public class InventoryModule {

  public static final List<Inventory> STORAGE = new ArrayList<>();
  public static final Random RANDOM = new Random();

  @SubscribeEvent
  public void serverStart(final FMLServerStartedEvent evt) {
    STORAGE.add(new VanillaInventory());

    if (ModList.get().isLoaded("curios")) {
      STORAGE.add(new CuriosInventory());
    }

    if (ModList.get().isLoaded("cosmeticarmorreworked")) {
      STORAGE.add(new CosmeticArmorInventory());
    }
  }

  @SubscribeEvent(priority = EventPriority.LOW)
  public void playerDrops(final LivingDropsEvent evt) {

    if (!(evt.getEntityLiving() instanceof PlayerEntity)) {
      return;
    }
    PlayerEntity playerEntity = (PlayerEntity) evt.getEntityLiving();
    DeathStorageCapability.getCapability(playerEntity).ifPresent(deathStorage -> {
      int despawnTime = deathStorage.getSettings().getInventorySettings().getDropDespawnTime();

      if (despawnTime < 1) {
        evt.getDrops().forEach(ItemEntity::setNoDespawn);
      } else {
        evt.getDrops().forEach(itemEntity -> itemEntity.lifespan = despawnTime * 20);
      }
    });
  }

  @SubscribeEvent(priority = EventPriority.HIGH)
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

  @SubscribeEvent(priority = EventPriority.LOW)
  public void playerRespawn(final PlayerEvent.Clone evt) {

    if (evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
          newStorage -> DeathStorageCapability.getCapability(evt.getOriginal()).ifPresent(
              oldStorage -> STORAGE
                  .forEach(storage -> storage.retrieveInventory(newStorage, oldStorage))));
    }
  }
}
