/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.helpers;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import c4.corpsecomplex.common.modules.ExperienceModule;
import c4.corpsecomplex.common.modules.HungerModule;
import c4.corpsecomplex.common.modules.compatibility.toughasnails.TANModule;
import c4.corpsecomplex.common.modules.effects.EffectsModule;
import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import c4.corpsecomplex.common.modules.spawning.SpawningModule;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class ModuleHelper {

  public static Configuration cfg;

  private static ArrayList<Class<? extends Module>> moduleClasses;
  private static Map<Class<? extends Module>, Module> instances =
          new HashMap<>();

  static {
    moduleClasses = new ArrayList<>();
    addModule(EffectsModule.class);
    addModule(ExperienceModule.class);
    addModule(InventoryModule.class);
    addModule(HungerModule.class);
    addModule(SpawningModule.class);
    addModule("toughasnails", TANModule.class);
  }

  public static void preInit(FMLPreInitializationEvent e) {

    moduleClasses.forEach(module -> {
      try {
        instances.put(module, module.getConstructor().newInstance());
      } catch (Exception e1) {
        CorpseComplex.logger.log(Level.ERROR,
                "Failed to initialize module " + module, e1);
      }
    });

    forEachModule(Module::loadSubmodules);
    initConfig(e);
  }

  private static void initConfig(FMLPreInitializationEvent e) {
    File directory = e.getModConfigurationDirectory();
    cfg = new Configuration(new File(directory.getPath(), "corpsecomplex.cfg"));
    try {
      cfg.load();
      forEachModule(module -> {
        registerEvents(module);
        module.setPropOrder();
      });
    } catch (Exception e1) {
      CorpseComplex.logger.log(Level.ERROR, "Problem loading config file!", e1);
    } finally {
      if (cfg.hasChanged()) {
        cfg.save();
      }
    }
  }

  private static void reloadConfigs() {

    forEachModule(ModuleHelper::registerEvents);

    if (cfg.hasChanged()) {
      cfg.save();
    }
  }

  private static void registerEvents(Module module) {
    module.loadModuleConfig();
    registerModuleEvents(module);
    module.forEachSubmodule(submodule -> {
      submodule.loadModuleConfig();
      registerSubmoduleEvents(submodule);
    });
  }

  private static void registerModuleEvents(Module module) {
    module.setEnabled();

    if (!module.enabled && module.prevEnabled && module.hasEvents()) {
      MinecraftForge.EVENT_BUS.unregister(module);
    } else if (module.enabled && !module.prevEnabled && module.hasEvents()) {
      MinecraftForge.EVENT_BUS.register(module);
    }

    module.prevEnabled = module.enabled;
  }

  private static void registerSubmoduleEvents(Submodule submodule) {
    submodule.setEnabled();

    if (!submodule.enabled && submodule.prevEnabled && submodule.hasEvents()) {
      MinecraftForge.EVENT_BUS.unregister(submodule);
    } else if (submodule.enabled && !submodule.prevEnabled &&
            submodule.hasEvents()) {
      MinecraftForge.EVENT_BUS.register(submodule);
    }

    submodule.prevEnabled = submodule.enabled;
  }

  private static void addModule(Class<? extends Module> module) {
    if (!moduleClasses.contains(module)) {
      moduleClasses.add(module);
    }
  }

  private static void addModule(String modid, Class<? extends Module> module) {
    if (Loader.isModLoaded(modid) && !moduleClasses.contains(module)) {
      moduleClasses.add(module);
    }
  }

  private static void forEachModule(Consumer<Module> module) {
    instances.values().forEach(module);
  }

  @Mod.EventBusSubscriber
  private static class ConfigChangeHandler {

    @SubscribeEvent
    public static void onConfigChanged(
            ConfigChangedEvent.OnConfigChangedEvent e) {
      if (e.getModID().equals(CorpseComplex.MODID)) {
        reloadConfigs();
      }
    }
  }
}
