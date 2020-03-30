package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryModule;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting.SectionSettings;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.InventoryHelper;

public class VanillaInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity player = deathStorage.getPlayer();

    if (player != null) {
      PlayerInventory inventory = player.inventory;
      ListNBT list = new ListNBT();

      for (int i = 0; i < 9; i++) {
        take(deathStorage, inventory, i, list,
            i == inventory.currentItem ? InventorySection.MAINHAND : InventorySection.HOTBAR);
      }

      for (int i = 9; i < 36; i++) {
        take(deathStorage, inventory, i, list, InventorySection.MAIN);
      }
      take(deathStorage, inventory, 36, list, InventorySection.FEET);
      take(deathStorage, inventory, 37, list, InventorySection.LEGS);
      take(deathStorage, inventory, 38, list, InventorySection.CHEST);
      take(deathStorage, inventory, 39, list, InventorySection.HEAD);
      take(deathStorage, inventory, 40, list, InventorySection.OFFHAND);
      deathStorage.addInventory("vanilla", list);
    }
  }

  private static void take(IDeathStorage deathStorage, PlayerInventory inventory, int index,
      ListNBT list, InventorySection section) {
    InventorySetting setting = deathStorage.getSettings().getInventorySettings();
    take(inventory, index, list, section, setting);
  }

  private static void take(PlayerInventory inventory, int index, ListNBT list,
      InventorySection section, InventorySetting setting) {
    ItemStack stack = inventory.getStackInSlot(index);
    DropMode inventoryRule = setting.getItems().get(stack.getItem());
    SectionSettings sectionSettings = setting.getInventorySettings().get(section);

    if (inventoryRule == null) {
      if (sectionSettings.keepChance > InventoryModule.RANDOM.nextFloat()) {
        inventoryRule = DropMode.KEEP;
      } else if (sectionSettings.destroyChance > InventoryModule.RANDOM.nextFloat()) {
        inventoryRule = DropMode.DESTROY;
      } else {
        inventoryRule = DropMode.DROP;
      }
    }

    if (inventoryRule == DropMode.KEEP) {
      InventoryHelper.applyDurabilityLoss(inventory.player, stack, setting,
          sectionSettings.keepDurabilityLoss);
      ItemStack keep = stack.split(sectionSettings.keepChance >= 1 ? stack.getCount()
          : InventoryHelper.getRandomAmount(stack.getCount(), sectionSettings.keepChance));
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putInt("Slot", index);
      keep.write(compoundnbt);
      list.add(compoundnbt);
    } else if (inventoryRule == DropMode.DESTROY) {
      inventory.setInventorySlotContents(index, ItemStack.EMPTY);
    } else {
      InventoryHelper.applyDurabilityLoss(inventory.player, stack, setting,
          sectionSettings.dropDurabilityLoss);
      stack
          .shrink(InventoryHelper.getRandomAmount(stack.getCount(), sectionSettings.destroyChance));
    }
  }

  @Override
  public void retrieveInventory(IDeathStorage newStorage, IDeathStorage oldStorage) {
    PlayerEntity player = newStorage.getPlayer();
    PlayerEntity oldPlayer = oldStorage.getPlayer();

    if (player != null && oldPlayer != null) {
      ListNBT list = (ListNBT) oldStorage.getInventory("vanilla");
      PlayerInventory inventory = player.inventory;

      for (int i = 0; i < list.size(); ++i) {
        CompoundNBT compoundnbt = list.getCompound(i);
        int slot = compoundnbt.getInt("Slot");
        ItemStack itemstack = ItemStack.read(compoundnbt);
        if (!itemstack.isEmpty()) {
          ItemStack existing = inventory.getStackInSlot(slot);

          if (existing.isEmpty()) {
            inventory.setInventorySlotContents(slot, itemstack);
          } else {
            ItemHandlerHelper.giveItemToPlayer(inventory.player, itemstack);
          }
        }
      }
    }
  }
}
