package top.theillusivec4.corpsecomplex.common.modules.inventory;

import java.util.Optional;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class InventoryUtils {

  public static Optional<InventoryRule> getImperative(ItemStack stack) {
    Item item = stack.getItem();

    if (InventoryModule.ESSENTIAL_ITEMS.contains(item)) {
      return Optional.of(InventoryRule.KEEP);
    } else if (InventoryModule.CURSED_ITEMS.containsKey(item)) {

      if (InventoryModule.CURSED_ITEMS.get(item)) {
        
        return Optional.of(InventoryRule.DESTROY);
      }
      return Optional.of(InventoryRule.DROP);
    }
    return Optional.empty();
  }

  public enum InventoryRule {
    KEEP, DROP, DESTROY
  }
}
