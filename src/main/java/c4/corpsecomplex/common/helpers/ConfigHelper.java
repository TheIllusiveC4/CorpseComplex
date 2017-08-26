package c4.corpsecomplex.common.helpers;

public class ConfigHelper {

    public static int getInt(String name, String category, int defaultInt, int min, int max, String comment) {
        return ModuleHelper.cfg.getInt(name, category, defaultInt, min, max, comment);
    }

    public static float getFloat(String name, String category, float defaultFloat, float min, float max, String comment) {
        return ModuleHelper.cfg.getFloat(name, category, defaultFloat, min, max, comment);
    }

    public static boolean getBool(String name, String category, boolean defaultBool, String comment) {
        return ModuleHelper.cfg.getBoolean(name, category, defaultBool, comment);
    }

    public static String getString(String name, String category, String defaultString, String comment, String[] validValues) {
        return ModuleHelper.cfg.getString(name, category, defaultString, comment, validValues);
    }

    public static String[] getStringList(String name, String category, String[] defaultStringList, String comment) {
        return ModuleHelper.cfg.getStringList(name, category, defaultStringList, comment);
    }
}
