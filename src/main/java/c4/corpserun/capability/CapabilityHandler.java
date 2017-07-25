package c4.corpserun.capability;

import c4.corpserun.CorpseRun;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler {

    private static final ResourceLocation DEATH_INV_CAP = new ResourceLocation(CorpseRun.MODID,"deathInventory");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent e) {

        if (!((e.getObject()) instanceof EntityPlayer)) { return;}

        e.addCapability(DEATH_INV_CAP, new DeathInventory.Provider());
    }
}
