package c4.corpsecomplex.common.modules.compatibility.thebetweenlands;

import c4.corpsecomplex.common.Module;
import c4.corpsecomplex.common.Submodule;

public class BetweenlandsModule extends Submodule {

  static boolean keepBetweenlands;

  public BetweenlandsModule(Module parentModule) {
    super(parentModule, null);
  }

  public void loadModuleConfig() {
    keepBetweenlands = getBool("Keep Betweenlands", false,
        "Set to true to keep Betweenlands inventory on death", false);
  }
}
