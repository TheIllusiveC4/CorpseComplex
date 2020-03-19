package top.theillusivec4.corpsecomplex.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.Provider;

public class CommonEventHandler {

  @SubscribeEvent
  public void attachCapability(final AttachCapabilitiesEvent<Entity> evt) {

    if (evt.getObject() instanceof PlayerEntity) {
      evt.addCapability(DeathStorageCapability.ID, new Provider((PlayerEntity) evt.getObject()));
    }
  }

  @SubscribeEvent
  public void serverAboutToStart(final FMLServerAboutToStartEvent evt) {
//    CorpseComplex.LOGGER.debug("Loading override configs");
//    final MinecraftServer server = evt.getServer();
//    final Path serverConfig = server.getActiveAnvilConverter()
//        .getFile(server.getFolderName(), "serverconfig").toPath();
//    final Path configPath = serverConfig.resolve("corpsecomplex-overrides.toml");
//    final CommentedFileConfig configData = CommentedFileConfig.builder(configPath).sync().
//        preserveInsertionOrder().
//        autosave().
//        onFileNotFound((newfile, configFormat)-> setupConfigFile(c, newfile, configFormat)).
//        writingMode(WritingMode.REPLACE).
//        build();
//    CorpseComplex.LOGGER.debug("Built TOML config for {}", configPath.toString());
//    configData.load();
//    CorpseComplex.LOGGER.debug("Loaded TOML config file {}", configPath.toString());
//    try {
//      FileWatcher
//          .defaultInstance().addWatch(configPath, new ConfigWatcher(c, configData, Thread.currentThread().getContextClassLoader()));
//      CorpseComplex.LOGGER.debug("Watching TOML config file {} for changes", configPath.toString());
//    } catch (IOException e) {
//      throw new RuntimeException("Couldn't watch config file", e);
//    }
//    return configData;
//    config.setConfigData(configData);
//    config.fireEvent(new ModConfig.Loading(config));
//    config.save();
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
