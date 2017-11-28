/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules;

import c4.corpsecomplex.common.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;

public class ExperienceModule extends Module {

    private static boolean keepXP;
    private static double xpLoss;
    private static double xpRecover;
    private static int maxXPRecover;
    private static boolean cfgEnabled;

    @SubscribeEvent (priority = EventPriority.HIGH)
    public void onPlayerXPDrop(LivingExperienceDropEvent e) {

        if (e.getEntityLiving() instanceof EntityPlayer) {

            setExperiencesValues(e);
        }
    }

    @SubscribeEvent (priority = EventPriority.HIGH)
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (e.isWasDeath()) {

            restoreXP(e.getEntityPlayer(), e.getOriginal());
        }
    }

    public ExperienceModule() {
        super("Experience", "Customize experience loss on death");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Experience Module", false, "Set to true to enable experience module", false);
        keepXP = getBool("Keep All XP", false, "Set to true to keep all XP on death", false);
        xpLoss = getDouble("Lost XP Percent", 1, 0, 1, "Percent of experience lost on death", false);
        xpRecover = getDouble("Recoverable XP Percent", 0.2F, 0, 1, "Percent of lost experience that can be recovered", false);
        maxXPRecover = getInt("Maximum Recoverable XP", 0, 0, 10000, "Maximum amount of experience that can be recovered, 0 to disable", false);
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Experience Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled && !Loader.isModLoaded("tombstone");
    }

    private static void restoreXP(EntityPlayer player, EntityPlayer oldPlayer) {

        resetXP(player);
        player.addExperience(oldPlayer.experienceTotal);
    }

    private static void setExperiencesValues(LivingExperienceDropEvent e) {

        EntityPlayer player = (EntityPlayer) e.getEntityLiving();

        if (keepXP) {
            e.setCanceled(true);
        } else {
            int dropXP = (int) Math.round(player.experienceTotal * xpLoss * xpRecover);
            int keptXP = (int) Math.round(player.experienceTotal * (1 - xpLoss));
            if (maxXPRecover > 0) {
                dropXP = Math.min(maxXPRecover, dropXP);
            }
            e.setDroppedExperience(dropXP);
            resetXP(player);
            addExperience(player, keptXP);
        }
    }

    private static void addExperience(EntityPlayer player, int amount) {

        int i = Integer.MAX_VALUE - player.experienceTotal;

        if (amount > i) {
            amount = i;
        }

        player.experience += (float)amount / (float)player.xpBarCap();

        for (player.experienceTotal += amount; player.experience >= 1.0F; player.experience /= (float)player.xpBarCap()) {
            player.experience = (player.experience - 1.0F) * (float)player.xpBarCap();
            player.experienceLevel += 1;

            if (player.experienceLevel < 0) {
                resetXP(player);
            }
        }
    }

    private static void resetXP(EntityPlayer player) {
        player.experienceLevel = 0;
        player.experience = 0.0F;
        player.experienceTotal = 0;
    }
}
