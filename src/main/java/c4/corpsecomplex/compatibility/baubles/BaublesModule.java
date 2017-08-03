package c4.corpsecomplex.compatibility.baubles;

import c4.corpsecomplex.core.modules.Module;
import c4.corpsecomplex.core.modules.Submodule;

public class BaublesModule extends Submodule {

    public static boolean keepBaubles;

    public BaublesModule(Module parentModule) {
        super(parentModule);
    }

    public void loadModuleConfig() {
        keepBaubles = getBool("Keep Baubles", false, "Set to true to keep Baubles on death");
    }
}
