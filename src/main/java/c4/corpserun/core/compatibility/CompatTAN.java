package c4.corpserun.core.compatibility;

import c4.corpserun.config.values.compatibility.ConfigCompatBool;
import c4.corpserun.config.values.compatibility.ConfigCompatInt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.thirst.ThirstHelper;

public class CompatTAN {

    private static final String MOD_ID = "toughasnails";

    @Optional.Method(modid = MOD_ID)
    public static void keepHydration(EntityPlayer player, EntityPlayer original) {
        ThirstHelper.getThirstData(player).setHydration(ThirstHelper.getThirstData(original).getHydration());
    }

    @Optional.Method(modid = MOD_ID)
    public static void restoreThirst(EntityPlayer player, EntityPlayer original) {
        if (ConfigCompatBool.KEEP_THIRST.getValue()) {
            ThirstHelper.getThirstData(player).setThirst(Math.max(ConfigCompatInt.MIN_THIRST.getValue(), (Math.min(ConfigCompatInt.MAX_THIRST.getValue(), ThirstHelper.getThirstData(original).getThirst()))));
        } else {
            ThirstHelper.getThirstData(player).setThirst(Math.max(ConfigCompatInt.MIN_THIRST.getValue(), (Math.min(ConfigCompatInt.MAX_THIRST.getValue(), 20))));
        }
    }

    @Optional.Method(modid = MOD_ID)
    public static void keepTemperature(EntityPlayer player, EntityPlayer original) {
        TemperatureHelper.getTemperatureData(player).setTemperature(TemperatureHelper.getTemperatureData(original).getTemperature());
    }

    public static boolean isLoaded() {
        return Loader.isModLoaded(MOD_ID);
    }
}
