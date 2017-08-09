package c4.corpsecomplex;

import c4.corpsecomplex.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = CorpseComplex.MODID,
        name = CorpseComplex.MODNAME,
        version = CorpseComplex.MODVER,
        dependencies = "required-after:forge@[14.21.1.2387,)",
        useMetadata = true,
        guiFactory = "c4."+ CorpseComplex.MODID+".config.GuiFactory",
        acceptableRemoteVersions = "*")

public class CorpseComplex {

    public static final String MODID = "corpsecomplex";
    public static final String MODNAME = "Corpse Complex";
    public static final String MODVER = "0.8b";

    @SidedProxy(clientSide = "c4.corpsecomplex.proxy.ClientProxy", serverSide = "c4.corpsecomplex.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static CorpseComplex instance;
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
