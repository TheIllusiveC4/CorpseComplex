package c4.corpserun.core;

import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.core.compatibility.TANModule;
import c4.corpserun.core.inventory.InventoryHandler;
import c4.corpserun.core.modules.EffectsModule;
import c4.corpserun.core.modules.ExperienceModule;
import c4.corpserun.core.modules.HungerModule;
import c4.corpserun.core.modules.InventoryModule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandler {

    //Catch the items we want to keep as soon as possible

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void storeDeathInventory (LivingDeathEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)
                || e.getEntity().getEntityWorld().getGameRules().getBoolean("keepInventory")) { return;}

        InventoryHandler inventoryHandler = new InventoryHandler((EntityPlayer) e.getEntityLiving());
        if (ConfigHandler.isInventoryModuleEnabled()) {
            inventoryHandler.initStorage();
        }
        inventoryHandler.iterateInventory();
    }

    /*Set priority to low here so we hopefully give the
    items back after other mods have done their business
    **/

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public void onPlayerDrop (PlayerDropsEvent e) {

        EntityPlayer player = e.getEntityPlayer();

        if (!player.world.getGameRules().getBoolean("keepInventory")) {
            if (ConfigBool.DESTROY_DROPPED_ITEMS.getValue()) {
                e.getDrops().clear();
            }
            InventoryModule.retrieveStorage(player);
        }
    }

    @SubscribeEvent
    public void onPlayerXPDrop(LivingExperienceDropEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)) { return;}

        ExperienceModule.setExperiencesValues(e);
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        HungerModule.restoreHunger(player, oldPlayer);
        ExperienceModule.restoreXP(player, oldPlayer);

        if (!player.world.getGameRules().getBoolean("keepInventory")) {
            InventoryModule.retrieveStorage(player, oldPlayer);
        }

        if (TANModule.isLoaded()){
            TANModule.restoreStats(player, oldPlayer);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        EntityPlayer player = e.player;

        EffectsModule.addPotionEffects(player);

        if (TANModule.isLoaded()){
            TANModule.updateClientTAN(player);
        }
    }
}
