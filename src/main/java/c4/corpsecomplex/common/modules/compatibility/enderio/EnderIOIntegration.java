package c4.corpsecomplex.common.modules.compatibility.enderio;

import crazypants.enderio.base.item.darksteel.upgrade.energy.EnergyUpgradeManager;
import net.minecraft.item.ItemStack;

public class EnderIOIntegration {

  public static int extractEnergy(ItemStack stack, int extract, boolean simulate) {
    return EnergyUpgradeManager.extractEnergy(stack, extract, simulate);
  }

  public static boolean hasPowerUpgrade(ItemStack stack) {
    return EnergyUpgradeManager.itemHasAnyPowerUpgrade(stack);
  }
}
