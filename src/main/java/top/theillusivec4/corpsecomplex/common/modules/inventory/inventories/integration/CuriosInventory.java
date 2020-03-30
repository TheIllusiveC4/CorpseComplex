package top.theillusivec4.corpsecomplex.common.modules.inventory.inventories.integration;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants.NBT;
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
import top.theillusivec4.curios.api.capability.ICurioItemHandler;

public class CuriosInventory implements Inventory {

  @Override
  public void storeInventory(IDeathStorage deathStorage) {
    PlayerEntity playerEntity = deathStorage.getPlayer();
    ListNBT list = new ListNBT();
    CuriosAPI.getCuriosHandler(playerEntity)
        .ifPresent(curioHandler -> curioHandler.getCurioMap().forEach((id, stackHandler) -> {
          ListNBT list1 = new ListNBT();
          for (int i = 0; i < stackHandler.getSlots(); i++) {
            take(curioHandler, id, i, list1, InventorySection.CURIOS,
                deathStorage.getSettings().getInventorySettings());
          }
          CompoundNBT tag = new CompoundNBT();
          tag.putString("Identifier", id);
          tag.put("Stacks", list1);
          list.add(tag);
        }));
    deathStorage.addInventory("curios", list);
  }

  private static void take(ICurioItemHandler itemHandler, String id, int index, ListNBT list,
      InventorySection section, InventorySetting setting) {
    ItemStack stack = itemHandler.getStackInSlot(id, index);
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
      InventoryHelper.applyDurabilityLoss((PlayerEntity) itemHandler.getWearer(), stack, setting,
          sectionSettings.keepDurabilityLoss);
      ItemStack keep = stack.split(sectionSettings.keepChance >= 1 ? stack.getCount()
          : InventoryHelper.getRandomAmount(stack.getCount(), sectionSettings.keepChance));
      CompoundNBT compoundnbt = new CompoundNBT();
      compoundnbt.putInt("Slot", index);
      keep.write(compoundnbt);
      list.add(compoundnbt);
    } else if (inventoryRule == DropMode.DESTROY) {
      itemHandler.setStackInSlot(id, index, ItemStack.EMPTY);
    } else {
      InventoryHelper.applyDurabilityLoss((PlayerEntity) itemHandler.getWearer(), stack, setting,
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
      ListNBT list = (ListNBT) oldStorage.getInventory("curios");
      CuriosAPI.getCuriosHandler(player).ifPresent(newHandler -> {
        for (int i = 0; i < list.size(); i++) {
          CompoundNBT tag = list.getCompound(i);
          String id = tag.getString("Identifier");
          ListNBT stacks = tag.getList("Stacks", NBT.TAG_COMPOUND);
          for (int j = 0; j < stacks.size(); j++) {
            CompoundNBT compoundnbt = stacks.getCompound(j);
            int slot = compoundnbt.getInt("Slot");
            ItemStack itemstack = ItemStack.read(compoundnbt);
            if (!itemstack.isEmpty()) {
              ItemStack existing = newHandler.getStackInSlot(id, slot);

              if (existing.isEmpty()) {
                newHandler.setStackInSlot(id, slot, itemstack);
                CuriosAPI.getCurio(itemstack).ifPresent((curio) -> {
                  player.getAttributes().applyAttributeModifiers(curio.getAttributeModifiers(id));
                  curio.onEquipped(id, player);
                });
              } else {
                ItemHandlerHelper.giveItemToPlayer(player, itemstack);
              }
            }
          }
        }
      });
    }
  }
}