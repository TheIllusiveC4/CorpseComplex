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

    protected boolean canApplyTogether(Enchantment ench)
    {
        return this != ench && ench != Enchantments.VANISHING_CURSE;
    }

    public int getMinEnchantability(int enchantmentLevel)
    {
        return super.getMinEnchantability(enchantmentLevel);
    }

    public int getMaxEnchantability(int enchantmentLevel)
    {
        return super.getMaxEnchantability(enchantmentLevel) + 30;
    }

    public int getMaxLevel()
    {
        return 1;
    }

    public boolean isTreasureEnchantment()
    {
        return !EnchantmentModule.canApplyEnchantingTable;
    }

}
