/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.powerinventory;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class OPModule extends Submodule {

    static boolean keepOP;

    public OPModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepOP = getBool("Keep Overpowered Inventory", true, "Set to true to keep Overpowered Inventory on death", false);
    }
}
