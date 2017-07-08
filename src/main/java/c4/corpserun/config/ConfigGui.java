package c4.corpserun.config;

import c4.corpserun.CorpseRun;
import c4.corpserun.config.values.ConfigCategories;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class ConfigGui extends GuiConfig{

    public ConfigGui(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), CorpseRun.MODID, false, false, CorpseRun.MODNAME);
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        for (ConfigCategories categories : ConfigCategories.values()) {
            list.add(new ConfigElement(ConfigHandler.cfg.getCategory(categories.name)));
        }

        return list;
    }
}
