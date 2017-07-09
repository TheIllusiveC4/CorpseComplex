package c4.corpserun.core;

import c4.corpserun.capability.DeathInventoryProvider;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.config.ConfigEffectsHelper;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigFloat;
import c4.corpserun.config.values.ConfigInt;
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
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class EventHandler {

    //Catch the items we want to keep as soon as possible

    @SubscribeEvent (priority = EventPriority.HIGHEST)
    public void onPlayerDeath (LivingDeathEvent e) {

        if(!(e.getEntityLiving() instanceof EntityPlayer)
                || e.getEntity().getEntityWorld().getGameRules().getBoolean("keepInventory")) { return;}

        EntityPlayer player = (EntityPlayer) e.getEntityLiving();

        IDeathInventory deathStorage = player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
        deathStorage.assignDeathInventory(new DeathInventoryHandler(player.inventory).getStorage());
    }

    /*Set priority to low here so we hopefully give the
    items back after other mods have done their business
    **/

    @SubscribeEvent (priority = EventPriority.LOWEST)
    public void onPlayerDrop (PlayerDropsEvent e) {

        EntityPlayer player = e.getEntityPlayer();

        if (player.getEntityWorld().getGameRules().getBoolean("keepInventory")) { return;}

        if (ConfigBool.DESTROY_DROPPED_ITEMS.value)         {   e.getDrops().clear();}

        IDeathInventory deathStorage = player.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
        addStorageContents(deathStorage.getDeathInventory(), player.inventory);
    }

    @SubscribeEvent
    public void onPlayerXPDrop(LivingExperienceDropEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)) { return;}

        EntityPlayer player = (EntityPlayer) e.getEntityLiving();

        if (ConfigBool.KEEP_XP.value) {
            e.setCanceled(true);
        } else {
            int dropXP = Math.round(player.experienceTotal * (ConfigFloat.XP_LOSS_PERCENT.value) * (ConfigFloat.XP_RECOVER_PERCENT.value));
            int keptXP = Math.round(player.experienceTotal * (1 - ConfigFloat.XP_LOSS_PERCENT.value));
            e.setDroppedExperience(dropXP);
            player.experienceLevel = 0;
            player.experience = 0.0F;
            player.experienceTotal = 0;
            ExperienceHandler.addExperience(player,keptXP);
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        if (!e.isWasDeath()) { return;}

        if (!player.world.getGameRules().getBoolean("keepInventory")) {
            IDeathInventory oldDeathStorage = oldPlayer.getCapability(DeathInventoryProvider.DEATH_INV_CAP, null);
            addStorageContents(oldDeathStorage.getDeathInventory(), player.inventory);
        }

        if (ConfigBool.KEEP_HUNGER.value) {
            player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.value, (Math.min(ConfigInt.MAX_FOOD.value, oldPlayer.getFoodStats().getFoodLevel()))));
        } else {
            player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.value, (Math.min(ConfigInt.MAX_FOOD.value, 20))));
        }

        player.addExperience(oldPlayer.experienceTotal);
    }

    @SubscribeEvent
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        ArrayList<String[]> effectsToApply = ConfigEffectsHelper.getEffectsList();
        if (effectsToApply.isEmpty()) { return;}

        for (String[] s : effectsToApply) {
            PotionEffect potionEffect = new PotionEffect(
                    Potion.getPotionFromResourceLocation(s[0]),
                    Integer.parseInt(s[1]) * 20,
                    Integer.parseInt(s[2])-1);
            if (!ConfigBool.ENABLE_CURE.value) {
                ArrayList<ItemStack> noCures = new ArrayList<>(0);
                potionEffect.setCurativeItems(noCures);
            }
            e.player.addPotionEffect(potionEffect);
        }
    }

    private void addStorageContents(NonNullList<ItemStack> storage, InventoryPlayer inventory) {
        for (int x = 0; x < storage.size(); x++) {

            if (storage.get(x).isEmpty()) { continue;}

            if (!inventory.getStackInSlot(x).isEmpty()) {
                inventory.addItemStackToInventory(storage.get(x));
            } else {
                inventory.setInventorySlotContents(x, storage.get(x));
            }
        }
    }
}
