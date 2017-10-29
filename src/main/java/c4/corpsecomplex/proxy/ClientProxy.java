/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.proxy;

import c4.corpsecomplex.common.modules.spawning.SpawningModule;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent e) {

        super.preInit(e);
    }

    @Override
    public void init(FMLInitializationEvent e) {

        super.init(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent e) {
        if (SpawningModule.registerScroll) {
            SpawningModule.scroll.initModel();
        }
    }
}
