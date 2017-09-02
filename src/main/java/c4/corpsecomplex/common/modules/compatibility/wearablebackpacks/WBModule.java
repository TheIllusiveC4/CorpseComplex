package c4.corpsecomplex.common.modules.compatibility.wearablebackpacks;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class WBModule extends Submodule {

    static boolean keepBackpack;

    public WBModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepBackpack = getBool("Keep Wearable Backpack", false, "Set to true to keep Wearable Backpack on death", false);
    }
}
