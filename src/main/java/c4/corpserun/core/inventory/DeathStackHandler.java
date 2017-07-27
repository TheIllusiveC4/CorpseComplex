package c4.corpserun.core.inventory;

import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Random;

abstract class DeathStackHandler extends DeathInventoryHandler {

    protected ItemStackHandler storage;

    public DeathStackHandler (EntityPlayer player, String modid) {
        this(player, modid, 1);
    }

    public DeathStackHandler (EntityPlayer player, String modid, int size) {
        super(player, modid);
        storage = new ItemStackHandler(size);
    }

    public void store() {

        boolean storeStack;
        boolean isCursed;
        boolean isEssential;
        Random generator = new Random();

        for (int index = 0; index < storage.getSlots(); index++) {
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
                System.out.println(index + ":" + storage.getStackInSlot(index));
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

        deathInventory.addStorage(modid, storage.serializeNBT());
    }

    abstract boolean toStoreStack(int index, ItemStack itemStack);
    abstract ItemStack getStackInSlot(int index);
    abstract void removeStackFromSlot(int index);
    abstract void storeStackFromInventory(int index, ItemStack itemStack);

}
