package c4.corpserun.config.values.compatibility;

import c4.corpserun.config.ConfigHandler;
import c4.corpserun.core.compatibility.TANModule;
import net.minecraftforge.common.config.ConfigCategory;

public enum ConfigCompatInt {

    MIN_THIRST("Minimum Thirst Level", ConfigCategory.getQualifiedName(ConfigCompatCategories.TAN.getName(), ConfigHandler.cfg.getCategory("compatibility")), 6, 0, 20, "Lowest amount of thirst you can respawn with", TANModule.isLoaded()),
    MAX_THIRST("Maximum Thirst Level", ConfigCategory.getQualifiedName(ConfigCompatCategories.TAN.getName(), ConfigHandler.cfg.getCategory("compatibility")), 20, MIN_THIRST.getValue(), 20, "Maximum amount of thirst you can respawn with", TANModule.isLoaded());

    public final String name;
    public final String category;
    public final int defaultInt;
    public final int min;
    public final int max;
    public final String comment;
    public final boolean isActive;

    public int value;

    ConfigCompatInt(String name, String category, int defaultInt, int min, int max, String comment, boolean isActive) {
        this.name = name;
        this.category = category;
        this.defaultInt = defaultInt;
        this.min = min;
        this.max = max;
        this.comment = comment;
        this.isActive = isActive;
    }

    public int getValue() {
        return value;
    }

}



