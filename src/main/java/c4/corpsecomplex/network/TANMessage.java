/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import toughasnails.api.temperature.Temperature;
import toughasnails.api.temperature.TemperatureHelper;
import toughasnails.api.thirst.ThirstHelper;

public class TANMessage implements IMessage {

    private int thirst;
    private int temp;
    private static final String MOD_ID = "toughasnails";

    public TANMessage() {
    }

    public TANMessage(int thirst, int temp) {
        this.thirst = thirst;
        this.temp = temp;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        // Writes the int into the buf
        buf.writeInt(thirst);
        buf.writeInt(temp);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        // Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
        thirst = buf.readInt();
        temp = buf.readInt();
    }

    public static class TANMessageHandler implements IMessageHandler<TANMessage, IMessage> {

        @Override
        @Optional.Method(modid = MOD_ID)
        public IMessage onMessage(TANMessage message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(() -> {
                // This is the player the packet was sent to the server from
                EntityPlayer player = Minecraft.getMinecraft().player;
                // The value that was sent
                ThirstHelper.getThirstData(player).setThirst(message.thirst);
                TemperatureHelper.getTemperatureData(player).setTemperature(new Temperature(message.temp));
            });
            // No response packet
            return null;
        }
    }
}
