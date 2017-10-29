/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.wearablebackpacks;

import c4.corpsecomplex.common.modules.inventory.helpers.DeathInventoryHandler;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHelper;
import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentModule;
import net.mcft.copy.backpacks.api.BackpackHelper;
import net.mcft.copy.backpacks.api.IBackpack;
import net.mcft.copy.backpacks.misc.BackpackCapability;
import net.mcft.copy.backpacks.misc.util.NbtUtils;
import net.minecraft.enchantment.EnchantmentHelper;
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
        playerBackpack = player.getCapability(BackpackCapability.CAPABILITY, null);
    }

    public boolean checkToStore(int slot) { return WBModule.keepBackpack; }

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
        boolean essential = DeathStackHelper.isEssential(stack);
        boolean cursed = !essential && DeathStackHelper.isCursed(stack);
        boolean store = ((checkToStore(0) && !cursed) || essential);

        if (!store && EnchantmentModule.registerEnchant) {
            int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentModule.soulbound, stack);
            if (level != 0) {
                store = essential = DeathStackHelper.handleSoulbound(stack, level);
                cursed = !essential && cursed;
            }
        }

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

    public void retrieveInventory(IDeathInventory oldDeathInventory) {

        NBTTagCompound storage = oldDeathInventory.getStorage(MOD_ID);
        if (storage == null || playerBackpack == null) { return; }
        BackpackCapability.CAPABILITY.readNBT(playerBackpack, null, storage);
    }
}
