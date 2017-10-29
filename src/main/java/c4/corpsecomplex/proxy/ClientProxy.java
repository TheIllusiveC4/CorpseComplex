/*
 * Copyright (c) 2017. C4, MIT License
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
