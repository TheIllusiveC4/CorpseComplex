package c4.corpserun.core.inventory;

import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.InventoryModule;
import c4.corpserun.core.modules.compatibility.WBModule;
import net.mcft.copy.backpacks.api.BackpackHelper;
import net.mcft.copy.backpacks.api.IBackpack;
import net.mcft.copy.backpacks.misc.BackpackCapability;
import net.mcft.copy.backpacks.misc.util.NbtUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class WBHandler extends DeathInventoryHandler {

    private static final String MOD_ID = "wearablebackpacks";

    public WBHandler (EntityPlayer player) {
        super(player, MOD_ID);
    }

    public boolean storeStack(ItemStack stack) {
        return (DeathItemHelper.isEssential(stack) || !DeathItemHelper.isCursed(stack) && WBModule.keepBackpack);
    }

    public void store() {
        IBackpack playerBackpack = player.getCapability(BackpackCapability.CAPABILITY, null);

        if (playerBackpack == null) { return; }

        ItemStack stack;

        if (BackpackHelper.equipAsChestArmor) {
            stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        } else {
            stack = playerBackpack.getStack();
        }

        Random generator = new Random();

        if (!storeStack(stack) || generator.nextDouble() < InventoryModule.randomDrop) { return; }

        if (generator.nextDouble() < InventoryModule.randomDestroy) {
            BackpackHelper.setEquippedBackpack(player, ItemStack.EMPTY, null);
            return;
        }

        NBTTagCompound storage = (NBTTagCompound) BackpackCapability.CAPABILITY.writeNBT(playerBackpack, null);

        if (storage != null && BackpackHelper.equipAsChestArmor) {
            NBTTagCompound compound = NbtUtils.writeItem(stack);
            storage.setTag("stack", compound);
        }
        deathInventory.addStorage(modid, storage);
        BackpackHelper.setEquippedBackpack(player, ItemStack.EMPTY, null);
    }

    public static void retrieve(EntityPlayer player, IDeathInventory deathInventory) {

        NBTTagCompound storage = deathInventory.getStorage(MOD_ID);
        IBackpack playerBackpack = player.getCapability(BackpackCapability.CAPABILITY, null);
        if (storage == null || playerBackpack == null) { return; }
        BackpackCapability.CAPABILITY.readNBT(playerBackpack, null, storage);
    }
}
