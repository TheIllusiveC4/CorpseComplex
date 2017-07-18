package c4.corpserun.core.modules;

import c4.corpserun.capability.DeathInventoryProvider;
import c4.corpserun.config.ConfigHandler;
import c4.corpserun.core.inventory.InventoryHandler;
import net.minecraft.entity.player.EntityPlayer;

public final class InventoryModule {

    private static boolean isEnabled = ConfigHandler.isInventoryModuleEnabled();

    public static void retrieveStorage(EntityPlayer player) {

        if (!isEnabled) { return;}

        InventoryHandler.retrieveStorage(player, player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null));
    }

    public static void retrieveStorage(EntityPlayer player, EntityPlayer oldPlayer) {

        if (!isEnabled) { return;}

        InventoryHandler.retrieveStorage(player, oldPlayer.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null));
    }
}
