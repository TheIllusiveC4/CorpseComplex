package c4.corpserun.core.modules;

import c4.corpserun.CorpseRun;
import c4.corpserun.core.modules.compatibility.TANModule;
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

public final class ModuleHandler {

    private static ArrayList<Class<? extends Module>> moduleClasses;
    private static Map<Class<? extends Module>, Module> instances = new HashMap<>();
    public static Configuration cfg;

    static {
        moduleClasses = new ArrayList<>();
        addModule(EffectsModule.class);
        addModule(ExperienceModule.class);
        addModule(InventoryModule.class);
        addModule(HungerModule.class);
    }

    public static void preInit(FMLPreInitializationEvent e) {

        addCompatibilityModule("toughasnails", TANModule.class);

        moduleClasses.forEach(module -> {
            try {
                instances.put(module, module.newInstance());
            } catch (Exception e1) {
                CorpseRun.logger.log(Level.ERROR, "Failed to initialize module " + module, e1);
            }
        });

        initConfig(e);
    }

    public static void init() {

        forEachModule(ModuleHandler::registerEvents);
    }

    private static void initConfig(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        cfg = new Configuration(new File(directory.getPath(),"corpserun.cfg"));
        try {
            cfg.load();
            forEachModule(module -> {
                module.loadModuleConfig();
                module.setPropOrder();
            });
        } catch (Exception e1) {
            CorpseRun.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if(cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void registerEvents(Module module) {
        module.setEnabled();

        if (!module.enabled && module.prevEnabled && module.hasEvents()) {
            MinecraftForge.EVENT_BUS.unregister(module);
        } else if (module.enabled && !module.prevEnabled && module.hasEvents()) {
            MinecraftForge.EVENT_BUS.register(module);
        }

        module.prevEnabled = module.enabled;
    }

    private static void reloadConfigs() {

        forEachModule(module -> {
            module.loadModuleConfig();
            registerEvents(module);
        });

        if(cfg.hasChanged()) {
            cfg.save();
        }
    }

    private static void addModule(Class<? extends Module> module) {
        if (!moduleClasses.contains(module)) {
            moduleClasses.add(module);
        }
    }

    private static void addCompatibilityModule(String modid, Class<? extends Module> module) {
        if (Loader.isModLoaded(modid) && !moduleClasses.contains(module)) {
            moduleClasses.add(module);
        }
    }

    public static void forEachModule(Consumer<Module> module) {
        instances.values().forEach(module);
    }

    @Mod.EventBusSubscriber
    private static class ConfigChangeHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
            if (e.getModID().equals(CorpseRun.MODID)) {
                reloadConfigs();
            }
        }
    }
}
