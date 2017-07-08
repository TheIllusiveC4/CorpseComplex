package c4.corpserun.config.values;

public enum ConfigBool {

    DESTROY_DROPPED_ITEMS("Destroy Dropped Items",ConfigCategories.INVENTORY.name, false, "Set to true to destroy all dropped items on death"),
    KEEP_ARMOR("Keep Armor",ConfigCategories.INVENTORY.name, true, "Set to true to keep equipped armor on death"),
    KEEP_HOTBAR("Keep Hotbar",ConfigCategories.INVENTORY.name, true, "Set to true to keep items on the hotbar on death"),
    KEEP_MAINHAND("Keep Mainhand ",ConfigCategories.INVENTORY.name, true, "Set to true to keep the mainhand item on death (only enabled if hotbar is not kept)"),
    KEEP_OFFHAND("Keep Offhand",ConfigCategories.INVENTORY.name, true, "Set to true to keep the offhand item on death"),
    KEEP_MAIN_INVENTORY("Keep Main Inventory",ConfigCategories.INVENTORY.name, false, "Set to true to keep main inventory (non-equipped non-hotbar) items on death"),
    DESTROY_CURSED("Destroy Cursed Items",ConfigCategories.INVENTORY.name, false, "Set to true to destroy cursed items instead of dropping them"),
    ENABLE_DURABILITY_LOSS("Enable Durability Loss on Death",ConfigCategories.DURABILITY.name, true,"Set to true to enable durability loss on death"),
    KEEP_XP("Retain All XP",ConfigCategories.EXPERIENCE.name, false, "Set to true to keep all XP on death"),
    ENABLE_CURE("Enable Curing Items",ConfigCategories.EFFECTS.name,true,"Set to true to enable curing buffs/debuffs (via milk buckets or other implementations)");

    public final String name;
    public final String category;
    public final boolean defaultBool;
    public final String comment;

    public boolean value;

    ConfigBool(String name, String category, boolean defaultBool, String comment) {
        this.name = name;
        this.category = category;
        this.defaultBool = defaultBool;
        this.comment = comment;
    }
}
