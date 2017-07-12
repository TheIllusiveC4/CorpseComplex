package c4.corpserun.core.compatibility;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.thirst.ThirstHelper;

public class CompatTAN {

    private static final String MOD_ID = "toughasnails";

    @Optional.Method(modid = MOD_ID)
    public static void keepHydration(EntityPlayer original, EntityPlayer player) {
        ThirstHelper.getThirstData(player).setHydration(ThirstHelper.getThirstData(original).getHydration());
    }

    @Optional.Method(modid = MOD_ID)
    public static void keepThirst(EntityPlayer original, EntityPlayer player) {
        ThirstHelper.getThirstData(player).setThirst(ThirstHelper.getThirstData(original).getThirst());
    }

    @Optional.Method(modid = MOD_ID)
    public static void keepTemperature(EntityPlayer original, EntityPlayer player) {
        TemperatureHelper.getTemperatureData(player).setTemperature(TemperatureHelper.getTemperatureData(original).getTemperature());
    }

    public static boolean isLoaded() {
        return Loader.isModLoaded(MOD_ID);
    }
}
