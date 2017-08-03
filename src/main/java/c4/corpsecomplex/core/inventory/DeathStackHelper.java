package c4.corpsecomplex.core.inventory;

import c4.corpsecomplex.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Random;

public class DeathStackHandler {

    public boolean isCursed;
    public boolean isEssential;
    public boolean store;
    public ItemStack stack;
    private static Random generator = new Random();

    public DeathStackHandler(ItemStack stack) {
        this.stack = stack;
        isCursed = isCursed();
        isEssential = !isCursed && isEssential();
        store = false;
    }

    public static ItemStack stackToStore(EntityPlayer player, ItemStack stack, boolean cfgStore) {

        boolean cursed = isCursed(stack);
        boolean essential = !cursed && isEssential(stack);
        boolean store = ((cfgStore && !cursed) || essential);

        if (cursed) {
            destroyCursed(stack);
            return ItemStack.EMPTY;
        }

        loseDurability(player, stack, store);
        loseEnergy(stack, store);

        int keepAmount = stack.getCount();
        keepAmount -= randomlyDrop(stack);

        return stack.splitStack(keepAmount);
    }

    public static boolean destroyCursed(ItemStack stack) {

        if (!InventoryModule.destroyCursed){ return false; }

        stack.setCount(0);
        return true;
    }

    public static int randomlyDrop(ItemStack stack) {

        if (InventoryModule.randomDrop == 0) { return 0; }

        int dropAmount = 0;
        for (int i = 0; i < stack.getCount(); i++) {
            if (generator.nextDouble() < InventoryModule.randomDrop) {
                dropAmount++;
            }
        }

        return dropAmount;
    }

    public static void randomlyDestroy(ItemStack stack) {

        if (InventoryModule.randomDestroy == 0) { return; }

        int destroyAmount = 0;
        for (int i = 0; i < stack.getCount(); i++) {
            if (generator.nextDouble() < InventoryModule.randomDestroy) {
                destroyAmount++;
            }
        }

        stack.shrink(destroyAmount);
    }

    public static void loseDurability (EntityPlayer player, ItemStack stack, boolean store) {

        if (!InventoryModule.enableDurability || !stack.isItemStackDamageable()) { return;}

        if (store) {
            stack.damageItem((int) Math.round(stack.getMaxDamage()* InventoryModule.keptLoss), player);
        } else {
            stack.damageItem((int) Math.round(stack.getMaxDamage()* InventoryModule.dropLoss), player);
        }
    }

    public static void loseEnergy (ItemStack stack, boolean store) {

        IEnergyStorage energy = stack.getCapability(CapabilityEnergy.ENERGY, null);

        if (energy == null || !InventoryModule.enableEnergy) { return;}

        int energyToLose;

        if (store) {
            energyToLose = (int) Math.round(energy.getMaxEnergyStored() * InventoryModule.keptDrain);
        } else {
            energyToLose = (int) Math.round(energy.getMaxEnergyStored() * InventoryModule.dropDrain);
        }

        while (energyToLose > 0 && energy.getEnergyStored() > 0) {
            energyToLose -= energy.extractEnergy(energyToLose, false);
        }
    }

    private static boolean isEssential(ItemStack stack) {

        for (String s : InventoryModule.essentialItems) {

            ResourceLocation name = stack.getItem().getRegistryName();

            if (name == null) { continue;}

            if (s.equals(name.toString())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCursed(ItemStack stack) {

        for (String s : InventoryModule.cursedItems) {

            ResourceLocation name = stack.getItem().getRegistryName();

            if (name == null) { continue;}

            if (s.equals(name.toString())) {
                return true;
            }
        }
        return false;
    }
}
