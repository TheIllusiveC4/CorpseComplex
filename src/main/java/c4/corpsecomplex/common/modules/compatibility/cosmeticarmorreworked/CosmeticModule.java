/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.cosmeticarmorreworked;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class CosmeticModule extends Submodule {

    static boolean keepCosmetic;

    public CosmeticModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepCosmetic = getBool("Keep Cosmetic Armor", false, "Set to true to keep Cosmetic Armor on death", false);
    }
}
