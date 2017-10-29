/*
 * Copyright (c) 2017. C4, MIT License
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
