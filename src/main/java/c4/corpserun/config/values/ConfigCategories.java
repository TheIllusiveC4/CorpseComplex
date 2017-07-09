package c4.corpserun.config.values;

public enum ConfigCategories {

    EXPERIENCE ("experience", "Experience Management"),
    INVENTORY ("inventory", "Inventory Management"),
    DURABILITY ("durability", "Durability Loss"),
    EFFECTS ("effects", "Effects on Respawn"),
    ENERGY ("energy", "Energy Drain"),
    HUNGER ("hunger", "Hunger Management");

    public final String name;
    public final String comment;

    ConfigCategories(String name, String comment){
        this.name = name;
        this.comment = comment;
    }

}
