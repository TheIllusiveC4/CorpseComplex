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

package top.theillusivec4.corpsecomplex.common.util;

public class Enums {

  public enum PermissionMode {
    BLACKLIST, WHITELIST
  }

  public enum XpDropMode {
    PERCENT, PER_LEVEL
  }

  public enum DropMode {
    KEEP, DROP, DESTROY
  }

  public enum InventorySection {
    DEFAULT, MAINHAND, HOTBAR, OFFHAND, MAIN, HEAD, CHEST, LEGS, FEET, CURIOS, COSMETIC_ARMOR
  }

  public enum ModuleType {
    HUNGER, EXPERIENCE, INVENTORY, MISCELLANEOUS, MEMENTO_MORI, EFFECTS
  }
}
