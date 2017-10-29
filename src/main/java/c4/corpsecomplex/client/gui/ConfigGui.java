/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.client.gui;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.helpers.ModuleHelper;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig{

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), CorpseComplex.MODID, false, false, CorpseComplex.MODNAME);
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        list.add(new ConfigElement(ModuleHelper.cfg.getCategory("Inventory")).listCategoriesFirst(false));
        list.add(new ConfigElement(ModuleHelper.cfg.getCategory("Experience")));
        list.add(new ConfigElement(ModuleHelper.cfg.getCategory("Effects")).listCategoriesFirst(false));
        list.add(new ConfigElement(ModuleHelper.cfg.getCategory("Hunger")));
        list.add(new ConfigElement(ModuleHelper.cfg.getCategory("Respawning")));

        if (Loader.isModLoaded("toughasnails")) {
            list.add(new ConfigElement(ModuleHelper.cfg.getCategory("Tough as Nails")));
        }

        return list;
    }
}
