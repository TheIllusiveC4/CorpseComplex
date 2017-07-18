package c4.corpserun.proxy;

import c4.corpserun.CorpseRun;
import c4.corpserun.capability.DeathInventory;
import c4.corpserun.capability.DeathStorage;
import c4.corpserun.config.ConfigHandler;
import c4.corpserun.core.EventHandler;
import c4.corpserun.capability.CapabilityHandler;
import c4.corpserun.capability.IDeathInventory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy {

    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {

        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(),"corpserun.cfg"));
        ConfigHandler.readConfig();
    }

    public void init(FMLInitializationEvent e) {

        MinecraftForge.EVENT_BUS.register(new EventHandler());
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        CapabilityManager.INSTANCE.register(IDeathInventory.class, new DeathStorage(), DeathInventory.class);
    }

    public void postInit(FMLPostInitializationEvent e) {

        if (config.hasChanged()){ config.save();}
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

    }
}
