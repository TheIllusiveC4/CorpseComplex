package c4.corpsecomplex.common;

import c4.corpsecomplex.CorpseComplex;
import c4.corpsecomplex.common.helpers.ConfigHelper;
import c4.corpsecomplex.common.helpers.ModuleHelper;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.fml.common.Loader;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Module {

    public abstract void loadModuleConfig();
    public abstract void initPropOrder();
    public abstract void setEnabled();

    public boolean enabled;
    public boolean prevEnabled;

    protected ConfigCategory configCategory;
    protected List<String> propOrder;
    protected ArrayList<Class<? extends Submodule>> submoduleClasses;

    private Map<Class<? extends Submodule>, Submodule> subInstances = new HashMap<>();

    public Module() {
        prevEnabled = false;
    }

    public Module(String category, String description) {
        configCategory = new ConfigCategory(category);
        configCategory.setComment(description);
        prevEnabled = false;
    }

    public void setPropOrder() {
        initPropOrder();
        if (propOrder != null) {
            ModuleHelper.cfg.getCategory(configCategory.getQualifiedName()).setPropertyOrder(propOrder);
        }
        forEachSubmodule(Submodule::setPropOrder);
    }

    public boolean hasEvents() {
        return true;
    }

    public void loadSubmodules() {

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

    public void forEachSubmodule(Consumer<Submodule> submodule) {
        subInstances.values().forEach(submodule);
    }

    protected void setCategoryComment() {
        ModuleHelper.cfg.addCustomCategoryComment(configCategory.getQualifiedName(), configCategory.getComment());
    }

    protected int getInt(String name, int defaultInt, int min, int max, String comment) {
        return ConfigHelper.getInt(name, configCategory.getQualifiedName(), defaultInt, min, max, comment);
    }

    protected float getFloat(String name, float defaultFloat, float min, float max, String comment) {
        return ConfigHelper.getFloat(name, configCategory.getQualifiedName(), defaultFloat, min, max, comment);
    }

    protected boolean getBool(String name, boolean defaultBool, String comment) {
        return ConfigHelper.getBool(name, configCategory.getQualifiedName(), defaultBool, comment);
    }

    protected String getString(String name, String defaultString, String comment, String[] validValues) {
        return ConfigHelper.getString(name, configCategory.getQualifiedName(), defaultString, comment, validValues);
    }

    protected String[] getStringList(String name, String[] defaultStringList, String comment) {
        return ConfigHelper.getStringList(name, configCategory.getQualifiedName(), defaultStringList, comment);
    }
}
