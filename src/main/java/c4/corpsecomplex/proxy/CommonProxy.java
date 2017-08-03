package c4.corpserun.proxy;

import c4.corpserun.CorpseRun;
import c4.corpserun.capability.DeathInventory;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.ModuleHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    private static final ResourceLocation DEATH_INV_CAP = new ResourceLocation(CorpseRun.MODID,"deathInventory");

    public void preInit(FMLPreInitializationEvent e) {

        ModuleHandler.preInit(e);
    }

    public void init(FMLInitializationEvent e) {

        ModuleHandler.init();
        CapabilityManager.INSTANCE.register(IDeathInventory.class, new DeathInventory.Storage(), DeathInventory.class);
    }

    public void attachCapability(AttachCapabilitiesEvent e) {

        if (!((e.getObject()) instanceof EntityPlayer)) { return;}

        e.addCapability(DEATH_INV_CAP, new DeathInventory.Provider());
    }
}
