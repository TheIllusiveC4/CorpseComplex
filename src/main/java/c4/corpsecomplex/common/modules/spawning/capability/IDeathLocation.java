/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.spawning.capability;

import net.minecraft.util.math.BlockPos;

public interface IDeathLocation {

  BlockPos getDeathLocation();

  void setDeathLocation(BlockPos pos);

  boolean hasDeathLocation();

  void setHasDeathLocation(boolean hasDeathLocation);

  int getDeathDimension();

  void setDeathDimension(int dimension);

  boolean hasUsedScroll();

  void setUsedScroll(boolean usedScroll);
}
