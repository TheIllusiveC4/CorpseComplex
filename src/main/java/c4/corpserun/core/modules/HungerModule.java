package c4.corpserun.core.modules;

import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.ConfigBool;
import c4.corpserun.config.values.ConfigInt;
import net.minecraft.entity.player.EntityPlayer;

public final class HungerModule {

    private static boolean isEnabled = ConfigHandler.isHungerModuleEnabled();

    public static void restoreHunger(EntityPlayer player, EntityPlayer oldPlayer) {

        if (!isEnabled) { return;}

        int oldFood = oldPlayer.getFoodStats().getFoodLevel();

        if (ConfigBool.KEEP_FOOD.getValue()) {
            player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.getValue(), (Math.min(ConfigInt.MAX_FOOD.getValue(), oldFood))));
        } else {
            player.getFoodStats().setFoodLevel(Math.max(ConfigInt.MIN_FOOD.getValue(), (Math.min(ConfigInt.MAX_FOOD.getValue(), 20))));
        }
    }
}
