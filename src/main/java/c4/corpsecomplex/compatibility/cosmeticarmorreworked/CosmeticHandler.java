package c4.corpsecomplex.compatibility.cosmeticarmorreworked;

import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.inventory.DeathStackHandler;
import lain.mods.cos.CosmeticArmorReworked;
import lain.mods.cos.inventory.InventoryCosArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class CosmeticHandler extends DeathStackHandler {

    private static final String MOD_ID = "cosmeticarmorreworked";
    private InventoryCosArmor playerCosmetics;

    public CosmeticHandler (EntityPlayer player) {
        super(player, MOD_ID);
        playerCosmetics = CosmeticArmorReworked.invMan.getCosArmorInventory(player.getUniqueID());
        setSize(playerCosmetics.func_70302_i_());
        cfgStore = CosmeticModule.keepCosmetic;
    }

    public boolean checkConfig(int index) {
        return cfgStore;
    }

    public ItemStack getStackInSlot(int index) {
        return playerCosmetics.func_70301_a(index);
    }

    public static void retrieveInventory(EntityPlayer player, IDeathInventory oldInventory) {

        NBTTagCompound nbt = oldInventory.getStorage(MOD_ID);
        if (nbt == null) { return; }

        ItemStackHandler storage = new ItemStackHandler();
        storage.deserializeNBT(nbt);
        InventoryCosArmor playerCosmetics = CosmeticArmorReworked.invMan.getCosArmorInventory(player.getUniqueID());

        for (int index = 0; index < storage.getSlots(); index++) {
            ItemStack stack = storage.getStackInSlot(index);
            if (stack.isEmpty()) { continue;}

            playerCosmetics.func_70299_a(index, stack);
        }
    }
}
