package c4.corpserun.core;

import c4.corpserun.config.values.ConfigStringList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public final class DeathItemHandler {

    private DeathItemHandler() {}

    public static void loseDurability (EntityPlayer player, ItemStack itemStack, float durabilityLoss) {
        if (itemStack.isItemStackDamageable()){
            itemStack.damageItem(Math.round(itemStack.getMaxDamage() * durabilityLoss), player);
        }
    }

    public static void loseEnergy (ItemStack itemStack, float energyLoss) {
        if (itemStack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage energy = itemStack.getCapability(CapabilityEnergy.ENERGY, null);
            int energyToLose = Math.round(energy.getMaxEnergyStored() * energyLoss);
            while (energyToLose > 0 && energy.getEnergyStored() > 0) {
                energyToLose -= energy.extractEnergy(energyToLose, false);
            }
        }
    }

    public static boolean keepItem (ItemStack itemStack, boolean checkConfig) {

        if (isEssential(itemStack)) {   return true;}

        if (isCursed(itemStack))    {   return false;}

        return checkConfig;
    }

    private static boolean isEssential(ItemStack itemStack) {

        for (String s : ConfigStringList.ESSENTIAL_ITEMS.getValue()) {
            if (s.equals(itemStack.getItem().getRegistryName().toString())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCursed(ItemStack itemStack) {

        for (String s : ConfigStringList.CURSED_ITEMS.getValue()) {
            if (s.equals(itemStack.getItem().getRegistryName().toString())) {
                return true;
            }
        }
        return false;
    }
}
