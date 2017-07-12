package c4.corpserun.core;

import c4.corpserun.capability.DeathInventoryProvider;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.config.ConfigEffectsHelper;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import c4.corpserun.config.values.ConfigInt;
import c4.corpserun.config.values.compatibility.ConfigCompatBool;
import c4.corpserun.core.compatibility.CompatTAN;
import jdk.nashorn.internal.objects.annotations.Function;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class EventHandler {

    //Catch the items we want to keep as soon as possible

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void onPlayerDeath (LivingDeathEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)
                || e.getEntity().getEntityWorld().getGameRules().getBoolean("keepInventory")) { return;}

        EntityPlayer player = (EntityPlayer) e.getEntityLiving();

        DeathInventoryHandler deathInventoryHandler = new DeathInventoryHandler(player);
        deathInventoryHandler.iterateInventory();
    }

    /*Set priority to low here so we hopefully give the
    items back after other mods have done their business
    **/

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public void onPlayerDrop (PlayerDropsEvent e) {

        if (ConfigBool.ENABLE_INVENTORY.getValue()) {

            if (ConfigBool.DESTROY_DROPPED_ITEMS.getValue()) {
                e.getDrops().clear();
            }

            EntityPlayer player = e.getEntityPlayer();

            if (player.getEntityWorld().getGameRules().getBoolean("keepInventory")) {return;}

            IDeathInventory deathStorage = player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
            DeathInventoryHandler.addStorageContents(deathStorage.getDeathInventory(), player.inventory);
        }
    }

    @SubscribeEvent
    public void onPlayerXPDrop(LivingExperienceDropEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)) { return;}

        if (ConfigBool.ENABLE_XP.getValue()) {

            EntityPlayer player = (EntityPlayer) e.getEntityLiving();

            if (ConfigBool.KEEP_XP.getValue()) {
                e.setCanceled(true);
            } else {
                int dropXP = Math.round(player.experienceTotal * (ConfigFloat.XP_LOSS_PERCENT.getValue()) * (ConfigFloat.XP_RECOVER_PERCENT.getValue()));
                int keptXP = Math.round(player.experienceTotal * (1 - ConfigFloat.XP_LOSS_PERCENT.getValue()));
                e.setDroppedExperience(dropXP);
                player.experienceLevel = 0;
                player.experience = 0.0F;
                player.experienceTotal = 0;
                ExperienceHandler.addExperience(player, keptXP);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        if (!e.isWasDeath()) { return;}

        if (ConfigBool.ENABLE_INVENTORY.getValue()) {
            if (!player.world.getGameRules().getBoolean("keepInventory")) {
                IDeathInventory oldDeathStorage = oldPlayer.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
                DeathInventoryHandler.addStorageContents(oldDeathStorage.getDeathInventory(), player.inventory);
            }
        }

        if (ConfigBool.ENABLE_HUNGER.getValue()) {
            if (ConfigBool.KEEP_HUNGER.getValue()) {
                player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.getValue(), (Math.min(ConfigInt.MAX_FOOD.getValue(), oldPlayer.getFoodStats().getFoodLevel()))));
            } else {
                player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.getValue(), (Math.min(ConfigInt.MAX_FOOD.getValue(), 20))));
            }
        }

        if (ConfigBool.ENABLE_XP.getValue()) {  player.addExperience(oldPlayer.experienceTotal);}

        if (CompatTAN.isLoaded() && ConfigCompatBool.ENABLE_TAN.getValue()){
            if (ConfigCompatBool.KEEP_THIRST.getValue()) { CompatTAN.keepThirst(oldPlayer, player); }
            if (ConfigCompatBool.KEEP_HYDRATION.getValue()) { CompatTAN.keepHydration(oldPlayer, player); }
            if (ConfigCompatBool.KEEP_TEMPERATURE.getValue()) { CompatTAN.keepTemperature(oldPlayer, player); }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        if (ConfigBool.ENABLE_EFFECTS.getValue()) {
            ArrayList<String[]> effectsToApply = ConfigEffectsHelper.getEffectsList();
            if (effectsToApply.isEmpty()) {
                return;
            }

            for (String[] s : effectsToApply) {
                PotionEffect potionEffect = new PotionEffect(
                        Potion.getPotionFromResourceLocation(s[0]),
                        Integer.parseInt(s[1]) * 20,
                        Integer.parseInt(s[2]) - 1);
                if (!ConfigBool.ENABLE_CURE.getValue()) {
                    potionEffect.setCurativeItems(new ArrayList<>(0));
                }
                e.player.addPotionEffect(potionEffect);
            }
        }
    }
}
