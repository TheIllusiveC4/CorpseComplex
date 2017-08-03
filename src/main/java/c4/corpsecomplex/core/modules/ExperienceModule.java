package c4.corpsecomplex.core.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;

public class ExperienceModule extends Module {

    private static boolean keepXP;
    private static double xpLoss;
    private static double xpRecover;
    private static boolean cfgEnabled;

    @SubscribeEvent
    public void onPlayerXPDrop(LivingExperienceDropEvent e) {

        if (!(e.getEntityLiving() instanceof EntityPlayer)) { return;}

        setExperiencesValues(e);
    }

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        restoreXP(e.getEntityPlayer(), e.getOriginal());
    }

    public ExperienceModule() {
        super("Experience", "Experience Management");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Experience Module", false, "Set to true to enable experience module");
        keepXP = getBool("Keep All XP", false, "Set to true to keep all XP on death");
        xpLoss = getFloat("Lost XP Percent", 1, 0, 1, "Percent of experience lost on death");
        xpRecover = getFloat("Recoverable XP Percent", 0.2F, 0, 1, "Percent of lost experience that can be recovered");
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Experience Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    private static void restoreXP(EntityPlayer player, EntityPlayer oldPlayer) {

        player.addExperience(oldPlayer.experienceTotal);
    }

    private static void setExperiencesValues(LivingExperienceDropEvent e) {

        if (keepXP) {
            e.setCanceled(true);
        } else {
            EntityPlayer player = (EntityPlayer) e.getEntityLiving();
            int dropXP = (int) Math.round(player.experienceTotal * xpLoss * xpRecover);
            int keptXP = (int) Math.round(player.experienceTotal * (1 - xpLoss));
            e.setDroppedExperience(dropXP);
            player.experienceLevel = 0;
            player.experience = 0.0F;
            player.experienceTotal = 0;
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
                player.experienceLevel = 0;
                player.experience = 0.0F;
                player.experienceTotal = 0;
            }
        }
    }
}
