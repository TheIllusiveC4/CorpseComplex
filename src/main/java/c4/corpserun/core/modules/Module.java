package c4.corpserun.core.modules;

import c4.corpserun.config.ConfigHelper;
import net.minecraftforge.common.config.ConfigCategory;

import java.util.List;

public abstract class Module {

    protected String configName;
    public ConfigCategory configCategory;
    protected String configDescription;
    protected boolean enabled;
    protected boolean prevEnabled;
    protected List<String> propOrder;

    abstract public void loadModuleConfig();
    abstract public void initPropOrder();
    abstract public void setEnabled();

    void setPropOrder() {
        initPropOrder();
        ModuleHandler.cfg.getCategory(configCategory.getName()).setPropertyOrder(propOrder);
    }

    boolean hasEvents() {
        return true;
    }

    protected void setCategoryComment() {
        ModuleHandler.cfg.addCustomCategoryComment(configCategory.getName(), configCategory.getComment());
    }

    protected int getInt(String name, int defaultInt, int min, int max, String comment) {
        return ConfigHelper.getInt(name, configCategory.getName(), defaultInt, min, max, comment);
    }

    protected double getFloat(String name, float defaultFloat, float min, float max, String comment) {
        return ConfigHelper.getFloat(name, configCategory.getName(), defaultFloat, min, max, comment);
    }

    protected boolean getBool(String name, boolean defaultBool, String comment) {
        return ConfigHelper.getBool(name, configCategory.getName(), defaultBool, comment);
    }

    protected String[] getStringList(String name, String[] defaultStringList, String comment) {
        return ConfigHelper.getStringList(name, configCategory.getName(), defaultStringList, comment);
    }
}
