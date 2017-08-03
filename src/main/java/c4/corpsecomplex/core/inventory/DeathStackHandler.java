package c4.corpsecomplex.core.inventory;

import c4.corpsecomplex.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public abstract class DeathStackHandler extends DeathInventoryHandler {

    protected ItemStackHandler storage;

    public DeathStackHandler (EntityPlayer player, String modid) {
        this(player, modid, 1);
    }

    public DeathStackHandler (EntityPlayer player, String modid, int size) {
        super(player, modid);
        storage = new ItemStackHandler(size);
    }

    public void setSize(int size) {
        storage.setSize(size);
    }

    public void storeInventory() {

        for (int index = 0; index < storage.getSlots(); index++) {

            ItemStack stack = getStackInSlot(index);

            if (!stack.isEmpty()) {
                storeStack(index, DeathStackHelper.stackToStore(player, stack, checkConfig(index)));
            }

            if (!stack.isEmpty() && InventoryModule.randomDestroy > 0) {
                DeathStackHelper.randomlyDestroy(stack);
            }
        }

        deathInventory.addStorage(modid, storage.serializeNBT());
    }

    public void storeStack(int index, ItemStack stack) {

        if (!stack.isEmpty()) {
            storage.insertItem(index, stack, false);
        }
    }

    public abstract boolean checkConfig(int index);
    public abstract ItemStack getStackInSlot(int index);
}
