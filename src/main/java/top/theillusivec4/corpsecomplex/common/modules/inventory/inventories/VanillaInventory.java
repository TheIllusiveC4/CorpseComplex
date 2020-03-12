package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.CorpseComplexConfig.InventorySection;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryModule;

public class VanillaInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity player = deathStorage.getPlayer();

    if (player != null) {
      PlayerInventory inventory = player.inventory;
      ListNBT list = new ListNBT();

      for (int i = 0; i < inventory.mainInventory.size(); ++i) {

        if ((i == inventory.currentItem && InventoryModule.KEEP.contains(InventorySection.MAINHAND)
            || (i != inventory.currentItem && i < 9 && InventoryModule.KEEP
            .contains(InventorySection.HOTBAR)) || (i >= 9 && i < 36 && InventoryModule.KEEP
            .contains(InventorySection.MAIN)))) {
          ItemStack stack = inventory.mainInventory.get(i);

          if (!stack.isEmpty()) {
            CompoundNBT compoundnbt = new CompoundNBT();
            compoundnbt.putByte("Slot", (byte) i);
            inventory.mainInventory.get(i).write(compoundnbt);
            inventory.mainInventory.set(i, ItemStack.EMPTY);
            list.add(compoundnbt);
          }
        }

      }

      if (InventoryModule.KEEP.contains(InventorySection.ARMOR)) {
        take(inventory.armorInventory, 100, list);
      }

      if (InventoryModule.KEEP.contains(InventorySection.OFFHAND)) {
        take(inventory.offHandInventory, 150, list);
      }
      deathStorage.addInventory("vanilla", list);
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
        int slot = compoundnbt.getByte("Slot") & 255;
        ItemStack itemstack = ItemStack.read(compoundnbt);
        if (!itemstack.isEmpty()) {
          if (slot < inventory.mainInventory.size()) {
            setOrGive(player, inventory.mainInventory, slot, itemstack);
          } else if (slot >= 100 && slot < inventory.armorInventory.size() + 100) {
            setOrGive(player, inventory.armorInventory, slot - 100, itemstack);
          } else if (slot >= 150 && slot < inventory.offHandInventory.size() + 150) {
            setOrGive(player, inventory.offHandInventory, slot - 150, itemstack);
          }
        }
      }
    }
  }

  private static void take(NonNullList<ItemStack> inventory, int offset, ListNBT list) {

    for (int i = 0; i < inventory.size(); ++i) {

      if (!inventory.get(i).isEmpty()) {
        CompoundNBT compoundnbt = new CompoundNBT();
        compoundnbt.putByte("Slot", (byte) (i + offset));
        inventory.get(i).write(compoundnbt);
        inventory.set(i, ItemStack.EMPTY);
        list.add(compoundnbt);
      }
    }
  }

  private static void setOrGive(PlayerEntity player, NonNullList<ItemStack> inventory, int slot,
      ItemStack stack) {
    ItemStack existing = inventory.get(slot);

    if (existing.isEmpty()) {
      inventory.set(slot, stack);
    } else {
      ItemHandlerHelper.giveItemToPlayer(player, stack);
    }
  }
}
