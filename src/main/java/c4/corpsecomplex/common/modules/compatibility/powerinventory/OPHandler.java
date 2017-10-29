/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.powerinventory;

import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHandler;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHelper;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentModule;
import com.lothrazar.powerinventory.inventory.InventoryOverpowered;
import com.lothrazar.powerinventory.util.UtilPlayerInventoryFilestorage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class OPHandler extends DeathStackHandler {

    private static final String MOD_ID = "powerinventory";

    private InventoryOverpowered playerInventory;

    public OPHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerInventory = UtilPlayerInventoryFilestorage.getPlayerInventory(player);
        setSize(playerInventory.func_70302_i_());
    }

    @Override
    public void storeInventory() {

        for (int index = 0; index < storage.getSlots(); index++) {

            ItemStack stack = getStackInSlot(index);

            if (stack.isEmpty()) { continue; }

            boolean essential = DeathStackHelper.isEssential(stack);
            boolean cursed = !essential && DeathStackHelper.isCursed(stack);
            boolean store = ((checkToStore(index) && !cursed) || essential);

            if (!store && EnchantmentModule.registerEnchant) {
                int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentModule.soulbound, stack);
                if (level != 0) {
                    store = essential = DeathStackHelper.handleSoulbound(stack, level);
                    cursed = !essential && cursed;
                }
            }

            if (cursed && InventoryModule.destroyCursed) {
                DeathStackHelper.destroyStack(stack);
                continue;
            }

            if (InventoryModule.dropLoss > 0 || InventoryModule.keptLoss > 0) {
                DeathStackHelper.loseDurability(player, stack, store);
            }

            if (InventoryModule.dropDrain > 0 || InventoryModule.keptDrain > 0) {
                DeathStackHelper.loseEnergy(stack, store);
            }

            if (stack.isEmpty()) { continue; }

            if (store) {
                if (!essential && InventoryModule.randomDrop > 0) {
                    int dropAmount = 0;
                    dropAmount += DeathStackHelper.randomlyDrop(stack);
                    player.dropItem(stack.splitStack(dropAmount), false);
                } else {
                    continue;
                }
            } else {
                playerInventory.dropStackInSlot(player, index);
                playerInventory.func_70299_a(index, ItemStack.EMPTY);
            }

            if (!stack.isEmpty() && InventoryModule.randomDestroy > 0) {
                DeathStackHelper.randomlyDestroy(stack);
            }
        }
    }

    public boolean checkToStore(int slot) {
        return OPModule.keepOP;
    }
    public ItemStack getStackInSlot(int slot) {
        return playerInventory.func_70301_a(slot);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {
        //NO-OP
    }
}
