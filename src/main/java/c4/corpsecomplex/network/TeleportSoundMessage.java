/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class TeleportSoundMessage implements IMessage {

  private static Random rand = new Random();

  public TeleportSoundMessage() {
  }

  @Override
  public void toBytes(ByteBuf buf) {

  }

  @Override
  public void fromBytes(ByteBuf buf) {

  }

  public static class TeleportSoundMessageHandler
          implements IMessageHandler<TeleportSoundMessage, IMessage> {

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(TeleportSoundMessage message,
            MessageContext ctx) {
      IThreadListener mainThread = Minecraft.getMinecraft();
      mainThread.addScheduledTask(() -> {
        Minecraft mc = Minecraft.getMinecraft();
        mc.ingameGUI.getBossOverlay().clearBossInfos();
        mc.getSoundHandler().playSound(PositionedSoundRecord
                .getMasterRecord(SoundEvents.BLOCK_PORTAL_TRAVEL,
                        rand.nextFloat() * 0.4F + 0.8F));
      });
      // No response packet
      return null;
    }
  }
}
