/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.compatibility.advinv;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class AdvModule extends Submodule {

    static boolean keepAdv;

    public AdvModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepAdv = getBool("Keep Advanced Inventory", false, "Set to true to keep Advanced Inventory on death", false);
    }
}
