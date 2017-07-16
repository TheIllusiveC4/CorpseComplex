package c4.corpserun.config.values;

public enum ConfigFloat {

    DROP_DURABILITY_LOSS("Durability Loss on Drops",ConfigCategories.DURABILITY.name, 0.0f,0,1,"Percent of durability lost on death for drops"),
    KEEP_DURABILITY_LOSS("Durability Loss on Kept Items",ConfigCategories.DURABILITY.name, 0.0f,0,1,"Percent of durability lost on death for kept items"),
    XP_LOSS_PERCENT("Lost XP Percent",ConfigCategories.EXPERIENCE.name, 1.0f, 0, 1, "Percent of experience lost on death"),
    XP_RECOVER_PERCENT("Recoverable XP Percent",ConfigCategories.EXPERIENCE.name, 0.2f, 0, 1, "Percent of lost experience that can be recovered"),
    DROP_ENERGY_DRAIN("Energy Drain on Drops",ConfigCategories.ENERGY.name, 0.0f,0,1,"Percent of energy drained on death for drops"),
    KEEP_ENERGY_DRAIN("Energy Drain on Kept Items",ConfigCategories.ENERGY.name, 0.0f,0,1, "Percent of energy drained on death for kept items"),
    RANDOM_DROP_CHANCE("Random Drop Chance",ConfigCategories.INVENTORY.getName(), 0.0f,0,1, "Percent chance that items that are kept will still drop");

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

    public float getValue() {
        return value;
    }

}
