package c4.corpsecomplex.compatibility.cosmeticarmorreworked;

import c4.corpsecomplex.core.modules.Module;
import c4.corpsecomplex.core.modules.Submodule;

public class CosmeticModule extends Submodule {

    public static boolean keepCosmetic;

    public CosmeticModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepCosmetic = getBool("Keep Cosmetic Armor", false, "Set to true to keep Cosmetic Armor on death");
    }
}
