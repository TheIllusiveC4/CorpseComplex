package c4.corpserun.core.inventory;

import c4.corpserun.capability.DeathInventory;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import java.util.Random;

abstract class DeathInventoryHandler {

    protected EntityPlayer player;
    protected String modid;
    protected IDeathInventory deathInventory;
    protected NonNullList<ItemStack> storage;

    public DeathInventoryHandler (EntityPlayer player, String modid) {
        this.player = player;
        this.modid = modid;
        deathInventory = player.getCapability(DeathInventory.Provider.DEATH_INV_CAP, null);
    }

    public void initStorage() {
        storage = deathInventory.assignStorage(modid, getSizeInventory());
    }

    protected NonNullList<ItemStack> getStorage() {
        return storage;
    }

    public void iterateInventory() {

        boolean storeStack;
        boolean isCursed;
        boolean isEssential;
        Random generator = new Random();

        for (int index = 0; index < getSizeInventory(); index++) {
            ItemStack itemStack = getStackInSlot(index);

            if (itemStack.isEmpty()) {
                continue;
            }

            int itemCount = itemStack.getCount();
            isCursed = DeathItemHelper.isCursed(itemStack);
            isEssential = DeathItemHelper.isEssential(itemStack);
            storeStack = ((toStoreStack(index, itemStack) && !isCursed) || isEssential);

            if (isCursed && InventoryModule.destroyCursed){
                removeStackFromSlot(index);
                continue;
            }

            if (InventoryModule.enableDurability) {
                DeathItemHelper.loseDurability(player, itemStack, storeStack);
            }

            if (InventoryModule.enableEnergy) {
                DeathItemHelper.loseEnergy(itemStack, storeStack);
            }

            if (storeStack) {
                int keptAmount = itemCount;
                if (!isEssential && InventoryModule.randomDrop != 0) {
                    for (int i = 0; i < itemCount; i++) {
                        if (generator.nextDouble() < InventoryModule.randomDrop) {
                            keptAmount--;
                        }
                    }
                }
                storeStackFromInventory(index, itemStack.splitStack(keptAmount));
                if (itemStack.isEmpty()) {
                    continue;
                }
                itemCount = itemStack.getCount();
            }

            if (isEssential || InventoryModule.randomDestroy == 0) { continue;}
            int destroyAmount = 0;
            for (int i = 0; i < itemCount; i++) {
                if (generator.nextDouble() < InventoryModule.randomDestroy) {
                    destroyAmount++;
                }
            }

            itemStack.shrink(destroyAmount);
        }
    }

    abstract int getSizeInventory();
    abstract boolean toStoreStack(int index, ItemStack itemStack);
    abstract ItemStack getStackInSlot(int index);
    abstract void removeStackFromSlot(int index);
    abstract void storeStackFromInventory(int index, ItemStack itemStack);
}
