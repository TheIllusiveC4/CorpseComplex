package c4.corpserun.config.values;

import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.compatibility.ConfigCompatCategories;
import net.minecraftforge.common.config.ConfigCategory;

public enum ConfigBool {

    DESTROY_DROPPED_ITEMS("Destroy Dropped Items",ConfigCategories.INVENTORY.name, false, "Set to true to destroy all dropped items on death"),
    KEEP_ARMOR("Keep Armor",ConfigCategories.INVENTORY.name, false, "Set to true to keep equipped armor on death"),
    KEEP_HOTBAR("Keep Hotbar",ConfigCategories.INVENTORY.name, false, "Set to true to keep items on the hotbar on death"),
    KEEP_MAINHAND("Keep Mainhand ",ConfigCategories.INVENTORY.name, false, "Set to true to keep the mainhand item on death"),
    KEEP_OFFHAND("Keep Offhand",ConfigCategories.INVENTORY.name, false, "Set to true to keep the offhand item on death"),
    KEEP_MAIN_INVENTORY("Keep Main Inventory",ConfigCategories.INVENTORY.name, false, "Set to true to keep main inventory (non-equipped non-hotbar) items on death"),
    DESTROY_CURSED("Destroy Cursed Items",ConfigCategories.INVENTORY.name, false, "Set to true to destroy cursed items instead of dropping them"),
    KEEP_XP("Keep All XP",ConfigCategories.EXPERIENCE.name, false, "Set to true to keep all XP on death"),
    ENABLE_CURE("Enable Curing Items",ConfigCategories.EFFECTS.name, true,"Set to true to enable curing buffs/debuffs (via milk buckets or other implementations)"),
    KEEP_FOOD("Keep Food Level",ConfigCategories.HUNGER.name, false, "Set to true to retain food level on death"),

    ENABLE_ENERGY_DRAIN(".Enable Energy Drain Module",ConfigCategories.ENERGY.getName(), false, "Set to true to enable energy drain on death"),
    ENABLE_DURABILITY_LOSS(".Enable Durability Loss Module",ConfigCategories.DURABILITY.getName(), false, "Set to true to enable durability loss on death"),
    ENABLE_XP(".Enable Experience Module",ConfigCategories.EXPERIENCE.getName(), false, "Set to true to enable experience modifications on death"),
    ENABLE_HUNGER(".Enable Hunger Module",ConfigCategories.HUNGER.getName(), false, "Set to true to enable hunger modifications on death"),
    ENABLE_INVENTORY(".Enable Inventory Module",ConfigCategories.INVENTORY.getName(), false, "Set to true to enable inventory modifications on death"),
    ENABLE_EFFECTS(".Enable Effects Module", ConfigCategories.EFFECTS.getName(), false, "Set to true to enable potion effects on respawn");

    public final String name;
    public final String category;
    public final boolean defaultBool;
    public final String comment;
    public final boolean compat;

    public boolean value;

    ConfigBool(String name, String category, boolean defaultBool, String comment) {
        this.name = name;
        this.category = category;
        this.defaultBool = defaultBool;
        this.comment = comment;
        this.compat = false;
    }

    public boolean getValue() {
        return value;
    }

}
