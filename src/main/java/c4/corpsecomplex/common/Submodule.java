/*
 * Copyright (c) 2017. <C4>
 *
 * This Java class is distributed as a part of Corpse Complex.
 * Corpse Complex is open source and licensed under the GNU General Public License v3.
 * A copy of the license can be found here: https://www.gnu.org/licenses/gpl.text
 */

package c4.corpsecomplex.common;

import net.minecraftforge.common.config.ConfigCategory;

import java.util.function.Consumer;

public abstract class Submodule extends Module {

    public abstract void loadModuleConfig();

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

    @Override
    public boolean hasEvents() {
        return false;
    }

    @Override
    public void loadSubmodules() {
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
    public void forEachSubmodule(Consumer<Submodule> submodule) {
        //NO-OP
    }
}
