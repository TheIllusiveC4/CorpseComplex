package c4.corpserun.config;

import c4.corpserun.CorpseRun;
import c4.corpserun.core.modules.ModuleHandler;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig{

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), CorpseRun.MODID, false, false, CorpseRun.MODNAME);
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Inventory")));
        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Experience")));
        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Effects")));
        list.add(new ConfigElement(ModuleHandler.cfg.getCategory("Hunger")));

        return list;
    }
}
