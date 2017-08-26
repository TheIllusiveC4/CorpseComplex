package c4.corpsecomplex.common.modules;

import c4.corpsecomplex.common.Module;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MiscModule extends Module {

    private static boolean cfgEnabled;
    private static boolean disableBeds;

    @SubscribeEvent
    public void onPlayerSetSpawn(PlayerSetSpawnEvent e) {
        if (disableBeds) {
            e.setCanceled(true);
        }
    }

    public MiscModule() {
        super("Miscellaneous", "Miscellaneous options");
    }

    public void loadModuleConfig() {
        setCategoryComment();
        cfgEnabled = getBool("Enable Miscellaneous Module", false, "Set to true to enable miscellaneous module");
        disableBeds = getBool("Disable Bed Spawns", false, "Set to true to disable players setting bed spawns");
    }

    public void initPropOrder() {
        propOrder = new ArrayList<>(Collections.singletonList("Enable Miscellaneous Module"));
    }

    public void setEnabled() {
        enabled = cfgEnabled;
    }
}
