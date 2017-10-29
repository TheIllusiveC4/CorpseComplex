/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.spawning;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.modules.spawning.capability.DeathLocation;
import c4.corpsecomplex.common.modules.spawning.capability.IDeathLocation;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Collections;

public class SpawningModule extends Module {

    @GameRegistry.ObjectHolder("corpsecomplex:scroll")
    public static ItemScroll scroll;

    public static boolean returnScroll;
    public static boolean registerScroll;
    private static boolean disableBeds;
    private static boolean cfgEnabled;

    public SpawningModule() {
        super("Respawning", "Customize general respawning rules");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Respawning Module", false, "Set to true to enable respawning module features", true);
        disableBeds = getBool("Disable Bed Spawn Points", false, "Set to true to disable beds setting spawn points", false);
        returnScroll = getBool("Return Scroll", false, "Set to true to give players a scroll on respawn that can return them to their death location", true);
        registerScroll = cfgEnabled && returnScroll;
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Respawning Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent e) {

        if (e.getEntity() instanceof EntityPlayer) {

            EntityPlayer player = (EntityPlayer) e.getEntityLiving();

            if (registerScroll) {

                IDeathLocation deathLoc = player.getCapability(DeathLocation.Provider.DEATH_LOC_CAP, null);
                if (deathLoc != null) {
                    deathLoc.setUsedScroll(false);
                    deathLoc.setDeathLocation(player.getPosition());
                    deathLoc.setDeathDimension(player.getEntityWorld().provider.getDimension());
                    deathLoc.setHasDeathLocation(true);
                }
            }

            if (disableBeds) {

                player.setSpawnPoint(null, false);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.Clone e) {

        if (registerScroll && e.isWasDeath()) {

            EntityPlayer player = e.getEntityPlayer();
            EntityPlayer oldPlayer = e.getOriginal();
            IDeathLocation oldDeathLoc = oldPlayer.getCapability(DeathLocation.Provider.DEATH_LOC_CAP, null);
            IDeathLocation newDeathLoc = player.getCapability(DeathLocation.Provider.DEATH_LOC_CAP, null);

            if (oldDeathLoc != null && newDeathLoc != null) {
                newDeathLoc.setUsedScroll(oldDeathLoc.hasUsedScroll());
                newDeathLoc.setDeathDimension(oldDeathLoc.getDeathDimension());
                newDeathLoc.setDeathLocation(oldDeathLoc.getDeathLocation());
                newDeathLoc.setHasDeathLocation(oldDeathLoc.hasDeathLocation());
            }

            if (!e.getEntityPlayer().world.isRemote) {
                ItemHandlerHelper.giveItemToPlayer(e.getEntityPlayer(), new ItemStack(scroll));
            }
        }
    }
}
