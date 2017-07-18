package c4.corpserun.core.inventory;

import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import c4.corpserun.config.values.ConfigStringList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

final class DeathItemHelper {

    private DeathItemHelper() {}

    static void loseDurability (EntityPlayer player, ItemStack itemStack, boolean toKeep) {

        if (!itemStack.isItemStackDamageable()) { return;}

        if (toKeep) {
            itemStack.damageItem(Math.round(itemStack.getMaxDamage()*ConfigFloat.KEEP_DURABILITY_LOSS.getValue()), player);
        } else {
            itemStack.damageItem(Math.round(itemStack.getMaxDamage()*ConfigFloat.DROP_DURABILITY_LOSS.getValue()), player);
        }
    }

    static void loseEnergy (ItemStack itemStack, boolean toKeep) {

        IEnergyStorage energy = itemStack.getCapability(CapabilityEnergy.ENERGY, null);

        if (energy == null) { return;}

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

    static boolean isEssential(ItemStack itemStack) {

        for (String s : ConfigStringList.ESSENTIAL_ITEMS.getValue()) {

            ResourceLocation name = itemStack.getItem().getRegistryName();

            if (name == null) { continue;}

            if (s.equals(name.toString())) {
                return true;
            }
        }
        return false;
    }

    static boolean isCursed(ItemStack itemStack) {

        for (String s : ConfigStringList.CURSED_ITEMS.getValue()) {

            ResourceLocation name = itemStack.getItem().getRegistryName();

            if (name == null) { continue;}

            if (s.equals(name.toString())) {
                return true;
            }
        }
        return false;
    }

}
