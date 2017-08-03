package c4.corpsecomplex.compatibility.powerinventory;

import c4.corpsecomplex.core.inventory.DeathStackHandler;
import c4.corpsecomplex.core.inventory.DeathStackHelper;
import c4.corpsecomplex.core.modules.InventoryModule;
import com.lothrazar.powerinventory.inventory.InventoryOverpowered;
import com.lothrazar.powerinventory.util.UtilPlayerInventoryFilestorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

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

            boolean cursed = DeathStackHelper.isCursed(stack);
            boolean essential = !cursed && DeathStackHelper.isEssential(stack);
            boolean store = ((checkConfig(index) && !cursed) || essential);

            if (cursed && InventoryModule.destroyCursed) {
                DeathStackHelper.destroyCursed(stack);
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

    public boolean checkConfig(int index) {
        return OPModule.keepOP;
    }
    public ItemStack getStackInSlot(int index) {
        return playerInventory.func_70301_a(index);
    }
}
