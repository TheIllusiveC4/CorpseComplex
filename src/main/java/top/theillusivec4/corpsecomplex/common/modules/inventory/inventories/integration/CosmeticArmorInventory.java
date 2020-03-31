package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration;

import lain.mods.cos.api.CosArmorAPI;
import lain.mods.cos.api.inventory.CAStacksBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.items.ItemHandlerHelper;
import top.theillusivec4.corpsecomplex.common.capability.DeathStorageCapability.IDeathStorage;
import top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.Inventory;
import top.theillusivec4.corpsecomplex.common.util.Enums.InventorySection;
import top.theillusivec4.corpsecomplex.common.util.InventoryHelper;

public class CosmeticArmorInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity playerEntity = deathStorage.getPlayer();
    CAStacksBase stacks = CosArmorAPI.getCAStacks(playerEntity.getUniqueID());
    ListNBT list = new ListNBT();

    for (int i = 0; i < stacks.getSlots(); ++i) {
      ItemStack stack = stacks.getStackInSlot(i);
      if (!stack.isEmpty()) {
        InventoryHelper.process(playerEntity, stacks.getStackInSlot(i), i, list,
            InventorySection.COSMETIC_ARMOR, deathStorage.getSettings().getInventorySettings());
      }
    }
    deathStorage.addInventory("cosmeticarmorreworked", list);
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
