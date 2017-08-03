package c4.corpsecomplex.compatibility.wearablebackpacks;

import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.inventory.DeathInventoryHandler;
import c4.corpsecomplex.core.inventory.DeathStackHelper;
import c4.corpsecomplex.core.modules.InventoryModule;
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
    private IBackpack playerBackpack;

    public WBHandler (EntityPlayer player) {
        super(player, MOD_ID);
        cfgStore = WBModule.keepBackpack;
        playerBackpack = player.getCapability(BackpackCapability.CAPABILITY, null);
    }

    public void storeInventory() {

        if (playerBackpack == null) { return; }

        ItemStack stack;

        if (BackpackHelper.equipAsChestArmor) {
            stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        } else {
            stack = playerBackpack.getStack();
        }

        if (stack.isEmpty()) { return; }

        Random generator = new Random();
        boolean cursed = DeathStackHelper.isCursed(stack);
        boolean essential = !cursed && DeathStackHelper.isEssential(stack);
        boolean store = ((cfgStore && !cursed) || essential);

        if (cursed && InventoryModule.destroyCursed) {
            BackpackHelper.setEquippedBackpack(player, ItemStack.EMPTY, null);
            return;
        }

        if ((InventoryModule.dropLoss > 0 || InventoryModule.keptLoss > 0) && !(BackpackHelper.equipAsChestArmor && !store)) {
            DeathStackHelper.loseDurability(player, stack, store);
        }

        if (stack.isEmpty()) { return; }

        if (store) {
            if (!essential && generator.nextDouble() < InventoryModule.randomDrop) {
                if (generator.nextDouble() < InventoryModule.randomDestroy) {
                    BackpackHelper.setEquippedBackpack(player, ItemStack.EMPTY, null);
                    return;
                }
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
    }

    public static void retrieveInventory(EntityPlayer player, IDeathInventory oldInventory) {

        IBackpack playerBackpack = player.getCapability(BackpackCapability.CAPABILITY, null);
        NBTTagCompound storage = oldInventory.getStorage(MOD_ID);
        if (storage == null || playerBackpack == null) { return; }
        BackpackCapability.CAPABILITY.readNBT(playerBackpack, null, storage);
    }
}
