package c4.corpsecomplex.core.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class HungerModule extends Module {

    private static boolean keepFood;
    private static int minFood;
    private static int maxFood;
    private static boolean cfgEnabled;
    private static boolean keepSaturation;

    @SubscribeEvent
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        restoreHunger(e.getEntityPlayer(), e.getOriginal());
    }

    public HungerModule() {
        super("Hunger", "Hunger Management");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Hunger Module", false, "Set to true to enable hunger module");
        keepFood = getBool("Keep Food Level", false, "Set to true to retain food level on death");
        minFood = getInt("Minimum Food Level", 6, 0, 20, "Lowest amount of food level you can respawn with");
        maxFood = getInt("Maximum Food Level", 20, minFood, 20, "Highest amount of food level you can respawn with");
        keepSaturation = getBool("Keep Saturation", false, "Set to true to retain saturation on death");
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Arrays.asList("Enable Hunger Module", "Keep Food Level", "Maximum Food Level", "Minimum Food Level"));
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
        
        if (keepSaturation) {
            player.getFoodStats().setFoodSaturationLevel(oldPlayer.getFoodStats().getSaturationLevel());
        }
    }
}
