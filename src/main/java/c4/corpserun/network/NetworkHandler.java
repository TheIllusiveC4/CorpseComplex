package c4.corpserun.network;

import c4.corpserun.CorpseRun;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CorpseRun.MODID);

    private static int id = 0;

    public static void init() {
        NetworkHandler.registerMessage(TANMessage.TANMessageHandler.class, TANMessage.class, Side.CLIENT);
    }

    private static void registerMessage(Class messageHandler, Class requestMessageType, Side side) {
        INSTANCE.registerMessage(messageHandler, requestMessageType, id++, side);
    }
}
