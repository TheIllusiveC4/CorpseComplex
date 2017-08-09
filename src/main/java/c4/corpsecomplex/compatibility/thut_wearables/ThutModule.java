package c4.corpsecomplex.compatibility.thut_wearables;

import c4.corpsecomplex.core.modules.Module;
import c4.corpsecomplex.core.modules.Submodule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thut.wearables.ThutWearables;
import thut.wearables.network.PacketSyncWearables;

public class ThutModule extends Submodule {

    private final static String MOD_ID = "thut_wearables";
    public static boolean keepThut;

    public ThutModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepThut = getBool("Keep Thut Wearables", false, "Set to true to keep Thut Wearables on death");
    }

    @Override
    public boolean hasEvents() {
        return true;
    }

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        updateClientThut(e.player);
    }

    @Optional.Method(modid = MOD_ID)
    private static void updateClientThut (EntityPlayer player) {

        ThutWearables.packetPipeline.sendTo(new PacketSyncWearables(player), (EntityPlayerMP) player);
//        NetworkHandler.INSTANCE.sendTo(new ThutMessage(player), (EntityPlayerMP) player);
    }

}
