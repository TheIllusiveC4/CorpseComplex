package top.theillusivec4.corpsecomplex.common.modules.inventory;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.corpsecomplex.CorpseComplex;
import top.theillusivec4.corpsecomplex.common.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.CorpseComplexConfig.InventorySection;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.Provider;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.Inventory;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.VanillaInventory;

public class InventoryModule {

  public static final Map<String, Class<? extends Inventory>> STORAGE_ADDONS = new HashMap<>();
  public static final List<Inventory> STORAGE = Collections.singletonList(new VanillaInventory());
  public static final Set<InventorySection> KEEP_SECTIONS = new HashSet<>();
  public static final Set<Item> ESSENTIAL_ITEMS = new HashSet<>();
  public static final Map<Item, Boolean> CURSED_ITEMS = new HashMap<>();

  static {
    //    STORAGE_ADDONS.put("curios", CuriosStorage.class);
  }

  @SubscribeEvent
  public void serverStarted(final FMLServerStartedEvent evt) {
    CorpseComplexConfig.SERVER.keepInventory.get().forEach(string -> {
      InventorySection section = null;
      try {
        section = InventorySection.valueOf(string);
      } catch (IllegalArgumentException e) {
        CorpseComplex.LOGGER.error("Invalid value " + string + " in keepInventory config!");
      }

      if (section != null) {
        KEEP_SECTIONS.add(section);
      }
    });
    CorpseComplexConfig.SERVER.essentialItems.get().forEach(string -> {
      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(string));

      if (item != null) {
        ESSENTIAL_ITEMS.add(item);
      }
    });
    CorpseComplexConfig.SERVER.cursedItems.get().forEach(string -> {
      String[] parsed = string.split(";");
      Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parsed[0]));

      if (item != null) {
        CURSED_ITEMS.put(item, parsed.length > 1);
      }
    });
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

    if (evt.isWasDeath()) {
      DeathStorageCapability.getCapability(evt.getPlayer()).ifPresent(
          newStorage -> DeathStorageCapability.getCapability(evt.getOriginal()).ifPresent(
              oldStorage -> STORAGE
                  .forEach(storage -> storage.retrieveInventory(newStorage, oldStorage))));
    }
  }
}
