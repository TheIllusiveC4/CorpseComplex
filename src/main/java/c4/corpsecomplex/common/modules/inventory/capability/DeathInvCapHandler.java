/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public
 * License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl
 * .text
 */

package c4.corpsecomplex.common.modules.inventory.capability;

import c4.corpsecomplex.CorpseComplex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeathInvCapHandler {

  private static final ResourceLocation DEATH_INV_CAP = new ResourceLocation(
          CorpseComplex.MODID, "deathInventory");

  @SubscribeEvent
  public void attachCapability(AttachCapabilitiesEvent<Entity> e) {

    if (!((e.getObject()) instanceof EntityPlayer)) {
      return;
    }

    e.addCapability(DEATH_INV_CAP, new DeathInventory.Provider());
  }
}
