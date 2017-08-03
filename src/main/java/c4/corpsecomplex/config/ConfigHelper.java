package c4.corpserun.config;

import c4.corpserun.core.modules.ModuleHandler;

public class ConfigHelper {

    public static int getInt(String name, String category, int defaultInt, int min, int max, String comment) {
        return ModuleHandler.cfg.getInt(name, category, defaultInt, min, max, comment);
    }

    public static double getFloat(String name, String category, float defaultFloat, float min, float max, String comment) {
        return ModuleHandler.cfg.getFloat(name, category, defaultFloat, min, max, comment);
    }

    public static boolean getBool(String name, String category, boolean defaultBool, String comment) {
        return ModuleHandler.cfg.getBoolean(name, category, defaultBool, comment);
    }

    public static String[] getStringList(String name, String category, String[] defaultStringList, String comment) {
        return ModuleHandler.cfg.getStringList(name, category, defaultStringList, comment);
    }
}
