package c4.corpserun.core.modules.compatibility;

import c4.corpserun.core.modules.Submodule;

public class WBModule extends Submodule {

    public static boolean keepBackpack;

    public WBModule() {
        configParent = "Inventory";
        prevEnabled = false;
    }

    public void loadModuleConfig() {
        keepBackpack = getBool("Keep Wearable Backpack", false, "Set to true to keep Wearable Backpack on death");
    }

    public void initPropOrder() {
        propOrder = null;
    }

    public void setEnabled() {

    }

    @Override
    public boolean hasEvents() {
        return false;
    }

}
