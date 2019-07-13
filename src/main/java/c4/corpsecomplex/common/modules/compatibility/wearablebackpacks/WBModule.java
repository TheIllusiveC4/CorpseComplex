/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.compatibility.wearablebackpacks;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class WBModule extends Submodule {

  static boolean keepBackpack;

  public WBModule(Module parentModule) {
    super(parentModule, null);
  }

  public void loadModuleConfig() {
    keepBackpack = getBool("Keep Wearable Backpack", false,
            "Set to true to keep Wearable Backpack on death", false);
  }
}
