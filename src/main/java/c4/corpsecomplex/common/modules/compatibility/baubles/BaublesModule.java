/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
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
