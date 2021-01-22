package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration;

import gigaherz.toolbelt.BeltFinder;
import gigaherz.toolbelt.BeltFinderBeltSlot;
import gigaherz.toolbelt.ConfigData;
import java.util.Random;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.Inventory;
import top.theillusivec4.corpsecomplex.common.util.Enums;
import top.theillusivec4.corpsecomplex.common.util.InventoryHelper;

public class ToolBeltInventory implements Inventory {

  @Override
  public void storeInventory(DeathStorageCapability.IDeathStorage deathStorage) {
    PlayerEntity playerEntity = deathStorage.getPlayer();

    if (ConfigData.customBeltSlotEnabled) {
      ItemStack belt = BeltFinder.findBelt(playerEntity).map(BeltFinder.BeltGetter::getBelt)
          .orElse(ItemStack.EMPTY);

      if (!belt.isEmpty()) {
        InventorySetting setting = deathStorage.getSettings().getInventorySettings();
        Enums.DropMode inventoryRule = InventoryHelper.getDropModeOverride(belt, setting);
        InventorySetting.SectionSettings defaultSettings =
            setting.getInventorySettings().get(Enums.InventorySection.DEFAULT);
        InventorySetting.SectionSettings sectionSettings = setting.getInventorySettings().get(
            Enums.InventorySection.TOOL_BELT);
        double keepChance =
            sectionSettings.keepChance >= 0 ? sectionSettings.keepChance :
                defaultSettings.keepChance;
        double destroyChance = sectionSettings.destroyChance >= 0 ? sectionSettings.destroyChance
            : defaultSettings.destroyChance;

        if (inventoryRule != null) {
          if (inventoryRule == Enums.DropMode.KEEP) {
            keepChance = 1.0D;
          } else if (inventoryRule == Enums.DropMode.DESTROY) {
            destroyChance = 1.0D;
          } else if (inventoryRule == Enums.DropMode.DROP) {
            keepChance = 0.0D;
          }
        }
        Random random = new Random();

        if (random.nextDouble() < keepChance) {
          deathStorage.addInventory("tool_belt", belt.serializeNBT());
          belt.shrink(1);
        } else if (random.nextDouble() < destroyChance) {
          belt.shrink(1);
        }
      }
    }
  }

  @Override
  public void retrieveInventory(DeathStorageCapability.IDeathStorage newStorage,
                                DeathStorageCapability.IDeathStorage oldStorage) {
    PlayerEntity player = newStorage.getPlayer();
    PlayerEntity oldPlayer = oldStorage.getPlayer();

    if (player != null && oldPlayer != null) {
      CompoundNBT tag = (CompoundNBT) oldStorage.getInventory("tool_belt");

      if (tag != null) {
        ItemStack stack = ItemStack.read(tag);
        BeltFinder.setFinderSlotContents(player, BeltFinderBeltSlot.FINDER_ID, 0, stack);
      }
    }
  }
}
