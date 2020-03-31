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
import net.minecraftforge.registries.ObjectHolder;
import top.theillusivec4.corpsecomplex.CorpseComplex;

@ObjectHolder(CorpseComplex.MODID)
public class CorpseComplexRegistry {

  @ObjectHolder(RegistryReference.MEMENTO_MORI)
  public static final Effect MEMENTO_MORI;

  @ObjectHolder(RegistryReference.SOULBINDING)
  public static final Enchantment SOULBINDING;

  static {
    MEMENTO_MORI = null;
    SOULBINDING = null;
  }
}
