package c4.corpserun.config.values;

public enum ConfigInt {

    MIN_FOOD("Minimum Food Level", ConfigCategories.HUNGER.getName(), 6, 0, 20, "Lowest amount of food level you can respawn with"),
    MAX_FOOD("Maximum Food Level", ConfigCategories.HUNGER.getName(), 20, MIN_FOOD.getValue(), 20, "Maximum amount of food level you can respawn with");

    public final String name;
    public final String category;
    public final int defaultInt;
    public final int min;
    public final int max;
    public final String comment;

    public int value;

    ConfigInt(String name, String category, int defaultInt, int min, int max, String comment) {
        this.name = name;
        this.category = category;
        this.defaultInt = defaultInt;
        this.min = min;
        this.max = max;
        this.comment = comment;
    }

    public int getValue() {
        return value;
    }

}
