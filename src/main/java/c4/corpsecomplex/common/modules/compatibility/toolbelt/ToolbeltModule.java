/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.compatibility.toolbelt;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class ToolbeltModule extends Submodule {

  static boolean keepToolbelt;

  public ToolbeltModule(Module parentModule) {
    super(parentModule, null);
  }

  public void loadModuleConfig() {
    keepToolbelt = getBool("Keep Toolbelt", false,
            "Set to true to keep Toolbelt on death", false);
  }
}
