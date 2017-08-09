package c4.corpsecomplex.compatibility.wearablebackpacks;

import c4.corpsecomplex.core.modules.Module;
import c4.corpsecomplex.core.modules.Submodule;

public class WBModule extends Submodule {

    public static boolean keepBackpack;

    public WBModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepBackpack = getBool("Keep Wearable Backpack", false, "Set to true to keep Wearable Backpack on death");
    }
}
