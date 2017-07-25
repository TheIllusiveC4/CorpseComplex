package c4.corpserun;

import c4.corpserun.debug.DebugCommand;
import c4.corpserun.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.Logger;

@Mod(modid = CorpseRun.MODID, name = CorpseRun.MODNAME, version = CorpseRun.MODVER,
        dependencies = "required-after:forge@[14.21.1.2387,)", useMetadata = true,
        guiFactory = "c4."+CorpseRun.MODID+".config.GuiFactory", acceptableRemoteVersions = "*")

public class CorpseRun {

    public static final String MODID = "corpserun";
    public static final String MODNAME = "Corpse Run";
    public static final String MODVER = "0.5b";

    @SidedProxy(clientSide = "c4.corpserun.proxy.ClientProxy", serverSide = "c4.corpserun.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static CorpseRun instance;
    public static Logger logger;

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent e) {

        logger = e.getModLog();
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

//    @Mod.EventHandler
//    public void serverLoad(FMLServerStartingEvent event) {
//        event.registerServerCommand(new DebugCommand());
//    }

}
