/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.compatibility.powerinventory;

import c4.corpsecomplex.CorpseComplex;
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
        setSize(playerInventory.getSizeInventory());
    }

    @Override
    public void storeInventory() {

        for (int index = 0; index < storage.getSlots(); index++) {

            ItemStack stack = getStackInSlot(index);

            if ( CorpseComplex.isStackEmpty(stack)) { continue; }

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

            if (CorpseComplex.isStackEmpty(stack)) { continue; }

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
                playerInventory.setInventorySlotContents(index, null);
            }

            if (!CorpseComplex.isStackEmpty(stack) && InventoryModule.randomDestroy > 0) {
                DeathStackHelper.randomlyDestroy(stack);
            }
        }
    }

    public boolean checkToStore(int slot) {
        return OPModule.keepOP;
    }
    public ItemStack getStackInSlot(int slot) {
        return playerInventory.getStackInSlot(slot);
    }

    public void retrieveInventory(IDeathInventory oldDeathInventory) {
        //NO-OP
    }
}
