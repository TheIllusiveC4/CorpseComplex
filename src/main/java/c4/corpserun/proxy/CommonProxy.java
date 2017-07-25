package c4.corpserun.proxy;

import c4.corpserun.capability.DeathInventory;
import c4.corpserun.capability.CapabilityHandler;
import c4.corpserun.capability.IDeathInventory;
import c4.corpserun.core.modules.ModuleHandler;
import c4.corpserun.network.NetworkHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {

        ModuleHandler.preInit(e);
    }

    public void init(FMLInitializationEvent e) {

        ModuleHandler.init();
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        CapabilityManager.INSTANCE.register(IDeathInventory.class, new DeathInventory.Storage(), DeathInventory.class);
    }
}
