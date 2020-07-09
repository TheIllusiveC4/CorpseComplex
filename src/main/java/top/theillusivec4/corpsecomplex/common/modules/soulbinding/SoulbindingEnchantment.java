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

package top.theillusivec4.corpsecomplex.common.modules.soulbinding;

import javax.annotation.Nonnull;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import top.theillusivec4.corpsecomplex.common.config.CorpseComplexConfig;
import top.theillusivec4.corpsecomplex.common.registry.RegistryReference;

public class SoulbindingEnchantment extends Enchantment {

  public SoulbindingEnchantment() {
    super(Rarity.VERY_RARE, EnchantmentType.create("ANY", (item) -> true),
        EquipmentSlotType.values());
    this.setRegistryName(RegistryReference.SOULBINDING);
  }

  @Nonnull
  @Override
  public Rarity getRarity() {
    return CorpseComplexConfig.rarity != null ? CorpseComplexConfig.rarity : super.getRarity();
  }

  @Override
  public int getMaxLevel() {
    return CorpseComplexConfig.maxSoulbindingLevel > 0 ? CorpseComplexConfig.maxSoulbindingLevel
        : super.getMaxLevel();
  }

  @Override
  public int getMinEnchantability(int enchantmentLevel) {
    return 1 + 10 * (enchantmentLevel - 1);
  }

  @Override
  public int getMaxEnchantability(int enchantmentLevel) {
    return super.getMinEnchantability(enchantmentLevel) + 50;
  }

  @Override
  protected boolean canApplyTogether(Enchantment ench) {
    ResourceLocation rl = ench.getRegistryName();
    boolean isSoulbound = false;

    if (rl != null) {
      isSoulbound = rl.getPath().equals("soulbound");
    }
    return !isSoulbound && ench != Enchantments.VANISHING_CURSE;
  }

  @Override
  public boolean isTreasureEnchantment() {
    return CorpseComplexConfig.isTreasure;
  }

  @Override
  public boolean canApplyAtEnchantingTable(ItemStack stack) {
    return CorpseComplexConfig.canApplyEnchantingTable;
  }

  @Override
  public boolean isAllowedOnBooks() {
    return CorpseComplexConfig.allowedOnBooks;
  }
}
