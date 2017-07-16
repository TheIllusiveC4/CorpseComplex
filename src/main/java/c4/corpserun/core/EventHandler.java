package c4.corpserun.core;

import c4.corpserun.capability.DeathInventoryProvider;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.compatibility.ConfigCompatBool;
import c4.corpserun.core.compatibility.CompatTAN;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.thirst.ThirstHelper;
import toughasnails.handler.thirst.ThirstOverlayHandler;
import toughasnails.handler.thirst.ThirstStatHandler;
import toughasnails.thirst.ThirstHandler;
import toughasnails.thirst.ThirstStorage;

public class EventHandler {

    //Catch the items we want to keep as soon as possible

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void onPlayerDeath (LivingDeathEvent e) {

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

        if (ConfigHandler.isInventoryModuleEnabled()) {

            if (ConfigBool.DESTROY_DROPPED_ITEMS.getValue()) {
                e.getDrops().clear();
            }

            EntityPlayer player = e.getEntityPlayer();

            if (player.getEntityWorld().getGameRules().getBoolean("keepInventory")) {return;}

            InventoryHandler.retrieveStorage(player, player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null));
        }
    }

    @SubscribeEvent
    public void onPlayerXPDrop(LivingExperienceDropEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer) || !ConfigHandler.isExperienceModuleEnabled()) { return;}

        if (ConfigBool.KEEP_XP.getValue()) {
            e.setCanceled(true);
        } else {
            ExperienceHelper.setExperiencesValues(e);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        if (ConfigHandler.isInventoryModuleEnabled()) {
            if (!player.world.getGameRules().getBoolean("keepInventory")) {
                InventoryHandler.retrieveStorage(player, oldPlayer.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null));
            }
        }

        if (ConfigHandler.isHungerModuleEnabled()) {
            RespawnHelper.restoreHunger(player, oldPlayer);
        }

        if (ConfigHandler.isExperienceModuleEnabled()) {
            ExperienceHelper.restoreXP(player, oldPlayer);
        }

        if (CompatTAN.isLoaded() && ConfigCompatBool.ENABLE_TAN.getValue()){
            CompatTAN.restoreThirst(player, oldPlayer);
            if (ConfigCompatBool.KEEP_HYDRATION.getValue()) { CompatTAN.keepHydration(player, oldPlayer); }
            if (ConfigCompatBool.KEEP_TEMPERATURE.getValue()) { CompatTAN.keepTemperature(player, oldPlayer); }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        if (ConfigBool.ENABLE_EFFECTS.getValue()) {
            RespawnHelper.addPotionEffects(e.player);
        }
    }
}
