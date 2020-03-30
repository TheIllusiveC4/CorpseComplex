package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration;

import lain.mods.cos.api.CosArmorAPI;
import lain.mods.cos.api.inventory.CAStacksBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventoryModule;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting;
import top.theillusivec4.corpsecomplex.common.modules.inventory.InventorySetting.SectionSettings;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.Inventory;
import top.theillusivec4.corpsecomplex.common.util.Enums.DropMode;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.InventoryHelper;
import top.theillusivec4.curios.api.CuriosAPI;

public class CosmeticArmorInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity playerEntity = deathStorage.getPlayer();
    CAStacksBase stacks = CosArmorAPI.getCAStacks(playerEntity.getUniqueID());
    ListNBT list = new ListNBT();

    for (int i = 0; i < stacks.getSlots(); ++i) {
      ItemStack stack = stacks.getStackInSlot(i);
      if (!stack.isEmpty()) {
        take(playerEntity, stacks, i, list, InventorySection.COSMETIC_ARMOR,
            deathStorage.getSettings().getInventorySettings());
      }
    }
    deathStorage.addInventory("cosmeticarmorreworked", list);
  }

  private static void take(PlayerEntity player, CAStacksBase stacks, int index, ListNBT list,
      InventorySection section, InventorySetting setting) {
    ItemStack stack = stacks.getStackInSlot(index);
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
      InventoryHelper
          .applyDurabilityLoss(player, stack, setting, sectionSettings.keepDurabilityLoss);
      ItemStack keep = stack.split(sectionSettings.keepChance >= 1 ? stack.getCount()
          : InventoryHelper.getRandomAmount(stack.getCount(), sectionSettings.keepChance));
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putInt("Slot", index);
      keep.write(compoundnbt);
      list.add(compoundnbt);
    } else if (inventoryRule == DropMode.DESTROY) {
      stack.shrink(stack.getCount());
    } else {
      InventoryHelper
          .applyDurabilityLoss(player, stack, setting, sectionSettings.dropDurabilityLoss);
      stack
          .shrink(InventoryHelper.getRandomAmount(stack.getCount(), sectionSettings.destroyChance));
    }
  }

  @Override
  public void retrieveInventory(IDeathStorage newStorage, IDeathStorage oldStorage) {
    PlayerEntity player = newStorage.getPlayer();
    PlayerEntity oldPlayer = oldStorage.getPlayer();

    if (player != null && oldPlayer != null) {
      ListNBT tagList = (ListNBT) oldStorage.getInventory("cosmeticarmorreworked");
      CAStacksBase stacks = CosArmorAPI.getCAStacks(player.getUniqueID());

      for (int i = 0; i < tagList.size(); ++i) {
        CompoundNBT tag = tagList.getCompound(i);
        int slot = tag.getInt("Slot");
        ItemStack itemstack = ItemStack.read(tag);
        if (!itemstack.isEmpty()) {
          ItemStack existing = stacks.getStackInSlot(slot);

          if (existing.isEmpty()) {
            stacks.setStackInSlot(slot, itemstack);
          } else {
            ItemHandlerHelper.giveItemToPlayer(player, itemstack);
          }
        }
      }
    }
  }
}
