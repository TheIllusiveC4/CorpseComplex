package c4.corpsecomplex.core.modules;

import java.util.function.Consumer;

public abstract class Submodule extends Module {

    private Module parentModule;

    public Submodule(Module parentModule) {
        super();
        this.parentModule = parentModule;
        configCategory = parentModule.configCategory;
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
    public void initPropOrder() {
        //NO-OP
    }

    @Override
    void setPropOrder() {
        //NO-OP
    }

    @Override
    void loadSubmodules() {
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
