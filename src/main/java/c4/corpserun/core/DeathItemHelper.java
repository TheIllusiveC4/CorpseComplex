package c4.corpserun.core;

import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import c4.corpserun.config.values.ConfigStringList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class DeathItemHelper {

    private DeathItemHelper() {}

    public static void loseDurability (EntityPlayer player, ItemStack itemStack, boolean toKeep) {

        if (!itemStack.isItemStackDamageable()) { return;}

        if (toKeep) {
            itemStack.damageItem(Math.round(itemStack.getMaxDamage()*ConfigFloat.KEEP_DURABILITY_LOSS.getValue()), player);
        } else {
            itemStack.damageItem(Math.round(itemStack.getMaxDamage()*ConfigFloat.DROP_DURABILITY_LOSS.getValue()), player);
        }
    }

    public static void loseEnergy (ItemStack itemStack, boolean toKeep) {

        if (!itemStack.hasCapability(CapabilityEnergy.ENERGY, null)) { return;}

        IEnergyStorage energy = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
        int energyToLose = 0;

        if (toKeep) {
            energyToLose = Math.round(energy.getMaxEnergyStored() * ConfigFloat.KEEP_ENERGY_DRAIN.getValue());
        } else {
            energyToLose = Math.round(energy.getMaxEnergyStored() * ConfigFloat.DROP_ENERGY_DRAIN.getValue());
        }

        while (energyToLose > 0 && energy.getEnergyStored() > 0) {
            energyToLose -= energy.extractEnergy(energyToLose, false);
        }
    }

    public static boolean isEssential(ItemStack itemStack) {

        for (String s : ConfigStringList.ESSENTIAL_ITEMS.getValue()) {
            if (s.equals(itemStack.getItem().getRegistryName().toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCursed(ItemStack itemStack) {

        for (String s : ConfigStringList.CURSED_ITEMS.getValue()) {
            if (s.equals(itemStack.getItem().getRegistryName().toString())) {
                return true;
            }
        }
        return false;
    }

}
