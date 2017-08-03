package c4.corpsecomplex.core.modules;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.config.ConfigHelper;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Module {

    protected ConfigCategory configCategory;
    protected String configDescription;
    protected boolean enabled;
    protected boolean prevEnabled;
    protected List<String> propOrder;
    protected ArrayList<Class<? extends Submodule>> submoduleClasses;
    private Map<Class<? extends Submodule>, Submodule> subInstances = new HashMap<>();

    public Module() {
        prevEnabled = false;
    }

    public Module(String category, String description) {
        configCategory = new ConfigCategory(category);
        configDescription = description;
        configCategory.setComment(configDescription);
        prevEnabled = false;
    }

    void setPropOrder() {
        initPropOrder();
        ModuleHandler.cfg.getCategory(configCategory.getName()).setPropertyOrder(propOrder);
    }

    boolean hasEvents() {
        return true;
    }

    abstract public void loadModuleConfig();
    abstract public void initPropOrder();
    abstract public void setEnabled();

    void loadSubmodules() {

        if (submoduleClasses == null || submoduleClasses.isEmpty()) { return; }

        submoduleClasses.forEach(submodule -> {
            try {
                subInstances.put(submodule, submodule.getDeclaredConstructor(Module.class).newInstance(this));
            } catch (Exception e1) {
                CorpseComplex.logger.log(Level.ERROR, "Failed to initialize submodule " + submodule, e1);
            }
        });
    }

    protected void addSubmodule(Class<? extends Submodule> submodule) {
        if (!submoduleClasses.contains(submodule)) {
            submoduleClasses.add(submodule);
        }
    }

    protected void addSubmodule(String modid, Class<? extends Submodule> submodule) {
        if (Loader.isModLoaded(modid) && !submoduleClasses.contains(submodule)) {
            submoduleClasses.add(submodule);
        }
    }

    protected void forEachSubmodule(Consumer<Submodule> submodule) {
        subInstances.values().forEach(submodule);
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
