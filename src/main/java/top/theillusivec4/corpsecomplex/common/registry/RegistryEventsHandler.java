/*
 * Copyright (c) 2017-2020 C4
 *
 * This file is part of Corpse Complex, a mod made for Minecraft.
 *
 * Corpse Complex is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Corpse Complex is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Corpse Complex.  If not, see <https://www.gnu.org/licenses/>.
 */

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
