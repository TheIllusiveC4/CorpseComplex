package c4.corpserun.core.modules;

import c4.corpserun.config.ConfigHelper;
import net.minecraftforge.common.config.ConfigCategory;

public abstract class Submodule extends Module {

    protected String configParent;

    abstract public void loadModuleConfig();
    abstract public void setEnabled();

    @Override
    public void initPropOrder() {
        //NO-OP
    }

    @Override
    void setPropOrder() {
        //NO-OP
    }

    @Override
    public boolean hasEvents() {
        return true;
    }

    private ConfigCategory getConfigParent() {
        return ModuleHandler.cfg.getCategory(configParent);
    }

    @Override
    protected int getInt(String name, int defaultInt, int min, int max, String comment) {
        return ConfigHelper.getInt(name, getConfigParent().getName(), defaultInt, min, max, comment);
    }

    @Override
    protected double getFloat(String name, float defaultFloat, float min, float max, String comment) {
        return ConfigHelper.getFloat(name, getConfigParent().getName(), defaultFloat, min, max, comment);
    }

    @Override
    protected boolean getBool(String name, boolean defaultBool, String comment) {
        return ConfigHelper.getBool(name, getConfigParent().getName(), defaultBool, comment);
    }

    @Override
    protected String[] getStringList(String name, String[] defaultStringList, String comment) {
        return ConfigHelper.getStringList(name, getConfigParent().getName(), defaultStringList, comment);
    }

}
