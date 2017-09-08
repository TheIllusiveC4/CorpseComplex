/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.compatibility.baubles;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class BaublesModule extends Submodule {

    static boolean keepBaubles;

    public BaublesModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepBaubles = getBool("Keep Baubles", false, "Set to true to keep Baubles on death", false);
    }
}
