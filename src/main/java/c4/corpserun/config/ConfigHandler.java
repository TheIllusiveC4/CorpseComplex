package c4.corpserun.config;

import c4.corpserun.CorpseRun;
import c4.corpserun.config.values.*;
import c4.corpserun.config.values.compatibility.ConfigCompatBool;
import c4.corpserun.config.values.compatibility.ConfigCompatCategories;
import c4.corpserun.config.values.compatibility.ConfigCompatInt;
import c4.corpserun.proxy.CommonProxy;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

public class ConfigHandler {

    public static Configuration cfg = CommonProxy.config;
    public static boolean compat = false;

    public static void readConfig() {

        try {
            cfg.load();
            initConfigCategories();
            initConfigValues();
            if (compat) { initCompatValues();}
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

        for (ConfigCompatCategories categories : ConfigCompatCategories.values()) {
            if (categories.isLoaded()) {
                setCompatActive();
                break;
            }
        }

        if (compat) {
            cfg.addCustomCategoryComment("compatibility", "Compatibility Management");
            for (ConfigCompatCategories categories : ConfigCompatCategories.values()) {
                if (categories.isLoaded()) {
                    cfg.addCustomCategoryComment(ConfigCategory.getQualifiedName(categories.getName(), cfg.getCategory("compatibility")), categories.getComment());
                }
            }
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

    private static void initCompatValues() {
        for(ConfigCompatBool bool : ConfigCompatBool.values()) {
            if (bool.isActive) { bool.value = cfg.getBoolean(bool.name, bool.category, bool.defaultBool, bool.comment);}
        }

        for(ConfigCompatInt num : ConfigCompatInt.values()) {
            if (num.isActive) { num.value = cfg.getInt(num.name, num.category, num.defaultInt, num.min, num.max, num.comment);}
        }
    }

    private static void setCompatActive() {
        compat = true;
    }

    public static boolean isInventoryModuleEnabled() {
        return ConfigBool.ENABLE_INVENTORY.getValue();
    }

    public static boolean isDurabilityModuleEnabled() {
        return ConfigBool.ENABLE_DURABILITY_LOSS.getValue();
    }

    public static boolean isEnergyModuleEnabled() {
        return ConfigBool.ENABLE_ENERGY_DRAIN.getValue();
    }

    public static boolean isExperienceModuleEnabled() { return ConfigBool.ENABLE_XP.getValue();}

    public static boolean isHungerModuleEnabled() { return ConfigBool.ENABLE_HUNGER.getValue();}

    public static boolean isEffectsModuleEnabled() { return ConfigBool.ENABLE_EFFECTS.getValue();}

    @Mod.EventBusSubscriber
    private static class EventHandler {

        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(CorpseRun.MODID)) {

                initConfigValues();
                if (compat) { initCompatValues();}

                if (cfg.hasChanged()){
                    cfg.save();
                }
            }
        }
    }
}
