package c4.corpserun.core;

import c4.corpserun.capability.DeathInventoryProvider;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

abstract class DeathInventoryHandler {

    protected EntityPlayer player;
    protected String modid;
    protected IDeathInventory deathInventory;
    protected NonNullList<ItemStack> storage;

    public DeathInventoryHandler (EntityPlayer player, String modid) {
        this.player = player;
        this.modid = modid;
        deathInventory = player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
    }

    protected void initStorage() {
        storage = deathInventory.assignStorage(modid, getSizeInventory());
    }

    protected NonNullList<ItemStack> getStorage() {
        return storage;
    }

    protected void iterateInventory() {

        boolean storeStack = false;

        for (int index = 0; index < getSizeInventory(); index++) {
            ItemStack itemStack = getStackInSlot(index);

            if (itemStack.isEmpty()) {
                continue;
            }

            if (ConfigHandler.isInventoryModuleEnabled()) {
                storeStack = toStoreStack(index, itemStack);
                if (DeathItemHelper.isCursed(itemStack) && ConfigBool.DESTROY_CURSED.getValue()){
                    removeStackFromSlot(index);
                    continue;
                }
            }

            if (ConfigHandler.isDurabilityModuleEnabled()) {
                DeathItemHelper.loseDurability(player, itemStack, storeStack);
            }

            if (ConfigHandler.isEnergyModuleEnabled()) {
                DeathItemHelper.loseEnergy(itemStack, storeStack);
            }

            if (ConfigHandler.isInventoryModuleEnabled() && storeStack) {
                if (Math.random() >= ConfigFloat.RANDOM_DROP_CHANCE.getValue()) {
                    storeStackFromInventory(index, itemStack);
                }
            }
        }
    }

    abstract int getSizeInventory();
    abstract boolean toStoreStack(int index, ItemStack itemStack);
    abstract ItemStack getStackInSlot(int index);
    abstract void removeStackFromSlot(int index);
    abstract void storeStackFromInventory(int index, ItemStack itemStack);
}
