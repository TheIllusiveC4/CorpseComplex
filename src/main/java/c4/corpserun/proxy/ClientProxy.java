package c4.corpserun.proxy;

import c4.corpserun.network.PacketHandler;
import c4.corpserun.network.TANMessage;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
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
        PacketHandler.INSTANCE.registerMessage(TANMessage.TANMessageHandler.class, TANMessage.class, 0, Side.CLIENT);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

    }

}
