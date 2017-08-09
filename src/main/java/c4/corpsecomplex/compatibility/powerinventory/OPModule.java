package c4.corpsecomplex.compatibility.powerinventory;

import c4.corpsecomplex.core.modules.Module;
import c4.corpsecomplex.core.modules.Submodule;

public class OPModule extends Submodule {

    public static boolean keepOP;

    public OPModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepOP = getBool("Keep Overpowered Inventory", true, "Set to true to keep Overpowered Inventory on death");
    }
}
