package top.theillusivec4.corpsecomplex.common.registry;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Effect;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.corpsecomplex.common.modules.mementomori.MementoMoriEffect;
import top.theillusivec4.corpsecomplex.common.modules.soulbinding.SoulbindingEnchantment;

@EventBusSubscriber(bus = Bus.MOD)
public class RegistryEventsHandler {

  @SubscribeEvent
  public static void registerEnchantments(final RegistryEvent.Register<Enchantment> evt) {
    evt.getRegistry().register(new SoulbindingEnchantment());
  }

  @SubscribeEvent
  public static void registerEffects(final RegistryEvent.Register<Effect> evt) {
    evt.getRegistry().register(new MementoMoriEffect());
  }
}
