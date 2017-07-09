package c4.corpserun.config;

import c4.corpserun.CorpseRun;
import c4.corpserun.config.values.*;
import c4.corpserun.proxy.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

public class ConfigHandler {

    public static Configuration cfg = CommonProxy.config;

    public static void readConfig() {

        try {
            cfg.load();
            initConfigCategories();
            initConfigValues();
        } catch (Exception e1) {
            CorpseRun.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()){
                cfg.save();
            }
        }
    }

    private static void initConfigCategories() {

        for(ConfigCategories categories : ConfigCategories.values()) {
            cfg.addCustomCategoryComment(categories.name, categories.comment);
        }
    }

    private static void initConfigValues() {

        ConfigEffectsHelper.initValidEffectsList();

        for(ConfigBool bool : ConfigBool.values()) {
            bool.value = cfg.getBoolean(bool.name, bool.category, bool.defaultBool, bool.comment);
        }

        for(ConfigInt num : ConfigInt.values()) {
            num.value = cfg.getInt(num.name, num.category, num.defaultInt, num.min, num.max, num.comment);
        }

        for(ConfigFloat num : ConfigFloat.values()) {
            num.value = cfg.getFloat(num.name, num.category, num.defaultFloat, num.min, num.max, num.comment);
        }

        for(ConfigStringList list : ConfigStringList.values()) {
            list.value = cfg.getStringList(list.name, list.category, list.defaultStringList, list.comment);
        }
    }

    @Mod.EventBusSubscriber
    private static class EventHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(CorpseRun.MODID)) {

                ConfigHandler.initConfigValues();

                if (cfg.hasChanged()){
                    cfg.save();
                }
            }
        }
    }
}
