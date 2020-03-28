package top.theillusivec4.corpsecomplex.common.util;

import java.util.Random;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class InventoryHelper {

  public static final Random RAND = new Random();

  public static void applyDurabilityLoss(PlayerEntity playerEntity, ItemStack stack,
      double durabilityLoss, boolean limit) {

    if (!stack.isDamageable()) {
      return;
    }
    LazyOptional<IEnergyStorage> energyStorage = stack.getCapability(CapabilityEnergy.ENERGY);

    energyStorage.ifPresent(energy -> {
      int energyLoss = (int) Math.round(energy.getMaxEnergyStored() * durabilityLoss);
      if (energy.canExtract()) {
        energy.extractEnergy(energyLoss, false);
      }
    });
    int maxLoss = limit ? stack.getMaxDamage() - stack.getDamage() - 1 : stack.getMaxDamage();
    int loss = (int) Math.round(stack.getMaxDamage() * durabilityLoss);
    stack.damageItem(Math.min(maxLoss, loss), playerEntity, damager -> {
    });
  }

  public static int getRandomAmount(int num, double chance) {
    int amount = 0;

    for (int i = 0; i < num; i++) {
      if (RAND.nextDouble() < chance) {
        amount++;
      }
    }
    return amount;
  }
}
