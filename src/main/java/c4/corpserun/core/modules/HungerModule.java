package c4.corpserun.core.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Collections;

public class HungerModule extends Module {

    private static boolean keepFood;
    private static int minFood;
    private static int maxFood;
    private static boolean cfgEnabled;

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        restoreHunger(e.getEntityPlayer(), e.getOriginal());
    }

    public HungerModule() {
        configName = "Hunger";
        configDescription = "Hunger Management";
        configCategory = new ConfigCategory(configName);
        configCategory.setComment(configDescription);
        prevEnabled = false;
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Hunger Module", false, "Set to true to enable hunger module");
        keepFood = getBool("Keep Food Level", false, "Set to true to retain food level on death");
        minFood = getInt("Minimum Food Level", 6, 0, 20, "Lowest amount of food level you can respawn with");
        maxFood = getInt("Maximum Food Level", 20, minFood, 20, "Highest amount of food level you can respawn with");
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Hunger Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    private static void restoreHunger(EntityPlayer player, EntityPlayer oldPlayer) {

        int oldFood = oldPlayer.getFoodStats().getFoodLevel();

        if (keepFood) {
            player.getFoodStats().setFoodLevel(Math.max(minFood, (Math.min(maxFood, oldFood))));
        } else {
            player.getFoodStats().setFoodLevel(Math.max(minFood, (Math.min(maxFood, 20))));
        }
    }
}
