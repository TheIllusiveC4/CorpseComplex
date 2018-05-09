/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex;

import c4.corpsecomplex.proxy.CommonProxy;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = CorpseComplex.MODID,
        name = CorpseComplex.MODNAME,
        version = CorpseComplex.MODVER,
        dependencies = "required-after:forge@[14.21.1.2387,);" +
                "after:toughasnails;after:wearablebackpacks;after:powerinventory;" +
                "after:thut_wearables;after:baubles;after:rpginventory;after:cosmeticarmorreworked;" +
                "after:tombstone;after:advinv",
        guiFactory = "c4."+ CorpseComplex.MODID+".client.gui.GuiFactory",
        acceptedMinecraftVersions = "[1.12, 1.13)",
        certificateFingerprint = "5d5b8aee896a4f5ea3f3114784742662a67ad32f")

public class CorpseComplex {

    public static final String MODID = "corpsecomplex";
    public static final String MODNAME = "Corpse Complex";
    public static final String MODVER = "1.0.2.2";

    @SidedProxy(clientSide = "c4.corpsecomplex.proxy.ClientProxy", serverSide = "c4.corpsecomplex.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static CorpseComplex instance;
    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {

        logger = e.getModLog();
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void onFingerPrintViolation(FMLFingerprintViolationEvent evt) {
        FMLLog.log.log(Level.ERROR, "Invalid fingerprint detected! The file " + evt.getSource().getName() + " may have been tampered with. This version will NOT be supported by the author!");
    }
}
