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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
    buf.writeInt(thirst);
    buf.writeInt(temp);
  }

  @Override
  public void fromBytes(ByteBuf buf) {
    thirst = buf.readInt();
    temp = buf.readInt();
  }

  public static class TANMessageHandler
          implements IMessageHandler<TANMessage, IMessage> {

    @Override
    @SideOnly(Side.CLIENT)
    @Optional.Method(modid = MOD_ID)
    public IMessage onMessage(TANMessage message, MessageContext ctx) {
      IThreadListener mainThread = Minecraft.getMinecraft();
      mainThread.addScheduledTask(() -> {
        EntityPlayerSP playerSP = Minecraft.getMinecraft().player;
        ThirstHelper.getThirstData(playerSP).setThirst(message.thirst);
        TemperatureHelper.getTemperatureData(playerSP).setTemperature(
                new Temperature(message.temp));
      });
      // No response packet
      return null;
    }
  }
}
