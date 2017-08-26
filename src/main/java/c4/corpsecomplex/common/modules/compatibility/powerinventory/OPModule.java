package c4.corpsecomplex.common.modules.compatibility.powerinventory;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class OPModule extends Submodule {

    static boolean keepOP;

    public OPModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepOP = getBool("Keep Overpowered Inventory", true, "Set to true to keep Overpowered Inventory on death");
    }
}
