/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex;

import c4.corpsecomplex.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = CorpseComplex.MODID,
        name = CorpseComplex.MODNAME,
        version = CorpseComplex.MODVER,
        dependencies = "required-after:Forge@[12.18.3.2185,);after:ToughAsNails;after:wearablebackpacks;after:powerinventory;after:thut_wearables;after:Baubles;after:rpginventory;after:cosmeticarmorreworked;after:tombstone;after:advInv",
        useMetadata = true,
        guiFactory = "c4."+ CorpseComplex.MODID+".client.gui.GuiFactory",
        acceptedMinecraftVersions = "[1.10.2]")

public class CorpseComplex {

    public static final String MODID = "corpsecomplex";
    public static final String MODNAME = "Corpse Complex";
    public static final String MODVER = "1.0.0-bp1";

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

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.stackSize < 1 || stack.getItem() == Item.getItemFromBlock(Blocks.AIR);
    }
}
