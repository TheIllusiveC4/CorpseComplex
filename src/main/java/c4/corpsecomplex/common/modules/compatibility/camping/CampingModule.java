/*
 * Copyright (c) 2018. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.compatibility.camping;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class CampingModule extends Submodule {

  static boolean keepCamping;

  public CampingModule(Module parentModule) {
    super(parentModule, null);
  }

  public void loadModuleConfig() {
    keepCamping = getBool("Keep Camping", false,
            "Set to true to keep Camping inventory on death", false);
  }
}
