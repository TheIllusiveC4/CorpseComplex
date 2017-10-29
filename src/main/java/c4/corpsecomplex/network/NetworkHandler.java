/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.network;

import c4.corpsecomplex.CorpseComplex;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler  {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CorpseComplex.MODID);

    private static int id = 0;

    public static void init() {
        NetworkHandler.registerMessage(TeleportSoundMessage.TeleportSoundMessageHandler.class, TeleportSoundMessage.class, Side.CLIENT);
        NetworkHandler.registerMessage(TeleportEffectMessage.TeleportEffectMessageHandler.class, TeleportEffectMessage.class, Side.CLIENT);
        NetworkHandler.registerMessage(TANMessage.TANMessageHandler.class, TANMessage.class, Side.CLIENT);
    }

    private static void registerMessage(Class messageHandler, Class requestMessageType, Side side) {
        INSTANCE.registerMessage(messageHandler, requestMessageType, id++, side);
    }
}
