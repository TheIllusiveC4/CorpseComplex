package c4.corpserun.core.modules.compatibility;

import c4.corpserun.core.modules.Module;
import c4.corpserun.network.NetworkHandler;
import c4.corpserun.network.TANMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import toughasnails.api.temperature.Temperature;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.thirst.ThirstHelper;

import java.util.ArrayList;
import java.util.Collections;

public class TANModule extends Module {

    private final static String MOD_ID = "toughasnails";
    private static boolean keepThirst;
    private static boolean keepHydration;
    private static boolean keepTemperature;
    private static int minThirst;
    private static int maxThirst;
    private static boolean cfgEnabled;

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerRespawnBegin(PlayerEvent.Clone e) {

        if (!e.isWasDeath() || e.getEntityPlayer().world.isRemote) { return;}

        EntityPlayer player = e.getEntityPlayer();
        EntityPlayer oldPlayer = e.getOriginal();

        restoreStats(player, oldPlayer);
    }

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        updateClientTAN(e.player);
    }

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerJoin(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e) {

        updateClientTAN(e.player);
    }

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerDimensionChange(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent e) {

        updateClientTAN(e.player);
    }

    public TANModule() {
        configName = "Tough as Nails";
        configDescription = "Tough as Nails Management";
        configCategory = new ConfigCategory(configName);
        configCategory.setComment(configDescription);
        prevEnabled = false;
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable TAN Module", false, "Set to true to enable Tough as Nails module");
        keepThirst = getBool("Keep Thirst Level", false, "Set to true to retain thirst level on respawn");
        keepHydration = getBool("Keep Hydration Level", false, "Set to true to retain hydration level on respawn");
        keepTemperature = getBool("Keep Temperature Level", false, "Set to true to retain temperature level on respawn");
        minThirst = getInt("Minimum Thirst Level", 6, 0, 20, "Lowest amount of thirst you can respawn with");
        maxThirst = getInt("Maximum Thirst Level", 20, minThirst, 20, "Maximum amount of thirst you can respawn with");
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable TAN Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }

    @Optional.Method(modid = MOD_ID)
    private static void restoreStats(EntityPlayer player, EntityPlayer oldPlayer) {

        int thirst = ThirstHelper.getThirstData(oldPlayer).getThirst();
        int temp = TemperatureHelper.getTemperatureData(oldPlayer).getTemperature().getRawValue();
        float hydration = ThirstHelper.getThirstData(oldPlayer).getHydration();

        if (keepThirst) {
            restoreThirst(player, thirst);
        } else {
            restoreThirst(player, 20);
        }
        if (keepHydration) {
            restoreHydration(player, hydration);
        }
        if (keepTemperature) {
            restoreTemperature(player, temp);
        }
    }

    @Optional.Method(modid = MOD_ID)
    private static void updateClientTAN (EntityPlayer player) {

        int thirst = ThirstHelper.getThirstData(player).getThirst();
        int temp = TemperatureHelper.getTemperatureData(player).getTemperature().getRawValue();

        NetworkHandler.INSTANCE.sendTo(new TANMessage(thirst, temp), (EntityPlayerMP) player);
    }

    @Optional.Method(modid = MOD_ID)
    private static void restoreThirst(EntityPlayer player, int thirst) {
        ThirstHelper.getThirstData(player).setThirst(Math.max(minThirst, (Math.min(maxThirst, thirst))));
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
