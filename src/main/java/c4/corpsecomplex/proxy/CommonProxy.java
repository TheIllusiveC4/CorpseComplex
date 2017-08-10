package c4.corpsecomplex.proxy;

import c4.corpsecomplex.capability.CapabilityHandler;
import c4.corpsecomplex.capability.DeathInventory;
import c4.corpsecomplex.capability.IDeathInventory;
import c4.corpsecomplex.core.modules.ModuleHandler;
import c4.corpsecomplex.core.modules.MoriModule;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

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

    @SubscribeEvent
    public static void registerPotions(RegistryEvent.Register<Potion> e) {
        e.getRegistry().register(MoriModule.moriPotion);
    }
}
