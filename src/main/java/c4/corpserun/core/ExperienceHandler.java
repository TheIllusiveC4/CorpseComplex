package c4.corpserun.core;

import net.minecraft.entity.player.EntityPlayer;

public final class ExperienceHandler {

    public static void addExperience(EntityPlayer player, int amount) {

        int i = Integer.MAX_VALUE - player.experienceTotal;

        if (amount > i) {
            amount = i;
        }

        player.experience += (float)amount / (float)player.xpBarCap();

        for (player.experienceTotal += amount; player.experience >= 1.0F; player.experience /= (float)player.xpBarCap()) {
            player.experience = (player.experience - 1.0F) * (float)player.xpBarCap();
            addExperienceLevel(player,1);
        }
    }

    public static void addExperienceLevel(EntityPlayer player, int levels) {

        player.experienceLevel += 1;

        if (player.experienceLevel < 0) {
            player.experienceLevel = 0;
            player.experience = 0.0F;
            player.experienceTotal = 0;
        }
    }

}
