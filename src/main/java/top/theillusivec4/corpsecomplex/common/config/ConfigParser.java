package top.theillusivec4.corpsecomplex.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class ConfigParser {

  public static Map<Item, Integer> parseItems(List<? extends String> configList) {
    Map<Item, Integer> map = new HashMap<>();
    configList.forEach(item -> {
      String[] parsed = item.split(";");
      Item item1 = ForgeRegistries.ITEMS.getValue(new ResourceLocation(parsed[0]));

      if (item1 != null) {
        int amount = parsed.length > 1 ? Integer.parseInt(parsed[1]) : 1;
        map.put(item1, amount);
      }
    });
    return map;
  }

  public static List<Effect> parseEffects(List<? extends String> configList) {
    List<Effect> list = new ArrayList<>();
    configList.forEach(effect -> {
      Effect effect1 = ForgeRegistries.POTIONS.getValue(new ResourceLocation(effect));

      if (effect1 != null) {
        list.add(effect1);
      }
    });
    return list;
  }

  public static List<EffectInstance> parseEffectInstances(List<? extends String> configList,
      List<ItemStack> cures) {
    List<EffectInstance> list = new ArrayList<>();
    configList.forEach(instance -> {
      String[] parse = instance.split(";");

      if (parse.length >= 2) {
        Effect effect1 = ForgeRegistries.POTIONS.getValue(new ResourceLocation(parse[0]));

        if (effect1 != null) {
          int amplifier = parse.length >= 3 ? Integer.parseInt(parse[2]) : 0;
          EffectInstance instance1 = new EffectInstance(effect1, Integer.parseInt(parse[1]) * 20,
              amplifier);

          if (parse.length >= 4) {
            instance1.setCurativeItems(new ArrayList<>());
          } else {
            instance1.setCurativeItems(cures);
          }
          list.add(instance1);
        }
      }
    });
    return list;
  }
}
