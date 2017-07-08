package c4.corpserun.config.values;

public enum ConfigFloat {

    ARMOR_DURABILITY_LOSS("Armor Durability Loss",ConfigCategories.DURABILITY.name,0.2f,0,1,"Percent of armor durability lost on death"),
    HOTBAR_DURABILITY_LOSS("Hotbar Durability Loss",ConfigCategories.DURABILITY.name,0.2f,0,1,"Percent of durability lost on hotbar items on death"),
    MAINHAND_DURABILITY_LOSS("Mainhand Durability Loss",ConfigCategories.DURABILITY.name,0.2f,0,1, "Percent of mainhand durability lost on death"),
    OFFHAND_DURABILITY_LOSS("Offhand Durability Loss",ConfigCategories.DURABILITY.name,0.2f,0,1, "Percent of offhand durability lost on death"),
    MAIN_INVENTORY_DURABILITY_LOSS("Main Inventory Durability Loss",ConfigCategories.DURABILITY.name,0.2f,0,1, "Percent of durability lost on main inventory items on death"),
    XP_LOSS_PERCENT("Lost XP Percent",ConfigCategories.EXPERIENCE.name, 0.25f, 0, 1, "Percent of experience lost on death"),
    XP_RECOVER_PERCENT("Recoverable XP Percent",ConfigCategories.EXPERIENCE.name, 1.0f, 0, 1, "Percent of lost experience that can be recovered");

    public final String name;
    public final String category;
    public final float defaultFloat;
    public final String comment;
    public final float min;
    public final float max;

    public float value;

    ConfigFloat(String name, String category, float defaultFloat, float min, float max, String comment) {
        this.name = name;
        this.category = category;
        this.defaultFloat = defaultFloat;
        this.min = min;
        this.max = max;
        this.comment = comment;
    }
}
