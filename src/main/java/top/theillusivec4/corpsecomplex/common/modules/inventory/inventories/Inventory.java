package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories;

import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;

public interface Inventory {

  void storeInventory(IDeathStorage deathStorage);

  void retrieveInventory(IDeathStorage newStorage, IDeathStorage oldStorage);
}
