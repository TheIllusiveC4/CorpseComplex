package c4.corpserun.core.inventory;

import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

final class DeathItemHelper {

    private DeathItemHelper() {}

    static void loseDurability (EntityPlayer player, ItemStack itemStack, boolean toKeep) {

        if (!itemStack.isItemStackDamageable()) { return;}

        if (toKeep) {
            itemStack.damageItem((int) Math.round(itemStack.getMaxDamage()* InventoryModule.keptLoss), player);
        } else {
            itemStack.damageItem((int) Math.round(itemStack.getMaxDamage()* InventoryModule.dropLoss), player);
        }
    }

    static void loseEnergy (ItemStack itemStack, boolean toKeep) {

        IEnergyStorage energy = itemStack.getCapability(CapabilityEnergy.ENERGY, null);

        if (energy == null) { return;}

        int energyToLose = 0;

        if (toKeep) {
            energyToLose = (int) Math.round(energy.getMaxEnergyStored() * InventoryModule.keptDrain);
        } else {
            energyToLose = (int) Math.round(energy.getMaxEnergyStored() * InventoryModule.dropDrain);
        }

        while (energyToLose > 0 && energy.getEnergyStored() > 0) {
            energyToLose -= energy.extractEnergy(energyToLose, false);
        }
    }

    static boolean isEssential(ItemStack itemStack) {

        for (String s : InventoryModule.essentialItems) {

            ResourceLocation name = itemStack.getItem().getRegistryName();

            if (name == null) { continue;}

            if (s.equals(name.toString())) {
                return true;
            }
        }
        return false;
    }

    static boolean isCursed(ItemStack itemStack) {

        for (String s : InventoryModule.cursedItems) {

            ResourceLocation name = itemStack.getItem().getRegistryName();

            if (name == null) { continue;}

            if (s.equals(name.toString())) {
                return true;
            }
        }
        return false;
    }

}
