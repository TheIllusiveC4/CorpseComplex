package c4.corpsecomplex.core.modules;

import net.minecraftforge.common.config.ConfigCategory;

import java.util.function.Consumer;

public abstract class Submodule extends Module {

    protected Module parentModule;

    public Submodule(Module parentModule, String childCategory) {
        super();
        this.parentModule = parentModule;
        if (childCategory != null) {
            configCategory = new ConfigCategory(childCategory, parentModule.configCategory);
        } else {
            configCategory = parentModule.configCategory;
        }
    }

    public void setEnabled() {
        enabled = parentModule.enabled;
    }

    abstract public void loadModuleConfig();

    @Override
    public boolean hasEvents() {
        return false;
    }

    @Override
    void loadSubmodules() {
        //NO-OP
    }

    @Override
    public void initPropOrder() {
        //NO-OP
    }

    @Override
    protected void addSubmodule(Class<? extends Submodule> submodule) {
        //NO-OP
    }

    @Override
    protected void addSubmodule(String modid, Class<? extends Submodule> submodule) {
        //NO-OP
    }

    @Override
    protected void forEachSubmodule(Consumer<Submodule> submodule) {
        //NO-OP
    }
}
