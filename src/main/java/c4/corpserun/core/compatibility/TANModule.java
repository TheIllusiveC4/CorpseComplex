package c4.corpserun.core.compatibility;

import c4.corpserun.config.ConfigHandler;
import c4.corpserun.config.values.compatibility.ConfigCompatBool;
import c4.corpserun.config.values.compatibility.ConfigCompatInt;
import c4.corpserun.network.PacketHandler;
import c4.corpserun.network.TANMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import toughasnails.api.temperature.Temperature;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.thirst.ThirstHelper;

public class TANModule {

    private static final String MOD_ID = "toughasnails";
    private static boolean isEnabled = ConfigCompatBool.ENABLE_TAN.getValue();

    public static boolean isLoaded() {
        return Loader.isModLoaded(MOD_ID);
    }

    @Optional.Method(modid = MOD_ID)
    public static void restoreStats(EntityPlayer player, EntityPlayer oldPlayer) {

        if (!isEnabled) { return;}

        int thirst = ThirstHelper.getThirstData(oldPlayer).getThirst();
        int temp = TemperatureHelper.getTemperatureData(oldPlayer).getTemperature().getRawValue();
        float hydration = ThirstHelper.getThirstData(oldPlayer).getHydration();

        if (ConfigCompatBool.KEEP_THIRST.getValue()) {
            restoreThirst(player, thirst);
        } else {
            restoreThirst(player, 20);
        }
        if (ConfigCompatBool.KEEP_HYDRATION.getValue()) {
            restoreHydration(player, hydration);
        }
        if (ConfigCompatBool.KEEP_TEMPERATURE.getValue()) {
            restoreTemperature(player, temp);
        }
    }

    @Optional.Method(modid = MOD_ID)
    public static void updateClientTAN (EntityPlayer player) {

        int thirst = ThirstHelper.getThirstData(player).getThirst();
        int temp = TemperatureHelper.getTemperatureData(player).getTemperature().getRawValue();

        PacketHandler.INSTANCE.sendTo(new TANMessage(thirst, temp), (EntityPlayerMP) player);
    }

    @Optional.Method(modid = MOD_ID)
    private static void restoreThirst(EntityPlayer player, int thirst) {
        ThirstHelper.getThirstData(player).setThirst(Math.max(ConfigCompatInt.MIN_THIRST.getValue(), (Math.min(ConfigCompatInt.MAX_THIRST.getValue(), thirst))));
    }

    @Optional.Method(modid = MOD_ID)
    private static void restoreHydration(EntityPlayer player, float hydration) {
        ThirstHelper.getThirstData(player).setHydration(hydration);
    }

    @Optional.Method(modid = MOD_ID)
    private static void restoreTemperature(EntityPlayer player, int temp) {
        TemperatureHelper.getTemperatureData(player).setTemperature(new Temperature(temp));
    }
}
