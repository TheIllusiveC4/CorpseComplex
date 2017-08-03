package c4.corpsecomplex.capability;

import c4.corpsecomplex.CorpseComplex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

    private static final ResourceLocation DEATH_INV_CAP = new ResourceLocation(CorpseComplex.MODID,"deathInventory");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> e) {

        if (!((e.getObject()) instanceof EntityPlayer)) { return;}

        e.addCapability(DEATH_INV_CAP, new DeathInventory.Provider());
    }
}
