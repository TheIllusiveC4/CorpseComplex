/*
 * Copyright (c) 2017. C4, MIT License
 */

package c4.corpsecomplex.common.modules.inventory.enchantment;

import c4.corpsecomplex.CorpseComplex;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;

public class EnchantmentSoulbound extends Enchantment {

    public EnchantmentSoulbound(Enchantment.Rarity rarity) {
        super(rarity, EnumEnchantmentType.ALL, EntityEquipmentSlot.values());
        this.setName(CorpseComplex.MODID + ".soulbound");
        this.setRegistryName("soulbound");
    }

    public boolean canApplyTogether(Enchantment ench)
    {
        return this != ench;
    }

    public int getMinEnchantability(int enchantmentLevel)
    {
        return 1 + 10 * (enchantmentLevel - 1);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel()
    {
        return EnchantmentModule.maxLevel;
    }

    public boolean isTreasureEnchantment()
    {
        return !EnchantmentModule.canApplyEnchantingTable;
    }

    public boolean isAllowedOnBooks() { return EnchantmentModule.allowedOnBooks; }

}
