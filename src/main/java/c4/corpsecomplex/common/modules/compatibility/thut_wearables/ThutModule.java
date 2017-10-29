/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.thut_wearables;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thut.wearables.ThutWearables;
import thut.wearables.network.PacketSyncWearables;

public class ThutModule extends Submodule {

    private final static String MOD_ID = "thut_wearables";

    static boolean keepThut;

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        updateClientThut(e.player);
    }

    public ThutModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepThut = getBool("Keep Thut Wearables", false, "Set to true to keep Thut Wearables on death", false);
    }

    @Override
    public boolean hasEvents() {
        return true;
    }

    @Optional.Method(modid = MOD_ID)
    private static void updateClientThut (EntityPlayer player) {

        ThutWearables.packetPipeline.sendTo(new PacketSyncWearables(player), (EntityPlayerMP) player);
    }
}
