package c4.corpserun;

import c4.corpserun.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = CorpseRun.MODID, name = CorpseRun.MODNAME, version = CorpseRun.MODVER,
        dependencies = "required-after:forge@[14.21.1.2387,)", useMetadata = true,
        guiFactory = "c4."+CorpseRun.MODID+".config.GuiFactory", acceptableRemoteVersions = "*")

public class CorpseRun {

    public static final String MODID = "corpserun";
    public static final String MODNAME = "Corpse Run";
    public static final String MODVER = "0.1a";

    @SidedProxy(clientSide = "c4.corpserun.proxy.ClientProxy", serverSide = "c4.corpserun.proxy.ServerProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static CorpseRun instance;
    public static Logger logger;

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event) {

        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

}
