/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common.modules.compatibility.rpginventory;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import subaraki.rpginventory.network.PacketHandler;
import subaraki.rpginventory.network.PacketInventoryToClient;

public class RPGModule extends Submodule {

    private final static String MOD_ID = "rpginventory";

    static boolean keepRPG;

    @SubscribeEvent
    @Optional.Method(modid = MOD_ID)
    public void onPlayerRespawnFinish(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {

        updateClientRPG(e.player);
    }

    public RPGModule(Module parentModule) {
        super(parentModule, null);
    }

    public void loadModuleConfig() {
        keepRPG = getBool("Keep RPG Inventory", false, "Set to true to keep RPG Inventory on death", false);
    }

    @Override
    public boolean hasEvents() {
        return true;
    }

    @Optional.Method(modid = MOD_ID)
    private static void updateClientRPG (EntityPlayer player) {

        PacketHandler.NETWORK.sendTo(new PacketInventoryToClient(player), (EntityPlayerMP) player);
    }
}
