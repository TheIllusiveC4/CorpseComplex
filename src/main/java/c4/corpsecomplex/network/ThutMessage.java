package c4.corpsecomplex.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import thut.wearables.ThutWearables;
import thut.wearables.inventory.PlayerWearables;

import java.io.IOException;

public class ThutMessage implements IMessage {

    private static final String MOD_ID = "thut_wearables";
    NBTTagCompound data;

    public ThutMessage(){
        this.data = new NBTTagCompound();
    }

    public ThutMessage(EntityPlayer player) {
        this();
        this.data.setInteger("id", player.getEntityId());
        PlayerWearables playerWearables = ThutWearables.getWearables(player);
        playerWearables.writeToNBT(this.data);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        (new PacketBuffer(buf)).writeCompoundTag(this.data);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        try {
            this.data = (new PacketBuffer(buf)).readCompoundTag();
        } catch (IOException var3) {
            var3.printStackTrace();
        }
    }

    public static class ThutMessageHandler implements IMessageHandler<ThutMessage, IMessage> {

        @Override
        @Optional.Method(modid = MOD_ID)
        public IMessage onMessage(ThutMessage message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                PlayerWearables playerWearables = ThutWearables.getWearables(player);
                playerWearables.readFromNBT(message.data);
            });
            // No response packet
            return null;
        }
    }
}
