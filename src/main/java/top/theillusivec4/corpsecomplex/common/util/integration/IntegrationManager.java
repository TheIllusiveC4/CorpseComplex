package top.theillusivec4.corpsecomplex.common.util.integration;

import net.minecraftforge.fml.ModList;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryModule;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration.CosmeticArmorInventory;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration.CuriosInventory;
import top.theillusivec4.corpsecomplex.common.util.DeathConditionManager;

public class IntegrationManager {

  public static void init() {
    if (ModList.get().isLoaded("gamestages")) {
      DeathConditionManager.CONDITION_ADDONS.add(GameStagesIntegration.HAS_STAGE);
    }
    if (ModList.get().isLoaded("curios")) {
      InventoryModule.STORAGE.add(new CuriosInventory());
    }

    if (ModList.get().isLoaded("cosmeticarmorreworked")) {
      InventoryModule.STORAGE.add(new CosmeticArmorInventory());
    }
  }
}
