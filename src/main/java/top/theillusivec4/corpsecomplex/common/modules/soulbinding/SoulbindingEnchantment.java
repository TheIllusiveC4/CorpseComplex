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
    super(Rarity.VERY_RARE, EnchantmentType.ALL, EquipmentSlotType.values());
    this.setRegistryName(RegistryReference.SOULBINDING);
  }

  @Nonnull
  @Override
  public Rarity getRarity() {
    return CorpseComplexConfig.rarity;
  }

  @Override
  public int getMaxLevel() {
    return CorpseComplexConfig.maxSoulbindingLevel;
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
