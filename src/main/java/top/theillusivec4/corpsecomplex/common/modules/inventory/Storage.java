package top.theillusivec4.corpsecomplex.common.modules.inventory;

import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;

public interface Storage {

  void storeInventory(IDeathStorage deathStorage);

  void retrieveInventory(IDeathStorage newStorage, IDeathStorage oldStorage);
}
