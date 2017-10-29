/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class TeleportEffectMessage implements IMessage {

    private static Random rand = new Random();
    private BlockPos pos;

    public TeleportEffectMessage() {
    }

    public TeleportEffectMessage(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(pos.toLong());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = BlockPos.fromLong(buf.readLong());
    }

    public static class TeleportEffectMessageHandler implements IMessageHandler<TeleportEffectMessage, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(TeleportEffectMessage message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(() -> {
                EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
                for (int x = 0; x < 128; x++) {
                    float f = (rand.nextFloat() - 0.5F) * 0.2F;
                    float f1 = (rand.nextFloat() - 0.5F) * 0.2F;
                    float f2 = (rand.nextFloat() - 0.5F) * 0.2F;
                    double d3 = message.pos.getX() + (rand.nextDouble() - 0.5D) * playerSP.width * 2.0D;
                    double d4 = message.pos.getY() + rand.nextDouble() * playerSP.height;
                    double d5 = message.pos.getZ() + (rand.nextDouble() - 0.5D) * playerSP.width * 2.0D;
                    playerSP.world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, (double)f, (double)f1, (double)f2);
                }
            });
            // No response packet
            return null;
        }
    }
}
