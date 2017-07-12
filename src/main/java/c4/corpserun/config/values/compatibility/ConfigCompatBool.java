package c4.corpserun.config.values.compatibility;

import c4.corpserun.config.ConfigHandler;
import c4.corpserun.core.compatibility.CompatTAN;
import net.minecraftforge.common.config.ConfigCategory;

public enum ConfigCompatBool {

    ENABLE_TAN(".Enable TAN Module", ConfigCategory.getQualifiedName(ConfigCompatCategories.TAN.getName(), ConfigHandler.cfg.getCategory("compatibility")), false, "Set to true to enable Tough as Nails configuration on death", CompatTAN.isLoaded()),
    KEEP_THIRST("Keep Thirst Level", ConfigCategory.getQualifiedName(ConfigCompatCategories.TAN.getName(), ConfigHandler.cfg.getCategory("compatibility")), false, "Set to true to retain thirst level on respawn", CompatTAN.isLoaded()),
    KEEP_HYDRATION("Keep Hydration Level", ConfigCategory.getQualifiedName(ConfigCompatCategories.TAN.getName(), ConfigHandler.cfg.getCategory("compatibility")), false, "Set to true to retain hydration level on respawn", CompatTAN.isLoaded()),
    KEEP_TEMPERATURE("Keep Temperature Level", ConfigCategory.getQualifiedName(ConfigCompatCategories.TAN.getName(), ConfigHandler.cfg.getCategory("compatibility")), false, "Set to true to retain temperature level on respawn", CompatTAN.isLoaded());

    public final String name;
    public final String category;
    public final boolean defaultBool;
    public final String comment;
    public final boolean isActive;

    public boolean value;

    ConfigCompatBool(String name, String category, boolean defaultBool, String comment, boolean isActive) {
        this.name = name;
        this.category = category;
        this.defaultBool = defaultBool;
        this.comment = comment;
        this.isActive = isActive;
    }

    public boolean getValue() {
        return value;
    }

}
