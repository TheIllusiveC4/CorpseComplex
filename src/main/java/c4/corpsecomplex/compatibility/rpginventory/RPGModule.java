package c4.corpsecomplex.compatibility.rpginventory;

import c4.corpsecomplex.core.modules.Module;
import c4.corpsecomplex.core.modules.Submodule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import subaraki.rpginventory.network.PacketHandler;
import subaraki.rpginventory.network.PacketInventoryToClient;

public class RPGModule extends Submodule {

    private final static String MOD_ID = "rpginventory";
    public static boolean keepRPG;

    public RPGModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepRPG = getBool("Keep RPG Inventory", false, "Set to true to keep RPG Inventory on death");
    }

    @Override
    public boolean hasEvents() {
        return true;
    }

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        updateClientRPG(e.player);
    }

    @Optional.Method(modid = MOD_ID)
    private static void updateClientRPG (EntityPlayer player) {

        PacketHandler.NETWORK.sendTo(new PacketInventoryToClient(player), (EntityPlayerMP) player);
//        NetworkHandler.INSTANCE.sendTo(new ThutMessage(player), (EntityPlayerMP) player);
    }
}
