package c4.corpsecomplex.common.modules.compatibility.thebetweenlands;

import c4.corpsecomplex.common.modules.inventory.InventoryModule;
import c4.corpsecomplex.common.modules.inventory.capability.IDeathInventory;
import c4.corpsecomplex.common.modules.inventory.enchantment.EnchantmentModule;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathInventoryHandler;
import c4.corpsecomplex.common.modules.inventory.helpers.DeathStackHelper;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import thebetweenlands.api.capability.IEquipmentCapability;
import thebetweenlands.common.capability.equipment.EnumEquipmentInventory;
import thebetweenlands.common.registries.CapabilityRegistry;

public class BetweenlandsHandler extends DeathInventoryHandler {

  private static final String MOD_ID = "thebetweenlands";
  private IEquipmentCapability betweenlandsEquipment;

  public BetweenlandsHandler(EntityPlayer player) {
    super(player, MOD_ID);
    betweenlandsEquipment = player.getCapability(CapabilityRegistry.CAPABILITY_EQUIPMENT, null);
  }

  @Override
  public boolean checkToStore(int slot) {
    return BetweenlandsModule.keepBetweenlands;
  }

  @Override
  public void storeInventory() {
    Map<EnumEquipmentInventory, IInventory> inventories = new EnumMap<>(
        EnumEquipmentInventory.class);

    if (betweenlandsEquipment == null) {
      return;
    }
    EnumEquipmentInventory.VALUES
        .forEach(value -> inventories.put(value, betweenlandsEquipment.getInventory(value)));
    Random generator = new Random();
    NBTTagCompound compound = new NBTTagCompound();
    inventories.forEach((equipment, inventory) -> {
      String name = equipment.toString();
      NonNullList<ItemStack> storedStacks = NonNullList
          .withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

      for (int i = 0; i < inventory.getSizeInventory(); i++) {
        ItemStack stack = inventory.getStackInSlot(i);

        if (stack.isEmpty()) {
          continue;
        }

        boolean essential = DeathStackHelper.isEssential(stack);
        boolean cursed = !essential && DeathStackHelper.isCursed(stack);
        boolean store = ((checkToStore(0) && !cursed) || essential);

        if (!store && EnchantmentModule.registerEnchant) {
          int level = EnchantmentHelper.getEnchantmentLevel(EnchantmentModule.soulbound, stack);

          if (level != 0) {
            store = essential = DeathStackHelper.handleSoulbound(stack, level);
            cursed = !essential && cursed;
          }
        }

        if (cursed && InventoryModule.destroyCursed) {
          inventory.setInventorySlotContents(i, ItemStack.EMPTY);
          continue;
        }

        if (InventoryModule.dropLoss > 0 || InventoryModule.keptLoss > 0) {
          DeathStackHelper.loseDurability(player, stack, store);
        }

        if (stack.isEmpty()) {
          return;
        }

        if (store) {

          if (!essential && generator.nextDouble() < InventoryModule.randomDrop) {

            if (generator.nextDouble() < InventoryModule.randomDestroy) {
              inventory.setInventorySlotContents(i, ItemStack.EMPTY);
              continue;
            }
            continue;
          }
          storedStacks.set(i, stack.copy());
          inventory.setInventorySlotContents(i, ItemStack.EMPTY);
        }
      }
      NBTTagCompound tag = new NBTTagCompound();
      ItemStackHelper.saveAllItems(tag, storedStacks);
      compound.setTag(name, tag);
    });
    deathInventory.addStorage(MOD_ID, compound);
  }

  @Override
  public void retrieveInventory(IDeathInventory oldDeathInventory) {
    NBTTagCompound storage = oldDeathInventory.getStorage(MOD_ID);

    if (storage != null && betweenlandsEquipment != null) {
      EnumEquipmentInventory.VALUES.forEach(value -> {
        NBTTagCompound tag = storage.getCompoundTag(value.toString());

        if (!tag.isEmpty()) {
          IInventory inventory = betweenlandsEquipment.getInventory(value);
          NonNullList<ItemStack> stored = NonNullList
              .withSize(inventory.getSizeInventory(), ItemStack.EMPTY);
          ItemStackHelper.loadAllItems(tag, stored);

          for (int i = 0; i < stored.size(); i++) {
            ItemStack stack = stored.get(i);

            if (!stack.isEmpty()) {
              inventory.setInventorySlotContents(i, stack);
            }
          }
        }
      });
    }
  }
}

