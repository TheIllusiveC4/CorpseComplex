package c4.corpserun.config.values.compatibility;

import c4.corpserun.core.compatibility.TANModule;

public enum ConfigCompatCategories {

    TAN ("tough as nails", "Tough as Nails Management", TANModule.isLoaded());

    public final String name;
    public final String comment;
    private boolean isLoaded;

    ConfigCompatCategories(String name, String comment, boolean isLoaded){
        this.name = name;
        this.comment = comment;
        this.isLoaded = isLoaded;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public boolean isLoaded() { return isLoaded;}

}
