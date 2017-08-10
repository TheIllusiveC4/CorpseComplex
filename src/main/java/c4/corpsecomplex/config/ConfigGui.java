package c4.corpsecomplex.config;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.core.modules.ModuleHandler;
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

        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Inventory")));
        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Experience")));
        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Effects")).listCategoriesFirst(false));
        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Hunger")));

        if (Loader.isModLoaded("toughasnails")) {
            list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Tough as Nails")));
        }

        return list;
    }
}
