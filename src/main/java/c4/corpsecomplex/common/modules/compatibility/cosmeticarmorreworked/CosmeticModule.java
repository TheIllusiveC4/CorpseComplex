package c4.corpsecomplex.common.modules.compatibility.cosmeticarmorreworked;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class CosmeticModule extends Submodule {

    static boolean keepCosmetic;

    public CosmeticModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepCosmetic = getBool("Keep Cosmetic Armor", false, "Set to true to keep Cosmetic Armor on death");
    }
}
