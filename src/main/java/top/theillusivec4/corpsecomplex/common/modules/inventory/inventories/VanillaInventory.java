package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories;

import java.util.function.Supplier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig.InventorySection;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryModule;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryUtils;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryUtils.InventoryRule;

public class VanillaInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity player = deathStorage.getPlayer();

    if (player != null) {
      PlayerInventory inventory = player.inventory;
      ListNBT list = new ListNBT();

      for (int i = 0; i < 9; i++) {
        int index = i;
        take(inventory, i, list, () -> {
          boolean store;

          if (index == inventory.currentItem) {
            store = InventoryModule.KEEP_SECTIONS.contains(InventorySection.MAINHAND);
          } else {
            store = InventoryModule.KEEP_SECTIONS.contains(InventorySection.HOTBAR);
          }
          return store ? InventoryRule.KEEP : InventoryRule.DROP;
        });
      }

      for (int i = 9; i < 36; i++) {
        take(inventory, i, list, InventorySection.MAIN);
      }
      take(inventory, 36, list, InventorySection.FEET);
      take(inventory, 37, list, InventorySection.LEGS);
      take(inventory, 38, list, InventorySection.CHEST);
      take(inventory, 39, list, InventorySection.HEAD);
      take(inventory, 40, list, InventorySection.OFFHAND);
      deathStorage.addInventory("vanilla", list);
    }
  }

  private static void take(PlayerInventory inventory, int index, ListNBT list,
      InventorySection section) {
    take(inventory, index, list,
        () -> InventoryModule.KEEP_SECTIONS.contains(section) ? InventoryRule.KEEP
            : InventoryRule.DROP);
  }

  private static void take(PlayerInventory inventory, int index, ListNBT list,
      Supplier<InventoryRule> ruleSupplier) {
    ItemStack stack = inventory.getStackInSlot(index);
    InventoryRule inventoryRule = InventoryUtils.getImperative(stack).orElseGet(ruleSupplier);

    if (inventoryRule == InventoryRule.KEEP) {
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putInt("Slot", index);
      stack.write(compoundnbt);
      list.add(compoundnbt);
    }

    if (inventoryRule != InventoryRule.DROP) {
      inventory.setInventorySlotContents(index, ItemStack.EMPTY);
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