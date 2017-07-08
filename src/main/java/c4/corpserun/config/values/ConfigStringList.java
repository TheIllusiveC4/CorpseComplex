package c4.corpserun.config.values;

import c4.corpserun.config.ConfigEffectsHelper;

public enum ConfigStringList {

    RESPAWN_EFFECTS("Respawn Effects",ConfigCategories.EFFECTS.name,new String[]{"minecraft:mining_fatigue 60 4","minecraft:weakness 60 4"},
            "List of effects to apply to player on respawn\n"+"Format: [effect] [duration(secs)] [power]\n"+"Valid effects: " + ConfigEffectsHelper.getValidEffectsList()),
    ESSENTIAL_ITEMS("Essential Items",ConfigCategories.INVENTORY.name, new String[]{}, "List of items that are always kept"),
    CURSED_ITEMS("Cursed Items",ConfigCategories.INVENTORY.name, new String[]{}, "List of items that are always dropped");

    public final String name;
    public final String category;
    public final String[] defaultStringList;
    public final String comment;

    public String[] value;

    ConfigStringList(String name, String category, String[] defaultStringList, String comment) {
        this.name = name;
        this.category = category;
        this.defaultStringList = defaultStringList;
        this.comment = comment;
    }
}
